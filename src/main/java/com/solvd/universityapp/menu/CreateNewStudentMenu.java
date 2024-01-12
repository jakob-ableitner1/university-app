package com.solvd.universityapp.menu;

import com.solvd.universityapp.App;
import com.solvd.universityapp.bin.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class CreateNewStudentMenu implements IMenu {

    private static final Logger LOGGER = LogManager.getLogger(CreateNewStudentMenu.class);

    @Override
    public Student getInput(Scanner scanner) {

        Student student = new Student();

        LOGGER.info("Enter the following information");
        LOGGER.info("email");
        student.setEmail(scanner.nextLine());
        LOGGER.info("first name");
        student.setFirstName(scanner.nextLine());
        LOGGER.info("last name");
        student.setLastName(scanner.nextLine());

        return student;
    }
}
