package com.travel.routehelper.models;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private String name;
    private double latitude;
    private double longitude;
    private String timestamp;
    private List<String> types;

    private double currentDistance = -1;
    private double previousDistance = -1;
    private boolean deleted = false;

    public Point(String name, double latitude, double longitude, String timestamp, List<String> types) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.types = types != null ? types : new ArrayList<>();
    }

    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getTimestamp() { return timestamp; }
    public List<String> getTypes() {
        if (types == null) types = new ArrayList<>();
        return types;
    }

    public double getCurrentDistance() { return currentDistance; }
    public void setCurrentDistance(double currentDistance) { this.currentDistance = currentDistance; }
    public double getPreviousDistance() { return previousDistance; }
    public void setPreviousDistance(double previousDistance) { this.previousDistance = previousDistance; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
