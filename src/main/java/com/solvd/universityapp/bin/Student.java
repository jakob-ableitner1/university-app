package com.solvd.universityapp.bin;

public class Student {

    private long id;
    private String first_name;
    private String last_name;
    private String major;

    public Student() {
    }

    public Student(long id, String first_name, String last_name, String major) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.major = major;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
}
