package com.example.FilesManager.servlets;

import com.example.FilesManager.models.User;
import com.example.FilesManager.services.CookieUtil;
import com.example.FilesManager.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (UserService.getUserByCookies(req.getCookies()) != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            return;
        }

        User user = UserService.getUserByLogin(login);

        if (user == null || !user.getPassword().equals(password)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserService.addSession(CookieUtil.getValue(req.getCookies(), "JSESSIONID"), user);

        resp.sendRedirect(req.getContextPath() + "/");

    }
}
