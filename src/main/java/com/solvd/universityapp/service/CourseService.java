package com.solvd.universityapp.service;

import com.solvd.universityapp.bin.Course;

import java.util.Set;

public interface CourseService {
    Set<Course> findAll();
    Course findById(Long id);
}
