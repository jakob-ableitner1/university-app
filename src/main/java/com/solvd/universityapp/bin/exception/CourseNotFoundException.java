package com.solvd.universityapp.bin.exception;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(String message){
        super(message);
    }

}
