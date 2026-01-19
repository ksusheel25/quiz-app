package com.sushilk.quiz_app.attempt.repository;

import com.sushilk.quiz_app.attempt.entity.Answer;
import com.sushilk.quiz_app.attempt.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByAttempt(QuizAttempt attempt);
}
