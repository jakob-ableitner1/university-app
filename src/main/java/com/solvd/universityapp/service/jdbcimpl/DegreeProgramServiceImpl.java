package com.solvd.universityapp.service.jdbcimpl;

import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.bin.exception.DegreeProgramNotFoundException;
import com.solvd.universityapp.dao.DegreeProgramDAO;
import com.solvd.universityapp.dao.jdbcimpl.DegreeProgramDAOImpl;
import com.solvd.universityapp.service.DegreeProgramService;

import java.util.Set;

public class DegreeProgramServiceImpl implements DegreeProgramService {

    private DegreeProgramDAO degreeProgramDAO = new DegreeProgramDAOImpl();

    @Override
    public DegreeProgram findById(Long id) {
        return degreeProgramDAO.findById(id).orElseThrow(() -> new DegreeProgramNotFoundException("No degree programs found with id of " + id));
    }

    @Override
    public Set<DegreeProgram> findAll() {
        return degreeProgramDAO.findAll();
    }
}
