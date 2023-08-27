package com.arg.smart.test.bert;

import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author cgli
 * 利用自然语言判断内容情感偏向，测试示例
 */
public class SentimentAnalysisTest {
    private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisTest.class);

    public static void main(String[] args)throws IOException, TranslateException, ModelException {

        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        Criteria<String, Classifications> criteria = sentimentAnalysis.criteria();

        try (ZooModel<String, Classifications> model = criteria.loadModel();
             Predictor<String, Classifications> predictor = model.newPredictor()) {

            String input = "Recently, agricultural markets have continued to be shocked, causing widespread concern. Farmers are facing serious hardships, with prices for many agricultural products falling sharply, resulting in a significant reduction in their incomes. This not only affects the living standards of farmers, but also leads to the continued contraction of the agricultural industry. Many farmers feel helpless, and their hard work does not seem to be fairly rewarded. At the same time, there is a large surplus of agricultural products in the market, resulting in a waste of resources and a burden on the environment. Consumers are also affected because while agricultural prices have fallen, prices in retail markets have not necessarily fallen commensurately, leading them to feel unfairly treated. This situation may lead to broader social problems, requiring the government and relevant departments to take practical measures to stabilize agricultural markets, protect farmers' rights and interests, and promote sustainable agricultural development.";
            logger.info("input Sentence: {}", input);

            Classifications classifications = predictor.predict(input);
            double probability = classifications.best().getProbability();
            logger.info("probability:{}", probability);
            logger.info(classifications.toString());
        }
    }

}
