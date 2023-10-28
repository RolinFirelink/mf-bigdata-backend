package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.SupplyData;
import com.arg.smart.web.docking.mapper.SupplyDataMapper;
import com.arg.smart.web.docking.req.ReqSupplyData;
import com.arg.smart.web.docking.service.SupplyDataService;
import com.arg.smart.web.docking.vo.SupplyDataVO;
import com.arg.smart.web.product.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 惠农网省份月供应数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class SupplyDataServiceImpl extends ServiceImpl<SupplyDataMapper, SupplyData> implements SupplyDataService {

    @Resource
    private ProductPriceService productPriceService;

    @Override
    public List<SupplyData> list(ReqSupplyData reqSupplyData) {
        LambdaQueryWrapper<SupplyData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqSupplyData.getFlag();
        String startMonth = reqSupplyData.getStartMonth();
        String endMonth = reqSupplyData.getEndMonth();
        String province = reqSupplyData.getProvince();
        String product = reqSupplyData.getProduct();
        lambdaQueryWrapper.eq(flag != null,SupplyData::getFlag,flag);
        lambdaQueryWrapper.ge(startMonth != null,SupplyData::getDate,startMonth);
        lambdaQueryWrapper.le(endMonth != null,SupplyData::getDate,endMonth);
        lambdaQueryWrapper.like(province != null,SupplyData::getProvinceName,province);
        lambdaQueryWrapper.like(product != null,SupplyData::getProduct,product);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public List<SupplyDataVO> getSupplyData(ReqSupplyData reqSupplyData) {
        String month = reqSupplyData.getMonth();
        Integer flag = reqSupplyData.getFlag();
        //获取广东省的
        QueryWrapper<SupplyData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",month).eq("flag",flag).eq("province_name","广东省");
        queryWrapper.select("sum(cnt_ratio) cnt_ratio","province_name");
        SupplyData guangdongData = this.getOne(queryWrapper);
        if(guangdongData == null){
            return new ArrayList<>();
        }
        QueryWrapper<SupplyData> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("date",month).ne("province_name","广东省").eq("flag",flag).groupBy("province_name").orderByDesc("sum(cnt_ratio)");
        queryWrapper1.select("sum(cnt_ratio) cnt_ratio","province_name");
        queryWrapper1.last("limit 4");
        List<SupplyData> list = this.list(queryWrapper1);
        List<SupplyDataVO> res = new ArrayList<>();
        SupplyDataVO  supplyDataVO= new SupplyDataVO();
        supplyDataVO.setProvinceName("广东");
        supplyDataVO.setCntRatio(guangdongData.getCntRatio());
        res.add(supplyDataVO);
        List<SupplyDataVO> collect = list.stream().map(item -> {
            SupplyDataVO supplyDataVO1 = new SupplyDataVO();
            supplyDataVO1.setCntRatio(item.getCntRatio());
            //取省份的前两个字
            supplyDataVO1.setProvinceName(item.getProvinceName().substring(0,2));
            return supplyDataVO1;
        }).collect(Collectors.toList());
        res.addAll(collect);
        return res.stream().peek(item-> item.setAvgPrice(productPriceService.getAvgPriceByMonthAndRegion(item.getProvinceName(),month,flag))).collect(Collectors.toList());
    }
}
