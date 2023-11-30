package com.arg.smart.web.cms.service.impl;

import co.elastic.clients.elasticsearch.tasks.GroupBy;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.entity.ArticleCategory;
import com.arg.smart.web.cms.mapper.ArticleMapper;
import com.arg.smart.web.cms.req.ReqArticle;
import com.arg.smart.web.cms.service.ArticleCategoryService;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.cms.service.RemoteArticleService;
import com.arg.smart.web.cms.service.info.ArticleInfoService;
import com.arg.smart.web.customer.entity.HotWord;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Routing;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.print.attribute.standard.NumberUp;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 文章内容
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleCategoryService articleCategoryService;

    @Resource
    private RemoteArticleService remoteArticleService;

    @Autowired
    public ArticleServiceImpl(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @Override
    public PageResult<Article> listArticle(ReqArticle reqArticle) {
        //设置查询条件
        Long categoryId = reqArticle.getCategoryId();
        Date startTime = reqArticle.getStartTime();
        Date endTime = reqArticle.getEndTime();
        String author = reqArticle.getAuthor();
        String title = reqArticle.getTitle();
        String source = reqArticle.getSource();
        Integer inclined = reqArticle.getInclined();
        Integer status = reqArticle.getStatus();
        Integer flag = reqArticle.getFlag();
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.orderByDesc("is_top");
        articleQueryWrapper.orderByAsc("sort");
        articleQueryWrapper.orderByDesc("start_time");
        if (categoryId != null && categoryId != 0) {
            if (categoryId == 5L) {
                List<Long> list = new ArrayList<>();
                list.add(5L);
                list.add(7L);
                list.add(8L);
                list.add(9L);
                list.add(10L);
                articleQueryWrapper.in("category_id", list);
            } else {
                articleQueryWrapper.eq("category_id", categoryId);
            }
        }
        if (startTime != null && endTime != null) {
            articleQueryWrapper.ge("start_time", startTime).le("start_time", endTime);
        }
        if (author != null) {
            articleQueryWrapper.like("author", author);
        }
        if (title != null) {
            articleQueryWrapper.like("title", title);
        }
        if (source != null) {
            articleQueryWrapper.like("source", source);
        }
        articleQueryWrapper.eq(inclined != null, "inclined", inclined);
        if (status != null) {
            articleQueryWrapper.eq("status", status);
        }
        if (flag != null) {
            articleQueryWrapper.eq("flag", flag);
        }
        List<Article> list = this.list(articleQueryWrapper);
        PageResult<Article> pageResult = new PageResult<>(list);
        //查询并设置分类名称
        List<Article> collect = list.stream().peek(item -> {
            //获取分类名称
            ArticleCategory articleCategory = articleCategoryService.getById(item.getCategoryId());
            if (articleCategory != null) {
                item.setCategoryName(articleCategory.getName());
            }
        }).collect(Collectors.toList());
        pageResult.setList(collect);
        return pageResult;
    }

    @Override
    @Transactional
    public boolean saveArticle(Article article) {
        if (!this.save(article)) {
            return false;
        }
        String content = article.getContent();
        //保存文章内容
        return this.baseMapper.saveContent(article.getId(), content) == 1;
    }

    @Override
    @Transactional
    public boolean updateArticle(Article article) {
        if (!this.updateById(article)) {
            return false;
        }
        return this.baseMapper.updateContent(article.getId(), article.getContent()) == 1;
    }

    @Override
    @Transactional
    public boolean removeArticle(String id) {
        if (!this.removeById(id)) {
            return false;
        }
        return this.baseMapper.deleteContent(id);
    }

    @Override
    public String getContent(Long id) {
        return this.baseMapper.getContent(id);
    }

    @Override
    public PageResult<Article> pageList(ReqArticle reqArticle) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long categoryId = reqArticle.getCategoryId();
        // 根据排序查
        lambdaQueryWrapper.orderByAsc(Article::getSort);
        if (categoryId != null && categoryId != 0) {
            if (categoryId == 5) {
                List<Long> list = new ArrayList<>();
                list.add(5L);
                list.add(7L);
                list.add(8L);
                list.add(9L);
                list.add(10L);
                lambdaQueryWrapper.in(Article::getCategoryId, list);
            } else {
                lambdaQueryWrapper.eq(Article::getCategoryId, categoryId);
            }
        }
        String title = reqArticle.getTitle();
        if (title != null) {
            lambdaQueryWrapper.like(Article::getTitle, title);
            lambdaQueryWrapper.like(Article::getSummary, title);
        }
        //只查询发布的
        lambdaQueryWrapper.eq(Article::getStatus, 2);
        // 倾向性
        Integer inclined = reqArticle.getInclined();
        // 发布时间倒排
        lambdaQueryWrapper.orderByDesc(Article::getStartTime);
        if (inclined != null) {
            lambdaQueryWrapper.eq(Article::getInclined, inclined);
        }
        Date startTime = reqArticle.getStartTime();
        if (startTime != null) {
            lambdaQueryWrapper.ge(Article::getStartTime, startTime);
        }
        Date endTime = reqArticle.getEndTime();
        if (endTime != null) {
            lambdaQueryWrapper.le(Article::getStartTime, endTime);
        }
        Integer flag = reqArticle.getFlag();
        lambdaQueryWrapper.eq(flag != null, Article::getFlag, flag);
        return new PageResult<>(this.list(lambdaQueryWrapper));
    }

    @Override
    public List<Article> list(Long categoryId, Integer count) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (categoryId != 0) {
            if (categoryId == 5L) {
                //市场行情报告
                List<Long> list = new ArrayList<>();
                list.add(5L);
                list.add(7L);
                list.add(8L);
                list.add(9L);
                list.add(10L);
                queryWrapper.in(Article::getCategoryId, list);
            } else {
                queryWrapper.eq(Article::getCategoryId, categoryId);
            }
        }else{
            //农业资讯
            List<Long> list = new ArrayList<>();
            list.add(1L);
            list.add(2L);
            list.add(3L);
            list.add(4L);
            queryWrapper.in(Article::getCategoryId,list);
        }
        queryWrapper.orderByDesc(Article::getStartTime);
        //只查询发布的
        queryWrapper.eq(Article::getStatus, 2);
        //只查询有图片的
        queryWrapper.isNotNull(Article::getCoverImg);
        queryWrapper.ne(Article::getCoverImg, "");
        queryWrapper.last("limit " + count);
        return this.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveFromMoagov() {
        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\pachong\\new\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tongZhiUrl = "http://www.moa.gov.cn/gk/tzgg_1/";
        String originUrl = "http://www.moa.gov.cn/gk/zcfg/";
        String[] urls = {originUrl, tongZhiUrl};
        long[] longs = {1, 4};

        for (int j = 0; j < urls.length; j++) {
            String url = urls[j];
            chromeDriver.get(url);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> commonlist = chromeDriver.findElements(By.className("commonlist"));
            List<String> list = new ArrayList<>();
            List<Article> articles = new ArrayList<>();
            for (WebElement element : commonlist) {
                List<WebElement> elements = element.findElements(By.tagName("li"));
                for (WebElement webElement : elements) {
                    Article article = new Article();
                    WebElement liA = webElement.findElement(By.cssSelector("li a"));
                    WebElement liSpan = webElement.findElement(By.cssSelector("li span"));
                    String title = liA.getText();
                    String time = liSpan.getText();
                    article.setTitle(title);
                    Date date;
                    try {
                        date = dateFormat.parse(time);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    article.setStartTime(date);
                    article.setCategoryId(longs[j]);
                    String href = liA.getAttribute("href");
                    list.add(href);
                    articles.add(article);
                }
            }

            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                Article article = articles.get(i);
                chromeDriver.get(s);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WebElement content = chromeDriver.findElement(By.className("gsj_content"));
                String htmlCode = content.getAttribute("outerHTML");
                article.setSource(originUrl);
                article.setContent(htmlCode);
                if (!saveArticle(article)) {
                    throw new RuntimeException("文章没有保存成功");
                }
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chromeDriver.quit();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDWMFromMoagov() {
        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\pachong\\new\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String originUrl = "http://zdscxx.moa.gov.cn:8080/nyb/pc/messageList.jsp?item=%E6%9C%80%E6%96%B0%E5%8F%91%E5%B8%83&isLatestMessage=true";
        chromeDriver.get(originUrl);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] xpaths = {"/html/body/div[1]/div[2]/div[1]/div[2]/ul/li[3]", "/html/body/div[1]/div[2]/div[1]/div[2]/ul/li[4]", "/html/body/div[1]/div[2]/div[1]/div[2]/ul/li[5]"};
        long[] longs = {7, 8, 9};
        for (int j = 0; j < xpaths.length; j++) {
            String xpath = xpaths[j];
            WebElement button = chromeDriver.findElement(By.xpath(xpath));
            button.click();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> elements = chromeDriver.findElement(By.id("dataTable")).findElements(By.cssSelector("li"));
            // 获取当前窗口句柄
            String originalHandle = chromeDriver.getWindowHandle();
            for (WebElement webElement : elements) {
                Article article = new Article();
                WebElement a = webElement.findElement(By.cssSelector("a"));
                String titile = a.findElement(By.cssSelector("p")).getText();
                String time = a.findElement(By.cssSelector("span")).getText();
                article.setTitle(titile);
                article.setCategoryId(longs[j]);
                article.setSource(originUrl);
                Date date;
                try {
                    date = dateFormat.parse(time);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                article.setStartTime(date);
                a.click();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 获取所有窗口句柄
                Set<String> handles = chromeDriver.getWindowHandles();
                // 切换到新窗口
                for (String handle : handles) {
                    if (!handle.equals(originalHandle)) {
                        chromeDriver.switchTo().window(handle);
                        break;
                    }
                }

                WebElement wraper = chromeDriver.findElement(By.className("wraper"));
                // 获取元素的 HTML 代码
                String htmlCode = wraper.getAttribute("outerHTML");
                article.setContent(htmlCode);
                if (!saveArticle(article)) {
                    throw new RuntimeException("文章没有保存成功");
                }
                // 回原窗口
                chromeDriver.switchTo().window(originalHandle);
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chromeDriver.quit();
        return true;
    }

    public void updateClickNum(Long id) {
        this.baseMapper.updateClickNum(id);
    }

    @Override
    public List<Article> listContent(Set<Long> ids) {
        return baseMapper.getContents(ids);
    }

    @Override
    @Transactional
    public void saveArticleBatch(List<Article> collect) {
        this.saveBatch(collect);
        baseMapper.saveContentBatch(collect);
    }

    @Override
    public void removeUseLessArticles(String sources,String titles) {
        //删除标题重复的
        baseMapper.deleteArticles();
        if(sources != null){
            UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
            List<String> sourceList = Arrays.asList(sources.split(";"));
            sourceList.forEach(item->{
                updateWrapper.like("source",item).or();
            });
            this.remove(updateWrapper);
        }
        if(titles != null){
            UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
            List<String> titlesList = Arrays.asList(titles.split(";"));
            titlesList.forEach(item->{
                updateWrapper.like("title",item).or();
            });
            this.remove(updateWrapper);
        }

        //将倾向性为空的查出来设置后存进去
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(Article::getInclined);
        List<Article> list = this.list(queryWrapper);
        if(list.size() > 0){
            Map<Long, List<Article>> collect = list.stream().collect(Collectors.groupingBy(Article::getId));
            Set<Long> longs = collect.keySet();
            List<Article> articles = this.listContent(longs);
            List<Article> collect1 = articles.stream().map(item -> {
                Article article = collect.get(item.getId()).get(0);
                article.setContent(item.getContent());
                Integer inclined = remoteArticleService.analyticalTendencies2(item.getTitle() + item.getContent());
                article.setInclined(inclined);
                return article;
            }).collect(Collectors.toList());
            this.updateBatchById(collect1);
        }

        //根据keyword或title设置flag
        Map<String, Integer> keywordToFlag = new HashMap<>();
        keywordToFlag.put("柑;柠;柚",2);
        keywordToFlag.put("菜心",5);
        keywordToFlag.put("兰",3);
        keywordToFlag.put("鱼",8);
        keywordToFlag.put("虾",4);
        keywordToFlag.put("鸡;",1);
        keywordToFlag.put("鸽",7);
        keywordToFlag.put("预制菜",6);
        keywordToFlag.forEach((key,value)->{
            //设置flag
            UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
            String[] split = key.split(";");
            for (String s : split) {
                updateWrapper.like("keyword",s).or();
                updateWrapper.like("title",s).or();
            }
            updateWrapper.isNull("flag");
            updateWrapper.set("flag",value);
            this.update(updateWrapper);
        });
    }

    @Override
    public List<Article> getRecommend(ReqArticle reqArticle) {
        Integer count = reqArticle.getCount();
        if(count == null){
            count = 5;
        }
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,2);
        queryWrapper.isNotNull(Article::getCoverImg);
        queryWrapper.ne(Article::getCoverImg, "");
        //只查置顶的
        queryWrapper.eq(Article::getIsTop,1);
        queryWrapper.orderByAsc(Article::getSort);
        queryWrapper.orderByDesc(Article::getStartTime);
        queryWrapper.last("limit " + count);
        return this.list(queryWrapper);
    }
}

