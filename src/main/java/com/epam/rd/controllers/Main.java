package com.epam.rd.controllers;


import com.epam.rd.model.ModelPackage;
import com.epam.rd.util.LanguageDefiner;
import com.epam.rd.util.LocaleMessageProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class Main extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageLanguage;
        HttpSession session = request.getSession();
        String pageLangRequest = request.getParameter("lang");
        if(pageLangRequest == null) {
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




        String customerInitials = (String) session.getAttribute("keycustomer");
        request.setAttribute("keycustomer", customerInitials);

        int langId;
        if (pageLanguage == null || pageLanguage.equals("en")) {
            langId = 1;
        } else langId = 2;


        ModelPackage factory = new ModelPackage(langId);
        List packages = factory.load();
        //    EntityPackage pack = factory.load((long)13);
        //     request.setAttribute("pack", pack);
        request.setAttribute("packages", packages);


        String url;
        RequestDispatcher rd;

        url = "/main.jsp";

        String action;
        action = request.getParameter("action");
        if (action != null) {
            if (action.equals("invalidate")) {

                invalidateSession(session);
            }
        }
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private static void localizePageAttributes(HttpServletRequest request) {
        String main = LocaleMessageProvider.getInstance().encode("main"); // Hey look here
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
    }

    private static void invalidateSession(HttpSession session) {
        session.invalidate();
    }
}