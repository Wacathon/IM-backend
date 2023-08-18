package com.namecard.feedback.service;

import com.namecard.exception.NotFoundException;
import com.namecard.feedback.domian.Feedback;
import com.namecard.feedback.domian.Relationship;
import com.namecard.feedback.dto.request.FeedbackRequest;
import com.namecard.feedback.dto.result.FeedbackResult;
import com.namecard.feedback.repository.FeedbackRepository;
import com.namecard.member.domain.MemberRepository;
import com.namecard.member.dto.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createFeedback(Long userId, FeedbackRequest feedbackRequest) {
        if (feedbackRequest == null) {
            throw new IllegalArgumentException("입력 값이 존재하지 않습니다.");
        }

        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        Users users = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Feedback feedback = Feedback.builder()
                .users(users)
                .relationship(Relationship
                        .findRelationshipByString(feedbackRequest.getRelationship()))
                .commonAnswer(feedbackRequest.getCommonAnswer())
                .build();

        feedbackRepository.save(feedback);
    }


    public List<FeedbackResult> getFeedbackList(Long userId) {
        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        Users users = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Feedback> feedbacks = feedbackRepository.findAllByUsersEquals(users);

        return feedbacks.stream().map(f -> FeedbackResult.builder()
                .relationship(f.getRelationship().toString())
                .commonAnswer(f.getCommonAnswer()).build())
                .toList();
    }
}
