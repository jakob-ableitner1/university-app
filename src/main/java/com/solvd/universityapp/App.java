package com.solvd.universityapp;

import com.solvd.universityapp.bin.ConnectionPool;
import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.service.StudentService;
import com.solvd.universityapp.service.impl.StudentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class App {
    public static void main(String[] args){

        StudentService studentService = new StudentServiceImpl();
        final Logger LOGGER = LogManager.getLogger(App.class);

        List<Student> studentsList = studentService.getAllStudents();
        studentsList.stream().forEach(student -> LOGGER.info(student));


//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//
//        try{
//            Connection connection = connectionPool.getConnection();
//            PreparedStatement ps = connection.prepareStatement("insert into passports (number) values (?)", Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, "123");
//            ps.executeUpdate();
//            Long id = 1L;
//
//            connection.commit();
//        } catch (Exception e) {
//            connection.rollback();
//        } finally {
//            connection.setAutoCommit(true);
//        }
//
//
//    public App() throws SQLException {
//        }




    }

}
