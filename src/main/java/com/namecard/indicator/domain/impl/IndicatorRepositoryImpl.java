package com.namecard.indicator.domain.impl;

import com.namecard.indicator.domain.IndicatorQuerydslRepository;
import com.namecard.indicator.dto.entity.QIndicator;
import com.namecard.indicator.dto.result.IndicatorInfoResult.IndicatorScoreResult;
import com.namecard.tag.dto.entity.QTag;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class IndicatorRepositoryImpl implements IndicatorQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<IndicatorScoreResult> findIndicatorScoreByUserId(long userId) {
        QIndicator qindicator = QIndicator.indicator;
        QTag qTag = QTag.tag;
        return jpaQueryFactory.select(Projections.constructor(IndicatorScoreResult.class,
                        qindicator.indicatorId,
                        qTag.tagName,
                        qindicator.tagScore))
                .from(qindicator)
                .innerJoin(qTag).on(qindicator.tagId.eq(qTag.tagId))
                .where(qindicator.userId.eq(userId), qindicator.feedbackId.eq(0L))
                .fetch();
    }
}
