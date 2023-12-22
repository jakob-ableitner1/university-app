package com.solvd.universityapp.bin;

public class Course {

    private long id;
    private String professorName;
    private String roomNumber;
    private CourseDetail courseDetail;

    public Course() {
    }

    public Course(long id, String professorName, String roomNumber, CourseDetail courseDetail) {
        this.professorName = professorName;
        this.roomNumber = roomNumber;
        this.courseDetail = courseDetail;
    }

    public long getId() {
        return id;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public CourseDetail getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
    }
}
