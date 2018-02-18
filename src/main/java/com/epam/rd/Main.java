package com.epam.rd;


import com.epam.rd.controllers.schedule.Scheduler;
import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;
import org.apache.commons.dbcp2.BasicDataSource;


import java.sql.SQLException;
import java.text.ParseException;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws ParseException, SQLException {
        BasicDataSource dataSource = DataBaseUtility.getDataSource();

        ModelWorkOrder factoryOrder = new ModelWorkOrder(dataSource.getConnection());
        ModelCustomer factoryCustomer = new ModelCustomer();
        ModelPackage factoryPackage = new ModelPackage(1);

        EntityCustomer customer = factoryCustomer.loadById(101L);
        EntityPackage pack = factoryPackage.loadById(19L);

        Date dateToCharge = new Date();
        dateToCharge.setTime(dateToCharge.getTime() + 60 * 50);

        EntityWorkOrder order = new EntityWorkOrder(customer, pack, new Date(), dateToCharge, true);
        factoryOrder.create(order);
        System.out.println(factoryOrder.load(1).get(factoryOrder.load(1).size() -1).getDateEnd());

        EntityWorkOrder wo = factoryOrder.loadByDetails(customer, pack);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(30);
        executorService.scheduleAtFixedRate(new Thread(() -> factoryOrder.deleteByDetails(wo)),
                order.getDateEnd().getTime() - new Date().getTime(), 3000L, TimeUnit.MILLISECONDS);



    }
}