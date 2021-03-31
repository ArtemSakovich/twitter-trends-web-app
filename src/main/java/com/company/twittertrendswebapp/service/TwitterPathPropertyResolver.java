package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.exceptions.AppException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Component
public class TwitterPathPropertyResolver {
    private final String PROPS_PATH = "tweetTopic.properties";
    Properties properties;

    @PostConstruct
    public void init() throws AppException {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(PROPS_PATH)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
