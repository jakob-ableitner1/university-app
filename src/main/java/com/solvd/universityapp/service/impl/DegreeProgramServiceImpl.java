package com.solvd.universityapp.service.impl;

import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.bin.exception.DegreeProgramNotFoundException;
import com.solvd.universityapp.dao.DegreeProgramRepository;
import com.solvd.universityapp.dao.impl.DegreeProgramRepositoryImpl;
import com.solvd.universityapp.service.DegreeProgramService;

import java.util.Set;

public class DegreeProgramServiceImpl implements DegreeProgramService {

    private DegreeProgramRepository degreeProgramRepository = new DegreeProgramRepositoryImpl();

    @Override
    public DegreeProgram findDegreeProgramById(Long id) {
        return degreeProgramRepository.findById(id).orElseThrow(() -> new DegreeProgramNotFoundException("No degree programs found with id of " + id));
    }

    @Override
    public Set<DegreeProgram> findDegreePrograms() {
        return degreeProgramRepository.findAll();
    }
}
