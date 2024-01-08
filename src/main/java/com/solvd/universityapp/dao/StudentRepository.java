package com.solvd.universityapp.dao;
import com.solvd.universityapp.bin.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface StudentRepository {

    void create(@Param("student") Student student, @Param("degreeProgramId") Long degreeProgramId);

    Optional<Student> findById(Long id);

    Optional<Student> findByEmail(String email);

    void updateById(@Param("student") Student student, @Param("degreeProgramId") Long degreeProgramId, @Param("studentId") Long studentId);

    void deleteById(Long id);

    void addCourse(@Param("studentId") Long studentId,@Param("courseId") Long courseId);

    void removeCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
