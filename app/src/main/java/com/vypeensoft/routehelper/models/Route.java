package com.vypeensoft.routehelper.models;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String routeName;
    private String createdAt;
    private List<Point> points;

    public Route(String routeName, String createdAt) {
        this.routeName = routeName;
        this.createdAt = createdAt;
        this.points = new ArrayList<>();
    }

    public String getRouteName() { return routeName; }
    public String getCreatedAt() { return createdAt; }
    public List<Point> getPoints() { return points; }

    public void addPoint(Point point) {
        points.add(point);
    }
}
