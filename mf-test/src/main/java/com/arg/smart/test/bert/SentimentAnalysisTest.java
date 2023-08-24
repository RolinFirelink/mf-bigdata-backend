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

            String input = "6月29日至7月12日，“金融心向党+奋进新征程”——盐城市银行业金融机构高质量发展成就展在市美术馆展出，短短两周时间，吸引了1500人次观展，在全市银行系统引起热烈反响。连日来，各银行机构组织各党支部积极开展主题党日活动，参加金融机构高质量发展成就展观展活动，在追寻初心使命、回望奋斗历程中凝聚金融力量，以昂扬的斗志、拼搏的姿态，投身推动经济社会高质量发展的伟大事业中。";
            logger.info("input Sentence: {}", input);

            Classifications classifications = predictor.predict(input);
            double probability = classifications.best().getProbability();
            logger.info("probability:{}", probability);
            logger.info(classifications.toString());
        }
    }

}
