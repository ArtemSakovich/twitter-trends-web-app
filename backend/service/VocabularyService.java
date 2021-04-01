package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.api.service.IVocabularyService;
import com.company.twittertrendswebapp.exceptions.AppException;
import com.company.twittertrendswebapp.model.Vocabulary;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@Component
public class VocabularyService implements IVocabularyService {
    Vocabulary vocabulary;

    @Override
    @PostConstruct
    public void init() throws AppException {
        String fileName = "src/main/resources/sentiments.csv";
        vocabulary = new Vocabulary();
        Map<String, Float> tempStorage = new TreeMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            int index = 0;
            while ((nextLine = reader.readNext()) != null) {
                index++;
                parseCsvString(nextLine, index, tempStorage);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new AppException(e);
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        if (tempStorage.isEmpty()) {
            throw new AppException("Storage initialization failed");
        } else {
            vocabulary.setStorageSentiments(new String[tempStorage.size()]);
            vocabulary.setStorageWeights(new float[tempStorage.size()]);
            int index = 0;
            for (Map.Entry<String, Float> entry : tempStorage.entrySet()) {
                vocabulary.getStorageSentiments()[index] = entry.getKey();
                vocabulary.getStorageWeights()[index] = entry.getValue();
                index++;
            }
        }
    }

    @Override
    public float getSentimentWeight(String sentiment) {
        int up = 0;
        int bottom = vocabulary.getStorageSentiments().length - 1;
        return getSentimentWeight(sentiment, up, bottom);
    }

    @Override
    public Vocabulary getVocabularyInstance() {
        return vocabulary;
    }

    private void addStorageItem(String item, float weight, Map<String, Float> tempStorage) {
        if (!tempStorage.containsKey(item)) {
            int itemLength = item.length();
            if (vocabulary.getMinSentimentLength() == 0 || itemLength < vocabulary.getMinSentimentLength()) {
                vocabulary.setMinSentimentLength(itemLength);
            }
            if (itemLength > vocabulary.getMaxSentimentLength()) {
                vocabulary.setMaxSentimentLength(itemLength);
            }
            tempStorage.put(item, weight);
        }
    }

    private void parseCsvString(String[] csvString, int index, Map<String, Float> tempStorage) {
        try {
            String sentiment = csvString[0];
            float weight = Float.parseFloat(csvString[1]);
            if (sentiment.length() > 0) {
                addStorageItem(sentiment, weight, tempStorage);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid line in csv file :" + index);
        }
    }

    private float getSentimentWeight(String sentiment, int up, int bottom) {
        float weight = 0.0f;
        if (bottom - up <= 1) { // exit from recursion
            if (sentiment.equalsIgnoreCase(vocabulary.getStorageSentiments()[up])) {
                weight = vocabulary.getStorageWeights()[up];
            } else if (sentiment.equalsIgnoreCase(vocabulary.getStorageSentiments()[bottom])) {
                weight = vocabulary.getStorageWeights()[bottom];
            }
            return weight;
        }
        int currentIndex = up + (bottom - up) / 2;
        int compareResult = compareSentiment(sentiment, currentIndex);
        if (compareResult == 0) {
            return vocabulary.getStorageWeights()[currentIndex];
        } else if (compareResult > 0) {
            weight = getSentimentWeight(sentiment, up, currentIndex);
        } else {
            weight = getSentimentWeight(sentiment, currentIndex, bottom);
        }
        return weight;
    }

    private int compareSentiment(String sentiment, int index) {
        return vocabulary.getStorageSentiments()[index].compareToIgnoreCase(sentiment);
    }
}

