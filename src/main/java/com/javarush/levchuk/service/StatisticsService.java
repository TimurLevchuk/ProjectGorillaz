package com.javarush.levchuk.service;

import com.javarush.levchuk.entity.Game;
import com.javarush.levchuk.entity.GameState;
import com.javarush.levchuk.entity.User;
import com.javarush.levchuk.repository.GameRepository;
import com.javarush.levchuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public Map<String, Map<GameState, Long>> calculate() {
        ArrayList<User> users = new ArrayList<>(userRepository.getAll());
        Map<String, Map<GameState, Long>> statsMap = new HashMap<>(users.size());
        for (User user : users) {
            Map<Long, Long> questGameMap = user.getQuestGameMap();
            Map<GameState, Long> countMap = new HashMap<>();

            countMap.put(GameState.PLAYING, 0L);
            countMap.put(GameState.FINISHED, 0L);

            for (var entry : questGameMap.entrySet()) {
                Long gameId = entry.getValue();

                Optional<Game> optionalGame = gameRepository.get(gameId);
                if (optionalGame.isPresent()) {
                    Game game = optionalGame.get();
                    GameState state = game.getState();
                    Long count = countMap.get(state);
                    countMap.put(state, ++count);
                }
            }
            statsMap.put(user.getName(), countMap);
        }
        return statsMap;
    }
}