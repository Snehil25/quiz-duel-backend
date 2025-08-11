package com.example.controller;
import com.example.dto.AnswerDto;
import com.example.model.Game;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private User getLoggedInUser(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User must be logged in");
        }
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found in database"));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGame(Principal principal) {
        User user = getLoggedInUser(principal);
        Game game = gameService.createGame(user);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinGame(@RequestParam String inviteCode, Principal principal) {
        User user = getLoggedInUser(principal);
        Game game = gameService.joinGame(inviteCode, user);
        return ResponseEntity.ok(game);
    }

//    @PostMapping("/{gameId}/start")
//    public ResponseEntity<?> startGame(@PathVariable Long gameId, Principal principal) {
//        User user = getLoggedInUser(principal);
//        Game game = gameService.startGame(gameId, user);
//        messagingTemplate.convertAndSend("/topic/game/" + game.getId(), "Game has started!");
//        return ResponseEntity.ok(game);
//    }

    @GetMapping("/{gameId}/question/{index}")
    public ResponseEntity<?> getQuestion(@PathVariable Long gameId, @PathVariable int index) {
        return ResponseEntity.ok(gameService.getQuestionByIndex(gameId, index));
    }



    @PostMapping("/{gameId}/answer")
    public ResponseEntity<?> submitAnswer(@PathVariable Long gameId, @RequestBody AnswerDto answerDto, Principal principal) {
        User user = getLoggedInUser(principal);
        gameService.submitAnswer(gameId, user, answerDto.getQuestionIndex(), answerDto.getChosenIndex());
        messagingTemplate.convertAndSend("/topic/game/" + gameId, user.getUsername() + " has answered a question.");
        return ResponseEntity.ok("Answer submitted successfully.");
    }

    @GetMapping("/{gameId}/summary")
    public ResponseEntity<?> getSummary(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.getGameSummary(gameId));
    }
}
