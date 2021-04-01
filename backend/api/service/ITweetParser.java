package com.company.twittertrendswebapp.api.service;

import java.io.FileNotFoundException;

public interface ITweetParser {
    void parseTweets(String fileName) throws FileNotFoundException;
}
