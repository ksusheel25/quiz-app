package com.sushilk.quiz_app.quiz.repository;

import com.sushilk.quiz_app.quiz.entity.Option;
import com.sushilk.quiz_app.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestion(Question question);
}
