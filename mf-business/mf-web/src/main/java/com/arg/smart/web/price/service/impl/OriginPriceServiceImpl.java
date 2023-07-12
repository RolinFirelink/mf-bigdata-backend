package com.arg.smart.web.price.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.price.entity.OriginPrice;
import com.arg.smart.web.price.mapper.OriginPriceMapper;
import com.arg.smart.web.price.req.ReqOriginPrice;
import com.arg.smart.web.price.service.OriginPriceService;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 产地价格
 * @date: 2023-07-10
 * @version: V1.0.0
 */
@Service
public class OriginPriceServiceImpl extends ServiceImpl<OriginPriceMapper, OriginPrice> implements OriginPriceService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public PageResult<OriginPrice> listPage(ReqOriginPrice reqMaterialProduce) {
        List<Long> companyId = null;
        List<Long> cityCode = null;
        if (reqMaterialProduce.getCompanyName() != null && reqMaterialProduce.getCompanyName() != "") {
            companyId = this.baseMapper.selectCompanyId(reqMaterialProduce.getCompanyName());
        }
        if (reqMaterialProduce.getCityName() != null && reqMaterialProduce.getCityName() != "") {
            cityCode = this.baseMapper.selectCityCode(reqMaterialProduce.getCityName());
        }
        LambdaQueryWrapper<OriginPrice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(companyId != null, OriginPrice::getCompanyId, companyId)
                .in(cityCode != null, OriginPrice::getCityCode, cityCode)
                .eq(reqMaterialProduce.getFlag() != null, OriginPrice::getFlag, reqMaterialProduce.getFlag());
        List<OriginPrice> list = this.list(queryWrapper);
        //设置公司名字和主要销售城市
        List<OriginPrice> collect = list.stream().peek(item -> {
            //设置公司名和城市名称
            item.setCompanyName(companyMapper.getNameById(item.getCompanyId()));
            item.setMainCity(this.baseMapper.selectMainCity(item.getCityCode()));
        }).collect(Collectors.toList());
        PageResult<OriginPrice> pageResult = new PageResult<>(list);
        pageResult.setList(collect);
        return pageResult;
    }

}
