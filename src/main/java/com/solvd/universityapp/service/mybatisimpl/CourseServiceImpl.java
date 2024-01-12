package com.solvd.universityapp.service.mybatisimpl;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.exception.CourseNotFoundException;
import com.solvd.universityapp.dao.CourseDAO;
import com.solvd.universityapp.service.CourseService;
import com.solvd.universityapp.util.MybatisDAOFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.Set;

public class CourseServiceImpl implements CourseService {
    @Override
    public Set<Course> findAll() {
        try (SqlSession sqlSession = MybatisDAOFactory.getSessionFactory().openSession(true)) {
            CourseDAO courseDAO = sqlSession.getMapper(CourseDAO.class);
            return courseDAO.findAll();
        }
    }

    @Override
    public Course findById(Long id) {
        try (SqlSession sqlSession = MybatisDAOFactory.getSessionFactory().openSession(true)) {
            CourseDAO courseDAO = sqlSession.getMapper(CourseDAO.class);
            return courseDAO.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));
        }
    }
}
