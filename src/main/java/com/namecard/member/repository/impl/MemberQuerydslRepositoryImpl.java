package com.namecard.member.repository.impl;

import com.namecard.member.domain.QCard;
import com.namecard.member.domain.QMember;
import com.namecard.member.repository.MemberQuerydslRepository;
import com.namecard.search.dto.result.SearchUsersResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepositoryImpl implements MemberQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SearchUsersResult> findLikeUserName(String userName) {
        QMember qMember = QMember.member;
        QCard qCard = QCard.card;
        return jpaQueryFactory.select(Projections.constructor(SearchUsersResult.class,
                        qMember.memberId,
                        qMember.userName,
                        qCard.introduce))
                .from(qMember)
                .join(qMember.card, qCard)
                .where(qMember.userName.contains(userName))
                .fetch();
    }
}
