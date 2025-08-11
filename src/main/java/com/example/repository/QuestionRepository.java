package com.example.repository;

import com.example.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    // Updated to find 10 random questions
    @Query(value = "SELECT * FROM question ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Question> findRandomQuestions();

    // NEW: Find 10 random questions from a specific list of categories
    @Query(value = "SELECT * FROM question WHERE category IN :categories ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Question> findRandomQuestionsByCategories(@Param("categories") List<String> categories);
}
