package com.company.twittertrendswebapp.api.service;

import com.company.twittertrendswebapp.model.Tweet;

import java.util.List;

public interface ITweetAnalyzer {
    void analyzeTwits(List<Tweet> allTweets);
}
