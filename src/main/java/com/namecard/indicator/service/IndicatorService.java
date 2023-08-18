package com.namecard.indicator.service;

import com.namecard.indicator.domain.IndicatorConnectRepository;
import com.namecard.indicator.domain.IndicatorRepository;
import com.namecard.indicator.dto.entity.Indicator;
import com.namecard.indicator.dto.entity.IndicatorConnect;
import com.namecard.indicator.dto.request.IndicatorRequest;
import com.namecard.indicator.dto.request.IndicatorRequest.IndicatorTag;
import com.namecard.indicator.dto.result.IndicatorInfoResult;
import com.namecard.indicator.dto.result.IndicatorInfoResult.IndicatorScoreResult;
import com.namecard.indicator.dto.result.MyIndicatorInfoResult;
import com.namecard.member.domain.MemberRepository;
import com.namecard.member.dto.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;
    private final IndicatorConnectRepository indicatorConnectRepository;
    private final MemberRepository memberRepository;

    @Transactional(rollbackFor = Exception.class)
    public void indicatorSave(IndicatorRequest dto) {
        long userId = dto.getUserId();
        indicatorConnectRepository.deleteByUserId(userId);
        for(IndicatorTag tag : dto.getTagList()) {
            IndicatorConnect entity = IndicatorConnect.builder()
                    .userId(userId)
                    .tagId(tag.getTagId())
                    .build();
            indicatorConnectRepository.save(entity);
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

    @Transactional(readOnly = true)
    public List<MyIndicatorInfoResult> getMyIndicatorInfo(long userId) {
        return indicatorRepository.findIndicatorInfoByUserId(userId);
    }
}
