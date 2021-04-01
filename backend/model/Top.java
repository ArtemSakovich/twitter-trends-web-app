package com.company.twittertrendswebapp.model;

public class Top {
    private double X;
    private double Y;


    public Double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public Double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    @Override
    public String toString() {
        return "Top{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}