package com.solvd.universityapp.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class RetrieveEmailMenu implements IMenu{

    private static final Logger LOGGER = LogManager.getLogger(RetrieveEmailMenu.class);

    @Override
    public String[] getInput(Scanner scanner) {
        LOGGER.info("Enter email address of student");
        return new String[]{scanner.nextLine()};
    }
}
