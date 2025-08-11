package com.example.dto;

import com.example.model.GameStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GameDto {
    private Long id;
    private String inviteCode;
    private GameStatus status;
    private LocalDateTime createdAt;
    private List<UserDto> players;
}
