package com.namecard.question.service;

import com.namecard.exception.NotFoundException;
import com.namecard.exception.QuestionLimitException;
import com.namecard.member.repository.MemberRepository;
import com.namecard.member.domain.Member;
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
        Optional<Member> optionalUsers = memberRepository.findByMemberId(userId);
        Member member = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        if (questionRepository.countQuestionsByMember(member) > 3) {
            throw new QuestionLimitException("사용자 설정 질문의 최대 생성 개수는 3개입니다.");
        }

        Question question = Question.builder()
                .member(member)
                .title(questionRequest.getTitle())
                .build();

        questionRepository.save(question);
    }

    @Transactional
    public void modifyQuestion(Long memberId, Long questionId, QuestionRequest questionRequest) {
        Optional<Member> optionalUsers = memberRepository.findByMemberId(memberId);
        if (optionalUsers.isEmpty()) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        Question question = questionOptional.orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));

        question.updateTitle(questionRequest.getTitle());
    }

    @Transactional
    public void deleteQuestion(Long userId, Long questionId) {
        Optional<Member> optionalUsers = memberRepository.findByMemberId(userId);
        if (optionalUsers.isEmpty()) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        Question question = questionOptional.orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));

        questionRepository.delete(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResult> loadQuestions(Long userId) {
        Optional<Member> optionalUsers = memberRepository.findByMemberId(userId);
        Member member = optionalUsers.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Question> questions = questionRepository.findQuestionsByMember(member);

        return questions.stream().map(q -> QuestionResult.builder()
                .questionId(q.getQuestionId())
                .title(q.getTitle())
                .build()).toList();
    }
}
