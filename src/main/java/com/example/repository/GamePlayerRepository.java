package com.example.repository;

import com.example.model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Use Optional for better null handling

public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {
    
    // Add this method!
    // It finds a player record based on the game and user IDs.
    Optional<GamePlayer> findByGameIdAndUserId(Long gameId, Long userId);
}