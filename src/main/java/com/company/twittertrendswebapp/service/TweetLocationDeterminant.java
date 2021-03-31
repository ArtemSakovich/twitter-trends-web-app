package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.api.dao.IStateDao;
import com.company.twittertrendswebapp.api.dao.ITweetDao;
import com.company.twittertrendswebapp.api.service.ITweetLocationDeterminant;
import com.company.twittertrendswebapp.model.Polygon;
import com.company.twittertrendswebapp.model.State;
import com.company.twittertrendswebapp.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

@Component
public class TweetLocationDeterminant implements ITweetLocationDeterminant {
    @Autowired
    private IStateDao stateDao;
    @Autowired
    private ITweetDao tweetDao;

    public void determineTweetLocation() {
        List<State> states = stateDao.getAll();
        List<Tweet> tweets = tweetDao.getAll();

        for (Tweet tweet : tweets) {
            double pointX = tweet.getLongitude();
            double pointY = tweet.getLatitude();
            boolean flag = false;
            for (State state : states) {
                List<Polygon> polygons = state.getPolygons();

                for (Polygon polygon : polygons) {
                    int number_crossings = 0;
                    for (int i = 0; i < polygon.getLength(); i++) {
                        if (i != polygon.getLength() - 1) {
                            double k = ((polygon.getTop(i).getY() - polygon.getTop(i + 1).getY()) /
                                    (polygon.getTop(i).getX() - polygon.getTop(i + 1).getX()));
                            double b = polygon.getTop(i).getY() - 1.0 * k * polygon.getTop(i).getX();


                            if (k > 0 && k != POSITIVE_INFINITY && pointY >= (1.0 * k * pointX + b) &&
                                    ((pointY >= polygon.getTop(i).getY() && pointY <= polygon.getTop(i + 1).getY())
                                            || (pointY <= polygon.getTop(i).getY() && pointY >=
                                            polygon.getTop(i + 1).getY()))) {
                                number_crossings++;
                            }
                            if (k < 0 && k != NEGATIVE_INFINITY && pointY <= (1.0 * k * pointX + b) &&
                                    ((pointY >= polygon.getTop(i).getY() && pointY <= polygon.getTop(i + 1).getY())
                                            || (pointY <= polygon.getTop(i).getY() && pointY >=
                                            polygon.getTop(i + 1).getY()))) {
                                number_crossings++;
                            }
                            if ((k == POSITIVE_INFINITY || k == NEGATIVE_INFINITY) && pointX <=
                                    polygon.getTop(i).getX() && ((pointY >= polygon.getTop(i).getY() && pointY <=
                                    polygon.getTop(i + 1).getY()) || (pointY <= polygon.getTop(i).getY() && pointY >=
                                    polygon.getTop(i + 1).getY()))) {
                                number_crossings++;
                            }

                        } else {
                            double k = ((polygon.getTop(i).getY() - polygon.getTop(0).getY()) /
                                    (polygon.getTop(i).getX()
                                            - polygon.getTop(0).getX()));
                            double b = polygon.getTop(i).getY() - 1.0 * k * polygon.getTop(i).getX();

                            if (k > 0 && k != POSITIVE_INFINITY && pointY >= (1.0 * k * pointX + b) &&
                                    ((pointY >= polygon.getTop(i).getY() && pointY <= polygon.getTop(0).getY()) ||
                                            (pointY <= polygon.getTop(i).getY() && pointY >=
                                                    polygon.getTop(0).getY()))) {
                                number_crossings++;
                            }
                            if (k < 0 && k != NEGATIVE_INFINITY && pointY <= (1.0 * k * pointX + b) &&
                                    ((pointY >= polygon.getTop(i).getY() && pointY <= polygon.getTop(0).getY()) ||
                                            (pointY <= polygon.getTop(i).getY() && pointY >=
                                                    polygon.getTop(0).getY()))) {
                                number_crossings++;
                            }
                            if ((k == POSITIVE_INFINITY || k == NEGATIVE_INFINITY) && pointX <=
                                    polygon.getTop(i).getX() && ((pointY >= polygon.getTop(i).getY() && pointY <=
                                    polygon.getTop(0).getY()) || (pointY <= polygon.getTop(i).getY() && pointY >=
                                    polygon.getTop(0).getY()))) {
                                number_crossings++;
                            }
                        }

                    }
                    if (number_crossings % 2 != 0) {
                        flag = true;
                        tweet.setState(state);
                        state.setStateWeight(tweet.getTweetWeight());
                        state.addAmountOfTweets();
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    @Override
    public void clearData() {
        stateDao.getAll().forEach(State::clearData);
    }
}




