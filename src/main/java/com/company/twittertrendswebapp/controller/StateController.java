package com.company.twittertrendswebapp.controller;

import com.company.twittertrendswebapp.model.State;
import com.company.twittertrendswebapp.service.FinalData;
import com.company.twittertrendswebapp.service.TwitterPathPropertyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class StateController {
    @Autowired
    private FinalData finalData;
    @Autowired
    private TwitterPathPropertyResolver twitterPathPropertyResolver;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/topicTweets/{topicId}")
    public List<State> getTweetsData(@PathVariable String topicId) throws FileNotFoundException {
        String filePath = twitterPathPropertyResolver.getProperties().getProperty(topicId);
        return finalData.getFinalData("src/main/resources/" + filePath);
    }
}
