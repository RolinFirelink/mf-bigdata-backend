package com.arg.smart.web.cms.service;

import com.arg.smart.web.cms.entity.ArticleCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 文章
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {
    List<ArticleCategory> listCategory();

}
