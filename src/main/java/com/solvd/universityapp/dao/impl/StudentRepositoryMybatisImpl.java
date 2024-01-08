package com.solvd.universityapp.dao.impl;

import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.dao.PersistenceConfig;
import com.solvd.universityapp.dao.StudentRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class StudentRepositoryMybatisImpl implements StudentRepository {

    @Override
    public void create(Student student, Long degreeProgramId) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)) {
            StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
            studentRepository.create(student, degreeProgramId);
        }
    }

    @Override
    public Optional<Student> findById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)) {
            StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
            return studentRepository.findById(id);
        }
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)) {
            StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
            return studentRepository.findByEmail(email);
        }
    }

    @Override
    public void updateById(Student student, Long degreeProgramId, Long studentId) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)) {
            StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
            studentRepository.updateById(student, degreeProgramId, studentId);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)) {
            StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
            studentRepository.deleteById(id);
        }
    }

    @Override
    public void addCourse(Long studentId, Long courseId) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)) {
            StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
            studentRepository.addCourse(studentId, courseId);
        }
    }

    @Override
    public void removeCourse(Long studentId, Long courseId) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)) {
            StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
            studentRepository.removeCourse(studentId, courseId);
        }
    }
}
