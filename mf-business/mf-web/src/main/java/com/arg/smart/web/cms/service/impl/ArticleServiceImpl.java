package com.arg.smart.web.cms.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.entity.ArticleCategory;
import com.arg.smart.web.cms.mapper.ArticleMapper;
import com.arg.smart.web.cms.req.ReqArticle;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.cms.service.ArticleCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
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
    public PageResult<Article> listArticle(ReqArticle reqArticle) {
        //设置查询条件
        Long categoryId = reqArticle.getCategoryId();
        Date startTime = reqArticle.getStartTime();
        Date endTime = reqArticle.getEndTime();
        String author = reqArticle.getAuthor();
        String title = reqArticle.getTitle();
        String source = reqArticle.getSource();
        Integer number = reqArticle.getNumber();
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if(categoryId != null){
            articleQueryWrapper.eq("category_id",categoryId);
        }
        if(startTime != null && endTime != null){
            articleQueryWrapper.ge("start_time",startTime).le("start_time",endTime);
        }
        if(author != null){
            articleQueryWrapper.like("author",author);
        }
        if(title != null){
            articleQueryWrapper.like("title",title);
        }
        if(source != null){
            articleQueryWrapper.like("source",source);
        }
        List<Article> list = this.list(articleQueryWrapper);
        if(number!=null && number>0){
            list.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
            list = list.subList(0, Math.min(list.size(),number));
        }
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
        if(!this.removeById(id)){
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
        if(categoryId != null && categoryId != 0){
            lambdaQueryWrapper.eq(Article::getCategoryId,categoryId);
        }
        String title = reqArticle.getTitle();
        if(title != null){
            lambdaQueryWrapper.like(Article::getTitle,title);
        }
        Date startTime = reqArticle.getStartTime();
        if(startTime != null){
            lambdaQueryWrapper.ge(Article::getStartTime,startTime);
        }
        Date endTime = reqArticle.getEndTime();
        if(endTime != null){
            lambdaQueryWrapper.le(Article::getEndTime,endTime);
        }
        return new PageResult<>(this.list(lambdaQueryWrapper));
    }

    @Override
    public List<Article> list(Long categoryId, Integer count) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(categoryId != 0){
            //按分类查询
            lambdaQueryWrapper.eq(Article::getCategoryId,categoryId);
        }
        lambdaQueryWrapper.orderByAsc(Article::getSort);
        lambdaQueryWrapper.orderByDesc(Article::getStartTime);
        lambdaQueryWrapper.last("limit "+count);
        return this.list(lambdaQueryWrapper);
    }
}

