package com.sushilk.quiz_app.quiz.repository;

import com.sushilk.quiz_app.quiz.entity.Quiz;
import com.sushilk.quiz_app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreatedBy(User user);
    List<Quiz> findByStatus(String status);  // DRAFT / PUBLISHED
}
