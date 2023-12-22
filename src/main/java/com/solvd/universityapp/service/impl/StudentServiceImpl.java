package com.solvd.universityapp.service.impl;

import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.dao.StudentRepository;
import com.solvd.universityapp.dao.impl.StudentRepositoryImpl;
import com.solvd.universityapp.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl() {
        studentRepository = new StudentRepositoryImpl();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getStudents();
    }
}
