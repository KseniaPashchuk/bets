package com.epam.bets.pool;

import com.epam.bets.exception.DBException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

class PoolManager {
    String url;
    int poolSize;
    Properties properties;
    private static final Logger LOGGER = LogManager.getLogger(PoolManager.class);

    PoolManager() {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("database");
            url = resource.getString("db.url");
            poolSize = Integer.parseInt(resource.getString("db.poolSize"));
            properties = new Properties();
            properties.put("user", resource.getString("db.user"));
            properties.put("password", resource.getString("db.password"));
            properties.put("autoReconnect", "true");
            properties.put("characterEncoding", resource.getString("db.encoding"));
            properties.put("useUnicode", resource.getString("db.useUnicode"));
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Can not find no properties file " + e.getMessage());
            throw new RuntimeException("Can not find no properties file " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "Can not register driver" + e.getMessage());
            throw new RuntimeException("Can not register driver " + e.getMessage());
        }
    }

    ProxyConnection getConnection() throws DBException {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = new ProxyConnection(DriverManager.getConnection(url, properties));
        } catch (SQLException e) {
            throw new DBException("Can not create connection " + e.getMessage());
        }
        return proxyConnection;
    }

    void deregisterDrivers() throws DBException {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            throw new DBException("Can not deregister driver " + e.getMessage());
        }
    }
}
