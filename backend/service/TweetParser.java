package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.api.dao.ITweetDao;
import com.company.twittertrendswebapp.api.service.ITweetParser;
import com.company.twittertrendswebapp.model.Tweet;
import com.company.twittertrendswebapp.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;
@Component
public class TweetParser implements ITweetParser {
    @Autowired
    private ITweetDao tweetDao;

    @Override
    public void parseTweets(String fileName) throws FileNotFoundException {
        String tweetContent;
        Double latitude;
        Double longitude;
        LocalDateTime dateAndTime;

        Scanner in = new Scanner(new File(fileName));

        while (in.hasNextLine()) {
            String currentString = in.nextLine();
            latitude = Double.parseDouble(currentString.split("\t")[0].substring(1, currentString.indexOf(',')));
            longitude = Double.parseDouble(currentString.split("\t")[0].substring(currentString.indexOf(' '),
                    currentString.indexOf(']')));
            dateAndTime = LocalDateTime.parse(currentString.split("\t")[2].replace(' ', 'T'));
            tweetContent = currentString.split("\t")[3];

            Tweet tweet = new Tweet(tweetContent, latitude, longitude, dateAndTime);
            tweet.setId(IdGenerator.getInstance().generateId(tweetDao.getMaxId()));
            tweetDao.save(tweet);
        }
    }
}
