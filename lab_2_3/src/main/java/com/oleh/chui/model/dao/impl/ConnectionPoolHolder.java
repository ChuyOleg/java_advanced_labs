package com.oleh.chui.model.dao.impl;

import com.sun.istack.internal.NotNull;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPoolHolder {

    private static final String DB_PROPERTIES = "C:/Users/User/java_advanced_labs/lab_2_3/src/main/resources/db.properties";
    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try (FileReader reader = new FileReader(DB_PROPERTIES)) {
                        Properties p = new Properties();
                        p.load(reader);
                        BasicDataSource ds = new BasicDataSource();
                        ds.setUrl(p.getProperty("db.url"));
                        ds.setUsername(p.getProperty("db.username"));
                        ds.setPassword(p.getProperty("db.password"));
                        ds.setMinIdle(Integer.parseInt(p.getProperty("db.min.idle")));
                        ds.setMaxIdle(Integer.parseInt(p.getProperty("db.max.idle")));
                        ds.setMaxOpenPreparedStatements(Integer.parseInt(
                                p.getProperty("db.max.open.prepared.statement")));
                        ds.setDriverClassName(p.getProperty("db.driver.class.name"));
                        dataSource = ds;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during connection to DB", e);
        }
    }

    public static void closeConnection(@NotNull Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during closing connection to DB", e);
        }
    }

}
