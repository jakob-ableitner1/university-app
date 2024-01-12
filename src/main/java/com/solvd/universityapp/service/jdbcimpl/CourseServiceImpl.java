package com.solvd.universityapp.service.jdbcimpl;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.exception.CourseNotFoundException;
import com.solvd.universityapp.dao.CourseDAO;
import com.solvd.universityapp.dao.jdbcimpl.CourseDAOImpl;
import com.solvd.universityapp.service.CourseService;

import java.util.Set;

public class CourseServiceImpl implements CourseService {

    private CourseDAO courseDAO = new CourseDAOImpl();

    @Override
    public Set<Course> findAll() {
        return courseDAO.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseDAO.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));
    }
}
