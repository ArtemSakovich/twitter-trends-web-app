package com.company.twittertrendswebapp.model;

import java.util.List;

public class State extends AEntity{
    private String stateName;
    private List<Polygon> polygons;
    private Integer amountOfTweets = 1;
    private Double stateWeight = 0.0;
    private Long id;

    public State(String stateName, List<Polygon> polygons){
        this.stateName = stateName;
        this.polygons = polygons;
    }

    public void setName(String stateName) {
        this.stateName = stateName;
    }

    public void setPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public Integer getAmountOfTweets() {
        return amountOfTweets;
    }

    public void addAmountOfTweets() {
        amountOfTweets++;
    }

    public List<Polygon> getPolygons(){
        return polygons;
    }

    public String getStateName(){
        return stateName;
    }

    public Double getStateWeight() {
        return stateWeight / amountOfTweets;
    }

    public void setStateWeight(Double tweetWeight) {
        stateWeight += tweetWeight;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return stateName + " " + getStateWeight();
    }

    public void clearData() {
        amountOfTweets = 1;
        stateWeight = 0.0;
    }
}
