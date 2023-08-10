package com.arg.smart.web.cms.service.impl;

import com.alibaba.nacos.common.packagescan.resource.ClassPathResource;
import com.alibaba.nacos.common.packagescan.resource.Resource;
import com.arg.smart.web.cms.service.RemoteArticleService;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.data.redis.connection.ReactiveStreamCommands;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

@Service
@Slf4j
public class RemoteArticleServiceImpl implements RemoteArticleService {

    @Override
    public Map<String, Object> indexAction(Integer id, Integer len, Integer content) {

        if (id == null || len == null || content == null)
            return null;

        Map<String, String> apiConfig = new HashMap<>();
        apiConfig.put("partner_token", "nIe1xTbs8Mmh2lz4lPtF5wnKlB6l6ZeVVn9ODqA7NGI3"); //  用户token
        apiConfig.put("partner_code", "581793"); // 用户代号
        apiConfig.put("public_key_file_path", "rsa_public_key.crt"); // 公钥证书
        String curDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 当前时间
        Map<String, String> query = new HashMap<>();
        query.put("id", String.valueOf(id));
        query.put("len", String.valueOf(len));
        query.put("content", String.valueOf(content));

        String queryString = null;
        try {
            queryString = URLEncoder.encode(getQueryString(query), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert queryString != null;
        String queryStringMd5 = getMd5(queryString);
        String data = apiConfig.get("partner_code") + "|" + apiConfig.get("partner_token") + "|" + curDate + "|" + queryStringMd5;
        String token = publicEncrypt(data, false, apiConfig);
        query.put("token", token);
        String url = "http://39.108.125.69:30080/ncb/sync?" + getQueryString(query);
        return sendHttpRequestWithParams(url);
    }

    // 公钥加密
    public static String publicEncrypt(String data, boolean serialize, Map<String, String> apiConfig) {
        try {
            Resource resource = new ClassPathResource(apiConfig.get("public_key_file_path"));
            File file = resource.getFile();
            byte[] publicKeyBytes = FileUtils.readFileToByteArray(file);
            byte[] keyContentAsBytes = Base64.getMimeDecoder().decode(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyContentAsBytes);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            int inputLen = bytes.length;
            int MAX_DECRYPT_BLOCK = 30;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(bytes, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            return Base64.getUrlEncoder().encodeToString(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 加密码时把特殊符号替换成URL的内容
    public static String urlsafeB64encode(String string) {
        String data = Base64.getEncoder().encodeToString(string.getBytes());
        data = data.replace("+", "-").replace("/", "_").replace("=", "");
        return data;
    }

    // GET方式发送HTTP请求
    public static Map<String, Object> sendHttpRequestWithParams(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        Map<String, Object> response = new HashMap<>();

        try {
            log.info(urlString);
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            response.put("http_code", responseCode);

            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            response.put("response", result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response;
    }

    private static String getQueryString(Map<String, String> query) {
        StringBuilder queryStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : query.entrySet()) {
            queryStringBuilder.append(entry.getKey());
            queryStringBuilder.append("=");
            queryStringBuilder.append(entry.getValue());
            queryStringBuilder.append("&");
        }
        return queryStringBuilder.toString();
    }

    private static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
