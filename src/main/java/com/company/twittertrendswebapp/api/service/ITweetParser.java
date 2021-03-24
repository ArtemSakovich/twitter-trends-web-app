package com.company.twittertrendswebapp.api.service;

import java.io.FileNotFoundException;

public interface ITweetParser {
    void tweetParser(String fileName) throws FileNotFoundException;
}
