package com.arg.smart.web.cms.service.info.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.entity.info.ArticleInfo;
import com.arg.smart.web.cms.repository.ArticleInfoRepository;
import com.arg.smart.web.cms.req.ReqArticle;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.cms.service.info.ArticleInfoService;
import com.arg.smart.web.cms.utils.DateUtils;
import com.arg.smart.web.customer.entity.HotWord;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        log.info("categoryId：" + categoryId + "，key：" + key);
        if (key != null) {
            boolQueryBuilder
                    .should(QueryBuilders.wildcardQuery("title", "*" + key + "*").boost(20))
                    .should(QueryBuilders.matchQuery("content", key));
        }
        RangeQueryBuilder startTimeRangeQuery = QueryBuilders.rangeQuery("startTime");
        if (flag != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("flag", flag));
        }
        if (startTime != null) {
            startTime = DateUtils.setStartTime(startTime);
            startTimeRangeQuery.gte(startTime);
        }
        if (endTime != null) {
            endTime = DateUtils.setEndTime(endTime);
            startTimeRangeQuery.lte(endTime);
        }
        log.info("startTime：" + startTime + "，endTime：" + endTime);
        if (startTime != null || endTime != null) {
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
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = nativeSearchQueryBuilder
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .build();
        if (key == null) {
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("startTime").order(SortOrder.DESC));
        }
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
        String content = article.getContent();
        // 使用Jsoup解析富文本内容
        String decode;
        try {
            decode = URLDecoder.decode(content, "UTF-8");
        } catch (Exception e) {
            decode = content;
        }
        article.setContent(decode);
        ArticleInfo articleInfo = new ArticleInfo();
        BeanUtils.copyProperties(article, articleInfo);
        elasticRepository.save(articleInfo);
    }

    @Override
    public List<HotWord> analysis(ReqArticle reqArticle) {
        Integer status = reqArticle.getStatus();
        String source = reqArticle.getSource();
        Integer inclined = reqArticle.getInclined();
        Date startTime = reqArticle.getStartTime();
        Date endTime = reqArticle.getEndTime();
        String key = reqArticle.getKey();
        Integer flag = reqArticle.getFlag();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (key != null) {
            MatchQueryBuilder titleQuery = QueryBuilders.matchQuery("title", key).fuzziness("AUTO");
            boolQueryBuilder
                    .should(titleQuery)
                    .should(QueryBuilders.wildcardQuery("summary", "*" + key + "*"))
                    .should(QueryBuilders.wildcardQuery("content", "*" + key + "*"));
            boolQueryBuilder.minimumShouldMatch(1); // 至少一个should子句匹配
        }
        RangeQueryBuilder startTimeRangeQuery = QueryBuilders.rangeQuery("startTime");
        if (flag != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("flag", flag));
        }
        if (startTime != null) {
            startTime = DateUtils.setStartTime(startTime);
            startTimeRangeQuery.gte(startTime);
        }
        if (endTime != null) {
            endTime = DateUtils.setEndTime(endTime);
            startTimeRangeQuery.lte(endTime);
        }
        if (startTime != null || endTime != null) {
            boolQueryBuilder.must(startTimeRangeQuery);
        }
        boolQueryBuilder.must(QueryBuilders.matchQuery("categoryId", 6));
        if (inclined != null && inclined != -2) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("inclined", inclined));
        }
        if (status != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("status", status));
        }
        // 构建查询条件
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
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
        if (source != null) {
            collect = collect.stream()
                    .filter(article -> article.getSource().contains(source))
                    .collect(Collectors.toList());
        }
        return analyzeArticles(collect);
    }

    @Override
    public Map<String, Object> analysisPublic(ReqArticle reqArticle) {
        String sources = reqArticle.getSources();
        Date startTime = reqArticle.getStartTime();
        Date endTime = reqArticle.getEndTime();
        Integer flag = reqArticle.getFlag();
        Map<String, Object> resultMap = new HashMap<>();
        List<String> sourceList = new ArrayList<String>();
        if (sources != null) {
            sourceList = Arrays.asList(sources.split(";"));
        }
        //查询source及其文章数量
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", 6);
        queryWrapper.eq(flag != null, "flag", flag);
        if (startTime != null) {
            startTime = DateUtils.setStartTime(startTime);
            queryWrapper.ge("start_time", startTime);
        }
        if (endTime != null) {
            endTime = DateUtils.setEndTime(endTime);
            queryWrapper.le("start_time", endTime);
        }
        for (String s : sourceList) {
            queryWrapper.like("source", s);
        }
        queryWrapper.groupBy("source").select("source", "count(*) as count");
        List<Map<String, Object>> maps = articleService.listMaps(queryWrapper);
        resultMap.put("sources", maps);
        //查询倾向性
        QueryWrapper<Article> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("category_id", 6);
        queryWrapper1.eq(flag != null, "flag", flag);
        if (startTime != null) {
            startTime = DateUtils.setStartTime(startTime);
            queryWrapper1.ge("start_time", startTime);
        }
        if (endTime != null) {
            endTime = DateUtils.setEndTime(endTime);
            queryWrapper1.le("start_time", endTime);
        }
        for (String s : sourceList) {
            queryWrapper1.like("source", s);
        }
        queryWrapper1.groupBy("inclined").select("inclined", "count(*) as count");
        List<Map<String, Object>> maps1 = articleService.listMaps(queryWrapper1);
        resultMap.put("inclineds", maps1);
        //词云数据
        List<HotWord> wordList = this.analysis(reqArticle);
        resultMap.put("wordList", wordList);
        return resultMap;
    }


    public static List<HotWord> analyzeArticles(List<Article> articles) {

        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (Article article : articles) {
            String content = article.getContent();
            List<SegToken> tokens = jiebaSegmenter.process(content, JiebaSegmenter.SegMode.SEARCH);

            for (SegToken token : tokens) {
                String word = token.word;
                if (!isFilteredWord(word)) {//过滤掉符号、数字和字母等
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
        }

        List<HotWord> hotWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            hotWords.add(new HotWord(entry.getKey(), entry.getValue()));
        }

        //排序
        hotWords.sort(Comparator.comparingInt(HotWord::getCount).reversed());

        return hotWords;
    }

    public static boolean isFilteredWord(String word) {
        return !word.matches("[\\u4e00-\\u9fa5]+") || word.length() <= 1;
    }

}


