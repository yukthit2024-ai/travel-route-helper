package com.travel.routehelper.models;

public class PointWithDistance {
    private final Point point;
    private final double distance;

    public PointWithDistance(Point point, double distance) {
        this.point = point;
        this.distance = distance;
    }

    public Point getPoint() {
        return point;
    }

    public double getDistance() {
        return distance;
    }

    public String getTimestamp() {
        return point.getTimestamp();
    }
}
