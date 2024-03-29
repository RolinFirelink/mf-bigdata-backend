package com.arg.smart.web.customer.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.customer.entity.HotWord;
import com.arg.smart.web.customer.mapper.HotWordMapper;
import com.arg.smart.web.customer.req.ReqHotWord;
import com.arg.smart.web.customer.service.HotWordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 热词表
 * @author zsj
 * @date: 2023-06-29
 * @version: V1.0.0
 */
@Service
@Slf4j
public class HotWordServiceImpl extends ServiceImpl<HotWordMapper, HotWord> implements HotWordService {

    /**
     * 按提及次数count查询前100条
     */
    @Override
    public List<HotWord> publicList(Integer type, ReqHotWord reqHotWord) {
        LambdaQueryWrapper<HotWord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(HotWord::getCount);
        if(type == 1){
            // 只查负面的
            queryWrapper.eq(HotWord::getSentiment,-1);
        }
        Integer count = reqHotWord.getCount();
        if(count == null){
            count = 20;
        }
        Integer flag = reqHotWord.getFlag();
        queryWrapper.like(flag != null,HotWord::getFlags,flag);
        queryWrapper.orderByDesc(HotWord::getCount);
        queryWrapper.last("limit "+count);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<HotWord> list(ReqHotWord reqHotWord) {
        LambdaQueryWrapper<HotWord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(reqHotWord.getName()!=null, HotWord::getName, reqHotWord.getName())
                .eq(reqHotWord.getSentiment()!=null, HotWord::getSentiment,reqHotWord.getSentiment())
                .between(reqHotWord.getStartTime()!=null&&reqHotWord.getEndTime()!=null,HotWord::getStatisticalTime,reqHotWord.getStartTime(),reqHotWord.getEndTime());
        return new PageResult<>(this.list(queryWrapper));
    }
}
