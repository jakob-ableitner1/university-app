package com.solvd.universityapp.menu;

import com.solvd.universityapp.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class CreateNewStudentMenu implements IMenu{

    private static final Logger LOGGER = LogManager.getLogger(CreateNewStudentMenu.class);

    @Override
    public String[] getInput(Scanner scanner) {
        LOGGER.info("Enter the following information");

        LOGGER.info("email");
        String email = scanner.nextLine();

        LOGGER.info("first name");
        String firstName = scanner.nextLine();

        LOGGER.info("last name");
        String lastName = scanner.nextLine();

        return new String[]{email, firstName, lastName};
    }
}
