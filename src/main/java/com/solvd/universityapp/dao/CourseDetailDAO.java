package com.solvd.universityapp.dao;

import com.solvd.universityapp.bin.CourseDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface CourseDetailDAO {

    void create(CourseDetail courseDetail);

    Optional<CourseDetail> findById(Long id);

    void updateById(@Param("courseDetail") CourseDetail courseDetail, @Param("id") Long id);

    void deleteById(Long id);
}
