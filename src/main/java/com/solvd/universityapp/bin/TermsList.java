package com.solvd.universityapp.bin;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "termsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class TermsList {

    @XmlElement(name = "term")
    private List<Term> terms;

    public TermsList(){}

    public TermsList(List<Term> terms) {
        this.terms = terms;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
}
