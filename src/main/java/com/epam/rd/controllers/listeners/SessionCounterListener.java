package com.epam.rd.controllers.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounterListener implements HttpSessionListener {
    protected static final Logger logger = LoggerFactory.getLogger(SessionCounterListener.class);
    private static int totalActiveSessions;

    public static int getTotalActiveSession(){
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        totalActiveSessions++;
        logger.info("SessionCreated. Total active sessions: " + getTotalActiveSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        logger.info("SessionDestroyed. Total active sessions: " + getTotalActiveSession());
    }
}
