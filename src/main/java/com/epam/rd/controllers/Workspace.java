package com.epam.rd.controllers;

import com.epam.rd.ConnectionPool;
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
import java.sql.SQLException;
import java.util.List;

public class Workspace extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
//                    showNewForm(request, response);
                    break;
                case "/insert":
                    //                   insertBook(request, response);
                    break;
                case "/delete":
                    //                   deleteBook(request, response);
                    break;
                case "/edit":
                    //                   showEditForm(request, response);
                    break;
                case "/update":
//                    updateBook(request, response);
                    break;
                default:
                    listProduct(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        long customerId = (long) session.getAttribute("customer_id");
        List<EntityWorkOrder> listOrders = modelWorkOrder.load(1);
        try {
            for (EntityWorkOrder workOrder : listOrders) {
                if (workOrder.getCustomer().getId() != customerId) {
                    listOrders.remove(workOrder);
                }
            }
        } catch (Exception ex) {
            System.out.println("Magic crutch");
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

        request.setAttribute("listOrders", listOrders);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
}
/*
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("addTariffAdmin.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingBook = bookDAO.getProduct(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("addTariffAdmin.jsp");
        request.setAttribute("product", existingBook);
        dispatcher.forward(request, response);

    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Product newBook = new Product(description, price);
        bookDAO.addProduct(newBook);
        response.sendRedirect("list");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");


        Product book = new Product(id, description, price);
        bookDAO.updateProduct(book);
        response.sendRedirect("list");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Product book = new Product(id);
        bookDAO.deleteProduct(book);
        response.sendRedirect("list");

    }*/