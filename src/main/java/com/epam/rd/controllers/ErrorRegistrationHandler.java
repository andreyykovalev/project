package com.epam.rd.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.rd.util.AttributesLocalizer.getLang;


public class ErrorRegistrationHandler extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pageLangRequest = request.getParameter("lang");

        getLang(pageLangRequest,request, session);

        RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
        rd.forward(request,response);

        session.removeAttribute("message");
        session.removeAttribute("customer");
    }
}
