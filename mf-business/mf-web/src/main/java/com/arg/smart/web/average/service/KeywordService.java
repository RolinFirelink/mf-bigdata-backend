package com.arg.smart.web.average.service;

import com.arg.smart.web.average.entity.Keyword;
import com.arg.smart.web.average.req.ReqKeyword;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;
public interface KeywordService extends IService<Keyword> {
    List<Keyword> selectKeywordList(Integer flag,Integer count);
    List<Keyword> list(ReqKeyword reqKeyword);
}
