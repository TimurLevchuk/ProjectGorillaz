package com.javarush.levchuk.service;

import com.javarush.levchuk.entity.User;
import com.javarush.levchuk.repository.GameRepository;
import com.javarush.levchuk.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class StatisticsServiceTest {
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        gameRepository = Mockito.mock(GameRepository.class);
        statisticsService = new StatisticsService(userRepository, gameRepository);
    }

    @Test
    void whenCalculateStats_thenCallsGetAllOnce() {
        statisticsService.calculate();
        Mockito.verify(userRepository, Mockito.only()).getAll();
    }

    @Test
    void whenCalculateStats_thenCallsGetAtMostUserCount() {
        ArrayList<User> users = new ArrayList<>();
        User user = Mockito.mock(User.class);
        users.add(user);
        users.add(user);
        Mockito.doReturn(users).when(userRepository).getAll();
        statisticsService.calculate();
        Mockito.verify(gameRepository, Mockito.atMost(users.size())).get(Mockito.any());
    }
}