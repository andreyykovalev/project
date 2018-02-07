package com.epam.rd.model;

import com.epam.rd.ConnectionPool;
import com.mysql.jdbc.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Model {
    protected static final Logger logger = LogManager.getLogger(Model.class);
    Long lastId = 0L;

    Model() {
    }

    List<Map> query(String sql) {
        try (Connection connection = new ConnectionPool().setUpPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData md = resultSet.getMetaData();
            int columns = md.getColumnCount();
            ArrayList list = new ArrayList(50);
            while (resultSet.next()) {
                HashMap row = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), resultSet.getObject(i));
                }
                list.add(row);
            }
            preparedStatement.close();
            connection.close();
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(sql);
        }
        return null;
    }

    void update(String sql) {
        try (Connection connection = new ConnectionPool().setUpPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.executeUpdate();


            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                lastId = generatedKeys.getLong(1);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(sql);
        }
    }

    String packSet(String key, String value) {
        return " `" + key + "`='" + value + "' ";
    }

    String packSet(String key, Number value) {
        return " `" + key + "`='" + value + "' ";
    }

}
