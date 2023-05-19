package com.arg.smart.web.cms.mapper;

import com.arg.smart.web.cms.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * @author cgli
 * @description: 文章内容
 * @date: 2023-05-08
 * @version: V1.0.0
 */
public interface ArticleMapper extends BaseMapper<Article> {

    @Insert("insert into sh_article_content values(#{id},#{content})")
    int saveContent(@Param("id") Long id, @Param("content") String content);

    @Update("update sh_article_content set content = #{content} where id = #{id}")
    int updateContent(@Param("id") Long id, @Param("content") String content);

    @Delete("delete from sh_article_content where id = #{id}")
    boolean deleteContent(@Param("id") String id);

    @Select("select content from sh_article_content where id = #{id}")
    String getContent(@Param("id") Long id);
}

