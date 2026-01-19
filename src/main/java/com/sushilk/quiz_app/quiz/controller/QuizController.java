package com.sushilk.quiz_app.quiz.controller;

import com.sushilk.quiz_app.quiz.dto.QuestionRequest;
import com.sushilk.quiz_app.quiz.dto.QuizRequest;
import com.sushilk.quiz_app.quiz.entity.Question;
import com.sushilk.quiz_app.quiz.entity.Quiz;
import com.sushilk.quiz_app.quiz.entity.QuizStatus;
import com.sushilk.quiz_app.quiz.service.QuestionService;
import com.sushilk.quiz_app.quiz.service.QuizService;
import com.sushilk.quiz_app.user.entity.User;
import com.sushilk.quiz_app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuestionService questionService; // 👈 IMPORTANT
    private final UserService userService;

    // ADMIN: Create Quiz
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Quiz createQuiz(@RequestParam String adminEmail,
                           @Valid @RequestBody QuizRequest quizRequest) {

        User admin = userService.getUserByEmail(adminEmail);

        Quiz quiz = Quiz.builder()
                .title(quizRequest.title())
                .description(quizRequest.description())
                .status(QuizStatus.valueOf(quizRequest.status()))
                .build();

        return quizService.createQuiz(quiz, admin);
    }

    // ADMIN: Add Question to Quiz
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{quizId}/questions")
    public Question addQuestion(@PathVariable Long quizId,
                                @Valid @RequestBody QuestionRequest request) {

        return questionService.addQuestion(quizId, request);
    }

    // GET: All quizzes
    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    // GET: Single quiz
    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }
}
