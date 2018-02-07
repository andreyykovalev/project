package com.epam.rd.controllers.admin;

import com.epam.rd.model.ModelUser;
import com.epam.rd.model.entity.EntityUser;
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

        String url = "/admin/Login.jsp";
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        EntityUser user = EntityUser.builder().login(email).password(password).build();
        String message = "";

        if (email != null && password != null) {

            ModelUser modelUser = new ModelUser();
            List<EntityUser> users = modelUser.load();

            boolean isUserFound = false;
            for (int i = 0; i < users.size(); i++) {
                String userEmail = users.get(i).getLogin();
                String userPassword = users.get(i).getPassword();
                if (userEmail.equals(email) && userPassword.equals(password)) {
                    message = "";
                    url = "/admin/main";
                    isUserFound = true;
                }
            }

            if (isUserFound) {
                EntityUser user1 = modelUser.loadByLogin(email);
                if (user1 != null) {
                    session.setAttribute("admin_id", user1.getId());
                    session.setAttribute("admin", user1);
                }
            }

            if (!isUserFound) {
                for (int i = 0; i < users.size(); i++) {
                    String userEmail = users.get(i).getLogin();

                    if (!userEmail.equals(email)) {
                        String noSuchEmail = LocaleMessageProvider.getInstance().encode("no_such_email"); // Hey look here
                        message = noSuchEmail;
                        url = "/admin/Login.jsp";
                    }

                }

                for (int i = 0; i < users.size(); i++) {
                    String userEmail = users.get(i).getLogin();
                    String userPassword = users.get(i).getPassword();

                    if (userEmail.equals(email) && !userPassword.equals(password)) {
                        String wrongPassword = LocaleMessageProvider.getInstance().encode("wrong_password");
                        message = wrongPassword;
                        url = "/admin/Login.jsp";
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
}
