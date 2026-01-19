package com.sushilk.quiz_app.attempt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sushilk.quiz_app.attempt.enums.AttemptStatus;
import com.sushilk.quiz_app.quiz.entity.Quiz;
import com.sushilk.quiz_app.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quiz_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAttempt {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Quiz quiz;
    @ManyToOne
    @JsonIgnore
    private User user;

    private Integer totalMarks;
    private Integer scoredMarks;

    @Enumerated(EnumType.STRING)
    private AttemptStatus status; // STARTED, SUBMITTED

    private LocalDateTime startedAt;   // 🔹 Add this field
    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL)
    private List<Answer> answers;
}
