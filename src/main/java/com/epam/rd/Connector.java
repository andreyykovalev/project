package com.epam.rd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//not used now, but connection pool can be replaced with this class
public class Connector {

    public Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in =
                     Connector.class.getClassLoader().getResourceAsStream("db.properties")) {
            props.load(in);
        }
        String drivers = props.getProperty("db.driver");
        if (drivers != null) {
            System.setProperty("db.driver", drivers);
        }
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }
}
