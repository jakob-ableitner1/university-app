package com.solvd.universityapp.menu;

import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.service.DegreeProgramService;
import com.solvd.universityapp.util.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.Set;

public class DegreeProgramMenu implements IMenu{

    private static final Logger LOGGER = LogManager.getLogger(DegreeProgramMenu.class);
    private static DegreeProgramService degreeProgramService = ServiceFactory.getDegreeProgramService();

    @Override
    public Long getInput(Scanner scanner) {
        Set<DegreeProgram> degreePrograms = degreeProgramService.findAll();
        LOGGER.info("Available degree programs");
        LOGGER.info("Degree Program Id, Degree Program Name, Total Credits");
        degreePrograms.stream().forEach(degreeProgram -> LOGGER.info(degreeProgram.getId() + ", " + degreeProgram.getDegreeProgramName() + ", " + degreeProgram.getTotalCredits()));

        Long degreeProgramInput = scanner.nextLong();
        scanner.nextLine();

        return degreeProgramInput;
    }
}
