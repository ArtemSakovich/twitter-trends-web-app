package com.company.twittertrendswebapp.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Top> points = new ArrayList<>();

    public void setPoints(Top point) {
        points.add(point);
    }
    public int getLength(){
        return points.size();
    }
    public Top getTop(int i){
        return points.get(i);
    }
    @Override
    public String toString() {
        return "Polygon{" +
                "points=" + points +
                '}';
    }
}
