package com.solvd.universityapp;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.service.CourseService;
import com.solvd.universityapp.service.mybatisimpl.CourseServiceImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CourseServiceTest {
    @Test(description = "Verify course service find by id method")
    public void verifyCourseFindById(){
        CourseService courseService = new CourseServiceImpl();
        Course course = courseService.findById(1L);

        Assert.assertNotNull(course);
        Assert.assertEquals(1L, course.getId());
        Assert.assertEquals("Intro to Java", course.getCourseDetail().getCourseName());
    }
}
