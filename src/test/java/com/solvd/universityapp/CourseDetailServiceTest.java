package com.solvd.universityapp;

import com.solvd.universityapp.bin.CourseDetail;
import com.solvd.universityapp.service.CourseDetailService;
import com.solvd.universityapp.service.mybatisimpl.CourseDetailServiceImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CourseDetailServiceTest {

    @DataProvider
    public Object[][] provider(){
        return new Object[][]{
                {1L, "Intro to Java", 3},
                {2L, "How to Build an SQL Query", 2},
                {3L, "Business for Beginers", 3}
        };
    }

    @Test (description = "Verify course detail service find by id method", dataProvider = "provider")
    public void verifyCourseDetailFindById(Long input, String courseName, int credits){
        CourseDetailService courseDetailService = new CourseDetailServiceImpl();
        CourseDetail courseDetail = courseDetailService.findById(input);

        Assert.assertEquals(input, courseDetail.getId());
        Assert.assertEquals(courseName, courseDetail.getCourseName());
        Assert.assertEquals(credits, courseDetail.getNumberOfCredits());
    }

}
