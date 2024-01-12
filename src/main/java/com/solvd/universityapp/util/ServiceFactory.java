package com.solvd.universityapp.util;

import com.solvd.universityapp.service.CourseService;
import com.solvd.universityapp.service.DegreeProgramService;
import com.solvd.universityapp.service.StudentService;
import com.solvd.universityapp.service.jdbcimpl.CourseServiceImpl;
import com.solvd.universityapp.service.jdbcimpl.DegreeProgramServiceImpl;
import com.solvd.universityapp.service.jdbcimpl.StudentServiceImpl;

import java.io.*;
import java.util.Properties;

public class ServiceFactory {

    private final static String JDBC = "jdbc";
    private final static String MYBATIS = "mybatis";

    private static String serviceImpl;

    private static StudentService studentService;
    private static DegreeProgramService degreeProgramService;
    private static CourseService courseService;

    private ServiceFactory(){}

    static {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        try(InputStream inputStream = ServiceFactory.class.getClassLoader().getResourceAsStream(propFileName)){
            if (inputStream != null) {
                prop.load(inputStream);
                serviceImpl = prop.getProperty("serviceImpl");
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static StudentService getStudentService(){
        if (studentService != null){
            return studentService;
        }
        if (JDBC.equals(serviceImpl)){
            studentService = new StudentServiceImpl();
        } else if (MYBATIS.equals(serviceImpl)){
            studentService = new com.solvd.universityapp.service.mybatisimpl.StudentServiceImpl();
        } else {
            throw new RuntimeException();
        }
        return studentService;
    }

    public static DegreeProgramService getDegreeProgramService(){
        if (degreeProgramService != null){
            return degreeProgramService;
        }
        if (JDBC.equals(serviceImpl)){
            degreeProgramService = new DegreeProgramServiceImpl();
        } else if (MYBATIS.equals(serviceImpl)){
            degreeProgramService = new com.solvd.universityapp.service.mybatisimpl.DegreeProgramServiceImpl();
        } else {
            throw new RuntimeException("Service implementation not recognized");
        }
        return degreeProgramService;
    }

    public static CourseService getCourseService(){
        if (courseService != null){
            return courseService;
        }
        if (JDBC.equals(serviceImpl)){
            courseService = new CourseServiceImpl();
        } else if (MYBATIS.equals(serviceImpl)){
            courseService = new com.solvd.universityapp.service.mybatisimpl.CourseServiceImpl();
        } else {
            throw new RuntimeException();
        }
        return courseService;
    }
}
