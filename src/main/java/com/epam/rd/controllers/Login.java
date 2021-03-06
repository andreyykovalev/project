package com.epam.rd.controllers;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.entity.EntityCustomer;

import com.epam.rd.util.LocaleMessageProvider;
import com.epam.rd.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.rd.util.AttributesLocalizer.getLang;


public class Login extends HttpServlet {
    protected static final Logger logger = LoggerFactory.getLogger(Login.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "/login.jsp";

        String email = request.getParameter("email");
        String password = new String(request.getParameter("password").getBytes("ISO-8859-1"), "utf-8");

        EntityCustomer user = new EntityCustomer(email, password);
        String message = "";


        if (email != null && password != null) {

            ModelCustomer factory = new ModelCustomer();
            List<EntityCustomer> users = factory.load();

            boolean isUserFound = false;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getMail() != null && users.get(i).getPassword() != null) {
                    String userEmail = users.get(i).getMail();
                    String userPassword = users.get(i).getPassword();
                    if (userEmail.equals(email) && userPassword.equals(PasswordUtil.hashPassword(password))) {
                        message = "";
                        url = "/main";
                        isUserFound = true;
                        logger.info(users.get(i).getFirstname() + " " + users.get(i).getLastname() + " logged in");
                    }
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

                logger.info("Login error due to wrong input data");

                for (int i = 0; i < users.size(); i++) {
                    String userEmail = users.get(i).getMail();

                    if (!userEmail.equals(email)) {
                        message = LocaleMessageProvider.getInstance().encode("no_such_email");
                        url = "/error";
                    }
                }

                for (int i = 0; i < users.size(); i++) {
                    String userEmail = users.get(i).getMail();
                    String userPassword = users.get(i).getPassword();

                    if (userEmail.equals(email) && !userPassword.equals(PasswordUtil.hashPassword(password))) {
                        message = LocaleMessageProvider.getInstance().encode("wrong_password");
                        url = "/error";
                    }
                }
            }
        }

        session.setAttribute("user", user);
        session.setAttribute("message", message);

        response.sendRedirect(url);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pageLangRequest = request.getParameter("lang");

        getLang(pageLangRequest, request, session);
        String url = "/login.jsp";

        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
