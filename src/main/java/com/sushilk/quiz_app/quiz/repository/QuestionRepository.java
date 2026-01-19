package com.sushilk.quiz_app.quiz.repository;

import com.sushilk.quiz_app.quiz.entity.Question;
import com.sushilk.quiz_app.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuiz(Quiz quiz);
}
