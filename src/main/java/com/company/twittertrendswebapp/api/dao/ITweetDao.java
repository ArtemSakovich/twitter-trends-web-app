package com.company.twittertrendswebapp.api.dao;

import com.company.twittertrendswebapp.model.Tweet;

public interface ITweetDao extends IGenericDao<Tweet> {
    void clearData();
}
