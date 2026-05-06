package com.vypeensoft.routehelper.models;

/**
 * I think this class holds a certain Point and the distance to that from the current location.
 */
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

    public String getPointId() {
        return point.getPointId();
    }

    public String getTimestamp() {
        return point.getTimestamp();
    }
}
