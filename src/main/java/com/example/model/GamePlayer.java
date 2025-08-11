package com.example.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int score = 0;
    private boolean finished = false;
}

