package com.namecard.indicator.domain.impl;

import com.namecard.indicator.domain.IndicatorQuerydslRepository;
import com.namecard.indicator.dto.entity.QIndicator;
import com.namecard.indicator.dto.entity.QIndicatorConnect;
import com.namecard.indicator.dto.result.IndicatorInfoResult.IndicatorScoreResult;
import com.namecard.indicator.dto.result.MyIndicatorInfoResult;
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
        QIndicatorConnect qIndicatorConnect = QIndicatorConnect.indicatorConnect;
        return jpaQueryFactory.select(Projections.constructor(IndicatorScoreResult.class,
                        qTag.tagName,
                        qindicator.tagScore.avg()))
                .from(qIndicatorConnect)
                .innerJoin(qTag)
                    .on(qIndicatorConnect.tagId.eq(qTag.tagId))
                .leftJoin(qindicator)
                    .on(qIndicatorConnect.tagId.eq(qindicator.tagId)
                    .and(qIndicatorConnect.userId.eq(qindicator.userId)))
                .where(qIndicatorConnect.userId.eq(userId))
                .groupBy(qTag.tagName)
                .fetch();
    }

    @Override
    public List<MyIndicatorInfoResult> findIndicatorInfoByUserId(long userId) {
        QIndicator qIndicator = QIndicator.indicator;
        QIndicatorConnect qIndicatorConnect = QIndicatorConnect.indicatorConnect;
        QTag qTag = QTag.tag;
        return jpaQueryFactory.select(Projections.constructor(MyIndicatorInfoResult.class,
                        qIndicatorConnect.tagId,
                        qTag.tagName,
                        qIndicator.tagScore.avg()))
                .from(qIndicatorConnect)
                .innerJoin(qTag)
                    .on(qIndicatorConnect.tagId.eq(qTag.tagId))
                .leftJoin(qIndicator)
                    .on(qIndicatorConnect.userId.eq(qIndicator.userId).and(qIndicatorConnect.tagId.eq(qTag.tagId)))
                .where(qIndicatorConnect.userId.eq(userId))
                .fetch();
    }
}
