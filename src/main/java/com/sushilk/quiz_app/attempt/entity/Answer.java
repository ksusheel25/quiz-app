package com.sushilk.quiz_app.attempt.entity;

import com.sushilk.quiz_app.quiz.entity.Option;
import com.sushilk.quiz_app.quiz.entity.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne private QuizAttempt attempt;
    @ManyToOne private Question question;
    @ManyToOne private Option selectedOption;

    private Boolean isCorrect;
}
