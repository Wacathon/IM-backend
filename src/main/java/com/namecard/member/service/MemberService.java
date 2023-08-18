package com.namecard.member.service;

import com.namecard.config.JwtConfig;
import com.namecard.member.dto.entity.Users;
import com.namecard.member.dto.request.JoinRequest;
import com.namecard.member.dto.request.LoginRequest;
import com.namecard.member.dto.request.PasswdResetRequest;
import com.namecard.member.dto.result.LoginResult;
import com.namecard.member.domain.MemberRepository;
import com.namecard.member.dto.result.MyProfileResult;
import com.namecard.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final JwtConfig jwtConfig;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    public void join(JoinRequest joinRequest) {
        this.validJoin(joinRequest);
        String encodePasswd = passwordEncoder.encode(joinRequest.getPasswd());
        joinRequest.setEncodePasswd(encodePasswd);

        Users users = Users.builder()
                .email(joinRequest.getEmail())
                .userName(joinRequest.getName())
                .phoneNum(joinRequest.getPhoneNum())
                .passwd(encodePasswd)
                .build();


        memberRepository.save(users);
        redisService.delete(joinRequest.getPhoneNum());
    }

    private void validJoin(JoinRequest joinRequest) {
        List<Users> members = memberRepository.findByEmailOrPhoneNum(joinRequest.getEmail(), joinRequest.getPhoneNum());
        for(Users usersEntity : members) {
            if(usersEntity.getEmail().equals(joinRequest.getEmail())) {
                throw new IllegalArgumentException("중복된 이메일이 있습니다.");
            }

            if(usersEntity.getPhoneNum().equals(joinRequest.getPhoneNum())) {
                throw new IllegalArgumentException("중복된 전화번호가 있습니다.");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult login(LoginRequest loginRequest) {
        Users users = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디와 패스워드를 다시 확인 바랍니다."));

        if (!passwordEncoder.matches(loginRequest.getPasswd(), users.getPasswd())) {
            throw new IllegalArgumentException("아이디와 패스워드를 다시 확인 바랍니다.");
        }

        users.afterLoginSuccess();
        memberRepository.save(users);

        return redisService.tokenCreate(users);
    }

    public MyProfileResult myProfile(long memberNo) {
        Users users = memberRepository.findByUserId(memberNo).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다.")
        );

        return MyProfileResult.builder()
                .email(users.getEmail())
                .name(users.getUserName())
                .phoneNum(users.getPhoneNum())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void passwdReset(PasswdResetRequest request) {
        Users usersEntity = this.validPasswdReset(request);
        String encodePasswd = passwordEncoder.encode(request.getNewPasswd());
        usersEntity.updatePassword(encodePasswd);
        memberRepository.save(usersEntity);
    }

    private Users validPasswdReset(PasswdResetRequest request) {
        Users usersEntity = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("이메일 정보가 없습니다."));

        return usersEntity;
    }
}
