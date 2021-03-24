package com.company.twittertrendswebapp.model;

import java.util.Arrays;

public class Vocabulary {
    private int minSentimentLength;
    private int maxSentimentLength;
    private String[] storageSentiments;
    private float[] storageWeights;

    public int getMinSentimentLength() {
        return minSentimentLength;
    }

    public void setMinSentimentLength(int minSentimentLength) {
        this.minSentimentLength = minSentimentLength;
    }

    public int getMaxSentimentLength() {
        return maxSentimentLength;
    }

    public void setMaxSentimentLength(int maxSentimentLength) {
        this.maxSentimentLength = maxSentimentLength;
    }

    public String[] getStorageSentiments() {
        return storageSentiments;
    }

    public void setStorageSentiments(String[] storageSentiments) {
        this.storageSentiments = storageSentiments;
    }

    public float[] getStorageWeights() {
        return storageWeights;
    }

    public void setStorageWeights(float[] storageWeights) {
        this.storageWeights = storageWeights;
    }

    @Override
    public String toString() {
        return "Vocabulary{" +
                "minSentimentLength=" + minSentimentLength +
                ", maxSentimentLength=" + maxSentimentLength +
                ", storageSentiments=" + Arrays.toString(storageSentiments) +
                ", storageWeights=" + Arrays.toString(storageWeights) +
                '}';
    }
}
