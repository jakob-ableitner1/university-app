package com.solvd.universityapp.service.impl;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.exception.CourseNotFoundException;
import com.solvd.universityapp.dao.CourseRepository;
import com.solvd.universityapp.dao.impl.CourseRepositoryImpl;
import com.solvd.universityapp.service.CourseService;

import java.util.Set;

public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository = new CourseRepositoryImpl();

    @Override
    public Set<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));
    }
}
