package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.api.dao.ITweetDao;
import com.company.twittertrendswebapp.api.service.ITweetAnalyzer;
import com.company.twittertrendswebapp.api.service.IVocabularyService;
import com.company.twittertrendswebapp.model.Tweet;
import com.company.twittertrendswebapp.model.Vocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TweetAnalyzer implements ITweetAnalyzer {
    private static final char SPACE_CHAR = ' ';
    @Autowired
    private ITweetDao tweetDao;
    @Autowired
    private IVocabularyService vocabularyService;

    private String normalizeBody(String body) {
        StringBuilder result = new StringBuilder();
        if (body.length() == 0) {
            return "";
        }
        for (int i = 0; i < body.length(); i++) {
            char substr = body.charAt(i);
            if ((substr >= 'a' && substr <= 'z') || (substr >= 'A' && substr <= 'Z') ||
                    (substr >= '0' && substr <= '9') || substr == '\'' || substr == '`') {
                result.append(substr);
            } else {
                result.append(SPACE_CHAR);
            }
        }
        return result.toString().trim();
    }

    private int analyzeSentimentAndReturnFoundedSentimentLength(String body, Vocabulary vocabulary,
                          IVocabularyService vocabularyService, int pos, int endPos, Tweet tweet) {
        int sentimentLength = 0;
        String sentiment = body.substring(pos, endPos).trim();
        while (sentiment.length() >= vocabulary.getMinSentimentLength()) {
            float sentimentWeight = vocabularyService.getSentimentWeight(sentiment);
            if (sentimentWeight != 0.0f) {
                sentimentLength = sentiment.length();
                tweet.appendFoundedSentiment(sentiment);
                tweet.addWeight(sentimentWeight);
                break;
            }
            int lastSpace = sentiment.lastIndexOf(SPACE_CHAR);
            if (lastSpace > 0) {
                sentiment = sentiment.substring(0, lastSpace).trim(); // last symbol before space
            } else {
                sentiment = "";
            }
        }
        return sentimentLength;
    }

    public void analyzeTwits(List<Tweet> allTweets) {
        Vocabulary vocabulary = vocabularyService.getVocabularyInstance();
        for (Tweet tweet : allTweets) {

            String body = tweet.getTweetContent();

            try {
                body = normalizeBody(body);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println(body);
            }
            int pos = 0;
            int bodySize = body.length();
            while (pos <= (bodySize - vocabulary.getMinSentimentLength()) && pos >= 0) {
                if (body.charAt(pos) == SPACE_CHAR) {
                    pos++;
                } else {
                    int endPos = Math.min(pos + vocabulary.getMaxSentimentLength(), bodySize);
                    pos += analyzeSentimentAndReturnFoundedSentimentLength(body, vocabulary,
                            vocabularyService, pos, endPos, tweet);
                    pos = body.indexOf(SPACE_CHAR, pos);
                }
            }
        }
    }
}

