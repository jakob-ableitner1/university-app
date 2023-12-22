package com.solvd.universityapp.dao;

import com.solvd.universityapp.bin.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> getStudents();
    Student getStudentById(long id);
}
