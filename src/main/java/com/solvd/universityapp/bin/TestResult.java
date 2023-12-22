package com.solvd.universityapp.bin;

public class TestResult {

    private long id;
    private int result;
    private String subject;
    private Student student;

    public TestResult() {
    }

    public TestResult(long id, int result, String subject, Student student) {
        this.result = result;
        this.subject = subject;
        this.student = student;
    }

    public long getId() {
        return id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
