package com.solvd.universityapp.service.mybatisimpl;

import com.solvd.universityapp.bin.CourseDetail;
import com.solvd.universityapp.dao.CourseDetailDAO;
import com.solvd.universityapp.service.CourseDetailService;
import com.solvd.universityapp.util.MybatisDAOFactory;
import org.apache.ibatis.session.SqlSession;

public class CourseDetailServiceImpl implements CourseDetailService {
    @Override
    public CourseDetail findById(Long id) {
        try (SqlSession sqlSession = MybatisDAOFactory.getSessionFactory().openSession(true)) {
            CourseDetailDAO courseDetailDAO = sqlSession.getMapper(CourseDetailDAO.class);
            return courseDetailDAO.findById(id).orElseThrow(() -> new RuntimeException("Course detail not found with id " + id));
        }
    }
}
