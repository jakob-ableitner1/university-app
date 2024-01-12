package com.solvd.universityapp.service.jdbcimpl;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.bin.exception.StudentNotFoundException;
import com.solvd.universityapp.dao.StudentDAO;
import com.solvd.universityapp.dao.jdbcimpl.StudentDAOImpl;
import com.solvd.universityapp.menu.CreateNewStudentMenu;
import com.solvd.universityapp.menu.IMenu;
import com.solvd.universityapp.service.*;
import com.solvd.universityapp.util.JaxbParser;
import com.solvd.universityapp.util.StAXParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class StudentServiceImpl implements StudentService {


    private static final Logger LOGGER = LogManager.getLogger(StudentServiceImpl.class);

    private static StudentDAO studentDAO = new StudentDAOImpl();

    private static DegreeProgramService degreeProgramService = new DegreeProgramServiceImpl();
    private static CourseService courseService = new CourseServiceImpl();
    private static CourseDetailService courseDetailService = new CourseDetailServiceImpl();

    @Override
    public void create(Student student, Long degreeProgramId) {

        Optional<Student> optStudent = studentDAO.findByEmail(student.getEmail());
        if (optStudent.isEmpty()){
            studentDAO.create(student, degreeProgramId);
        } else {
            LOGGER.info("This email is not unique");
        }
    }

    @Override
    public Student findByEmail(String email) {
        Student student = studentDAO.findByEmail(email).orElseThrow(() -> new StudentNotFoundException("student not found with email of " + email));
        student.getCourses().stream().forEach(course -> course.setCourseDetail(courseDetailService.findById(course.getCourseDetail().getId())));
        addXMLDataToStudent(student);
        return student;
    }

    @Override
    public Student findById(Long id) {
        Student student = studentDAO.findById(id).orElseThrow(() -> new StudentNotFoundException("student not found with id of " + id));
        student.getCourses().stream().forEach(course -> course.setCourseDetail(courseDetailService.findById(course.getCourseDetail().getId())));
        addXMLDataToStudent(student);
        return student;
    }

    private void addXMLDataToStudent(Student student){
        student.setAddress(StAXParser.findAddressById(student.getAddress().getId()));
        student.getCourses().stream().forEach(course -> course.setTerm(JaxbParser.findTermById(course.getTerm().getId())));
    }


    @Override
    public void updateStudentDegreeProgram(Student student, Long degreeProgramId) {
        DegreeProgram degreeProgram = degreeProgramService.findById(degreeProgramId);
        student.setDegreeProgram(degreeProgram);
        studentDAO.updateById(student, degreeProgram.getId(), student.getId());
    }

    @Override
    public void addCourse(Student student, Long courseId) {
        Course course = courseService.findById(courseId);

        if(!student.getCourses().contains(course)){
            student.getCourses().add(course);
            studentDAO.addCourse(student.getId(), courseId);
        } else {
            LOGGER.info("This student has already enrolled in this course");
        }
    }

    @Override
    public void removeCourse(Student student, Long courseId) {
        Course course = courseService.findById(courseId);

        if(student.getCourses().contains(course)){
            student.getCourses().remove(course);
            studentDAO.removeCourse(student.getId(), courseId);
        } else {
            LOGGER.info("This student is not enrolled in this course");
        }
    }

    @Override
    public void deleteByEmail(String email){
        Optional<Student> optStudent = studentDAO.findByEmail(email);

        if(optStudent.isPresent()){
            studentDAO.deleteById(optStudent.get().getId());
        } else {
            LOGGER.info("No students with this email");
        }
    }
}
