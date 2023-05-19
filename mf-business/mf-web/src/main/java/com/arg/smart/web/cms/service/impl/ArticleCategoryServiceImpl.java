package com.arg.smart.web.cms.service.impl;

import com.arg.smart.common.core.utils.TreeUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.cms.entity.ArticleCategory;
import com.arg.smart.web.cms.mapper.ArticleCategoryMapper;
import com.arg.smart.web.cms.service.ArticleCategoryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 文章
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

    @Override
    public List<ArticleCategory> listCategory() {
        List<ArticleCategory> list = this.list();
        List<ArticleCategory> categoryTree = new ArrayList<>();
        TreeUtils.buildTree(0L, list, categoryTree, ArticleCategory.class);
        return categoryTree;
    }
}
