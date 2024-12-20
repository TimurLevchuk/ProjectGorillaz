package com.javarush.levchuk.controller;

import com.javarush.levchuk.repository.UserRepository;
import com.javarush.levchuk.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Long userId = userService.loginUser(username, password);
        String resource = "/login";
        Long idIsMissing = 0L;
        if (!idIsMissing.equals(userId)) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            resource = "/index";
        }
        resp.sendRedirect(resource);
    }
}
