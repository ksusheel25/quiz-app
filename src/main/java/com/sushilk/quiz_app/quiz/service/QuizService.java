package com.sushilk.quiz_app.quiz.service;

import com.sushilk.quiz_app.common.exception.ApiException;
import com.sushilk.quiz_app.quiz.entity.Quiz;
import com.sushilk.quiz_app.quiz.repository.QuizRepository;
import com.sushilk.quiz_app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public Quiz createQuiz(Quiz quiz, User admin) {
        quiz.setCreatedBy(admin);
        return quizRepository.save(quiz);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ApiException("Quiz not found", HttpStatus.NOT_FOUND));
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
}
