package com.solvd.universityapp.service;

import com.solvd.universityapp.bin.DegreeProgram;

import java.util.Set;

public interface DegreeProgramService {
    Set<DegreeProgram> findAll();
    DegreeProgram findById(Long id);
}
