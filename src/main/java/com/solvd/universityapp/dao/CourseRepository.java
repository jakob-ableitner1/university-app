package com.solvd.universityapp.dao;

import com.solvd.universityapp.bin.Course;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.Set;

public interface CourseRepository {

    void create(@Param("course") Course course, @Param("courseDetailId") Long courseDetailId);

    Optional<Course> findById(Long id);

    Set<Course> findAll();

    void updateById(@Param("course") Course course, @Param("courseDetailId") Long courseDetailId, @Param("courseId") Long courseId);

    void deleteById(Long id);
}
