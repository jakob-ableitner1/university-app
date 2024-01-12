package com.solvd.universityapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.universityapp.bin.*;
import com.solvd.universityapp.menu.CreateNewStudentMenu;
import com.solvd.universityapp.menu.DegreeProgramMenu;
import com.solvd.universityapp.menu.IMenu;
import com.solvd.universityapp.menu.RetrieveEmailMenu;
import com.solvd.universityapp.service.CourseService;
import com.solvd.universityapp.service.StudentService;
import com.solvd.universityapp.util.JSONMapper;
import com.solvd.universityapp.util.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.Set;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    private static IMenu createNewStudentMenu = new CreateNewStudentMenu();
    private static IMenu retrieveEmailMenu = new RetrieveEmailMenu();
    private static IMenu degreeProgramMenu = new DegreeProgramMenu();

    private static StudentService studentService = ServiceFactory.getStudentService();
    private static CourseService courseService = ServiceFactory.getCourseService();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean isLoopOpen = true;

        while(isLoopOpen){
            LOGGER.info("sign in - 1, create new student - 2, delete student - 3, view building details - 4, end program - 5");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 1:
                    studentMenu(scanner);
                    break;
                case 2:
                    Student student = (Student) createNewStudentMenu.getInput(scanner);
                    Long degreeProgramInput = (Long) degreeProgramMenu.getInput(scanner);
                    studentService.create(student, degreeProgramInput);
                    break;
                case 3:
                    String email = (String) retrieveEmailMenu.getInput(scanner);
                    studentService.deleteByEmail(email);
                    break;
                case 4:
                    JSONMapper.viewBuildingAndRoomsDetails();
                    break;
                case 5:
                    isLoopOpen = false;
                    break;
            }
        }

        scanner.close();
    }

    private static void studentMenu(Scanner scanner){

        String inputEmail = (String) retrieveEmailMenu.getInput(scanner);
        Student student = studentService.findByEmail(inputEmail);
        LOGGER.info("Viewing " + student.getFirstName() + ' ' + student.getLastName() + "'s account");

        IMenu studentOptionMenu = (lambdaScanner) -> {
            LOGGER.info("view student information - 1, change/add degree program - 2, add/remove course - 3, quit program - 4");
            return scanner.nextLine();
        };

        boolean quitStudentOptionLoop = false;

        while(!quitStudentOptionLoop){
            String input = (String) studentOptionMenu.getInput(scanner);

            switch (input){
                case "1":
                    LOGGER.info(student);
                    break;
                case "2":
                    editStudentDegreeProgram(student, scanner);
                    break;
                case "3":
                    editStudentCourses(student, scanner);
                    break;
                case "4":
                    quitStudentOptionLoop = true;
                    break;
                default:
                    LOGGER.warn("invalid input");
            }
        }
    }

    private static void editStudentDegreeProgram(Student student, Scanner scanner){
        LOGGER.info("Current degree program: " + student.getDegreeProgram().getDegreeProgramName() + ". Enter degree program id to change to");

        Long degreeProgramInput = (Long) degreeProgramMenu.getInput(scanner);
        studentService.updateStudentDegreeProgram(student, degreeProgramInput);
    }

    private static void editStudentCourses(Student student, Scanner scanner){
        boolean isLoopOpen = true;

        while(isLoopOpen){
            LOGGER.info("Currently enrolled in the following courses. Id, Course Name, Credits");
            student.getCourses().stream().forEach(course -> {
                if (course.getId() != 0){
                    LOGGER.info(course.getId() + ", " + course.getCourseDetail().getCourseName() + ", " + course.getCourseDetail().getNumberOfCredits());
                }});

            LOGGER.info("Add course to student - 1, remove course from student - 2, back to student menu - 3");

            int input = scanner.nextInt();
            scanner.nextLine();
            switch(input){
                case 1:
                    LOGGER.info("Enter course id that you would like to enroll for. Id, Course Name, Credits");
                    Set<Course> courses = courseService.findAll();
                    courseService.findAll().stream().forEach(course ->{
                        if(course.getId() != 0){
                            LOGGER.info(course.getId() + ", " + course.getCourseDetail().getCourseName() + ", " + course.getCourseDetail().getNumberOfCredits());
                        }});
                    Long courseIdInput = scanner.nextLong();
                    studentService.addCourse(student, courseIdInput);
                    break;
                case 2:
                    LOGGER.info("Enter course id that you would like to unenroll in");
                    courseIdInput = scanner.nextLong();
                    studentService.removeCourse(student, courseIdInput);
                    break;
                case 3:
                    isLoopOpen = false;
                    break;
            }
        }
    }
}
