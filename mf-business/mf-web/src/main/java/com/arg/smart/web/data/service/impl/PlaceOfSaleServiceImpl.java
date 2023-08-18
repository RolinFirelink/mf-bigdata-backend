package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.PlaceOfSale;
import com.arg.smart.web.data.mapper.PlaceOfSaleMapper;
import com.arg.smart.web.data.req.ReqPlaceOfSale;
import com.arg.smart.web.data.service.PlaceOfSaleService;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 销售地表
 * @date: 2023-08-18
 * @version: V1.0.0
 */
@Service
public class PlaceOfSaleServiceImpl extends ServiceImpl<PlaceOfSaleMapper, PlaceOfSale> implements PlaceOfSaleService {

    @Override
    public List<PlaceOfSale> pagelist(ReqPlaceOfSale reqPlaceOfSale) {
        String placeOfSale = reqPlaceOfSale.getPlaceOfSale();
        Integer flag = reqPlaceOfSale.getFlag();
        Date startTime = reqPlaceOfSale.getStartTime();
        Date endTime = reqPlaceOfSale.getEndTime();
        LambdaQueryWrapper<PlaceOfSale> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(placeOfSale != null, PlaceOfSale::getPlaceOfSale, placeOfSale)
                .eq(flag != null, PlaceOfSale::getFlag, flag)
                .between(startTime != null && endTime != null, PlaceOfSale::getRecordDate, startTime, endTime);
        //设置区域名字
        List<PlaceOfSale> list = this.list(queryWrapper);
        list.stream().peek(item -> {
            if (item.getAreaCode() != null) {
                System.out.println(item.getAreaCode());
                String areaCodeName = this.baseMapper.getPidName(item.getAreaCode());
                System.out.println(areaCodeName);
                if (areaCodeName != null) {
                    areaCodeName = areaCodeName.replace(".", "");
                    item.setAreaCodeName(areaCodeName);
                }
            }
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean savePlaceOfSale(PlaceOfSale placeOfSale) {
        if (setLatAndLng(placeOfSale)) {
            return this.save(placeOfSale);
        }
        return false;
    }

    @Override
    public boolean updatePlaceOfSaleById(PlaceOfSale placeOfSale) {
        if (setLatAndLng(placeOfSale)) {
            return this.updateById(placeOfSale);
        }
        return false;
    }

    private boolean setLatAndLng(PlaceOfSale placeOfSale) {
        if (placeOfSale.getAreaCode() != null) {
            PositionData positionData = this.baseMapper.getLatAndLng(placeOfSale.getAreaCode());
            if (positionData != null) {
                if (positionData.getLng() != null) {
                    placeOfSale.setLng(positionData.getLng());
                }
                if (positionData.getLat() != null) {
                    placeOfSale.setLat(positionData.getLat());
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
