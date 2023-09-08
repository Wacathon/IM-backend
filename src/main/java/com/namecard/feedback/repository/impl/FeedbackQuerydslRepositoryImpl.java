package com.namecard.feedback.repository.impl;

import com.namecard.answer.domain.QAnswer;
import com.namecard.feedback.dto.request.FeedbackListRequest;
import com.namecard.feedback.dto.result.FeedbackResult;
import com.namecard.feedback.repository.FeedbackQuerydslRepository;
import com.namecard.question.domain.QQuestion;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedbackQuerydslRepositoryImpl implements FeedbackQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FeedbackResult> getFeedbackList(FeedbackListRequest feedbackListRequest) {
        QAnswer qAnswer = QAnswer.answer;
        QQuestion qQuestion = QQuestion.question;
        return jpaQueryFactory.select(Projections.constructor(FeedbackResult.class,
                    qAnswer.answerId,
                    qQuestion.title.as("questionTitle"),
                    qAnswer.title,
                    qAnswer.content,
                    qAnswer.pinned
                ))
                .from(qAnswer)
                .innerJoin(qQuestion)
                    .on(qAnswer.question.questionId.eq(qQuestion.questionId))
                .where(qQuestion.member.memberId.eq(feedbackListRequest.getUserId()))
                .fetch();
    }

    @Override
    public List<FeedbackResult> getFeedbackListLimit3(FeedbackListRequest feedbackListRequest) {
        QAnswer qAnswer = QAnswer.answer;
        QQuestion qQuestion = QQuestion.question;
        return jpaQueryFactory.select(Projections.constructor(FeedbackResult.class,
                        qAnswer.answerId,
                        qQuestion.title.as("questionTitle"),
                        qAnswer.title,
                        qAnswer.content,
                        qAnswer.pinned
                ))
                .from(qAnswer)
                .innerJoin(qQuestion)
                .on(qAnswer.question.questionId.eq(qQuestion.questionId))
                .where(qQuestion.member.memberId.eq(feedbackListRequest.getUserId()))
                .limit(3)
                .fetch();
    }
}
