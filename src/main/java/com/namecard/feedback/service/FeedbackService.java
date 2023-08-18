package com.namecard.feedback.service;

import com.namecard.answer.domain.Answer;
import com.namecard.answer.repository.AnswerRepository;
import com.namecard.exception.NotFoundException;
import com.namecard.feedback.domain.Feedback;
import com.namecard.feedback.domain.Relationship;
import com.namecard.feedback.dto.request.FeedbackAnswerRequest;
import com.namecard.feedback.dto.request.FeedbackIndicatorRequest;
import com.namecard.feedback.dto.request.FeedbackRequest;
import com.namecard.feedback.dto.result.FeedbackResult;
import com.namecard.feedback.repository.FeedbackRepository;
import com.namecard.indicator.domain.IndicatorRepository;
import com.namecard.indicator.dto.entity.Indicator;
import com.namecard.member.domain.MemberRepository;
import com.namecard.member.dto.entity.Users;
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
    public void createFeedback(Long userId, FeedbackRequest feedbackRequest) {
        if (feedbackRequest == null) {
            throw new IllegalArgumentException("입력 값이 존재하지 않습니다.");
        }

        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        Users users = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        /*
            피드백 데이터 생성
         */
        Feedback feedback = Feedback.builder()
                .users(users)
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
                    .userId(userId)
                    .build();
            indicatorRepository.save(indicator);
        }
    }

    public List<FeedbackResult> getFeedbackList(Long userId) {
        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        Users users = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Feedback> feedbacks = feedbackRepository.findAllByUsersEquals(users);

        return feedbacks.stream().map(f -> FeedbackResult.builder()
                        .relationship(f.getRelationship().toString())
                        .build())
                .toList();
    }
}
