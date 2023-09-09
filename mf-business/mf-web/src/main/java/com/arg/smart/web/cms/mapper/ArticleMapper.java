package com.arg.smart.web.cms.mapper;

import com.arg.smart.web.cms.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

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

    @Update("update mf_market.sh_article set click_num = click_num + 1 where id = #{id}")
    void updateClickNum(Long id);

    @Select("<script> select * from sh_article_content WHERE id IN " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach> </script>")
    List<Article> getContents(@Param("ids") Set<Long> ids);

    @Insert({
            "<script>",
            "INSERT INTO sh_article_content (id, content) VALUES ",
            "<foreach collection='list' item='item' separator=','>",
            "(#{item.id}, #{item.content})",
            "</foreach>",
            "</script>"
    })
    void saveContentBatch(@Param("list") List<Article> collect);


    @Delete(
            "Delete from sh_article Where id Not In (Select a.id FROM(SELECT Max(id) AS id From sh_article where title not like concat('%','周报','%') and title not like concat('%','月报','%') Group By title)a)"
    )
    void deleteArticles();


}

