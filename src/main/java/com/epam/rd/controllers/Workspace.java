package com.epam.rd.controllers;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityWorkOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Workspace extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        EntityCustomer customer = (EntityCustomer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("/login.jsp");
        } else {

            try {
                listProduct(request, response);

            } catch (Exception ex) {
                throw new ServletException(ex);
            }
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        long customerId = (long) session.getAttribute("customer_id");
        List<EntityWorkOrder> listOrders = modelWorkOrder.load(1);
        List<EntityWorkOrder> listOrdersThisCustomer = new ArrayList<>();

        for (EntityWorkOrder workOrder : listOrders) {
            if (workOrder.getCustomer().getId() == customerId) {
                listOrdersThisCustomer.add(workOrder);
            }
        }
        String url = "/workspace.jsp";
        EntityCustomer customer = (EntityCustomer) session.getAttribute("customer");
        request.setAttribute("balance", customer.getBalance());
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("replenish")) {
                int amount = Integer.parseInt(request.getParameter("amount"));
                customer.setBalance(customer.getBalance() + amount);
                ModelCustomer modelCustomer = new ModelCustomer();
                modelCustomer.update(customer);
            }
        }

        request.setAttribute("listOrders", listOrdersThisCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
}