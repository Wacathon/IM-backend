package com.namecard.question.service;

import com.namecard.exception.NotFoundException;
import com.namecard.exception.QuestionLimitException;
import com.namecard.member.domain.MemberRepository;
import com.namecard.member.dto.entity.Users;
import com.namecard.question.domain.Question;
import com.namecard.question.dto.request.QuestionRequest;
import com.namecard.question.dto.result.QuestionResult;
import com.namecard.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createQuestion(Long userId, QuestionRequest questionRequest) {
        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        Users users = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        if (questionRepository.countQuestionsByUsers(users) > 3) {
            throw new QuestionLimitException("사용자 설정 질문의 최대 생성 개수는 3개입니다.");
        }

        Question question = Question.builder()
                .users(users)
                .title(questionRequest.getTitle())
                .build();

        questionRepository.save(question);
    }

    @Transactional
    public void modifyQuestion(Long userId, Long questionId, QuestionRequest questionRequest) {
        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        if (optionalUsers.isEmpty()) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        Question question = questionOptional.orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));

        question.updateTitle(questionRequest.getTitle());
    }

    @Transactional
    public void deleteQuestion(Long userId, Long questionId) {
        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        if (optionalUsers.isEmpty()) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        Question question = questionOptional.orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));

        questionRepository.delete(question);
    }

    public List<QuestionResult> loadQuestions(Long userId) {
        Optional<Users> optionalUsers = memberRepository.findByUserId(userId);
        Users users = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Question> questions = questionRepository.findQuestionsByUsers(users);

        return questions.stream().map(q -> QuestionResult.builder()
                .questionId(q.getQuestionId())
                .title(q.getTitle())
                .build()).toList();
    }
}
