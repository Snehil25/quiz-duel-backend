package com.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String inviteCode;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference 
    private List<GamePlayer> players;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "game_questions", 
        joinColumns = @JoinColumn(name = "game_id"), 
        inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions;
}
