package com.epam.rd.controllers;

import com.epam.rd.controllers.schedule.Scheduler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ControllerScheduleWorkOrder extends HttpServlet {

    @Override
    public void init() throws ServletException {
        new Thread(() -> new Scheduler().addTasks()).start();
    }
}
