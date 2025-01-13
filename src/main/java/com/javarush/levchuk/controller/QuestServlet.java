package com.javarush.levchuk.controller;

import com.javarush.levchuk.config.Components;
import com.javarush.levchuk.entity.Answer;
import com.javarush.levchuk.entity.Game;
import com.javarush.levchuk.entity.Question;
import com.javarush.levchuk.entity.User;
import com.javarush.levchuk.service.GameService;
import com.javarush.levchuk.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.javarush.levchuk.config.Config.*;

@WebServlet(QUEST_RESOURCE)
public class QuestServlet extends HttpServlet {

    private final GameService gameService = Components.get(GameService.class);
    private final UserService userService = Components.get(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long questId = getQuestId(req);
        Game game = getUserGame(session, questId);
        Question question = game.getCurrentQuestion();
        List<Answer> answers = gameService.getAnswers(game, question);
        session.setAttribute(ATTRIBUTE_QUESTION, question);
        session.setAttribute(ATTRIBUTE_ANSWERS, answers);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(QUEST_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Long questId = getQuestId(req);
        Game game = getUserGame(session, questId);
        sendAnswer(req, game);
        resp.sendRedirect(QUEST_RESOURCE);
    }

    private Long getQuestId(HttpServletRequest req) {
        String stringQuestId = req.getParameter(PARAMETER_QUEST_ID);
        HttpSession session = req.getSession();
        return Objects.isNull(stringQuestId)
                ? (Long) session.getAttribute(ATTRIBUTE_QUEST_ID)
                : Long.parseLong(stringQuestId);
    }

    private void sendAnswer(HttpServletRequest req, Game game) {
        String stringAnswerId = req.getHeader(REQUEST_HEADER_ANSWER_ID);
        if (Objects.nonNull(stringAnswerId)) {
            Long answerId = Long.parseLong(stringAnswerId);
            gameService.sendAnswer(game, answerId);
        }
    }

    private Game getUserGame(HttpSession session, Long questId) {
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        User user = userService.getUser(userId);
        Game game = gameService.getUserGame(user, questId);
        session.setAttribute(ATTRIBUTE_QUEST_ID, questId);
        return game;
    }
}