package com.solvd.universityapp.service.mybatisimpl;

import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.bin.exception.DegreeProgramNotFoundException;
import com.solvd.universityapp.dao.DegreeProgramDAO;
import com.solvd.universityapp.service.DegreeProgramService;
import com.solvd.universityapp.util.MybatisDAOFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.Set;

public class DegreeProgramServiceImpl implements DegreeProgramService {

    @Override
    public Set<DegreeProgram> findAll() {
        try (SqlSession sqlSession = MybatisDAOFactory.getSessionFactory().openSession(true)) {
            DegreeProgramDAO degreeProgramDAO = sqlSession.getMapper(DegreeProgramDAO.class);
            return degreeProgramDAO.findAll();
        }
    }

    @Override
    public DegreeProgram findById(Long id) {
        try (SqlSession sqlSession = MybatisDAOFactory.getSessionFactory().openSession(true)) {
            DegreeProgramDAO degreeProgramDAO = sqlSession.getMapper(DegreeProgramDAO.class);
            return degreeProgramDAO.findById(id).orElseThrow(() -> new DegreeProgramNotFoundException("No degree programs found with id of " + id));
        }
    }
}
