package com.javarush.levchuk.service;

import com.javarush.levchuk.entity.*;
import com.javarush.levchuk.exception.ProjectException;
import com.javarush.levchuk.repository.AnswerRepository;
import com.javarush.levchuk.repository.GameRepository;
import com.javarush.levchuk.repository.QuestRepository;
import com.javarush.levchuk.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameRepository gameRepository;
    private QuestRepository questRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameRepository = Mockito.mock(GameRepository.class);
        questRepository = Mockito.mock(QuestRepository.class);
        questionRepository = Mockito.mock(QuestionRepository.class);
        answerRepository = Mockito.mock(AnswerRepository.class);
        gameService = new GameService(gameRepository, questRepository, questionRepository, answerRepository);
    }


    @Test
    void getUserGame() {
        User user = Mockito.mock(User.class);
        Long gameId = 1L;
        Long questId = 1L;
        Map<Long, Long> questGameMap = new HashMap<>();
        questGameMap.put(questId, gameId);
        Mockito.doReturn(questGameMap).when(user).getQuestGameMap();
        Game expected = Game.builder().build();
        Mockito.doReturn(Optional.of(expected)).when(gameRepository).get(gameId);
        Game actual = gameService.getUserGame(user, questId);
        assertEquals(expected, actual);
    }

    @Test
    void whenGetUserGameThatNotExist_thenCreateNewGame() {
        User user = User.builder().build();
        Long questId = 1L;
        Quest quest = new Quest();
        quest.setId(questId);
        Mockito.doReturn(Optional.of(quest)).when(questRepository).get(questId);
        gameService.getUserGame(user, questId);
        Mockito.verify(gameRepository).create(Mockito.any(Game.class));
    }

    @Test
    void whenGetUserGameAndQuestIdNotExist_thenThrowsProjectException() {
        User user = User.builder().build();
        Long questId = 0L;
        assertThrows(ProjectException.class, () -> gameService.getUserGame(user, questId));
    }

    @Test
    void restartGame() {
        User user = Mockito.mock(User.class);
        Long gameId = 1L;
        Long questId = 1L;
        Map<Long, Long> questGameMap = new HashMap<>();
        questGameMap.put(questId, gameId);
        Mockito.doReturn(questGameMap).when(user).getQuestGameMap();
        gameService.restartGame(user, questId);
        Long actual = questGameMap.get(questId);
        assertNull(actual);
    }

    @Test
    void whenGetAnswersWithNullGame_thenReturnsEmptyList() {
        Question question = new Question();
        List<Answer> actual = gameService.getAnswers(null, question);
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void whenGetAnswersWithNullQuestion_thenReturnsEmptyList() {
        Game game = Game.builder().build();
        List<Answer> actual = gameService.getAnswers(game, null);
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void sendAnswer() {
        Game game = Game.builder().build();
        Long answerId = 1L;
        Long nextQuestionId = 2L;
        Answer answer = Answer.builder()
                .id(answerId)
                .nextQuestionId(nextQuestionId)
                .build();
        Mockito.doReturn(Optional.of(answer)).when(answerRepository).get(answerId);

        Question expected = new Question();
        expected.setId(nextQuestionId);
        Mockito.doReturn(Optional.of(expected)).when(questionRepository).get(nextQuestionId);

        gameService.sendAnswer(game, answerId);
        Question actual = game.getCurrentQuestion();
        assertEquals(expected, actual);
    }

    @Test
    void finishGame() {
        Game game = Game.builder().build();
        gameService.finishGame(game);
        GameState actual = game.getState();
        assertEquals(GameState.FINISHED, actual);
    }
}