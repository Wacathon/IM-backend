package com.namecard.member.repository.impl;

import com.namecard.member.dto.entity.QUsers;
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
        QUsers qUsers = QUsers.users;
        return jpaQueryFactory.select(Projections.constructor(SearchUsersResult.class,
                        qUsers.userId,
                        qUsers.userName,
                        qUsers.introduce
                )).from(qUsers)
                .where(qUsers.userName.contains(userName))
                .fetch();
    }
}
