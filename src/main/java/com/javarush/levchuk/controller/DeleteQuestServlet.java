package com.javarush.levchuk.controller;

import com.javarush.levchuk.config.Components;
import com.javarush.levchuk.service.QuestService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.javarush.levchuk.config.Config.*;

@WebServlet(DELETE_QUEST_RESOURCE)
public class DeleteQuestServlet extends HttpServlet {

    private final QuestService questService = Components.get(QuestService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        String stringQuestId = req.getParameter(PARAMETER_QUEST_ID);
        Long questId = Long.parseLong(stringQuestId);
        questService.deleteQuest(questId, userId);
        resp.sendRedirect(QUESTS_LIST_RESOURCE);
    }
}
