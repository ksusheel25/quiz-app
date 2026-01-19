package com.sushilk.quiz_app.attempt.service;

import com.sushilk.quiz_app.attempt.dto.SubmitQuizRequest;
import com.sushilk.quiz_app.attempt.entity.Answer;
import com.sushilk.quiz_app.attempt.entity.QuizAttempt;
import com.sushilk.quiz_app.attempt.enums.AttemptStatus;
import com.sushilk.quiz_app.attempt.repository.QuizAttemptRepository;
import com.sushilk.quiz_app.common.exception.ApiException;
import com.sushilk.quiz_app.quiz.entity.Option;
import com.sushilk.quiz_app.quiz.entity.Question;
import com.sushilk.quiz_app.quiz.entity.Quiz;
import com.sushilk.quiz_app.quiz.entity.QuizStatus;
import com.sushilk.quiz_app.quiz.repository.OptionRepository;
import com.sushilk.quiz_app.quiz.repository.QuestionRepository;
import com.sushilk.quiz_app.quiz.repository.QuizRepository;
import com.sushilk.quiz_app.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizAttemptService {

    private final QuizRepository quizRepo;
    private final QuizAttemptRepository attemptRepo;
    private final QuestionRepository questionRepo;
    private final OptionRepository optionRepo;

    // 🔹 Student starts a quiz
    @Transactional
    public QuizAttempt startQuiz(User user, Long quizId) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new ApiException("Quiz not found", HttpStatus.NOT_FOUND));

        if (quiz.getStatus() != QuizStatus.PUBLISHED) {
            throw new ApiException("Quiz is not published yet", HttpStatus.BAD_REQUEST);
        }

        if (attemptRepo.existsByQuizAndUser(quiz, user)) {
            throw new ApiException("Quiz already started or submitted", HttpStatus.BAD_REQUEST);
        }

        QuizAttempt attempt = QuizAttempt.builder()
                .quiz(quiz)
                .user(user)
                .status(AttemptStatus.STARTED)
                .startedAt(LocalDateTime.now())
                .build();

        return attemptRepo.save(attempt);
    }

    // 🔹 Student submits quiz
    @Transactional
    public QuizAttempt submitQuiz(Long quizId, User user, SubmitQuizRequest request) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new ApiException("Quiz not found", HttpStatus.NOT_FOUND));

        if (quiz.getStatus() != QuizStatus.PUBLISHED) {
            throw new ApiException("Quiz not published", HttpStatus.BAD_REQUEST);
        }

        if (attemptRepo.existsByQuizAndUser(quiz, user)) {
            throw new ApiException("Quiz already attempted", HttpStatus.BAD_REQUEST);
        }

        QuizAttempt attempt = QuizAttempt.builder()
                .quiz(quiz)
                .user(user)
                .status(AttemptStatus.SUBMITTED)
                .submittedAt(LocalDateTime.now())
                .build();

        List<Answer> answers = request.answers().stream().map(a -> {
            Question q = questionRepo.findById(a.questionId())
                    .orElseThrow(() -> new ApiException("Question not found", HttpStatus.NOT_FOUND));

            Option o = optionRepo.findById(a.optionId())
                    .orElseThrow(() -> new ApiException("Option not found", HttpStatus.NOT_FOUND));

            return Answer.builder()
                    .attempt(attempt)
                    .question(q)
                    .selectedOption(o)
                    .isCorrect(o.isCorrect())
                    .build();
        }).toList();

        int score = answers.stream()
                .filter(Answer::getIsCorrect)
                .mapToInt(a -> a.getQuestion().getMarks())
                .sum();

        int total = quiz.getQuestions().stream()
                .mapToInt(Question::getMarks)
                .sum();

        attempt.setAnswers(answers);
        attempt.setScoredMarks(score);
        attempt.setTotalMarks(total);

        return attemptRepo.save(attempt);
    }

    // 🔹 Get all attempts for a student
    public List<QuizAttempt> getAttemptsByUser(User user) {
        return attemptRepo.findByUser(user);
    }

    // 🔹 Get all attempts for a quiz (admin)
    public List<QuizAttempt> getAttemptsByQuiz(Long quizId) {
        return attemptRepo.findByQuizId(quizId);
    }

    // 🔹 Get all attempts (admin optional)
    public List<QuizAttempt> getAllAttempts() {
        return attemptRepo.findAll();
    }
}
