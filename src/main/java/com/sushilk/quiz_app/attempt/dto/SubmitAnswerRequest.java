package com.sushilk.quiz_app.attempt.dto;

import jakarta.validation.constraints.NotNull;

public record SubmitAnswerRequest(
        @NotNull Long questionId,
        @NotNull Long optionId
) {}

