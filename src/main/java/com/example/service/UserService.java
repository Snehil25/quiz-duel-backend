package com.example.service;

import com.example.dto.GameHistoryDto; 
import com.example.dto.UpdateUserDto;
import com.example.model.Game;
import com.example.model.GamePlayer;
import com.example.model.User;
import com.example.repository.GameRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors; 

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User updateUser(String username, UpdateUserDto updateUserDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPreferredCategories(updateUserDto.getPreferredCategories());
        return userRepository.save(user);
    }


    public List<GameHistoryDto> getGameHistory(String username) {
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Game> games = gameRepository.findGamesByUser(currentUser);

        return games.stream()
            .filter(game -> game.getStatus() == com.example.model.GameStatus.FINISHED) 
            .map(game -> {
                GamePlayer me = null;
                GamePlayer opponent = null;

                for (GamePlayer p : game.getPlayers()) {
                    if (p.getUser().getId().equals(currentUser.getId())) {
                        me = p;
                    } else {
                        opponent = p;
                    }
                }

                if (me == null) return null; 

                String result;
                if (opponent == null || me.getScore() > opponent.getScore()) {
                    result = "Win";
                } else if (me.getScore() < opponent.getScore()) {
                    result = "Loss";
                } else {
                    result = "Tie";
                }

                return GameHistoryDto.builder()
                        .gameId(game.getId())
                        .opponentUsername(opponent != null ? opponent.getUser().getUsername() : "No Opponent")
                        .myScore(me.getScore())
                        .opponentScore(opponent != null ? opponent.getScore() : 0)
                        .result(result)
                        .build();
            })
            .filter(dto -> dto != null)
            .collect(Collectors.toList());
    }
}

