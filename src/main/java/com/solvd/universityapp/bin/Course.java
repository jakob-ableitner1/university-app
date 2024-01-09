package com.solvd.universityapp.bin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Course {

    private Long id;
    private CourseDetail courseDetail;
    private Term term;
    private Set<Professor> professors = new HashSet<>();
    private Set<Time> times = new HashSet<>();

    public Course(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseDetail getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
    }

    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    public Set<Time> getTimes() {
        return times;
    }

    public void setTimes(Set<Time> times) {
        this.times = times;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseDetail=" + courseDetail +
                ", term=" + term +
                ", professors=" + professors +
                ", times=" + times +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (courseDetail != null ? !courseDetail.equals(course.courseDetail) : course.courseDetail != null)
            return false;
        if (professors != null ? !professors.equals(course.professors) : course.professors != null) return false;
        return times != null ? times.equals(course.times) : course.times == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (courseDetail != null ? courseDetail.hashCode() : 0);
        result = 31 * result + (professors != null ? professors.hashCode() : 0);
        result = 31 * result + (times != null ? times.hashCode() : 0);
        return result;
    }
}
