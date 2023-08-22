package com.arg.smart.web.data.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.data.mapper.SalesFlowMapper;
import com.arg.smart.web.data.req.ReqSalesFlow;
import com.arg.smart.web.data.service.SalesFlowService;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 销售流向
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Service
public class SalesFlowServiceImpl extends ServiceImpl<SalesFlowMapper, SalesFlow> implements SalesFlowService {

    @Override
    public PageResult<SalesFlow> pageList(ReqSalesFlow reqSalesFlow) {
        LambdaQueryWrapper<SalesFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(reqSalesFlow.getFlag() != null, SalesFlow::getFlag, reqSalesFlow.getFlag());
        List<SalesFlow> list = this.list(queryWrapper);
        //设置起始地点和终点地点
        list.stream().peek(item -> {
            if (item.getStartAreaCode() != null) {
                String startAddress = this.baseMapper.getAddress(item.getStartAreaCode());
                if (startAddress != null) {
                    item.setStartAddress(startAddress);
                }
            }
            if (item.getEndAreaCode() != null) {
                String endAddress = this.baseMapper.getAddress(item.getEndAreaCode());
                if (endAddress != null) {
                    item.setEndAddress(endAddress);
                }
            }
        }).collect(Collectors.toList());
        return new PageResult<>(list);
    }

    public List<SalesFlow> list(ReqSalesFlow reqSalesFlow) {

        Integer flag = reqSalesFlow.getFlag();
        LambdaQueryWrapper<SalesFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalesFlow::getFlag, flag);
        return this.list(queryWrapper);
    }

    @Override
    public boolean saveFlow(SalesFlow salesFlow) {
        if (setLngAndLat(salesFlow)) {
            return this.save(salesFlow);
        }
        return false;
    }

    @Override
    public boolean updateFlowById(SalesFlow salesFlow) {
        if (setLngAndLat(salesFlow)) {
            return this.updateById(salesFlow);
        }
        return false;
    }

    private boolean setLngAndLat(SalesFlow salesFlow) {
        String startAreaCode = salesFlow.getStartAreaCode();
        String endAreaCode = salesFlow.getEndAreaCode();
        if (startAreaCode == null || endAreaCode == null) {
            return false;
        }
        PositionData startLatAndLng = this.baseMapper.getLatAndLng(startAreaCode);
        PositionData endLatAndLng = this.baseMapper.getLatAndLng(endAreaCode);
        if (startLatAndLng != null) {
            salesFlow.setStartLat(startLatAndLng.getLat());
            salesFlow.setStartLng(startLatAndLng.getLng());
        }
        if (endLatAndLng != null) {
            salesFlow.setEndLat(endLatAndLng.getLat());
            salesFlow.setEndLng(endLatAndLng.getLng());
        }
        return true;
    }
}
