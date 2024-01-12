package com.solvd.universityapp.util;

import com.solvd.universityapp.bin.Term;
import com.solvd.universityapp.bin.TermsList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class JaxbParser {

    public static Term findTermById(Long id){
        List<Term> terms = getTermsList();
        Optional<Term> optTerm = terms.stream().filter(term -> term.getId() == id).findFirst();
        return optTerm.isPresent() ? optTerm.get() : null;
    }

    public static List<Term> getTermsList(){
        File file = new File("src/main/resources/datastorage/terms-list.xml");

        try {
            JAXBContext context = JAXBContext.newInstance(TermsList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            TermsList termsList = (TermsList) unmarshaller.unmarshal(file);
            return termsList.getTerms();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
