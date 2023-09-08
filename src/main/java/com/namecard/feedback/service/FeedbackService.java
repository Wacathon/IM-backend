package com.namecard.feedback.service;

import com.namecard.answer.domain.Answer;
import com.namecard.answer.repository.AnswerRepository;
import com.namecard.exception.NotFoundException;
import com.namecard.feedback.domain.Feedback;
import com.namecard.feedback.domain.Relationship;
import com.namecard.feedback.dto.request.*;
import com.namecard.feedback.dto.result.FeedbackResult;
import com.namecard.feedback.repository.FeedbackRepository;
import com.namecard.indicator.repository.IndicatorRepository;
import com.namecard.indicator.domain.Indicator;
import com.namecard.member.repository.MemberRepository;
import com.namecard.member.domain.Member;
import com.namecard.question.domain.Question;
import com.namecard.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AnswerRepository answerRepository;
    private final IndicatorRepository indicatorRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createFeedback(Long memberId, FeedbackRequest feedbackRequest) {
        if (feedbackRequest == null) {
            throw new IllegalArgumentException("입력 값이 존재하지 않습니다.");
        }

        Optional<Member> optionalUsers = memberRepository.findByMemberId(memberId);
        Member member = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        /*
            피드백 데이터 생성
         */
        Feedback feedback = Feedback.builder()
                .member(member)
                .relationship(Relationship.findRelationshipByString(feedbackRequest.getRelationship()))
                .build();

        Feedback savedFeedback = feedbackRepository.save(feedback);

        /*
            질문에 대답 저장
         */
        for(FeedbackAnswerRequest far: feedbackRequest.getAnswers()){
            Optional<Question> optionalQuestion = questionRepository.findById(far.getQuestionId());

            if(optionalQuestion.isEmpty())
                throw new NotFoundException("존재하지 않는 질문입니다.");

            Answer answer = Answer.builder()
                    .title(far.getTitle())
                    .content(far.getContent())
                    .question(optionalQuestion.get())
                    .feedback(savedFeedback)
                    .pinned(false)
                    .build();
            answerRepository.save(answer);
        }

        /*
            육각형 지표 저장
         */
        for(FeedbackIndicatorRequest fir: feedbackRequest.getIndicators()){
            Indicator indicator = Indicator.builder()
                    .feedbackId(savedFeedback.getFeedbackId())
                    .tagId(fir.getTagId())
                    .tagScore(fir.getTagScore())
                    .memberId(memberId)
                    .build();
            indicatorRepository.save(indicator);
        }
    }


    public List<FeedbackResult> getFeedbackList(FeedbackListRequest feedback) {
        memberRepository.findByMemberId(feedback.getUserId()).orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<FeedbackResult> result;

        if(feedback.getPinned()) {
            result = feedbackRepository.getFeedbackListLimit3(feedback);
        } else {
            result = feedbackRepository.getFeedbackList(feedback);
        }
        return result;
    }

    public void changePinned(FeedbackPinnedRequest pinnedRequest) {
        Answer entity = answerRepository.findById(pinnedRequest.getAnswerId()).orElseThrow(() -> new NotFoundException("존재하지 않는 답변입니다."));
        entity.changePinned(pinnedRequest.isPinned());
        answerRepository.save(entity);
    }
}
