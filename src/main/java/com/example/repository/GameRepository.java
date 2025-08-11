package com.example.repository;


import com.example.model.Game;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByInviteCode(String inviteCode);

    // NEW: Find all games a specific user has played in
    @Query("SELECT g FROM Game g JOIN g.players p WHERE p.user = :user ORDER BY g.createdAt DESC")
    List<Game> findGamesByUser(@Param("user") User user);
}