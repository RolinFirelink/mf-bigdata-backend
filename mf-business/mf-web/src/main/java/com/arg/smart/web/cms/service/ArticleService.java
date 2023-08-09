package com.arg.smart.web.cms.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.req.ReqArticle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @description: 文章内容
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
public interface ArticleService extends IService<Article> {

    PageResult<Article> listArticle(ReqArticle reqArticle);

    boolean saveArticle(Article article);

    boolean updateArticle(Article article);

    boolean removeArticle(String id);

    String getContent(Long id);

    PageResult<Article> pageList(ReqArticle reqArticle);

    List<Article> list(Long categoryId, Integer count);

    /**
     * 从农业农村网爬取数据保存到数据库中
     * @return
     */
    boolean saveFromMoagov();

    /**
     * 从农业农村网爬取日报周报月报数据保存到数据库中
     * @return
     */
    boolean saveDWMFromMoagov();
    void updateClickNum(Long id);

    List<Article> listContent(Set<Long> ids);
}

