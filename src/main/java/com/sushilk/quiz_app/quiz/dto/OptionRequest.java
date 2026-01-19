package com.sushilk.quiz_app.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OptionRequest(

        @NotBlank(message = "Option text must not be blank")
        @Size(max = 200, message = "Option text must not exceed 200 characters")
        String text,

        @NotNull(message = "isCorrect must be provided")
        Boolean isCorrect
) {}
