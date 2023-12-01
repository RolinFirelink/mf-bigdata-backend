package com.arg.smart.web.average.service.impl;

import com.arg.smart.web.average.entity.Keyword;
import com.arg.smart.web.average.mapper.KeywordMapper;
import com.arg.smart.web.average.req.ReqKeyword;
import com.arg.smart.web.average.service.KeywordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class KeywordServiceImpl extends ServiceImpl<KeywordMapper, Keyword> implements KeywordService {
    @Override
    public List<Keyword> selectKeywordList(Integer flag, Integer count) {
        LambdaQueryWrapper<Keyword> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Keyword::getStatus, 1)
                .last("LIMIT " + count)
                .like(Keyword::getFlags, "%;" + flag.toString() + ";%");

        return baseMapper.selectList(lambdaQueryWrapper);
    }

//    @Override
//    public List<Keyword> list(ReqKeyword reqKeyword) {
//
//        String keywordName = reqKeyword.getKeywordName();
//
//        String flag = reqKeyword.getFlags();
//
//        Boolean sort = reqKeyword.getSort();
//
//        LambdaQueryWrapper<Keyword> lambdaQueryWrapper = new LambdaQueryWrapper();
//        int i = 1;
//        if (flag != null) {
//            String[] flags = flag.split(";");
//            for (; i < flags.length - 2; i++) {
//                lambdaQueryWrapper.like(Keyword::getFlags, "%;" + flags[i] + ";%").or();
//            }
//            lambdaQueryWrapper.like(Keyword::getFlags, "%;" + flags[i] + ";%");
//        }
//
//        if (sort != null && sort == true) {
//            lambdaQueryWrapper.orderByAsc(sort, Keyword::getSortOrder);
//        }
//        if (keywordName != null) {
//            lambdaQueryWrapper.like(keywordName != null, Keyword::getKeywordName, reqKeyword.getKeywordName());
//        }
//
//
//        return baseMapper.selectList(lambdaQueryWrapper);
//    }
@Override
public List<Keyword> list(ReqKeyword reqKeyword) {
    String keywordName = reqKeyword.getKeywordName();
    String flag = reqKeyword.getFlags();
    Boolean sort = reqKeyword.getSort();

    LambdaQueryWrapper<Keyword> lambdaQueryWrapper = new LambdaQueryWrapper<>();

    if (flag != null) {
        String[] flags = flag.split(";");
        if (flags.length > 1) {
            lambdaQueryWrapper.nested(wrapper -> {
                for (int i = 0; i < flags.length - 1; i++) {
                    if (i > 0) {
                        wrapper.or();
                    }
                    wrapper.like(Keyword::getFlags, "%;" + flags[i] + ";%");
                }
            });
        } else {
            lambdaQueryWrapper.like(Keyword::getFlags, "%;" + flags[0] + ";%");
        }
    }

    if (keywordName != null) {
        if (lambdaQueryWrapper.getExpression().getNormal().size() > 0) {
            lambdaQueryWrapper.and(wrapper -> wrapper.like(Keyword::getKeywordName, keywordName));
        } else {
            lambdaQueryWrapper.like(Keyword::getKeywordName, keywordName);
        }
    }

    if (sort != null && sort) {
        lambdaQueryWrapper.orderByAsc(Keyword::getSortOrder);
    }

    return baseMapper.selectList(lambdaQueryWrapper);
}

}
