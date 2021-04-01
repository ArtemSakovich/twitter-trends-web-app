package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.api.dao.IStateDao;
import com.company.twittertrendswebapp.api.dao.ITweetDao;
import com.company.twittertrendswebapp.api.service.IFinalData;
import com.company.twittertrendswebapp.api.service.ITweetAnalyzer;
import com.company.twittertrendswebapp.api.service.ITweetLocationDeterminant;
import com.company.twittertrendswebapp.api.service.ITweetParser;
import com.company.twittertrendswebapp.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
@Component
public class FinalData implements IFinalData {
    private List<State> finalData;
    @Autowired
    private IStateDao stateDao;
    @Autowired
    private ITweetDao tweetDao;
    @Autowired
    private ITweetParser tweetParser;
    @Autowired
    private ITweetAnalyzer tweetAnalyzer;
    @Autowired
    private ITweetLocationDeterminant tweetLocationDeterminant;

    @Override
    public List<State> getFinalData(String tweetsFileName) throws FileNotFoundException {
        tweetDao.clearData();
        tweetParser.parseTweets(tweetsFileName);
        tweetAnalyzer.analyzeTwits(tweetDao.getAll());
        tweetLocationDeterminant.clearData();
        tweetLocationDeterminant.determineTweetLocation();
        return finalData = new ArrayList<>(stateDao.getAll());
    }
}