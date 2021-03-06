package com.epam.rd.controllers.admin;


import com.epam.rd.model.ModelPackage;

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
        HttpSession session = request.getSession();

        String emailSes = (String) session.getAttribute("email");
        request.setAttribute("emailSes", emailSes);

        int langId = 1;
        ModelPackage factory = new ModelPackage(langId);
        List packages = factory.load();

        request.setAttribute("packages", packages);


        String url;
        RequestDispatcher rd;

        url = "admin/main.jsp";

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

    private static void invalidateSession(HttpSession session) {
        session.invalidate();
    }
}