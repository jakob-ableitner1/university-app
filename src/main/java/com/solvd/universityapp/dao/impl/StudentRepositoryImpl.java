package com.solvd.universityapp.dao.impl;

import com.solvd.universityapp.bin.ConnectionPool;
import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.dao.StudentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    public List<Student> getStudents() {
        Connection connection = CONNECTION_POOL.getConnection();
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from students");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String major = rs.getString("major");

                students.add(new Student(id, firstName, lastName, major));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get student list");
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    public Student getStudentById(long id){
        Connection connection = CONNECTION_POOL.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from students where id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String major = rs.getString("major");

                return new Student(id, firstName, lastName, major);
            }
        } catch (SQLException e){
            throw new RuntimeException("Unable to get student");
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }
}
