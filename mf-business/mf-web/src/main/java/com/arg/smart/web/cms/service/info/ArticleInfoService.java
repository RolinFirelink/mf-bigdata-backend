package com.arg.smart.web.cms.service.info;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.req.ReqArticle;
import com.arg.smart.web.customer.entity.HotWord;

import java.util.List;
import java.util.Map;

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
    PageResult<Article> findArticlesByEs(ReqArticle reqArticle, ReqPage reqPage);

    /**
     * 批量删除
     */
    void deleteBatch(List<Long> ids);

    /**
     * 新增或修改数据
     */
    void saveArticle(Article article);

    List<HotWord> analysis(ReqArticle reqArticle);

    Map<String,Object> analysisPublic(String sources);
}
