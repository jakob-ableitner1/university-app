package com.solvd.universityapp.bin;

public class Professor {

    private String firstName;
    private String lastName;
    private String departmentName;
    private Boolean departmentHead;

    public Professor(){}

    public Professor(String firstName, String lastName, String departmentName, Boolean departmentHead) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentName = departmentName;
        this.departmentHead = departmentHead;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Boolean getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(Boolean departmentHead) {
        this.departmentHead = departmentHead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Professor professor = (Professor) o;

        if (firstName != null ? !firstName.equals(professor.firstName) : professor.firstName != null) return false;
        if (lastName != null ? !lastName.equals(professor.lastName) : professor.lastName != null) return false;
        if (departmentName != null ? !departmentName.equals(professor.departmentName) : professor.departmentName != null)
            return false;
        return departmentHead != null ? departmentHead.equals(professor.departmentHead) : professor.departmentHead == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (departmentHead != null ? departmentHead.hashCode() : 0);
        return result;
    }
}
