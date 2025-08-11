package com.example.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class GameHistoryDto {
    private Long gameId;
    private String opponentUsername;
    private int myScore;
    private int opponentScore;
    private String result; 
}
