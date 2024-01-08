//package com.solvd.universityapp.service.impl;
//
//import com.solvd.universityapp.bin.TestResult;
//import com.solvd.universityapp.dao.TestResultRepository;
//import com.solvd.universityapp.dao.impl.TestResultRepositoryImpl;
//import com.solvd.universityapp.service.TestResultService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.Set;
//
//public class TestResultServiceImpl implements TestResultService {
//
//    private static final Logger LOGGER = LogManager.getLogger(TestResultServiceImpl.class);
//    private TestResultRepository testResultRepository = new TestResultRepositoryImpl();
//
//    @Override
//    public Set<TestResult> findTestResultsByStudentId(Long studentId) {
//        return testResultRepository.findTestResultsByStudentId(studentId);
//    }
//}
