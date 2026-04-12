package com.travel.routehelper.models;

public class Point {
    private String name;
    private double latitude;
    private double longitude;
    private String timestamp;

    public Point(String name, double latitude, double longitude, String timestamp) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getTimestamp() { return timestamp; }
}
