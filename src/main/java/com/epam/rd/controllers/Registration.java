package com.epam.rd.controllers;

import com.epam.rd.DataBaseUtility;
import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.util.LocaleMessageProvider;
import com.epam.rd.util.PasswordUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.rd.util.AttributesLocalizer.getLang;

public class Registration extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        String url = "/register.jsp";
        String action = request.getParameter("action");

        if (action.equals("add")) {

            String firstName = new String(request.getParameter("firstName").getBytes("ISO-8859-1"), "utf-8");
            String lastName = new String(request.getParameter("lastName").getBytes("ISO-8859-1"), "utf-8");
            String email = new String(request.getParameter("email").getBytes("ISO-8859-1"), "utf-8");
            String password = new String(request.getParameter("password").getBytes("ISO-8859-1"), "utf-8");
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
                message = LocaleMessageProvider.getInstance().encode("wrong_email_format");
                url = "/issue";
                isEmailMatches = false;
            }


            Pattern pattern3 = Pattern.compile("^[а-яА-Я]{30}|[a-zA-Z]{30}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher3 = pattern3.matcher(firstName);
            Matcher matcher4 = pattern3.matcher(lastName);
            if (!matcher3.find() || !matcher4.find()) {
                message = LocaleMessageProvider.getInstance().encode("name_issue");
                url = "/issue";
                isEmailMatches = false;
            }

            Pattern pattern2 = Pattern.compile(".{8,}", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(password);
            if (!matcher2.find()) {
                message = LocaleMessageProvider.getInstance().encode("password_too_short");
                url = "/issue";
                isEmailMatches = false;
            }


            for (int i = 0; i < users.size(); i++) {
                String userEmail = users.get(i).getMail();
                if (userEmail.equals(email)) {
                    message = LocaleMessageProvider.getInstance().encode("email_already_exists");
                    url = "/issue";
                    isEmailExists = true;
                }
            }

            if (lastName == null || firstName == null || email == null || password == null
                    || lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                message = LocaleMessageProvider.getInstance().encode("empty_fields");
                url = "/issue";
                isEverythingFilled = false;
            }


            if (isEverythingFilled && !isEmailExists && isEmailMatches) {
                message = "";
                url = "/main";
                BasicDataSource dataSource = DataBaseUtility.getDataSource();
                try {
                    ModelCustomer modelCustomerCreate = new ModelCustomer(dataSource.getConnection());


                    EntityCustomer customerWithHashedPassword = EntityCustomer.builder()
                            .firstname(firstName)
                            .lastname(lastName)
                            .mail(email)
                            .password(PasswordUtil.hashPassword(password))
                            .balance(balance).build();

                    modelCustomerCreate.create(customerWithHashedPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            session.setAttribute("customer", customer);
            session.setAttribute("message", message);
        }
        response.sendRedirect(url);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pageLangRequest = request.getParameter("lang");

        getLang(pageLangRequest, request, session);

        String url = "/register.jsp";

        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);

    }
}
