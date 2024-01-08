package com.solvd.universityapp.service.impl;

import com.solvd.universityapp.bin.CourseDetail;
import com.solvd.universityapp.dao.CourseDetailRepository;
import com.solvd.universityapp.dao.impl.CourseDetailRepositoryImpl;
import com.solvd.universityapp.service.CourseDetailService;

public class CourseDetailServiceImpl implements CourseDetailService {

    private CourseDetailRepository courseDetailRepository = new CourseDetailRepositoryImpl();

    @Override
    public CourseDetail findById(Long id) {
        return courseDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Course detail not found with id " + id));
    }
}
