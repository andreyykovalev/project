package com.epam.rd.controllers;


import com.epam.rd.model.ModelPackage;
import com.epam.rd.util.PackageSortByName;
import com.epam.rd.util.PackageSortByPrice;
import com.epam.rd.util.LanguageDefiner;
import com.epam.rd.util.LocaleMessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main extends HttpServlet {
    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

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


        String customerInitials = (String) session.getAttribute("keycustomer");
        request.setAttribute("keycustomer", customerInitials);

        int langId;
        if (pageLanguage == null || pageLanguage.equals("en") || pageLanguage.equals("")) {
            langId = 1;
        } else langId = 2;


        ModelPackage factory = new ModelPackage(langId);
        List packages = factory.load();
        logger.info("Showing a list of all available packages");

        String path = request.getServletPath();

        if (path.equals("/price/sort")) {
            PackageSortByPrice epcomp = new PackageSortByPrice();
            packages.sort(epcomp);
        } else if (path.equals("/name/sort")) {
            PackageSortByName epcomp = new PackageSortByName();
            packages.sort(epcomp);
        } else if (path.equals("/reverse/name/sort")) {
            PackageSortByName epcomp = new PackageSortByName();
            packages.sort(epcomp);
            Collections.reverse(packages);
        }


        request.setAttribute("packages", packages);


        String url;
        RequestDispatcher rd;

        url = "/main.jsp";

        String action;
        boolean isResponceSent = false;
        action = request.getParameter("action");
        if (action != null) {
            if (action.equals("invalidate")) {
                invalidateSession(session);
                response.sendRedirect("/SessionInvalidation");
                isResponceSent = true;
            }
        }
        if (!isResponceSent) {
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
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

        String myProfile = LocaleMessageProvider.getInstance().encode("myprofile");
        request.setAttribute("myprofile", myProfile);

        String description = LocaleMessageProvider.getInstance().encode("description");
        request.setAttribute("description", description);

        String title = LocaleMessageProvider.getInstance().encode("title");
        request.setAttribute("title", title);

        String picture = LocaleMessageProvider.getInstance().encode("picture");
        request.setAttribute("picture", picture);

        String details = LocaleMessageProvider.getInstance().encode("details");
        request.setAttribute("details", details);

        String logout = LocaleMessageProvider.getInstance().encode("logout");
        request.setAttribute("logout", logout);

        String sortBy = LocaleMessageProvider.getInstance().encode("sort_by");
        request.setAttribute("sort_by", sortBy);

        String sortFormat = LocaleMessageProvider.getInstance().encode("sort_format");
        request.setAttribute("sort_format", sortFormat);

        String sortFormatReversed = LocaleMessageProvider.getInstance().encode("sort_format_reversed");
        request.setAttribute("sort_format_reversed", sortFormatReversed);

        String workspace = LocaleMessageProvider.getInstance().encode("mainWorkspace");
        request.setAttribute("workspace", workspace);

        String buttonDetails = LocaleMessageProvider.getInstance().encode("button_details");
        request.setAttribute("buttondetails", buttonDetails);

        String currentDate = LocaleMessageProvider.getInstance().encode("currentDate");
        request.setAttribute("currentdate", currentDate);

        String download = LocaleMessageProvider.getInstance().encode("download");
        request.setAttribute("download", download);
    }

    private static void invalidateSession(HttpSession session) {
        session.invalidate();
    }
}