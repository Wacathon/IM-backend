package com.namecard.indicator.service;

import com.namecard.indicator.domain.IndicatorRepository;
import com.namecard.indicator.dto.entity.Indicator;
import com.namecard.indicator.dto.request.IndicatorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;

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
}
