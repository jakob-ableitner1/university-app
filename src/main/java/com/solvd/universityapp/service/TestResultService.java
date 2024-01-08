package com.solvd.universityapp.service;

import com.solvd.universityapp.bin.TestResult;

import java.util.Set;

public interface TestResultService {

    public Set<TestResult> findTestResultsByStudentId(Long studentId);

}
