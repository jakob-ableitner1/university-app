package com.solvd.universityapp.bin;

public enum DayOfTheWeek {

    MONDAY("monday", 1),
    TUESDAY("tuesday", 2),
    WEDNESDAY("wednesday", 3),
    THURSDAY("thursday", 4),
    FRIDAY("friday", 5),
    SATURDAY("saturday", 6),
    SUNDAY("sunday", 7);

    private String displayName;
    private int number;

    DayOfTheWeek(String displayName, int number){
        this.displayName = displayName;
        this.number = number;
    }

}
