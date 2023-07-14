package com.arg.smart.web.position.service.impl;


import com.arg.smart.web.position.entity.PositionData;
import com.arg.smart.web.position.mapper.PositionMapper;
import com.arg.smart.web.position.service.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PositionServiceImpl extends ServiceImpl<PositionMapper, PositionData> implements PositionService {


    @Override
    public List<PositionData> statisticalDistribution(Integer flag) {
        return this.baseMapper.positionOfOrigin(flag);
    }
}
