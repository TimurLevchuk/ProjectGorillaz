package com.javarush.levchuk.controller;

import com.javarush.levchuk.config.Components;
import com.javarush.levchuk.entity.GameState;
import com.javarush.levchuk.service.StatisticsService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

import static com.javarush.levchuk.config.Config.*;

@WebServlet(STATISTICS_RESOURCE)
public class StatisticsServlet extends HttpServlet {

    private final StatisticsService statisticsService = Components.get(StatisticsService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<String, Map<GameState, Long>> statsMap = statisticsService.calculate();
        session.setAttribute(ATTRIBUTE_STATISTICS_MAP, statsMap);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(STATISTICS_PAGE);
        requestDispatcher.forward(req, resp);
    }
}
