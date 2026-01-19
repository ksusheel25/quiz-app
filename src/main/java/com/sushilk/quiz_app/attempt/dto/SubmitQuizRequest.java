package com.sushilk.quiz_app.attempt.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SubmitQuizRequest(
        @NotEmpty List<@Valid SubmitAnswerRequest> answers
) {}
