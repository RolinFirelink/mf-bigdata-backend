package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.ProvinceSupply;
import com.arg.smart.web.statistics.mapper.ProvinceSupplyMapper;
import com.arg.smart.web.statistics.req.ReqProvinceSupply;
import com.arg.smart.web.statistics.service.ProvinceSupplyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 省份供应表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Service
public class ProvinceSupplyServiceImpl extends ServiceImpl<ProvinceSupplyMapper, ProvinceSupply> implements ProvinceSupplyService {

    @Override
    public List<ProvinceSupply> list(ReqProvinceSupply reqProvinceSupply) {
        Integer flag = reqProvinceSupply.getFlag();
        LambdaQueryWrapper<ProvinceSupply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProvinceSupply::getFlag,flag);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<ProvinceSupply> listPage(ReqProvinceSupply reqProvinceSupply) {
        LambdaQueryWrapper<ProvinceSupply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(reqProvinceSupply.getFlag()!=null,ProvinceSupply::getFlag,reqProvinceSupply.getFlag())
                .like(reqProvinceSupply.getProvince()!=null,ProvinceSupply::getProvince, reqProvinceSupply.getProvince());
        return new PageResult<>(this.list(queryWrapper));
    }
}
