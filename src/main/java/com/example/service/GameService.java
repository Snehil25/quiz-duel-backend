package com.example.service;

import com.example.dto.GameSummaryDto;
import com.example.dto.PlayerScoreDto;
import com.example.model.*;
import com.example.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Game createGame(User user) {
        Game game = new Game();
        game.setInviteCode(UUID.randomUUID().toString().substring(0, 6));
        game.setStatus(GameStatus.PENDING);
        game.setCreatedAt(LocalDateTime.now());
        game.setPlayers(new ArrayList<>());
        
        Game savedGame = gameRepository.save(game);

        GamePlayer player = new GamePlayer();
        player.setGame(savedGame);
        player.setUser(user);
        gamePlayerRepository.save(player);
        
        savedGame.getPlayers().add(player);

        return savedGame;
    }

    @Transactional
    public Game joinGame(String inviteCode, User user) {
        Game game = gameRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Game not found with code: " + inviteCode));

        if (game.getPlayers().size() >= 2) {
            throw new RuntimeException("Game is full");
        }

        GamePlayer newPlayer = new GamePlayer();
        newPlayer.setGame(game);
        newPlayer.setUser(user);
        
        game.getPlayers().add(newPlayer);

        gamePlayerRepository.save(newPlayer);
        
        if (game.getPlayers().size() == 2) {
            User creator = game.getPlayers().get(0).getUser();
            List<Question> questions;
            if (creator.getPreferredCategories() != null && !creator.getPreferredCategories().isEmpty()) {
                 try {
                    ObjectMapper mapper = new ObjectMapper();
                    List<String> categories = mapper.readValue(creator.getPreferredCategories(), new TypeReference<List<String>>(){});
                    questions = questionRepository.findRandomQuestionsByCategories(categories);
                } catch (Exception e) {
                    questions = questionRepository.findRandomQuestions();
                }
            } else {
                questions = questionRepository.findRandomQuestions();
            }

            game.setQuestions(questions);
            game.setStatus(GameStatus.ACTIVE);
            
            // --- THIS IS THE FIX ---
            // We must explicitly save the game object to persist the new questions and status.
            gameRepository.save(game);
            
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    messagingTemplate.convertAndSend("/topic/game/"  + game.getId(), "Game has started!");
                }
            });
        } else {
             messagingTemplate.convertAndSend("/topic/game/"  + game.getId(), "A player has joined the game!");
        }

        return game;
    }

    public Question getQuestionByIndex(Long gameId, int index) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.getQuestions() == null || index < 0 || index >= game.getQuestions().size()) {
            throw new RuntimeException("Invalid question index: " + index);
        }
        return game.getQuestions().get(index);
    }

    @Transactional
    public Answer submitAnswer(Long gameId, User user, int questionIndex, int chosenIndex) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        Question question = game.getQuestions().get(questionIndex);

        boolean isCorrect = question.getCorrectIndex() == chosenIndex;

        Answer answer = new Answer();
        answer.setGameId(gameId);
        answer.setUserId(user.getId());
        answer.setQuestionIndex(questionIndex);
        answer.setChosenIndex(chosenIndex);
        answer.setCorrect(isCorrect);
        answer.setAnsweredAt(LocalDateTime.now());
        answerRepository.save(answer);

        if (isCorrect) {
            GamePlayer player = gamePlayerRepository.findByGameIdAndUserId(gameId, user.getId())
                .orElseThrow(() -> new RuntimeException("Player not found in this game"));
            player.setScore(player.getScore() + 1);
        }
        
        if (questionIndex == game.getQuestions().size() - 1) {
            GamePlayer player = gamePlayerRepository.findByGameIdAndUserId(gameId, user.getId()).orElseThrow();
            player.setFinished(true);

            boolean allFinished = game.getPlayers().stream().allMatch(p -> p.isFinished() || p.getId().equals(player.getId()));
            if (allFinished) {
                game.setStatus(GameStatus.FINISHED);
            }
        }
        
        return answer;
    }

    public GameSummaryDto getGameSummary(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        List<PlayerScoreDto> scores = game.getPlayers().stream()
                .map(player -> PlayerScoreDto.builder()
                        .username(player.getUser().getUsername())
                        .score(player.getScore())
                        .build())
                .collect(Collectors.toList());

        String winner = "Tie";
        if (scores.size() == 2) {
            if (scores.get(0).getScore() > scores.get(1).getScore()) {
                winner = scores.get(0).getUsername();
            } else if (scores.get(1).getScore() > scores.get(0).getScore()) {
                winner = scores.get(1).getUsername();
            }
        } else if (scores.size() == 1) {
            winner = scores.get(0).getUsername();
        }

        return GameSummaryDto.builder()
                .gameId(game.getId())
                .status(game.getStatus())
                .playerScores(scores)
                .winnerUsername(winner)
                .build();
    }
}
