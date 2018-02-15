package com.epam.rd.controllers;


import com.epam.rd.DataBaseUtility;
import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;
import com.epam.rd.util.LanguageDefiner;
import com.epam.rd.util.LocaleMessageProvider;
import org.apache.commons.dbcp2.BasicDataSource;
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

import java.util.Date;
import java.util.List;

import static com.epam.rd.util.AttributesLocalizer.getLang;

public class Product extends HttpServlet {
    protected static final Logger logger = LoggerFactory.getLogger(Product.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pageLangRequest = request.getParameter("lang");
        String pageLanguage;

        if (pageLangRequest == null) {
            String pageLanguageSession = (String) session.getAttribute("lang");
            session.setAttribute("lang", pageLanguageSession);
            LanguageDefiner.definePageLang(pageLanguageSession);
            pageLanguage = pageLanguageSession;
        } else {
            session.setAttribute("lang", pageLangRequest);
            LanguageDefiner.definePageLang(pageLangRequest);
            pageLanguage = pageLangRequest;
        }

        getLang(pageLangRequest, request, session);

        ModelWorkOrder modelWorkOrder = null;
        BasicDataSource dataSource = DataBaseUtility.getDataSource();
        try {
            modelWorkOrder = new ModelWorkOrder(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String id = request.getParameter("id");
        if (id != null) {
            session.setAttribute("id", id);
        }
        int langId;
        if (pageLanguage == null || pageLanguage.equals("en")) {
            langId = 1;
        } else langId = 2;

        EntityPackage pack = null;
        ModelPackage modelPackage = new ModelPackage(langId);

        if (id != null) {
            pack = modelPackage.loadById((long) Integer.parseInt(id));
            logger.info("Showing a page with desired package");
        } else {
            long sessionId = Long.parseLong((String) session.getAttribute("id"));
            pack = modelPackage.loadById(sessionId);
        }
        request.setAttribute("pack", pack);
        String message = "";

        String url = "/product.jsp";

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("buy")) {
                Date dateToCharge = new Date();
                for (int i = 0; i < 30; i++) {
                    dateToCharge.setTime(dateToCharge.getTime() + 24 * 60 * 60 * 1000);
                }
                EntityCustomer customer = (EntityCustomer) session.getAttribute("customer");
                boolean status = true;

                EntityWorkOrder order = new EntityWorkOrder(customer, pack, new Date(), dateToCharge, status);

                List<EntityWorkOrder> listOrders = new ArrayList<>();
                modelWorkOrder.load(1).forEach((o) -> {
                    if (o.getCustomer().getId().longValue() == customer.getId()) {
                        listOrders.add(o);
                    }
                });

                boolean IsOrderValid = true;
                for (EntityWorkOrder workOrderThisCust : listOrders) {
                    if (workOrderThisCust.getPackages().getType() == pack.getType() && workOrderThisCust.getStatus()) {
                        message = LocaleMessageProvider.getInstance().encode("package_exists");
                        IsOrderValid = false;
                        logger.info("Purchase failure. Order already exists");
                    }
                }

                    if (customer.getBalance() < pack.getPrice()) {
                        message = LocaleMessageProvider.getInstance().encode("low_balance");
                        IsOrderValid = false;
                        logger.info("Purchase failure. Not enough money");
                    }

                if (IsOrderValid) {
                    ModelCustomer factory = new ModelCustomer();
                    customer.setBalance(customer.getBalance() - pack.getPrice());
                    factory.update(customer);
                    modelWorkOrder.create(order);

                    url = "/workspace";
                    logger.info("Purchase success");

                }
            }
        }
        request.setAttribute("message", message);
        RequestDispatcher rd;

        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
