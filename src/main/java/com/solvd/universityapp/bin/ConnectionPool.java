package com.solvd.universityapp.bin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ConnectionPool {

    private volatile static ConnectionPool connectionPool;
    private List<Connection> connections = new ArrayList<>();

    private ConnectionPool(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/university", "hbstudent", "hbstudent");
            connections.add(conn);
        } catch (ClassNotFoundException | SQLException e) {
             new RuntimeException();
        }
    }

    public synchronized static ConnectionPool getInstance(){
        if (connectionPool == null){
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public synchronized Connection getConnection() {

        if (connections.isEmpty()){
            return null;
        }

        Connection connection = connections.get(0);
        connections.remove(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection){
        connections.add(connection);
    }

}
