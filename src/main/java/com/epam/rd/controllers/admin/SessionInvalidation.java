package com.epam.rd.controllers.admin;

import com.epam.rd.util.LocaleMessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.rd.util.LanguageDefiner.definePageLang;


public class SessionInvalidation extends HttpServlet {
    protected static final Logger logger = LoggerFactory.getLogger(SessionInvalidation.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();


        session.invalidate();
        logger.info("Current session is terminated");

        response.sendRedirect("/admin/main");

    }
}