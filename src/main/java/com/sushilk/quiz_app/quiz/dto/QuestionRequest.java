package com.sushilk.quiz_app.quiz.dto;

import com.sushilk.quiz_app.quiz.entity.Option;
import com.sushilk.quiz_app.quiz.entity.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record QuestionRequest(

        @NotBlank(message = "Question text must not be blank")
        @Size(min = 5, max = 500, message = "Question text must be between 5 and 500 characters")
        String text,

        @NotNull(message = "Marks is required")
        @Min(value = 1, message = "Marks must be at least 1")
        Integer marks,

        @NotNull(message = "Question type is required")
        QuestionType type,

        @NotEmpty(message = "Options must not be empty")
        @Size(min = 2, message = "At least two options are required")
        List<@Valid OptionRequest> options
) {}

