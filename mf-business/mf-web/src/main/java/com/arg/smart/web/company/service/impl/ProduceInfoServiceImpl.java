package com.arg.smart.web.company.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.entity.ProduceInfo;
import com.arg.smart.web.company.mapper.ProduceInfoMapper;
import com.arg.smart.web.company.req.ReqProduceInfo;
import com.arg.smart.web.company.service.ProduceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 企业生产信息表
 * @date: 2023-07-11
 * @version: V1.0.0
 */
@Service
public class ProduceInfoServiceImpl extends ServiceImpl<ProduceInfoMapper, ProduceInfo> implements ProduceInfoService {

    @Override
    public PageResult<ProduceInfo> list(ReqProduceInfo reqProduceInfo) {
        List<Long> companyId = null;
        if (reqProduceInfo.getCompanyName() != null && reqProduceInfo.getCompanyName() != "") {
            companyId = this.baseMapper.selectCompanyId(reqProduceInfo.getCompanyName());
        }
        System.out.println(companyId);
        LambdaQueryWrapper<ProduceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(companyId != null, ProduceInfo::getCompanyId, companyId)
                .eq(reqProduceInfo.getFlag() != null, ProduceInfo::getFlag, reqProduceInfo.getFlag());
        List<ProduceInfo> list = this.list(queryWrapper);
        System.out.println(list);
        //设置公司名字
        List<ProduceInfo> collect = list.stream().peek(item -> {
            item.setCompanyName(this.baseMapper.getNameById(item.getCompanyId()));
        }).collect(Collectors.toList());
        PageResult<ProduceInfo> pageResult = new PageResult<>(list);
        pageResult.setList(collect);
        return pageResult;
    }
}
