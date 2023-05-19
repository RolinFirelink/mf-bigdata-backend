package com.arg.smart.web.cms.service.impl;

import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.entity.ArticleCategory;
import com.arg.smart.web.cms.mapper.ArticleMapper;
import com.arg.smart.web.cms.service.ArticleCategoryService;
import com.arg.smart.web.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 文章内容
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleCategoryService articleCategoryService;

    @Autowired
    public ArticleServiceImpl(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @Override
    public List<Article> listArticle() {
        return this.list().stream().peek(item -> {
            //获取分类名称
            ArticleCategory articleCategory = articleCategoryService.getById(item.getCategoryId());
            if (articleCategory != null) {
                item.setCategoryName(articleCategory.getName());
            }
        }).collect(Collectors.toList());
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
        if(!this.removeById(id)){
            return false;
        }
        return this.baseMapper.deleteContent(id);
    }

    @Override
    public String getContent(Long id) {
        return this.baseMapper.getContent(id);
    }
}

