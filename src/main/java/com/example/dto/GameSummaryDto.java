package com.example.dto;

import com.example.model.GameStatus;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GameSummaryDto {
    private Long gameId;
    private GameStatus status;
    private List<PlayerScoreDto> playerScores;
    private String winnerUsername;
}
