package com.javarush.levchuk.controller;

import com.javarush.levchuk.config.Components;
import com.javarush.levchuk.entity.User;
import com.javarush.levchuk.service.GameService;
import com.javarush.levchuk.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.javarush.levchuk.config.Config.*;

@WebServlet(RESTART_RESOURCE)
public class RestartServlet extends HttpServlet {

    private final UserService userService = Components.get(UserService.class);
    private final GameService gameService = Components.get(GameService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        String username = (String) session.getAttribute(ATTRIBUTE_USERNAME);
        Long questId = (Long) session.getAttribute(ATTRIBUTE_QUEST_ID);
        session.invalidate();

        User user = userService.getUser(userId);
        gameService.restartGame(user, questId);

        session = req.getSession();
        session.setAttribute(ATTRIBUTE_USER_ID, userId);
        session.setAttribute(ATTRIBUTE_USERNAME, username);
        session.setAttribute(ATTRIBUTE_QUEST_ID, questId);
        resp.sendRedirect(QUEST_RESOURCE);
    }
}