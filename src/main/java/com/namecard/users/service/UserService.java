package com.namecard.users.service;

import com.namecard.users.dto.entity.Users;
import com.namecard.users.dto.request.JoinRequest;
import com.namecard.users.dto.request.LoginRequest;
import com.namecard.users.dto.request.MyProfileRequest;
import com.namecard.users.dto.request.PasswdResetRequest;
import com.namecard.users.dto.result.LoginResult;
import com.namecard.users.domain.UsersRepository;
import com.namecard.users.dto.result.MyProfileResult;
import com.namecard.redis.RedisService;
import com.namecard.search.dto.result.SearchUsersResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository memberRepository;
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
                .introduce(joinRequest.getIntroduce())
                .build();

        memberRepository.save(users);
        redisService.delete(joinRequest.getPhoneNum());
    }

    private void validJoin(JoinRequest joinRequest) {
        List<Users> members = memberRepository.findByEmailOrPhoneNum(joinRequest.getEmail(), joinRequest.getPhoneNum());
        for (Users usersEntity : members) {
            if (usersEntity.getEmail().equals(joinRequest.getEmail())) {
                throw new IllegalArgumentException("중복된 이메일이 있습니다.");
            }

            if (usersEntity.getPhoneNum().equals(joinRequest.getPhoneNum())) {
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
                .introduce(users.getIntroduce())
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

    public void changeMyProfile(MyProfileRequest profileRequest, long userId) {
        Users entity = memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
        entity.updateUsers(profileRequest);
        memberRepository.save(entity);
    }

    public List<SearchUsersResult> getSearchUsers(String userName) {
        return memberRepository.findLikeUserName(userName);
    }
}
