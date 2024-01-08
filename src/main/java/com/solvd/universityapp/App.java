package com.solvd.universityapp;

import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.menu.CreateNewStudentMenu;
import com.solvd.universityapp.menu.IMenu;
import com.solvd.universityapp.menu.RetrieveEmailMenu;
import com.solvd.universityapp.service.CourseService;
import com.solvd.universityapp.service.DegreeProgramService;
import com.solvd.universityapp.service.StudentService;
import com.solvd.universityapp.service.impl.CourseServiceImpl;
import com.solvd.universityapp.service.impl.DegreeProgramServiceImpl;
import com.solvd.universityapp.service.impl.StudentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.Set;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    private static CreateNewStudentMenu createNewStudentMenu = new CreateNewStudentMenu();
    private static IMenu retrieveEmailMenu = new RetrieveEmailMenu();

    private static StudentService studentService = new StudentServiceImpl();
    private static DegreeProgramService degreeProgramService = new DegreeProgramServiceImpl();
    private static CourseService courseService = new CourseServiceImpl();
//    private static TestResultService testResultService = new TestResultServiceImpl();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean isLoopOpen = true;

        while(isLoopOpen){
            LOGGER.info("sign in - 1, create new student - 2, delete student - 3, end program - 4");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 1:
                    studentMenu(scanner);
                    break;
                case 2:
                    String[] studentInfo = createNewStudentMenu.getInput(scanner);
                    Student student = new Student();
                    student.setEmail(studentInfo[0]);
                    student.setFirstName(studentInfo[1]);
                    student.setLastName(studentInfo[2]);
                    Long degreeProgramInput = getDegreeProgramInput(scanner);
                    studentService.create(student, degreeProgramInput);
                    break;
                case 3:
                    String email = retrieveEmailMenu.getInput(scanner)[0];
                    studentService.deleteByEmail(email);
                    break;
                case 4:
                    isLoopOpen = false;
                    break;
            }
        }

        scanner.close();
    }

    private static void studentMenu(Scanner scanner){

        String inputEmail = retrieveEmailMenu.getInput(scanner)[0];
        Student student = studentService.findByEmail(inputEmail);
        LOGGER.info("Viewing " + student.getFirstName() + ' ' + student.getLastName() + "'s account");

        IMenu studentOptionMenu = (lambdaScanner) -> {
            LOGGER.info("view student information - 1, change/add degree program - 2, add/remove course - 3, quit program - 4");
            return new String[]{lambdaScanner.nextLine()};
        };

        boolean quitStudentOptionLoop = false;

        while(!quitStudentOptionLoop){
            String input = studentOptionMenu.getInput(scanner)[0];

            switch (input){
                case "1":
                    studentService.viewStudentInformation(student);
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

        Long degreeProgramInput = getDegreeProgramInput(scanner);
        studentService.editStudentDegreeProgram(student, degreeProgramInput);
    }

    private static void editStudentCourses(Student student, Scanner scanner){
        boolean isLoopOpen = true;

        while(isLoopOpen){
            LOGGER.info("Currently enrolled in the following courses. Id, Course Name, Credits");
            student.getCourses().stream().forEach(course -> LOGGER.info(course.getId() + ", " + course.getCourseDetail().getCourseName() + ", " + course.getCourseDetail().getNumberOfCredits()));
            LOGGER.info("Add course to student - 1, remove course from student - 2, back to student menu - 3");

            int input = scanner.nextInt();
            scanner.nextLine();
            switch(input){
                case 1:
                    LOGGER.info("Enter course id that you would like to enroll for. Id, Course Name, Credits");
                    courseService.findAll().stream().forEach(course -> LOGGER.info(course.getId() + ", " + course.getCourseDetail().getCourseName() + ", " + course.getCourseDetail().getNumberOfCredits()));
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

    private static Long getDegreeProgramInput(Scanner scanner){
        Set<DegreeProgram> degreePrograms = degreeProgramService.findDegreePrograms();
        LOGGER.info("Available degree programs");
        LOGGER.info("Degree Program Id, Degree Program Name, Total Credits");
        degreePrograms.stream().forEach(degreeProgram -> LOGGER.info(degreeProgram.getId() + ", " + degreeProgram.getDegreeProgramName() + ", " + degreeProgram.getTotalCredits()));

        Long degreeProgramInput = scanner.nextLong();
        scanner.nextLine();

        return degreeProgramInput;
    }
}
