package com.sushilk.quiz_app.attempt.repository;

import com.sushilk.quiz_app.attempt.entity.QuizAttempt;
import com.sushilk.quiz_app.quiz.entity.Quiz;
import com.sushilk.quiz_app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    // STUDENT: all attempts by user
    List<QuizAttempt> findByUser(User user);

    // ADMIN: attempts of a quiz
    List<QuizAttempt> findByQuiz(Quiz quiz);

    // Prevent re-attempt
    boolean existsByQuizAndUser(Quiz quiz, User user);

    // (Optional but useful)
    List<QuizAttempt> findByQuizId(Long quizId);
}
