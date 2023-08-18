package com.namecard.feedback.repository;

import com.namecard.feedback.domian.Feedback;
import com.namecard.member.dto.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByUsersEquals(Users users);
}
