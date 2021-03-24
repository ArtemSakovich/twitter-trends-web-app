package com.company.twittertrendswebapp.controller;

import com.company.twittertrendswebapp.model.State;
import com.company.twittertrendswebapp.service.FinalData;
import com.company.twittertrendswebapp.service.TweetParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class StateController {
    @Autowired
    private TweetParser tweetParser;
    @Autowired
    private FinalData finalData;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/states")
    public List<State> getAllStates() throws FileNotFoundException {
        return finalData.getFinalData();
    }
}
