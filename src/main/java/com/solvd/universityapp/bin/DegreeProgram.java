package com.solvd.universityapp.bin;

public class DegreeProgram {

    private Long id;
    private String degreeProgramName;
    private short totalCredits;

    public DegreeProgram(){}

    public DegreeProgram(Long id, String degreeProgramName, short totalCredits) {
        this.id = id;
        this.degreeProgramName = degreeProgramName;
        this.totalCredits = totalCredits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegreeProgramName() {
        return degreeProgramName;
    }

    public void setDegreeProgramName(String degreeProgramName) {
        this.degreeProgramName = degreeProgramName;
    }

    public short getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(short totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String toString() {
        return "DegreeProgram{" +
                "id=" + id +
                ", degreeProgramName='" + degreeProgramName + '\'' +
                ", totalCredits=" + totalCredits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DegreeProgram that = (DegreeProgram) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}