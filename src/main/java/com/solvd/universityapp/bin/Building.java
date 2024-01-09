package com.solvd.universityapp.bin;

import java.util.List;

public class Building {

    private String name;

    private Long addressId;

    private List<Room> rooms;

    public Building(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Building{" +
                "name='" + name + '\'' +
                ", addressId=" + addressId +
                ", rooms=" + rooms +
                '}';
    }
}
