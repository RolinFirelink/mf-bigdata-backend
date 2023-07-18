package com.arg.smart.web.cms.repository;

import com.arg.smart.web.cms.entity.info.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ArticleInfoRepository extends ElasticsearchRepository<ArticleInfo, Long> {
}
