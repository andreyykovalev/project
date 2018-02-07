package com.epam.rd.controllers.admin;


import com.epam.rd.model.ModelCustomer;

import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomersManaging extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        HttpSession session = request.getSession();
        EntityUser admin = (EntityUser) session.getAttribute("admin");
        request.setAttribute("admin", admin);

        try {
            switch (action) {
                default:
                    listProduct(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        ModelCustomer modelCustomer = new ModelCustomer();
        List<EntityCustomer> listProducts = modelCustomer.load();
        request.setAttribute("listProduct", listProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Customers.jsp");
        dispatcher.forward(request, response);
    }
}