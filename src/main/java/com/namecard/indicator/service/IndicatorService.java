package com.namecard.indicator.service;

import com.namecard.indicator.domain.IndicatorRepository;
import com.namecard.indicator.dto.entity.Indicator;
import com.namecard.indicator.dto.request.IndicatorRequest;
import com.namecard.indicator.dto.result.IndicatorInfoResult;
import com.namecard.indicator.dto.result.IndicatorInfoResult.IndicatorScoreResult;
import com.namecard.member.domain.MemberRepository;
import com.namecard.member.dto.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;
    private final MemberRepository memberRepository;

    public void indicatorSave(List<IndicatorRequest> list, long userId) {
        for(IndicatorRequest request : list) {
            Indicator entity = Indicator.builder()
                    .userId(userId)
                    .tagId(request.getTagId())
                    .tagScore(request.getTagScore())
                    .build();
            indicatorRepository.save(entity);
        }
    }

    public IndicatorInfoResult getIndicatorInfo(long userId) {
        Users users = memberRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다.")
        );

        List<IndicatorScoreResult> myScorelist = indicatorRepository.findIndicatorScoreByUserId(userId);
        /**
         * TODO
         * FEEDBACK 추가 이후 피드백 데이터 추가 필요
         * */
//        List<IndicatorScoreResult> friendsScoreList = indicatorRepository.findIndicatorScoreByUserId(userId);

        IndicatorInfoResult result = IndicatorInfoResult.builder()
                .email(users.getEmail())
                .userName(users.getUserName())
                .phoneNum(users.getPhoneNum())
                .introduce(users.getIntroduce())
                .myScoreList(myScorelist)
//                .friendsScoreList(friendsScoreList)
                .build();
        return result;
    }
}
