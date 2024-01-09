package com.solvd.universityapp.service;

import com.solvd.universityapp.bin.Address;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class StAXHandler {
    public StringBuffer processXMLFile(File xmlFile){

        XMLEvent event = null;
        Characters characters = null;
        StringBuffer rawXML = new StringBuffer();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try(FileInputStream fileInputStream = new FileInputStream(xmlFile)){
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(fileInputStream);
            while(reader.hasNext()){
                event = reader.nextEvent();
                switch (event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        rawXML.append("<" + (((StartElement) event).getName()).getLocalPart() + ">");
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        characters = (Characters) event;
                        if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())){
                            rawXML.append(characters.getData());
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        rawXML.append("</" + (((EndElement) event).getName()).getLocalPart() + ">");
                        break;
                }
            }
        } catch(IOException | XMLStreamException e){
            throw new RuntimeException(e);
        }
        return rawXML;
    }

    public static Address findAddressById(Long id) {

        File file = new File("src/main/resources/addresses.xml");
        Address address = new Address(id);
        String currentElement = "";
        boolean hasMatchingId = false;

        XMLEvent event = null;
        Characters characters = null;
        StringBuffer rawXML = new StringBuffer();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(fileInputStream);
            while (reader.hasNext()) {
                event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        if((((StartElement) event).getName()).getLocalPart() == "address"){
                            if(((StartElement) event).getAttributeByName(QName.valueOf("id")).getValue().equals(id.toString())){
                                hasMatchingId = true;
                            }
                        } else{
                            currentElement = (((StartElement) event).getName()).getLocalPart();
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if(hasMatchingId){
                            characters = (Characters) event;
                            setAddressData(address, currentElement, characters);
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if(hasMatchingId && (((EndElement) event).getName()).getLocalPart() == "address"){
                            return address;
                        }
                        break;

                }
            }
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return address;
    }

    private static void setAddressData(Address address, String currentElement, Characters characters) {
        switch (currentElement) {
            case "streetLine":
                if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())) {
                    address.setStreetLine(characters.getData());
                }
                break;
            case "city":
                if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())) {
                    address.setCity(characters.getData());
                }
                break;
            case "state":
                if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())) {
                    address.setState(characters.getData());
                }
            case "country":
                if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())) {
                    address.setCountry(characters.getData());
                }
                break;
            case "zipCode":
                if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())) {
                    address.setZipCode(Integer.parseInt(characters.getData()));
                }
                break;
        }
    }
}
