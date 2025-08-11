package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerScoreDto {
    private String username;
    private int score;
}
