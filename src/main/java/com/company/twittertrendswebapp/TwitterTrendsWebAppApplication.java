package com.company.twittertrendswebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.LogManager;

@SpringBootApplication
public class TwitterTrendsWebAppApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TwitterTrendsWebAppApplication.class, args);
    }

}
