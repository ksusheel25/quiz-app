package com.sushilk.quiz_app.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuizRequest(
        @NotBlank(message = "Quiz title is required")
        String title,

        String description,

        @NotNull(message = "Status is required")
        String status // DRAFT / PUBLISHED
) {}
