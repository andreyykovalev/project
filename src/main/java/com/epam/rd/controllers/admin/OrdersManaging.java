package com.epam.rd.controllers.admin;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.ModelWorkOrder;

import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityUser;
import com.epam.rd.model.entity.EntityWorkOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersManaging extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /*private DaoProduct bookDAO;*/

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
                case "/orders/delete":
                    deleteOrder(request, response);
                    break;
                case "/orders/edit":
                    showEditForm(request, response);
                    break;
                case "/orders/update":
                    updateOrder(request, response);
                    break;
                case "/orders/list":
                    listOrders(request, response);
                    break;
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        List<EntityWorkOrder> listOrders = modelWorkOrder.load(1);

        request.setAttribute("listProduct", listOrders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Orders.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        long id = Integer.parseInt(request.getParameter("id"));
        EntityWorkOrder existingOrder = modelWorkOrder.load(id, 1);
        RequestDispatcher dispatcher = request.getRequestDispatcher("EditOrder.jsp");
        request.setAttribute("product", existingOrder);
        dispatcher.forward(request, response);

    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        ModelCustomer modelCustomer = new ModelCustomer();
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        ModelPackage modelPackage = new ModelPackage(1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        long id = Long.parseLong(request.getParameter("id"));
        Date dateEnd = format.parse(request.getParameter("date"));
        Integer status  = Integer.parseInt(request.getParameter("status"));
        boolean isActivated;
        if(status == 1){
            isActivated = true;
        }else isActivated = false;

        EntityWorkOrder workOrder = modelWorkOrder.load(id, 1);

        EntityCustomer cust = modelCustomer.loadById(workOrder.getCustomer().getId());
        EntityPackage pack = modelPackage.loadById(workOrder.getPackages().getId());

        EntityWorkOrder newOrder = EntityWorkOrder.builder()
                .customer(cust)
                .packages(pack)
                .dateEnd(dateEnd)
                .status(isActivated)
                .createdAt(workOrder.getCreatedAt())
                .id(workOrder.getId())
                .build();


        modelWorkOrder.update(newOrder);
        response.sendRedirect("list");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        long id = Integer.parseInt(request.getParameter("id"));
        modelWorkOrder.forceCharge(id);
        response.sendRedirect("list");
    }
}