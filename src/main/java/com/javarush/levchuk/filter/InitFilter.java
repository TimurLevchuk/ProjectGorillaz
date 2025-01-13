package com.javarush.levchuk.filter;

import com.javarush.levchuk.config.Components;
import com.javarush.levchuk.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class InitFilter extends HttpFilter {
    private final UserService userService = Components.get(UserService.class);
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        userService.registerAdmin();
        chain.doFilter(req, res);
    }
}