package com.javarush.levchuk.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.javarush.levchuk.config.Config.LOGIN_RESOURCE;
import static com.javarush.levchuk.config.Config.LOGOUT_RESOURCE;
import static java.util.Objects.nonNull;

@WebServlet(LOGOUT_RESOURCE)
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (nonNull(session)) {
            session.invalidate();
        }
        resp.sendRedirect(LOGIN_RESOURCE);
    }
}