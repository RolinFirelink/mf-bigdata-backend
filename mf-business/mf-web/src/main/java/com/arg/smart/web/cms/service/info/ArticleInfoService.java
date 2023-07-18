package com.arg.smart.web.cms.service.info;

import com.arg.smart.web.cms.entity.Article;

import java.util.List;

public interface ArticleInfoService {
    /**
     * 将Mysql数据库中的文章数据添加到Es中
     * @return 是否添加成功
     */
    boolean saveArticleToEs();

    /**
     * 从ES中查询数据返回给前端
     * @return 查询出的文章集合
     */
    List<Article> findArticlesByEs(String content);
}
