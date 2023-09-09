package com.namecard.member.service;

import com.namecard.exception.UnauthorizedException;
import com.namecard.member.domain.Card;
import com.namecard.member.domain.Member;
import com.namecard.member.dto.request.JoinRequest;
import com.namecard.member.dto.request.LoginRequest;
import com.namecard.member.dto.request.MyProfileRequest;
import com.namecard.member.dto.request.PasswdResetRequest;
import com.namecard.member.dto.result.LoginResult;
import com.namecard.member.repository.MemberRepository;
import com.namecard.member.dto.result.MyProfileResult;
import com.namecard.redis.RedisService;
import com.namecard.search.dto.result.SearchUsersResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    public void signUp(JoinRequest joinRequest) {
        this.validateSignUp(joinRequest);

        String encodePasswd = passwordEncoder.encode(joinRequest.getPasswd());
        joinRequest.setEncodePasswd(encodePasswd);

        Card card = Card.builder()
                .isPublicEmail(true)
                .isPublicIntroduce(true)
                .isPublicPhone(true)
                .cardEmail(null)
                .phoneNumber(joinRequest.getPhoneNum())
                .introduce(joinRequest.getIntroduce())
                .build();

        Member member = Member.builder()
                .email(joinRequest.getEmail())
                .userName(joinRequest.getName())
                .passwd(encodePasswd)
                .card(card)
                .build();

        memberRepository.save(member);

        redisService.delete(joinRequest.getPhoneNum());
    }

    private void validateSignUp(JoinRequest joinRequest) {
        List<Member> members = memberRepository.findByEmailOrPhoneNum(joinRequest.getEmail(), joinRequest.getPhoneNum());
        for (Member member : members) {
            if (member.getEmail().equals(joinRequest.getEmail())) {
                throw new IllegalArgumentException("중복된 이메일이 있습니다.");
            }

            if (member.getCard().getPhoneNumber().equals(joinRequest.getPhoneNum())) {
                throw new IllegalArgumentException("중복된 전화번호가 있습니다.");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult signIn(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디와 패스워드를 다시 확인 바랍니다."));

        if (!passwordEncoder.matches(loginRequest.getPasswd(), member.getPasswd())) {
            throw new IllegalArgumentException("아이디와 패스워드를 다시 확인 바랍니다.");
        }

        member.afterLoginSuccess();
        return redisService.createNewTokens(member);
    }

    public MyProfileResult getMyProfile(String strMemberId) {
        Long memberId = convertPrimaryKeyType(strMemberId);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        return MyProfileResult.builder()
                .memberId(memberId)
                .email(member.getCard().getCardEmail())
                .name(member.getUserName())
                .phoneNum(member.getCard().getPhoneNumber())
                .introduce(member.getCard().getIntroduce())
                .isPublicEmail(member.getCard().isPublicEmail())
                .isPublicPhone(member.getCard().isPublicPhone())
                .isPublicIntroduce(member.getCard().isPublicIntroduce())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetNewPassword(PasswdResetRequest request, String strMemberId) {
        Long memberId = convertPrimaryKeyType(strMemberId);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

        if (!passwordEncoder.matches(request.getOriginPasswd(), member.getPasswd())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        if (request.getOriginPasswd().equals(request.getNewPasswd())) {
            throw new IllegalArgumentException("동일한 비밀번호입니다.");
        }

        String encodePasswd = passwordEncoder.encode(request.getNewPasswd());
        member.updatePassword(encodePasswd);
    }

    @Transactional
    public void changeMyProfile(MyProfileRequest profileRequest, String strMemberId) {
        Long memberId = convertPrimaryKeyType(strMemberId);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

        member.getCard().updateCard(profileRequest);
    }

    public List<SearchUsersResult> getSearchUsers(String userName) {
        return memberRepository.findLikeUserName(userName);
    }

    private Long convertPrimaryKeyType(String strMemberId) {
        try {
            return Long.parseLong(strMemberId);
        } catch (Exception e) {
            throw new UnauthorizedException("토큰이 필요합니다.");
        }
    }
}
