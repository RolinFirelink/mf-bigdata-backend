package com.arg.smart.web.cms.service;

import com.arg.smart.web.cms.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 文章内容
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
public interface ArticleService extends IService<Article> {

    List<Article> listArticle();

    boolean saveArticle(Article article);

    boolean updateArticle(Article article);

    boolean removeArticle(String id);

    String getContent(Long id);
}

