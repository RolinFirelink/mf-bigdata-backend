package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.entity.vo.TransportInformation;
import com.arg.smart.web.cargo.mapper.ProductCirculationDataMapper;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Service
public class ProductCirculationDataServiceImpl extends ServiceImpl<ProductCirculationDataMapper, ProductCirculationData> implements ProductCirculationDataService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    ProductCirculationDataMapper productCirculationDataMapper;

    @Override
    public PageResult<ProductCirculationData> selectListByCondition(ReqProductCirculationData reqProductCirculationData) {
        LambdaQueryWrapper<ProductCirculationData> queryWrapper = new LambdaQueryWrapper<>();
        Long orderId = reqProductCirculationData.getOrderId();
        Integer businessType = reqProductCirculationData.getBusinessType();
        String modeTransport = reqProductCirculationData.getModeTransport();
        if(orderId != null){
            queryWrapper.eq(ProductCirculationData::getOrderId,orderId);
        }
        if(businessType != null){
            queryWrapper.eq(ProductCirculationData::getBusinessType,businessType);
        }
        if(modeTransport != null){
            queryWrapper.like(ProductCirculationData::getModeTransport,modeTransport);
        }
        List<ProductCirculationData> list = this.list(queryWrapper);
        PageResult<ProductCirculationData> pageResult = new PageResult<>(list);
        List<ProductCirculationData> collect = list.stream().peek(item -> {
            item.setCompanyName(companyMapper.getNameById(item.getCompanyId()));
        }).collect(Collectors.toList());
        pageResult.setList(collect);
        return pageResult;
    }

    @Override
    public Result<Map<String, TransportInformation>> getTransportInformation(Integer flag) {
        if (Objects.isNull(flag) || !(flag <= 6 && flag >=0)){
            return Result.fail("传入参数错误!");
        }
        List<TransportInformation> transportInformation = productCirculationDataMapper.getTransportInformation(flag);
        Map<String, TransportInformation> collect = transportInformation.stream().collect(Collectors.toMap(
                transportInformation1 -> {
                    String isPro = transportInformation1.getReceivingLocation().substring(0, 3);
                    if ("内蒙古".equals(isPro) || "黑龙江".equals(isPro)) {
                        return isPro;
                    } else {
                        return isPro.substring(0, 2);
                    }
                }, transportInformation1 -> transportInformation1, (key1, key2) -> key2
        ));
        return Result.ok(collect);
    }
}
