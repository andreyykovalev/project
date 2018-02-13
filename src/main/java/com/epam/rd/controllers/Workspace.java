package com.epam.rd.controllers;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityWorkOrder;
import com.epam.rd.util.PackageSortByPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.rd.util.AttributesLocalizer.getLang;

public class Workspace extends HttpServlet {
    protected static final Logger logger = LoggerFactory.getLogger(Workspace.class);
    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String pageLangRequest = request.getParameter("lang");
        getLang(pageLangRequest, request, session);


        EntityCustomer customer = (EntityCustomer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("/login.jsp");
        } else {
            try {
                String action = request.getServletPath();
                switch (action) {
                    default:
                        listProduct(request, response);
                        logger.info("Showing a list of customer's packages");
                        break;
                }
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

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("replenish")) {
                int amount = Integer.parseInt(request.getParameter("amount"));
                customer.setBalance(customer.getBalance() + amount);
                ModelCustomer modelCustomer = new ModelCustomer();
                modelCustomer.update(customer);
                logger.info("Adding " + amount + " to " + customer.getLastname() + "'s account");
            }
        }
        request.setAttribute("balance", customer.getBalance());
        request.setAttribute("listOrders", listOrdersThisCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
}