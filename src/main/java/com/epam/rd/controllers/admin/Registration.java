package com.epam.rd.controllers.admin;

import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelUser;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityUser;

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

        String url = "/admin/Register.jsp";

        String action = request.getParameter("action");
        if (action.equals("show")) {
            url = "/admin/Register.jsp";
        } else if (action.equals("add")) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Integer level = Integer.parseInt(request.getParameter("level"));

            EntityUser customer = EntityUser.builder()
                    .login(email)
                    .password(password)
                    .level(level).build();

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
                    url = "/admin/Register.jsp";
                    isEmailExists = true;
                }
            }

            if (email == null || password == null || level == null
                    || email.isEmpty() || password.isEmpty()) {
                message = "Please fill out all four text boxes.";
                url = "/admin/Register.jsp";
                isEverythingFilled = false;
            }


            if (isEverythingFilled && !isEmailExists && isEmailMatches) {
                message = "Registration successful";
                url = "/admin/Register.jsp";
                try {
                    ModelUser modelUser = new ModelUser();
                    modelUser.create(customer);
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
