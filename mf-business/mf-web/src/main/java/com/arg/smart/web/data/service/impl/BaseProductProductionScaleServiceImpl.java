package com.arg.smart.web.data.service.impl;

import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.data.entity.BaseProductProductionScale;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.vo.ProductSupply;
import com.arg.smart.web.data.entity.vo.ProductionScale;
import com.arg.smart.web.data.entity.vo.ProductionScaleResponseData;
import com.arg.smart.web.data.entity.vo.SupplyHeatResponseData;
import com.arg.smart.web.data.mapper.BaseProductProductionScaleMapper;
import com.arg.smart.web.data.req.ReqBaseProductProductionScale;
import com.arg.smart.web.data.service.BaseProductProductionScaleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 基地产品生产规模数据表
 * @author cgli
 * @date: 2023-07-20
 * @version: V1.0.0
 */
@Service
public class BaseProductProductionScaleServiceImpl extends ServiceImpl<BaseProductProductionScaleMapper, BaseProductProductionScale> implements BaseProductProductionScaleService {

    @Resource
    private ProductBaseService productBaseService;

    @Override
    public List<BaseProductProductionScale> list(ReqBaseProductProductionScale reqBaseProductProductionScale) {

        return this.list();
    }

    @Override
    public List<ProductionScaleResponseData> getProductionScale(ReqBaseProductProductionScale reqBaseProductProductionScale) {
        QueryWrapper<BaseProductProductionScale> queryWrapper = new QueryWrapper<>();
        Integer flag = reqBaseProductProductionScale.getFlag();
        Date startTime = reqBaseProductProductionScale.getStartTime();
        Date endTime = reqBaseProductProductionScale.getEndTime();
        queryWrapper.eq("flag",flag)
                .ge(startTime != null,"statistical_time",startTime)
                .le(endTime != null,"statistical_time",endTime)
                .select("sum(production_scale) as production_scale","product","scale_unit","base_id")
                .groupBy("base_id","product","scale_unit");
        List<BaseProductProductionScale> list = this.list(queryWrapper);
        if(list.size() == 0){
            return new ArrayList<>();
        }
        Map<Long, List<BaseProductProductionScale>> map = list.stream().collect(Collectors.groupingBy(BaseProductProductionScale::getBaseId));
        // 根据基地ID集合获取基地信息（位置、名称）
        List<ProductBase> productBases = productBaseService.listByIds(map.keySet());
        Map<Long, List<ProductBase>> productBaseMap = productBases.stream().collect(Collectors.groupingBy(ProductBase::getId));
        List<ProductionScaleResponseData> res = new ArrayList<>();
        map.forEach((baseId,value) -> {
            ProductionScaleResponseData data = new ProductionScaleResponseData();
            List<ProductBase> productBaseInfo = productBaseMap.get(baseId);
            data.setUnit(value.get(0).getScaleUnit());
            if(productBaseInfo != null){
                data.setBaseName(productBaseInfo.get(0).getBaseName());
                data.setLat(productBaseInfo.get(0).getLat());
                data.setLng(productBaseInfo.get(0).getLng());
                data.setRegion(productBaseInfo.get(0).getRegion());
                data.setCity(productBaseInfo.get(0).getCity());
            }
            List<ProductionScale> productionScales = new ArrayList<>();
            for (BaseProductProductionScale baseProductProductionScale : value){
                ProductionScale productionScale = new ProductionScale(baseProductProductionScale.getProduct(), baseProductProductionScale.getProductionScale());
                productionScales.add(productionScale);
            }
            data.setProductProductionScale(productionScales);
            res.add(data);
        });
        return res;
    }
}
