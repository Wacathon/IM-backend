package com.namecard.indicator.service;

import com.namecard.exception.NotFoundException;
import com.namecard.indicator.repository.IndicatorConnectRepository;
import com.namecard.indicator.repository.IndicatorRepository;
import com.namecard.indicator.domain.IndicatorConnect;
import com.namecard.indicator.dto.request.IndicatorRequest;
import com.namecard.indicator.dto.request.IndicatorRequest.IndicatorTag;
import com.namecard.indicator.dto.result.IndicatorInfoResult;
import com.namecard.indicator.dto.result.IndicatorInfoResult.IndicatorScoreResult;
import com.namecard.indicator.dto.result.IndicatorListResult;
import com.namecard.indicator.dto.result.MyIndicatorInfoResult;
import com.namecard.member.repository.MemberRepository;
import com.namecard.member.domain.Member;
import com.namecard.tag.repository.TagRepository;
import com.namecard.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;
    private final IndicatorConnectRepository indicatorConnectRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    @Transactional(rollbackFor = Exception.class)
    public void indicatorSave(IndicatorRequest dto) {
        long userId = dto.getUserId();
        indicatorConnectRepository.deleteByMemberId(userId);
        for (IndicatorTag tag : dto.getTagList()) {
            IndicatorConnect entity = IndicatorConnect.builder()
                    .memberId(userId)
                    .tagId(tag.getTagId())
                    .build();
            indicatorConnectRepository.save(entity);
        }
    }

    @Transactional(readOnly = true)
    public List<MyIndicatorInfoResult> getMyIndicatorInfo(long userId) {
        return indicatorRepository.findIndicatorInfoByMemberId(userId);
    }

    @Transactional(readOnly = true)
    public IndicatorInfoResult getIndicatorInfo(long userId) {
        Member member = memberRepository.findByMemberId(userId).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다.")
        );
        List<IndicatorScoreResult> scoreList = indicatorRepository.findIndicatorScoreByMemberId(userId);

        IndicatorInfoResult result = IndicatorInfoResult.builder()
                .email(member.getEmail())
                .userName(member.getUserName())
                .phoneNum(member.getCard().getPhoneNumber())
                .introduce(member.getCard().getIntroduce())
                .scoreList(scoreList)
                .build();
        return result;
    }

    @Transactional(readOnly = true)
    public List<IndicatorListResult> getUserIndicatorsForFeedback(Long userId) {
        Member member = memberRepository.findByMemberId(userId).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다.")
        );
        List<IndicatorConnect> indicatorConnects = indicatorConnectRepository.findIndicatorConnectsByMemberId(userId);

        List<IndicatorListResult> results = new ArrayList<>();

        for (IndicatorConnect indicatorConnect : indicatorConnects) {
            Optional<Tag> optionalTag = tagRepository.findById(indicatorConnect.getTagId());
            if (optionalTag.isEmpty())
                throw new NotFoundException("존재하지 않는 태그입니다.");

            Tag tag = optionalTag.get();
            results.add(IndicatorListResult.builder()
                    .tagId(tag.getTagId())
                    .tagName(tag.getTagName())
                    .build());
        }
        return results;
    }
}
