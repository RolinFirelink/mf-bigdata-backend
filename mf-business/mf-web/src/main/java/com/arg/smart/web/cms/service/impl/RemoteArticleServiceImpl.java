package com.arg.smart.web.cms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.arg.smart.web.cms.service.RemoteArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class RemoteArticleServiceImpl implements RemoteArticleService {

    @Resource
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://39.108.125.69:30080/ncb/sync";
    private static final String PARTNER_TOKEN = "nIe1xTbs8Mmh2lz4lPtF5wnKlB6l6ZeVVn9ODqA7NGI3";
    private static final String PARTNER_CODE = "581793";
    private static final String PUBLIC_KEY_FILE_PATH = "rsa_public_key.crt";

    /**
     * 拉取远程文章数据
     * @param fromId 起始id
     * @param len 条数（接口最大100条）
     */
    @Override
    public List<JSONObject> fetch(Long fromId, Integer len){

        //非法参数处理
        if(fromId == null) fromId = 0L;
        if (len == null) len = 0;
        if (len > 100) len = 100;

        Map<String,String> query = new HashMap<>();
        query.put("content", "1");
        query.put("id", fromId+"");
        query.put("len", len+"");
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yMdHms");
        String date = LocalDateTime.now().format(f);

        String queryString = getQueryString(query);
        log.info("query_string: {}", queryString);


        String md5ed = MD5Utils.encodeHexString(queryString.getBytes(StandardCharsets.UTF_8));

        log.info("md5ed: {}", md5ed);

        String md5ed16 = md5ed.substring(8,24);
        log.info("md5ed16: {}", md5ed16);


        String data = PARTNER_CODE +"|"+ PARTNER_TOKEN +"|"+date+"|"+ md5ed16;

        String key = getPublicKeyFile();
        log.info("key: {}", key);

        String encrypted = opensslEncrypt(data,key);
        log.info("encrypted: {}", encrypted);

        //计算token
        String token = new String(Base64.getEncoder().encode(encrypted.getBytes(StandardCharsets.UTF_8)))
                .replace("+","-")
                .replace("/","_")
                .replace("=","");

        log.info("token: {}", token);

        query.put("token", token);

        String url =  BASE_URL +"?"+getQueryString(query);

        log.info("url: {}", url);

        //发起请求
        JSONObject res =  restTemplate.getForObject(url, JSONObject.class);
        log.info("请求结果，{}", res);
        return null;
    }

    /**
     * 拼接键值对
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
        return queryStringBuilder.substring(0,queryStringBuilder.length()-1);
    }

    /**
     * 获取密钥
     */
    public static String getPublicKeyFile() {
        URL url = RemoteArticleServiceImpl.class.getClassLoader().getResource(PUBLIC_KEY_FILE_PATH);
        if(url == null) return null;
        StringBuilder res = new StringBuilder();
        try(Scanner sc = new Scanner(Files.newInputStream(new File(url.toURI()).toPath()))){
            while (sc.hasNext()){
                res.append("\n").append(sc.next());
            }
        } catch (IOException | URISyntaxException e) {
            log.error("获取密钥失败");
            throw new RuntimeException(e);
        }
        return res.substring(1);
    }

    /**
     * SHA加密算法
     * @param data 原始数据
     * @param key 密钥
     */
    public static String opensslEncrypt(String data, String key) {
        byte[] keyBytes = new byte[32];
        for (int i = 0; i < 32; i++) {
            if (i < key.getBytes(StandardCharsets.UTF_8).length) {
                keyBytes[i] = key.getBytes(StandardCharsets.UTF_8)[i];
            } else {
                keyBytes[i] = 0;
            }
        }
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
