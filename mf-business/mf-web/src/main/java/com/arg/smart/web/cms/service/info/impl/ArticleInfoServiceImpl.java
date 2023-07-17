package com.arg.smart.web.cms.service.info.impl;

import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.entity.info.ArticleInfo;
import com.arg.smart.web.cms.repository.ArticleInfoRepository;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.cms.service.info.ArticleInfoService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleInfoRepository elasticRepository;
    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;
    private static final String IK_MAX_WORD = "ik_max_word";

    @Override
    public boolean saveArticleToEs() {
        List<Article> articles = articleService.list();
        List<ArticleInfo> articleInfos = articles.stream().map((item) -> {
            ArticleInfo articleInfo = new ArticleInfo();
            String content = articleService.getContent(item.getId());
            if(content==null){
                BeanUtils.copyProperties(item, articleInfo);
                return articleInfo;
            }
            // 使用Jsoup解析富文本内容
            String decode;
            try {
                decode = URLDecoder.decode(content, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            Document doc = Jsoup.parseBodyFragment(decode);
            content = doc.text();
            item.setContent(content);
            BeanUtils.copyProperties(item, articleInfo);
            return articleInfo;
        }).collect(Collectors.toList());
        elasticRepository.saveAll(articleInfos);
        return true;
    }

    @Override
    public List<Article> findArticlesByEs(String content) {
        // 构建查询条件

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("content", content).analyzer(IK_MAX_WORD))
                .withQuery(QueryBuilders.matchQuery("title", content).analyzer(IK_MAX_WORD))
                .withQuery(QueryBuilders.matchQuery("summary", content).analyzer(IK_MAX_WORD))
                .withQuery(QueryBuilders.matchQuery("author", content).analyzer(IK_MAX_WORD))
                .withQuery(QueryBuilders.matchQuery("source", content).analyzer(IK_MAX_WORD))
                .withSort(Sort.by(Sort.Direction.DESC, "_score"))
                .build();

        // 执行查询
        SearchHits<ArticleInfo> hits = elasticsearchTemplate.search(query, ArticleInfo.class);

        // 提取查询结果
        List<ArticleInfo> articles = hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        List<Article> collect = articles.stream().map((item) -> {
            Article article = new Article();
            BeanUtils.copyProperties(item, article);
            return article;
        }).collect(Collectors.toList());
        return collect;
    }
}
