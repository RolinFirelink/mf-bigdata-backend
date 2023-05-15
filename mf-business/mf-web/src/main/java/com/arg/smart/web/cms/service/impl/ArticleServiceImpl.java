package com.arg.smart.web.cms.service.impl;

import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.mapper.ArticleMapper;
import com.arg.smart.web.cms.service.ArticleService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 文章内容
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}

