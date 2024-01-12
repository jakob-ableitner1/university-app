package com.solvd.universityapp.service.jdbcimpl;

import com.solvd.universityapp.bin.CourseDetail;
import com.solvd.universityapp.dao.CourseDetailDAO;
import com.solvd.universityapp.dao.jdbcimpl.CourseDetailDAOImpl;
import com.solvd.universityapp.service.CourseDetailService;

public class CourseDetailServiceImpl implements CourseDetailService {

    private CourseDetailDAO courseDetailDAO = new CourseDetailDAOImpl();

    @Override
    public CourseDetail findById(Long id) {
        return courseDetailDAO.findById(id).orElseThrow(() -> new RuntimeException("Course detail not found with id " + id));
    }
}
