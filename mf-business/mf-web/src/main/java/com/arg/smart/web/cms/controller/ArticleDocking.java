package com.arg.smart.web.cms.controller;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.docking.utils.DateUtils;
import io.swagger.annotations.Api;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 文章内容
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "文章爬取")
@RestController
@RequestMapping("/cms/article/docking/public")
public class ArticleDocking {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ArticleService articleService;

    //南方网，预制菜

    @GetMapping
    public Result<List<Article>> docking1(Integer count){
        if (count == null){
            count = 2;
        }
       String url = "https://api.nfapp.southcn.com/nanfang_if/getArticles?columnId=34421&version=0&lastFileId=0&count="+count+"&rowNumber=0&adv=1";
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject body = responseEntity.getBody();
        List<Map<String,Object>> list = (List<Map<String, Object>>) body.get("list");
        List<Article> collect = list.stream().map(item -> {
            Article article = new Article();
            article.setTitle((String) item.get("title"));
            article.setSummary((String) item.get("compositeTitle"));
            article.setSource((String) item.get("releaseSource"));
            article.setClickNum(Long.valueOf((Integer) item.get("countClick")));
            article.setCoverImg((String) item.get("picBig"));
            String createTime = (String) item.get("createTime");
            try {
                article.setStartTime(DateUtils.parseStringToDate(createTime,"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            article.setCategoryId(2L);
            article.setFlag(6);
            //获取文章内容
            ResponseEntity<String> urlPad = restTemplate.getForEntity(String.valueOf(item.get("urlPad")), String.class);
            String html = urlPad.getBody();
            log.info("{}",html);
            // 使用 Jsoup 解析 HTML 字符串
            Document doc = Jsoup.parse(html);

            Element articleNode = doc.select(".article").first();

            // 打印第二个 body 元素的内容
            if (articleNode != null) {
                log.info("类为article的div：" + articleNode.outerHtml());
                // 获取所有 <p> 标签
                Elements pTags = articleNode.select("p");

                // 创建一个 StringBuilder 来存储 <p> 标签的内容
                StringBuilder pText = new StringBuilder();

                // 遍历 <p> 标签并将内容添加到 StringBuilder
                for (Element p : pTags) {
                    pText.append(p.outerHtml());
                }
                String html1 = String.valueOf(pText);
                //将src默认图片去掉
                html1 = html1.replaceAll("src=\"https://static.nfapp.southcn.com/apptpl/img/poster.png\"","");
                html1 = html1.replaceAll("\\n","");
                //将data-original真实图片地址换成src
                html1 = html1.replaceAll("data-original","src");
                article.setContent(html1);
            } else {
                log.info("没有类为article的div");
            }
            return article;
        }).collect(Collectors.toList());
        articleService.saveArticleBatch(collect);
        return Result.ok(collect,"文章爬取成功");
    }
}
