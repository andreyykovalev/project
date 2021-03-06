package com.epam.rd.controllers.schedule;

import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityWorkOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Scheduler {

    private static final Logger logger = LogManager.getLogger(Scheduler.class);

        private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(30);

    public void addTasks() {
        logger.error("PLANNING SCHEDULE");
        ModelWorkOrder wo = new ModelWorkOrder();
        List<EntityWorkOrder> list = wo.load(1);

        list.forEach((EntityWorkOrder e) -> executorService.scheduleAtFixedRate(() -> wo.forceCharge(e.getId()),
                e.getDateEnd().getTime() - new Date().getTime(), 2592000000L , TimeUnit.MILLISECONDS));
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
