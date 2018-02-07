package com.epam.rd.controllers;

import com.epam.rd.ConnectionPool;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ConnectionPool connectionPool = new ConnectionPool();
        ModelWorkOrder modelWorkOrder = null;
        try {
            modelWorkOrder = new ModelWorkOrder(connectionPool.setUpPool().getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id = request.getParameter("id");
        session.setAttribute("id", id);
        String pageLanguage = request.getParameter("lang");


        int langId;
        if (pageLanguage == null || pageLanguage.equals("en")) {
            langId = 1;
        } else langId = 2;

        ModelPackage modelPackage = new ModelPackage(langId);
        EntityPackage pack = modelPackage.loadById((long) Integer.parseInt(id));
        request.setAttribute("pack", pack);

        String message = "";

        String url = "/internet.jsp";

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("buy")) {
                Date dateToCharge = new Date();
                for (int i = 0; i < 30; i++) {
                    dateToCharge.setTime(dateToCharge.getTime() + 24 * 60 * 60 * 1000);
                }
                EntityCustomer customer = (EntityCustomer) session.getAttribute("customer");
                EntityWorkOrder order = new EntityWorkOrder(customer, pack, new Date(), dateToCharge);


                List<EntityWorkOrder> listOrders = new ArrayList<>();
                modelWorkOrder.load(1).forEach((o) -> {
                    if (o.getCustomer().getId().longValue() == customer.getId()) {
                        listOrders.add(o);
                    }
                });
//                for (EntityWorkOrder workOrder : listOrders) {
//                    if (workOrder.getCustomer().getId() != customer.getId()) {
//                        listOrders.remove(workOrder);
//                    }
//                }
                boolean IsOrderValid = true;
                for (EntityWorkOrder workOrderThisCust : listOrders) {
                    if (workOrderThisCust.getPackages().getType() == pack.getType()) {
                        message = "You've already got the service of this type";
                        IsOrderValid = false;
                    }
                }

                for (EntityWorkOrder workOrderThisCust : listOrders) {
                    if (workOrderThisCust.getCustomer().getBalance() < pack.getPrice()) {
                        message = "Are you crazy? Did you see the price?";
                        IsOrderValid = false;
                    }
                }
                if (IsOrderValid) {
                    modelWorkOrder.create(order);
                    url = "/workspace";
                }
            }
        }
        request.setAttribute("message", message);
        RequestDispatcher rd;

        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
