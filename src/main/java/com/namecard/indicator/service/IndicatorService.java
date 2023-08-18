package com.namecard.indicator.service;

import com.namecard.exception.NotFoundException;
import com.namecard.indicator.domain.IndicatorConnectRepository;
import com.namecard.indicator.domain.IndicatorRepository;
import com.namecard.indicator.dto.entity.IndicatorConnect;
import com.namecard.indicator.dto.request.IndicatorRequest;
import com.namecard.indicator.dto.request.IndicatorRequest.IndicatorTag;
import com.namecard.indicator.dto.result.IndicatorInfoResult;
import com.namecard.indicator.dto.result.IndicatorInfoResult.IndicatorScoreResult;
import com.namecard.indicator.dto.result.IndicatorListResult;
import com.namecard.indicator.dto.result.MyIndicatorInfoResult;
import com.namecard.users.domain.UsersRepository;
import com.namecard.users.dto.entity.Users;
import com.namecard.tag.domain.TagRepository;
import com.namecard.tag.dto.entity.Tag;
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
    private final UsersRepository memberRepository;
    private final TagRepository tagRepository;

    @Transactional(rollbackFor = Exception.class)
    public void indicatorSave(IndicatorRequest dto) {
        long userId = dto.getUserId();
        indicatorConnectRepository.deleteByUserId(userId);
        for (IndicatorTag tag : dto.getTagList()) {
            IndicatorConnect entity = IndicatorConnect.builder()
                    .userId(userId)
                    .tagId(tag.getTagId())
                    .build();
            indicatorConnectRepository.save(entity);
        }
    }

    @Transactional(readOnly = true)
    public List<MyIndicatorInfoResult> getMyIndicatorInfo(long userId) {
        return indicatorRepository.findIndicatorInfoByUserId(userId);
    }

    @Transactional(readOnly = true)
    public IndicatorInfoResult getIndicatorInfo(long userId) {
        Users users = memberRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다.")
        );
        List<IndicatorScoreResult> scoreList = indicatorRepository.findIndicatorScoreByUserId(userId);

        IndicatorInfoResult result = IndicatorInfoResult.builder()
                .email(users.getEmail())
                .userName(users.getUserName())
                .phoneNum(users.getPhoneNum())
                .introduce(users.getIntroduce())
                .scoreList(scoreList)
                .build();
        return result;
    }

    @Transactional(readOnly = true)
    public List<IndicatorListResult> getUserIndicatorsForFeedback(Long userId) {
        Users users = memberRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다.")
        );
        List<IndicatorConnect> indicatorConnects = indicatorConnectRepository.findIndicatorConnectsByUserId(userId);

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
