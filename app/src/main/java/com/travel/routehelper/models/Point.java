package com.travel.routehelper.models;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private String name;
    private double latitude;
    private double longitude;
    private String timestamp;
    private List<String> types;

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

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
