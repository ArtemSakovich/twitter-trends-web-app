package com.company.twittertrendswebapp.model;

import java.time.LocalDateTime;

public class Tweet extends AEntity {

    private String tweetContent;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dateAndTime;
    private Double tweetWeight = 0.0;
    private State state;
    private String foundedSentiments;
    private Long id;

    public Tweet(String tweetContent, Double latitude, Double longitude, LocalDateTime dateAndTime) {
        this.tweetContent = tweetContent;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateAndTime = dateAndTime;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getDate() {
        return dateAndTime;
    }

    public void setDate(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Double getTweetWeight() {
        return tweetWeight;
    }

    public void setTweetWeight(Double tweetWeight) {
        this.tweetWeight = tweetWeight;
    }

    public void appendFoundedSentiment(String sentiment) {
        foundedSentiments += sentiment + ";";
    }

    public void addWeight(float tweetWeight) {
        this.tweetWeight += tweetWeight;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
        return "Tweet{" +
                "tweetContent='" + tweetContent + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", dateAndTime=" + dateAndTime +
                ", tweetWeight=" + tweetWeight +
                ", state=" + state +
                ", foundedSentiments='" + foundedSentiments + '\'' +
                ", id=" + id +
                '}';
    }
}

