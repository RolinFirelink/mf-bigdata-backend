package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.MarketInfo;
import com.arg.smart.web.docking.mapper.MarketInfoMapper;
import com.arg.smart.web.docking.req.ReqMarketInfo;
import com.arg.smart.web.docking.service.MarketInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;

/**
 * @description: 惠农网产品信息表
 * @author cgli
 * @date: 2023-09-26
 * @version: V1.0.0
 */
@Service
public class MarketInfoServiceImpl extends ServiceImpl<MarketInfoMapper, MarketInfo> implements MarketInfoService {

    @Override
    public List<MarketInfo> list(ReqMarketInfo reqMarketInfo) {
        String cate3Name = reqMarketInfo.getCate3Name();
        String dateMonth = reqMarketInfo.getDateMonth();
        QueryWrapper<MarketInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(cate3Name!= null,"cate3Name",cate3Name);
        queryWrapper.like(dateMonth != null,"dateMonth",dateMonth);
        return this.list(queryWrapper);
    }
}
