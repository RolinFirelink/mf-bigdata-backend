package com.arg.smart.sys.service.impl;

import com.arg.smart.sys.entity.Region;
import com.arg.smart.sys.mapper.RegionMapper;
import com.arg.smart.sys.req.ReqRegion;
import com.arg.smart.sys.service.RegionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cgli
 * @description: 行政区域
 * @date: 2023-05-06
 * @version: V1.0.0
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public List<Region> listRegion(ReqRegion queryWrapper) {
        return baseMapper.selectList(buildCondition(queryWrapper));
    }

    private LambdaQueryWrapper buildCondition(ReqRegion reqRegion) {
        return new LambdaQueryWrapper<Region>()
                .eq(reqRegion.getCode() != null, Region::getCode, reqRegion.getCode())
                .like(reqRegion.getName() != null, Region::getName, reqRegion.getName())
                .like(reqRegion.getEnglishName() != null, Region::getEnglishName, reqRegion.getEnglishName())
                .eq(reqRegion.getPid() != null, Region::getPid, reqRegion.getPid())
                .eq(Region::getDeletedFlag, 0)
                .eq(Region::getCountryId, 1)
                .orderByAsc(Region::getPid);
    }
}
