package com.arg.smart.web.data.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.entity.ProduceInfo;
import com.arg.smart.web.data.entity.BaseProductProductionScale;
import com.arg.smart.web.data.mapper.BaseProductProductionScaleMapper;
import com.arg.smart.web.data.req.ReqBaseProductProductionScale;
import com.arg.smart.web.data.service.BaseProductProductionScaleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author cgli
 * @description: 基地产品生产规模数据表
 * @date: 2023-07-20
 * @version: V1.0.0
 */
@Service
public class BaseProductProductionScaleServiceImpl extends ServiceImpl<BaseProductProductionScaleMapper, BaseProductProductionScale> implements BaseProductProductionScaleService {

    @Override
    public PageResult<BaseProductProductionScale> list(ReqBaseProductProductionScale reqBaseProductProductionScale) {
        List<Long> baseId = null;
        if (reqBaseProductProductionScale.getBaseName() != null && reqBaseProductProductionScale.getBaseName() != "") {
            baseId = this.baseMapper.selectBaseId(reqBaseProductProductionScale.getBaseName());
            if (baseId.size() <= 0){
                return new PageResult<>();
            }
        }
        LambdaQueryWrapper<BaseProductProductionScale> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(baseId != null && baseId.size() > 0, BaseProductProductionScale::getBaseId, baseId)
                .eq(reqBaseProductProductionScale.getFlag() != null, BaseProductProductionScale::getFlag, reqBaseProductProductionScale.getFlag())
                .between(reqBaseProductProductionScale.getStartTime() != null & reqBaseProductProductionScale.getEndTime() != null, BaseProductProductionScale::getStatisticalTime, reqBaseProductProductionScale.getStartTime(), reqBaseProductProductionScale.getEndTime());
        List<BaseProductProductionScale> list = this.list(queryWrapper);
        System.out.println(list);
        //设置公司名字
        List<BaseProductProductionScale> resultList = list.stream().peek(item -> {
            item.setBaseName(this.baseMapper.getNameById(item.getBaseId()));
        }).collect(Collectors.toList());
        return new PageResult<>(resultList);
    }
}
