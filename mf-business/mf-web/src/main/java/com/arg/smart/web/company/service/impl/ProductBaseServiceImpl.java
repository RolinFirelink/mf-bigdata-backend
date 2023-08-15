package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.company.vo.ProductBaseVO;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.stream.Collectors;

/**
 * @author lwy
 * @description: 产品基地
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Service
@Slf4j
public class ProductBaseServiceImpl extends ServiceImpl<ProductBaseMapper, ProductBase> implements ProductBaseService {

    @Override
    public List<ProductBase> getOptions() {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductBase::getId, ProductBase::getBaseName);
        return this.list(queryWrapper);
    }

    @Override
    public List<ProductBase> list(ReqProductBase reqProductBase) {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        String baseName = reqProductBase.getBaseName();
        if (baseName != null) {
            queryWrapper.like(ProductBase::getBaseName, baseName);
        }
        Integer maxArea = reqProductBase.getMaxArea();
        if (maxArea != null) {
            queryWrapper.le(ProductBase::getArea, maxArea);
        }
        Integer minArea = reqProductBase.getMinArea();
        if (minArea != null) {
            queryWrapper.ge(ProductBase::getArea, minArea);
        }
        Integer attestation = reqProductBase.getAttestation();
        if (attestation != null) {
            queryWrapper.like(ProductBase::getAttestation, attestation);
        }
        Integer flag = reqProductBase.getFlag();
        if (flag != null) {
            queryWrapper.eq(ProductBase::getFlag, flag);
        }
        queryWrapper.orderByDesc(ProductBase::getWebsiteAddress);
        List<ProductBase> list = this.list(queryWrapper);
        list.stream().peek(item -> {
            String companyName = this.baseMapper.getCompanyName(item.getCompanyId());
            if (companyName != null) {
                item.setCompanyName(companyName);
            }
            //设置详细地址
            if (item.getAreaCode() != null) {
                String address = this.baseMapper.queryAddr(item.getAreaCode());
                if (address != null) {
                    //去除前面的东西剩下具体位置
                    address = address.replace(".", "");
                    item.setDetail(item.getAddress().replace(address, ""));
                }
            }
        }).collect(Collectors.toList());
        return list;
    }


    public List<ProductBaseVO> getProductBaseInfo(ReqProductBase reqProductBase) {
        QueryWrapper<ProductBase> wrapper = new QueryWrapper<>();
        Integer flag = reqProductBase.getFlag();
        wrapper.eq("flag", flag);
        List<ProductBase> list = baseMapper.selectList(wrapper);
        return list.stream().map(productBase -> {
            ProductBaseVO productBaseVO = new ProductBaseVO();
            // 设置其他属性
            productBaseVO.setBaseName(productBase.getBaseName());
            productBaseVO.setIphoneNumber(productBase.getContactPhone());
            productBaseVO.setCity(productBase.getCity());
            productBaseVO.setAnnualOutput(productBase.getAnnualOutput());
            productBaseVO.setMainProduct(productBase.getMainProduct());
            productBaseVO.setLat(productBase.getLat());
            productBaseVO.setLng(productBase.getLng());
            productBaseVO.setOutputUnit(productBase.getOutputUnit());
            productBaseVO.setRegion(productBase.getRegion());
            return productBaseVO;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean saveBase(ProductBase productBase) {
        //将处理的数据设置到对象中
        setBaseLocationData(productBase);
        return this.save(productBase);
    }

    @Override
    public boolean updateBaseById(ProductBase productBase) {
        setBaseLocationData(productBase);
        return this.updateById(productBase);
    }

    private void setBaseLocationData(ProductBase productBase) {
        String areaCode = productBase.getAreaCode();
        if (areaCode == null) {
            return;
        }
        //保存详细地址
        String detail = productBase.getDetail();
        //获取经纬度
        PositionData positionData = this.baseMapper.queryLatAndLng(areaCode);
        //查到经维度
        if (positionData != null) {
            productBase.setLng(positionData.getLng());
            productBase.setLat(positionData.getLat());
        }
        //设置地点并拼接具体位置
        String address = this.baseMapper.queryAddr(areaCode);
        //查到位置
        if (address != null) {
            //地址处理
            String[] split = address.split("\\.");
            //判断城市和区是否有设置
            if (split.length >= 3) {
                //存在城市
                productBase.setCity(split[2]);
            }
            //判断区是否有设置
            if (split.length >= 4) {
                //存在城市
                productBase.setRegion(split[3]);
            }
            address = address.replace(".", "");
            //如果有详细地址则设置详细地址
            if (detail != null) {
                address = address + detail;
            }
            //设置地址
            productBase.setAddress(address);
        }
    }
}

