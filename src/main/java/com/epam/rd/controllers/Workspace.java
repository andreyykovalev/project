package com.epam.rd.controllers;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityWorkOrder;
import com.epam.rd.util.LanguageDefiner;
import com.epam.rd.util.LocaleMessageProvider;

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

        String pageLangRequest = request.getParameter("lang");
        if (pageLangRequest == null) {
            String pageLanguage = (String) session.getAttribute("lang");
            session.setAttribute("lang", pageLanguage);
            LanguageDefiner.definePageLang(pageLanguage);
            localizePageAttributes(request);
        } else {
            session.setAttribute("lang", pageLangRequest);
            LanguageDefiner.definePageLang(pageLangRequest);
            localizePageAttributes(request);
        }


        EntityCustomer customer = (EntityCustomer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("/login.jsp");
        } else {
            try {
                String action = request.getServletPath();
                switch (action) {
                    case "/sort":
                 //       updatePackage(request, response);
                        break;
                    default:
                        listProduct(request, response);
                        break;
                }
            } catch(Exception ex){
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

    private static void localizePageAttributes(HttpServletRequest request) {


        String main = LocaleMessageProvider.getInstance().encode("main");
        request.setAttribute("main", main);

        String login = LocaleMessageProvider.getInstance().encode("login");
        request.setAttribute("login", login);

        String register = LocaleMessageProvider.getInstance().encode("register");
        request.setAttribute("register", register);

        String settings = LocaleMessageProvider.getInstance().encode("settings");
        request.setAttribute("settings", settings);

        String currentDate = LocaleMessageProvider.getInstance().encode("currentDate");
        request.setAttribute("currentdate", currentDate);

        String title = LocaleMessageProvider.getInstance().encode("workspaceTitle");
        request.setAttribute("workspacetitle", title);

        String workspacePrice = LocaleMessageProvider.getInstance().encode("workspacePrice");
        request.setAttribute("price", workspacePrice);

        String workspaceDescription = LocaleMessageProvider.getInstance().encode("workspaceDescription");
        request.setAttribute("description", workspaceDescription);

        String startDate = LocaleMessageProvider.getInstance().encode("startDate");
        request.setAttribute("startdate", startDate);

        String endDate = LocaleMessageProvider.getInstance().encode("endDate");
        request.setAttribute("enddate", endDate);

        String status = LocaleMessageProvider.getInstance().encode("status");
        request.setAttribute("status", status);

        String message = LocaleMessageProvider.getInstance().encode("workspaceMessage");
        request.setAttribute("workspacemessage", message);

        String amount = LocaleMessageProvider.getInstance().encode("amount");
        request.setAttribute("amount", amount);

        String replenish = LocaleMessageProvider.getInstance().encode("replenish");
        request.setAttribute("replenish", replenish);

        String balance = LocaleMessageProvider.getInstance().encode("balance");
        request.setAttribute("lablebalance", balance);


    }
}