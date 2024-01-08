package com.solvd.universityapp.service.impl;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.bin.exception.StudentNotFoundException;
import com.solvd.universityapp.dao.StudentRepository;
import com.solvd.universityapp.dao.impl.StudentRepositoryImpl;
import com.solvd.universityapp.dao.impl.StudentRepositoryMybatisImpl;
import com.solvd.universityapp.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class StudentServiceImpl implements StudentService {


    private static final Logger LOGGER = LogManager.getLogger(StudentServiceImpl.class);

//    private StudentRepository studentRepository = new StudentRepositoryImpl();
    private StudentRepository studentRepository = new StudentRepositoryMybatisImpl();

    private DegreeProgramService degreeProgramService = new DegreeProgramServiceImpl();
//    private TestResultService testResultService = new TestResultServiceImpl();
    private CourseService courseService = new CourseServiceImpl();

    @Override
    public void create(Student student, Long degreeProgramId) {
        Optional<Student> optStudent = studentRepository.findByEmail(student.getEmail());
        if (optStudent.isEmpty()){
            studentRepository.create(student, degreeProgramId);
        } else {
            LOGGER.info("This email is not unique");
        }
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() -> new StudentNotFoundException("student not found with email of " + email));
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("student not found with id of " + id));
    }


    @Override
    public void editStudentDegreeProgram(Student student, Long degreeProgramId) {
        DegreeProgram degreeProgram = degreeProgramService.findDegreeProgramById(degreeProgramId);
        student.setDegreeProgram(degreeProgram);
        studentRepository.updateById(student, degreeProgram.getId(), student.getId());
    }

    @Override
    public void addCourse(Student student, Long courseId) {
        Course course = courseService.findById(courseId);

        if(!student.getCourses().contains(course)){
            student.getCourses().add(course);
            studentRepository.addCourse(student.getId(), courseId);
        } else {
            LOGGER.info("This student has already enrolled in this course");
        }
    }

    @Override
    public void removeCourse(Student student, Long courseId) {
        Course course = courseService.findById(courseId);

        if(student.getCourses().contains(course)){
            student.getCourses().remove(course);
            studentRepository.removeCourse(student.getId(), courseId);
        } else {
            LOGGER.info("This student has already enrolled in this course");
        }
    }

    @Override
    public void viewStudentInformation(Student student){
        LOGGER.info(student);
    }

    @Override
    public void deleteByEmail(String email){
        Optional<Student> optStudent = studentRepository.findByEmail(email);

        if(optStudent.isPresent()){
            studentRepository.deleteById(optStudent.get().getId());
        } else {
            LOGGER.info("No students with this email");
        }
    }
}
