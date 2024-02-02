package com.solvd.universityapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.universityapp.bin.Building;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class JSONMapper {

    private static final Logger LOGGER = LogManager.getLogger(JSONMapper.class);

    public static void viewBuildingAndRoomsDetails(){
        File file = new File("src/main/resources/datastorage/building.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            Building building = mapper.readValue(file, Building.class);
            LOGGER.info(building);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
