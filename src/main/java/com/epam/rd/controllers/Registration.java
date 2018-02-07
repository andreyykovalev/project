package com.epam.rd.controllers;

import com.epam.rd.ConnectionPool;
import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.service.DaoUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionPool connectionPool = new ConnectionPool();


        String url = "/register.jsp";

        String action = request.getParameter("action");
        if (action.equals("show")) {
            url = "/register.jsp";
        } else if (action.equals("add")) {

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            double balance = 0.0;

            EntityCustomer customer = EntityCustomer.builder()
                    .firstname(firstName)
                    .lastname(lastName)
                    .mail(email)
                    .password(password)
                    .balance(balance).build();

            ModelCustomer modelCustomer = new ModelCustomer();
            List<EntityCustomer> users = modelCustomer.load();


            String message = "";
            boolean isEverythingFilled = true;
            boolean isEmailExists = false;
            boolean isEmailMatches = true;

            Pattern pattern = Pattern.compile(".+@.+\\..+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.find()) {
                message = "Wrong email format. Please, try again";
                isEmailMatches = false;
            }

            Pattern pattern2 = Pattern.compile(".{8,}", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(password);
            if (!matcher2.find()) {
                message = "Password should contain at least 8 symbols";
                isEmailMatches = false;
            }


            for (int i = 0; i < users.size(); i++) {
                String userEmail = users.get(i).getMail();
                if (userEmail.equals(email)) {
                    message = "This email already exists in the system. Please try another one.";
                    url = "/register.jsp";
                    isEmailExists = true;
                }
            }

            if (lastName == null || firstName == null || email == null || password == null
                    || lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                message = "Please fill out all four text boxes.";
                url = "/register.jsp";
                isEverythingFilled = false;
            }


            if (isEverythingFilled && !isEmailExists && isEmailMatches) {
                message = "";
                url = "/main";
                try {
                    ModelCustomer modelCustomerCreate = new ModelCustomer(connectionPool.setUpPool().getConnection());
                    modelCustomerCreate.create(customer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            request.setAttribute("customer", customer);
            request.setAttribute("message", message);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
