package com.solvd.universityapp.service;

import com.solvd.universityapp.bin.Student;

import java.util.List;
import java.util.Scanner;

public interface StudentService {

    void create(Student student, Long degreeProgramId);

    Student findByEmail(String email);

    Student findById(Long id);

    void viewStudentInformation(Student student);

    void editStudentDegreeProgram(Student student, Long degreeProgramId);

    void addCourse(Student student, Long courseId);

    void removeCourse(Student student, Long courseId);

    void deleteByEmail(String email);

}
