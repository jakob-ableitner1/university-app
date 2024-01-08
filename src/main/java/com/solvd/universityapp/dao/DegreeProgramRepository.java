package com.solvd.universityapp.dao;

import com.solvd.universityapp.bin.DegreeProgram;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.Set;

public interface DegreeProgramRepository {

    void create(DegreeProgram degreeProgram);

    Optional<DegreeProgram> findById(Long id);

    Set<DegreeProgram> findAll();

    void updateById(@Param("degreeProgram") DegreeProgram degreeProgram, @Param("id") Long id);

    void deleteById(Long id);

}
