package com.epam.rd.controllers;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.entity.EntityCustomer;

import com.epam.rd.util.LanguageDefiner;
import com.epam.rd.util.LocaleMessageProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String pageLanguage = (String) session.getAttribute("lang");
        session.setAttribute("lang", pageLanguage);

        LanguageDefiner.definePageLang(pageLanguage);

        localizePageAttributes(request);


        String url = "/login.jsp";
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        EntityCustomer user = new EntityCustomer(email, password);
        String message = "";


        if (email != null && password != null) {

            ModelCustomer factory = new ModelCustomer();
            List<EntityCustomer> users = factory.load();

            boolean isUserFound = false;
            for (int i = 0; i < users.size(); i++) {
                String userEmail = users.get(i).getMail();
                String userPassword = users.get(i).getPassword();
                if (userEmail.equals(email) && userPassword.equals(password)) {
                    message = "";
                    url = "/main";
                    isUserFound = true;
                }
            }

            if (isUserFound) {
                EntityCustomer user1 = factory.loadByEmail(email);
                if (user1 != null) {
                    session.setAttribute("customer", user1);
                    session.setAttribute("customer_id", user1.getId());
                    session.setAttribute("keycustomer", user1.getFirstname() + " " + user1.getLastname());
                }
            }

            if (!isUserFound) {
                for (int i = 0; i < users.size(); i++) {
                    String userEmail = users.get(i).getMail();

                    if (!userEmail.equals(email)) {
                        String noSuchEmail = LocaleMessageProvider.getInstance().encode("no_such_email"); // Hey look here
                        message = noSuchEmail;
                        url = "/login.jsp";
                    }

                }

                for (int i = 0; i < users.size(); i++) {
                    String userEmail = users.get(i).getMail();
                    String userPassword = users.get(i).getPassword();

                    if (userEmail.equals(email) && !userPassword.equals(password)) {
                        String wrongPassword = LocaleMessageProvider.getInstance().encode("wrong_password");
                        message = wrongPassword;
                        url = "/login.jsp";
                    }
                }
            }
        }

        request.setAttribute("user", user);
        request.setAttribute("message", message);


        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

    }

    private static void localizePageAttributes(HttpServletRequest request) {
        String welcomeMessage = LocaleMessageProvider.getInstance().encode("login_welcome");
        request.setAttribute("login_welcome", welcomeMessage);

        String main = LocaleMessageProvider.getInstance().encode("main"); // Hey look here
        request.setAttribute("main", main);

        String login = LocaleMessageProvider.getInstance().encode("login");
        request.setAttribute("login", login);

        String register = LocaleMessageProvider.getInstance().encode("register");
        request.setAttribute("register", register);

        String settings = LocaleMessageProvider.getInstance().encode("settings");
        request.setAttribute("settings", settings);
    }
}
