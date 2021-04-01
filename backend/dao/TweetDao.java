package com.company.twittertrendswebapp.dao;

import com.company.twittertrendswebapp.api.dao.ITweetDao;
import com.company.twittertrendswebapp.model.Tweet;
import org.springframework.stereotype.Repository;

@Repository
public class TweetDao extends AbstractDao<Tweet> implements ITweetDao {
}
