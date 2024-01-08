package com.solvd.universityapp.service;

import com.solvd.universityapp.bin.DegreeProgram;

import java.util.Set;

public interface DegreeProgramService {

    DegreeProgram findDegreeProgramById(Long Id);
    Set<DegreeProgram> findDegreePrograms();

}
