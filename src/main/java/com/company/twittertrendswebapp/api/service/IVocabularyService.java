package com.company.twittertrendswebapp.api.service;

import com.company.twittertrendswebapp.exceptions.AppException;
import com.company.twittertrendswebapp.model.Vocabulary;

public interface IVocabularyService {
    void init() throws AppException;

    float getSentimentWeight(String sentiment);

    Vocabulary getVocabularyInstance();
}
