package com.example.inclassassignment10_thomass;

public class Holiday {

    private String location;
    private int costs;
    private boolean car, plane;

    public Holiday() { //empty constructor to work

    }

    public Holiday(String location, int costs, boolean car, boolean plane) {
        this.location = location;
        this.costs = costs;
        this.car = car;
        this.plane = plane;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCosts() {
        return costs;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public boolean isCar() {
        return car;
    }

    public void setCar(boolean car) {
        this.car = car;
    }

    public boolean isPlane() {
        return plane;
    }

    public void setPlane(boolean plane) {
        this.plane = plane;


    }
}