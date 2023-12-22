package com.solvd.universityapp.bin;

public class CourseDetail {

    private long id;
    private String courseName;
    private int numberOfCredits;
    private String departmentName;

    public CourseDetail() {
    }

    public CourseDetail(long id, String courseName, int numberOfCredits, String departmentName) {
        this.courseName = courseName;
        this.numberOfCredits = numberOfCredits;
        this.departmentName = departmentName;
    }

    public long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
