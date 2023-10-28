package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.SellData;
import com.arg.smart.web.docking.mapper.SellDataMapper;
import com.arg.smart.web.docking.req.ReqSellData;
import com.arg.smart.web.docking.service.SellDataService;
import com.arg.smart.web.docking.vo.SellDataVO;
import com.arg.smart.web.product.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 惠农网省份月销售数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class SellDataServiceImpl extends ServiceImpl<SellDataMapper, SellData> implements SellDataService {

    @Resource
    private ProductPriceService productPriceService;

    @Override
    public List<SellData> list(ReqSellData reqSellData) {

        LambdaQueryWrapper<SellData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqSellData.getFlag();
        String province = reqSellData.getProvince();
        String startMonth = reqSellData.getStartMonth();
        String endMonth = reqSellData.getEndMonth();
        lambdaQueryWrapper.eq(flag != null,SellData::getFlag,flag);
        lambdaQueryWrapper.like(province != null,SellData::getProvinceName,province);
        lambdaQueryWrapper.ge(startMonth != null,SellData::getDate,startMonth);
        lambdaQueryWrapper.le(endMonth != null,SellData::getDate,endMonth);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public List<SellDataVO> getSellData(ReqSellData reqSellData) {
        String month = reqSellData.getMonth();
        Integer flag = reqSellData.getFlag();
        //获取广东省的
        QueryWrapper<SellData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",month).eq("flag",flag).eq("province_name","广东省");
        SellData guangdongData = this.getOne(queryWrapper);
        if(guangdongData == null){
            return new ArrayList<>();
        }
        QueryWrapper<SellData> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("date",month).ne("province_name","广东省").eq("flag",flag).orderByDesc("cnt_ratio");
        queryWrapper1.last("limit 4");
        List<SellData> list = this.list(queryWrapper1);
        List<SellDataVO> res = new ArrayList<>();
        SellDataVO sellDataVO = new SellDataVO();
        sellDataVO.setProvinceName("广东");
        sellDataVO.setCntRatio(guangdongData.getCntRatio());
        res.add(sellDataVO);
        List<SellDataVO> collect = list.stream().map(item -> {
            SellDataVO sellDataVO1 = new SellDataVO();
            sellDataVO1.setCntRatio(item.getCntRatio());
            sellDataVO1.setProvinceName(item.getProvinceName().substring(0,2));
            return sellDataVO1;
        }).collect(Collectors.toList());
        res.addAll(collect);
        return res.stream().peek(item-> item.setAvgPrice(productPriceService.getAvgPriceByMonthAndRegion(item.getProvinceName(),month,flag))).collect(Collectors.toList());
    }
}
