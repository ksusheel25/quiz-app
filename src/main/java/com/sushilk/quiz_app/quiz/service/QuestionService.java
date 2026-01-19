package com.sushilk.quiz_app.quiz.service;

import com.sushilk.quiz_app.common.exception.ApiException;
import com.sushilk.quiz_app.quiz.dto.OptionRequest;
import com.sushilk.quiz_app.quiz.dto.QuestionRequest;
import com.sushilk.quiz_app.quiz.entity.Option;
import com.sushilk.quiz_app.quiz.entity.Question;
import com.sushilk.quiz_app.quiz.entity.Quiz;
import com.sushilk.quiz_app.quiz.repository.QuestionRepository;
import com.sushilk.quiz_app.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public Question addQuestion(Long quizId, QuestionRequest request) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ApiException("Quiz not found", HttpStatus.NOT_FOUND));

        validateQuestionRequest(request);

        Question question = Question.builder()
                .quiz(quiz)
                .text(request.text())
                .marks(request.marks())
                .type(request.type())
                .build();

        List<Option> options = request.options().stream()
                .map(o -> Option.builder()
                        .text(o.text())
                        .correct(o.isCorrect())
                        .question(question)
                        .build())
                .toList();

        question.setOptions(options);

        return questionRepository.save(question);
    }

    private void validateQuestionRequest(QuestionRequest request) {

        List<OptionRequest> options = request.options();

        long correctCount = options.stream()
                .filter(OptionRequest::isCorrect)
                .count();

        if (correctCount != 1) {
            throw new ApiException(
                    "Exactly one option must be correct",
                    HttpStatus.BAD_REQUEST
            );
        }

        switch (request.type()) {

            case MCQ -> {
                if (options.size() < 2) {
                    throw new ApiException(
                            "MCQ must have at least 2 options",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }

            case TRUE_FALSE -> {
                if (options.size() != 2) {
                    throw new ApiException(
                            "TRUE_FALSE must have exactly 2 options",
                            HttpStatus.BAD_REQUEST
                    );
                }

                List<String> values = options.stream()
                        .map(o -> o.text().toLowerCase())
                        .toList();

                if (!(values.contains("true") && values.contains("false"))) {
                    throw new ApiException(
                            "TRUE_FALSE options must be true and false",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }
        }
    }
}
