package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.ProductMarket;
import com.arg.smart.web.data.mapper.ProductMarketMapper;
import com.arg.smart.web.data.req.ReqProductMarket;
import com.arg.smart.web.data.service.ProductMarketService;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 批发市场表
 * @date: 2023-08-18
 * @version: V1.0.0
 */
@Service
public class ProductMarketServiceImpl extends ServiceImpl<ProductMarketMapper, ProductMarket> implements ProductMarketService {

    @Override
    public List<ProductMarket> pageList(ReqProductMarket reqProductMarket) {
        String market = reqProductMarket.getMarket();
        Integer flag = reqProductMarket.getFlag();
        Date startTime = reqProductMarket.getStartTime();
        Date endTime = reqProductMarket.getEndTime();
        LambdaQueryWrapper<ProductMarket> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(market != null, ProductMarket::getMarket, market)
                .eq(flag != null, ProductMarket::getFlag, flag)
                .between(startTime != null && endTime != null, ProductMarket::getRecordDate, startTime, endTime);
        //设置区域名字
        List<ProductMarket> list = this.list(queryWrapper);
        list.stream().peek(item -> {
            if (item.getAreaCode() != null) {
                String areaCodeName = this.baseMapper.getPidName(item.getAreaCode());
                if (areaCodeName != null) {
                    areaCodeName = areaCodeName.replace(".", "");
                    item.setAreaCodeName(areaCodeName);
                }
            }
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean updateProductMarketById(ProductMarket productMarket) {
        if (setLatAndLng(productMarket)) {
            return this.updateById(productMarket);
        }
        return false;
    }

    @Override
    public boolean saveProductMarket(ProductMarket productMarket) {
        if (setLatAndLng(productMarket)) {
            return this.save(productMarket);
        }
        return false;
    }

    private boolean setLatAndLng(ProductMarket productMarket) {
        if (productMarket.getAreaCode() != null) {
            PositionData positionData = this.baseMapper.getLatAndLng(productMarket.getAreaCode());
            if (positionData != null) {
                if (positionData.getLng() != null) {
                    productMarket.setLng(positionData.getLng());
                }
                if (positionData.getLat() != null) {
                    productMarket.setLat(positionData.getLat());
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
