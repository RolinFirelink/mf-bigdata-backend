package com.arg.smart.web.cms.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.cms.utils.KeywordSentimentAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rainkyzhong
 * @date 2023/8/11 22:42
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class RemoteServiceTest {
    @Resource
    private RemoteArticleService remoteArticleService;

    @Test
    public void test(){
        remoteArticleService.fetch(0L, 1000);
    }

    @Test
    public void md5Test(){
        String test ="id=0&len=100&content=1";
        String md5ed = MD5Utils.md5Hex(test, StandardCharsets.UTF_8.name());
        System.out.println("md5="+md5ed);
    }

    @Test
    public void testAnalysis(){
        List<String> testTexts = new ArrayList<>();
        testTexts.add("这个电影太棒了，剧情非常精彩，我喜欢它的表现。");
        testTexts.add("今天的天气真是糟糕透了，下雨让人感到非常烦闷。");
        testTexts.add("这个新闻报道真是令人感动，我感到很鼓舞。");
        // ... 添加更多测试文本

        String filePath = KeywordSentimentAnalyzer.class.getResource("/data/keyword_weights.csv").getPath();
        KeywordSentimentAnalyzer analyzer = new KeywordSentimentAnalyzer(filePath);

        for (String text : testTexts) {
            int sentiment = analyzer.analyzeSentiment(text);
            System.out.println("Text: " + text);
            System.out.println("Sentiment: " + sentiment);
            System.out.println("-------------------------");
        }
    }
}
