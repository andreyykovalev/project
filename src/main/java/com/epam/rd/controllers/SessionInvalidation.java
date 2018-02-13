package com.epam.rd.controllers;


import com.epam.rd.util.LanguageDefiner;
import com.epam.rd.util.LocaleMessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class SessionInvalidation extends HttpServlet {
    protected static final Logger logger = LoggerFactory.getLogger(SessionInvalidation.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageLanguage;
        HttpSession session = request.getSession();
        String pageLangRequest = request.getParameter("lang");
        if (pageLangRequest == null) {
            String pageLanguageSession = (String) session.getAttribute("lang");
            session.setAttribute("lang", pageLanguageSession);
            LanguageDefiner.definePageLang(pageLanguageSession);
            localizePageAttributes(request);
            pageLanguage = pageLanguageSession;
        } else {
            session.setAttribute("lang", pageLangRequest);
            LanguageDefiner.definePageLang(pageLangRequest);
            localizePageAttributes(request);
            pageLanguage = pageLangRequest;
        }

        session.invalidate();
        logger.info("Current session is terminated");
        HttpSession session2 = request.getSession();
        session2.setAttribute("lang", pageLanguage);

        response.sendRedirect("/main");

    }

    private static void localizePageAttributes(HttpServletRequest request) {
        String main = LocaleMessageProvider.getInstance().encode("main");
        request.setAttribute("main", main);

        String login = LocaleMessageProvider.getInstance().encode("login");
        request.setAttribute("login", login);

        String register = LocaleMessageProvider.getInstance().encode("register");
        request.setAttribute("register", register);

        String welcome = LocaleMessageProvider.getInstance().encode("welcome");
        request.setAttribute("welcome", welcome);

        String settings = LocaleMessageProvider.getInstance().encode("settings");
        request.setAttribute("settings", settings);

        String price = LocaleMessageProvider.getInstance().encode("price");
        request.setAttribute("price", price);

        String description = LocaleMessageProvider.getInstance().encode("description");
        request.setAttribute("description", description);

        String title = LocaleMessageProvider.getInstance().encode("title");
        request.setAttribute("title", title);

        String picture = LocaleMessageProvider.getInstance().encode("picture");
        request.setAttribute("picture", picture);

        String details = LocaleMessageProvider.getInstance().encode("details");
        request.setAttribute("details", details);

        String workspace = LocaleMessageProvider.getInstance().encode("mainWorkspace");
        request.setAttribute("workspace", workspace);

        String buttonDetails = LocaleMessageProvider.getInstance().encode("button_details");
        request.setAttribute("buttondetails", buttonDetails);
    }
}