package com.solvd.universityapp.bin;

import java.time.LocalTime;

public class Time {

    private DayOfTheWeek dayOfTheWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public Time(){}

    public Time(DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (dayOfTheWeek != time.dayOfTheWeek) return false;
        if (startTime != null ? !startTime.equals(time.startTime) : time.startTime != null) return false;
        return endTime != null ? endTime.equals(time.endTime) : time.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = dayOfTheWeek != null ? dayOfTheWeek.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }
}
