package com.solvd.universityapp.bin;

import jakarta.xml.bind.annotation.*;

import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class Term {

    @XmlAttribute(name = "id")
    private Long id;

    private String termName;

    private Date startDate;

    private Date endDate;

    public Term(){}

    public Term(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", termName='" + termName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
