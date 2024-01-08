package com.solvd.universityapp.dao;

import com.solvd.universityapp.bin.TestResult;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface TestResultRepository {

    void create(@Param("testResult") TestResult testResult, @Param("studentId") Long studentId);

    Optional<TestResult> findTestResultById(Long id);

    void updateById(@Param("testResult") TestResult testResult, @Param("id") Long id);

    void deleteById(Long id);

}
