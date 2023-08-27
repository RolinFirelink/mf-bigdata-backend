package com.arg.smart.web.cms.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeywordSentimentAnalyzer {
    private Map<String, Integer> keywordWeights;

    public KeywordSentimentAnalyzer(String filePath) {
        keywordWeights = readKeywordWeightsFromFile(filePath);
    }

    private Map<String, Integer> readKeywordWeightsFromFile(String filePath) {
        Map<String, Integer> keywordWeights = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String keyword = parts[0];
                    int weight = Integer.parseInt(parts[1]);
                    keywordWeights.put(keyword, weight);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keywordWeights;
    }

    public int analyzeSentiment(String text) {
        int sentimentScore = calculateSentimentScore(text);
        return mapToSentiment(sentimentScore);
    }

    private int calculateSentimentScore(String text) {
        int score = 0;
        for (Map.Entry<String, Integer> entry : keywordWeights.entrySet()) {
            if (text.contains(entry.getKey())) {
                score += entry.getValue();
            }
        }
        return score;
    }

    private int mapToSentiment(int sentimentScore) {
        if (sentimentScore > 0) {
            return 1; // Positive
        } else if (sentimentScore < 0) {
            return -1; // Negative
        } else {
            return 0; // Neutral
        }
    }
}
