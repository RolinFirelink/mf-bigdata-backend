package com.arg.smart.web.cms.service.info.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.entity.info.ArticleInfo;
import com.arg.smart.web.cms.repository.ArticleInfoRepository;
import com.arg.smart.web.cms.req.ReqArticle;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.cms.service.info.ArticleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        elasticRepository.deleteAll();
        List<Article> articles = articleService.list();
        Map<Long, List<Article>> map = articles.stream().collect(Collectors.groupingBy(Article::getId));
        Set<Long> ids = map.keySet();
        List<Article> contentList = articleService.listContent(ids);
        List<ArticleInfo> articleInfos = contentList.stream().map(item -> {
            String content = item.getContent();
            ArticleInfo articleInfo = new ArticleInfo();
            Article article = map.get(item.getId()).get(0);
            // 使用Jsoup解析富文本内容
            String decode;
            try {
                decode = URLDecoder.decode(content, "UTF-8");
            } catch (Exception e) {
                decode = content;
            }
            Document doc = Jsoup.parseBodyFragment(decode);
            content = doc.text();
            BeanUtils.copyProperties(article, articleInfo);
            articleInfo.setContent(content);
            return articleInfo;
        }).collect(Collectors.toList());
        //一次最多插入的数量,太多会出现请求体过大的错误
        int batchSize = 300;
        for (int i = 0; i < articleInfos.size(); i += batchSize) {
            List<ArticleInfo> batch = articleInfos.subList(i, Math.min(i + batchSize, articleInfos.size()));
            elasticRepository.saveAll(batch);
            log.info(i + ":" + String.valueOf(batch.size()));
        }
        return true;
    }

    @Override
    public PageResult<Article> findArticlesByEs(ReqArticle reqArticle, ReqPage reqPage) {
        String key = reqArticle.getKey();
        Long categoryId = reqArticle.getCategoryId();
        Integer inclined = reqArticle.getInclined();
        Date startTime = reqArticle.getStartTime();
        Date endTime = reqArticle.getEndTime();
        Integer flag = reqArticle.getFlag();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        log.info("categoryId：" + categoryId + "key：" + key);
        if (key != null) {
            boolQueryBuilder
                    .should(QueryBuilders.wildcardQuery("title", "*" + key + "*"))
                    .should(QueryBuilders.wildcardQuery("summary", "*" + key + "*"))
                    .should(QueryBuilders.wildcardQuery("content", "*" + key + "*"));
            boolQueryBuilder.minimumShouldMatch(1); // 至少一个should子句匹配
        }
        RangeQueryBuilder startTimeRangeQuery = QueryBuilders.rangeQuery("startTime");
        if(flag != null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("flag",flag));
        }
        if(startTime != null){
            startTimeRangeQuery.gte(startTime);
        }
        if(endTime != null){
            startTimeRangeQuery.lte(endTime);
        }
        if(startTime != null || endTime != null){
            boolQueryBuilder.must(startTimeRangeQuery);
        }
        if (categoryId != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("categoryId", categoryId));
        }
        if (inclined != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("inclined", inclined));
        }
        boolQueryBuilder.must(QueryBuilders.matchQuery("status", 2));

        Integer pageNum = reqPage.getPageNum();
        Integer pageSize = reqPage.getPageSize();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //分页
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        // 构建查询条件
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withSorts(SortBuilders.fieldSort("isTop").order(SortOrder.DESC))
                .withSorts(SortBuilders.fieldSort("sort").order(SortOrder.ASC))
                .withSorts(SortBuilders.fieldSort("startTime").order(SortOrder.DESC))
                .withPageable(pageRequest)
                .build();

        // 执行查询
        SearchHits<ArticleInfo> hits = elasticsearchTemplate.search(query, ArticleInfo.class);

        long total = hits.getTotalHits();
        PageResult<Article> pageResult = new PageResult<>();

        log.info(String.valueOf(hits));

        // 提取查询结果
        List<ArticleInfo> articles = hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        List<Article> collect = articles.stream().map((item) -> {
            Article article = new Article();
            BeanUtils.copyProperties(item, article);
            return article;
        }).collect(Collectors.toList());
        pageResult.setList(collect);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(total);
        pageResult.setPages((int) ((total + pageSize - 1) / pageSize));
        return pageResult;
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        elasticRepository.deleteAllById(ids);
    }

    @Override
    public void saveArticle(Article article) {
        ArticleInfo articleInfo = new ArticleInfo();
        BeanUtils.copyProperties(article, articleInfo);
        elasticRepository.save(articleInfo);
    }
}
