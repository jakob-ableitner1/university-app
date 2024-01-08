package com.solvd.universityapp.dao;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {

    private volatile static ConnectionPool connectionPool;
    private List<Connection> connections = new ArrayList<>();

    private ConnectionPool() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/university", "hbstudent", "hbstudent");
            connections.add(conn);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public synchronized static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public synchronized Connection getConnection() {

        while (connections.isEmpty()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                return null;
            }
        }

        Connection connection = connections.get(0);
        connections.remove(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        connections.add(connection);
    }
}

