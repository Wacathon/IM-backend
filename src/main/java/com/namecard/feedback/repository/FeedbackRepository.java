package com.namecard.feedback.repository;

import com.namecard.feedback.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, FeedbackQuerydslRepository {
}
