package com.namecard.indicator.repository.impl;

import com.namecard.indicator.dto.entity.QIndicator;
import com.namecard.indicator.dto.entity.QIndicatorConnect;
import com.namecard.indicator.dto.result.IndicatorInfoResult.IndicatorScoreResult;
import com.namecard.indicator.dto.result.MyIndicatorInfoResult;
import com.namecard.indicator.repository.IndicatorQuerydslRepository;
import com.namecard.tag.dto.entity.QTag;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class IndicatorRepositoryImpl implements IndicatorQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<IndicatorScoreResult> findIndicatorScoreByMemberId(long userId) {
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
                    .and(qIndicatorConnect.memberId.eq(qindicator.memberId)))
                .where(qIndicatorConnect.memberId.eq(userId))
                .groupBy(qTag.tagName)
                .fetch();
    }

    @Override
    public List<MyIndicatorInfoResult> findIndicatorInfoByMemberId(long userId) {
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
                    .on(qIndicatorConnect.memberId.eq(qIndicator.memberId)
                    .and(qTag.tagId.eq(qIndicator.tagId)))
                .where(qIndicatorConnect.memberId.eq(userId))
                .groupBy(qIndicatorConnect.tagId, qTag.tagName)
                .fetch();
    }
}
