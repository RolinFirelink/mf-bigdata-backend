package com.arg.smart.web.cms.service.impl;

import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.cms.service.RemoteArticleService;
import com.arg.smart.web.cms.utils.DateUtils;
import com.arg.smart.web.cms.utils.KeywordSentimentAnalyzer;
import com.arg.smart.web.cms.utils.SentimentAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.annotation.Resource;
import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class RemoteArticleServiceImpl implements RemoteArticleService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ArticleService articleService;

    private static final String BASE_URL = "http://39.108.125.69:30080/ncb/sync";
    private static final String PARTNER_TOKEN = "nIe1xTbs8Mmh2lz4lPtF5wnKlB6l6ZeVVn9ODqA7NGI3";
    private static final String PARTNER_CODE = "581793";
    private static final String PUBLIC_KEY_FILE_PATH = "rsa_public_key.crt";
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyIpO8RzaPWeALwIKDQfs+eoOcXs5QW55f6jY/STpvj7Q2csCwAzGd99/tqYVZ4byV0xPkHqeCG4oYW74B9N45UhophSCnOWHcsie34KVrDA0c1Qzt1DmK13Oro9QjCrZubGVVyiJHiJvzanW3Uc0LsexRAF0kKUnldeAqcC5IXwIDAQAB";

    /**
     * @version V1.0
     * @desc AES 加密工具类
     */
    private static final String KEY_ALGORITHM = "AES";
    //默认的加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 拉取远程文章数据
     *
     * @param fromId 起始id
     * @param len    条数（接口最大100条）
     */
    @Override
    @Log(title = "舆情文章对接获取", operateType = OperateType.IMPORT)
    public List<JSONObject> fetch(Long fromId, Integer len) {

        //非法参数处理
        if (fromId == null) fromId = 0L;
        if (len == null) len = 0;
        if (len > 100) len = 100;

        StringBuilder sb = new StringBuilder();
        sb.append("id").append("=").append(fromId);
        sb.append("&len").append("=").append(len);
        sb.append("&content").append("=").append("1");

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHms");
        String date = LocalDateTime.now().format(f);
        String queryString = sb.toString();
        log.info("query_string: {}", queryString);


        String md5ed = MD5Utils.md5Hex(queryString, StandardCharsets.UTF_8.name());

        log.info("md5ed: {}", md5ed);

        String md5ed16 = md5ed.substring(8, 24);
        log.info("md5ed16: {}", md5ed16);

        String data = PARTNER_CODE + "|" + PARTNER_TOKEN + "|" + date + "|" + md5ed16;

        //String key = getPublicKeyFile();

        log.info("key: {}", PUBLIC_KEY);

        String encrypted = opensslEncrypt(data, PUBLIC_KEY);
        log.info("encrypted: {}", encrypted);

        //计算token
        String token = getSafeUrl(encrypted);

        log.info("token: {}", token);

        //query.put("token", token);
        sb.append("&token").append("=").append(token);

        String url = BASE_URL + "?" + sb;

        log.info("url: {}", url);

        //发起请求
        JSONObject res = restTemplate.getForObject(url, JSONObject.class);
//        log.info("res：{}",res);
        List<Map<String, Object>> articleMaps = (List<Map<String, Object>>) res.get("data");
        List<Article> collect = articleMaps.stream().map(item -> {
            Article article = new Article();
            article.setTitle((String) item.get("title"));
            article.setSource((String) item.get("source"));
            article.setContent((String) item.get("content"));
            article.setStartTime(DateUtils.stringToDateTime((String) item.get("sourcetime")));
            article.setKeyword((String) item.get("keyword"));
            article.setMedianame((String) item.get("medianame"));
            article.setAuthor((String) item.get("medianame"));
            String emotion = (String) item.get("emotion");
            if(!StringUtils.isEmpty(emotion)){
                emotion = emotion.substring(0,emotion.length() - 1);
                if(emotion.compareTo("52.00") > 0 || emotion.equals("100") ){
                    article.setInclined(1);
                }else if(emotion.compareTo("48.00") < 0 ){
                    article.setInclined(-1);
                }else{
                    article.setInclined(0);
                }
            }
            article.setStatus(1);
            if(article.getInclined() == null){
                Integer inclined = analyticalTendencies2(
                        article.getTitle() + article.getContent());
                log.info("结果为：{}", inclined);
                article.setInclined(inclined);
            }
            article.setCategoryId(6L);
            return article;
        }).collect(Collectors.toList());
        articleService.saveArticleBatch(collect);
        return null;
    }


    public Integer analyticalTendencies2(String str){
        String filePath = KeywordSentimentAnalyzer.class.getResource("/data/keyword_weights.csv").getPath();
        KeywordSentimentAnalyzer analyzer = new KeywordSentimentAnalyzer(filePath);
        return analyzer.analyzeSentiment(str);
    }
    //解析倾向性
    private Integer analyticalTendencies(String str){
        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        Criteria<String, Classifications> criteria = sentimentAnalysis.criteria();
        try (ZooModel<String, Classifications> model = criteria.loadModel();
             Predictor<String, Classifications> predictor = model.newPredictor()) {
            Classifications classifications = predictor.predict(str);
            double probability = classifications.best().getProbability();
            log.info("分析结果：{},{}",probability,classifications);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    private static String getSafeUrl(String data) {
        String ret = "";
        try {
            ret = data.replace("+", "-")
                    .replace("/", "_")
                    .replace("=", "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return ret;
    }

    /**
     * 拼接键值对
     *
     * @param query 参数
     */
    private static String getQueryString(Map<String, String> query) {
        StringBuilder queryStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : query.entrySet()) {
            queryStringBuilder.append(entry.getKey());
            queryStringBuilder.append("=");
            queryStringBuilder.append(entry.getValue());
            queryStringBuilder.append("&");
        }
        return queryStringBuilder.substring(0, queryStringBuilder.length() - 1);
    }

    /**
     * 获取密钥
     */
    public static String getPublicKeyFile() {
        URL url = RemoteArticleServiceImpl.class.getClassLoader().getResource(PUBLIC_KEY_FILE_PATH);
        if (url == null) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(url.toURI().getPath());
            br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                res.append(str);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res.toString();
    }

    /**
     * SHA加密算法
     *
     * @param data 原始数据
     * @param key  密钥
     */
    public static String opensslEncrypt(String data, String key) {
        try {
            return encrypt(data, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用keystore对明文进行加密
     *
     * @param plainText       明文
     * @param publicKeyString 公钥文件路径
     * @return
     */
    public static String encrypt(String plainText, String publicKeyString) {
        Cipher cipher;
        try {
            //对应PHP的 OPENSSL_PKCS1_OAEP_PADDING
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return (new BASE64Encoder()).encode(enBytes);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
}