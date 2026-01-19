package com.sushilk.quiz_app.attempt.controller;

import com.sushilk.quiz_app.attempt.dto.SubmitQuizRequest;
import com.sushilk.quiz_app.attempt.entity.QuizAttempt;
import com.sushilk.quiz_app.attempt.service.QuizAttemptService;
import com.sushilk.quiz_app.quiz.service.QuizService;
import com.sushilk.quiz_app.user.entity.User;
import com.sushilk.quiz_app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final QuizAttemptService attemptService;
    private final UserService userService;
    private final QuizService quizService;

    // STUDENT: Start Quiz
    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/start")
    public QuizAttempt startAttempt(@RequestParam String studentEmail,
                                    @RequestParam Long quizId) {
        User student = userService.getUserByEmail(studentEmail);
        return attemptService.startQuiz(student, quizId);
    }

    // STUDENT: Submit Quiz
    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/quiz/{quizId}/submit")
    public QuizAttempt submitAttempt(@PathVariable Long quizId,
                                     @RequestParam String studentEmail,
                                     @Valid @RequestBody SubmitQuizRequest request) {
        User student = userService.getUserByEmail(studentEmail);
        return attemptService.submitQuiz(quizId, student, request);
    }

    // STUDENT: Get own attempts
    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/me")
    public List<QuizAttempt> getMyAttempts(@RequestParam String studentEmail) {
        User student = userService.getUserByEmail(studentEmail);
        return attemptService.getAttemptsByUser(student);
    }

    // ADMIN: Get attempts by quiz
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/quiz/{quizId}")
    public List<QuizAttempt> getAttemptsByQuiz(@PathVariable Long quizId) {
        return attemptService.getAttemptsByQuiz(quizId);
    }

    // ADMIN: Get all attempts
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<QuizAttempt> getAllAttempts() {
        return attemptService.getAllAttempts();
    }
}


