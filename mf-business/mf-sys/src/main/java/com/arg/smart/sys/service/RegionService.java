package com.arg.smart.sys.service;

import com.arg.smart.sys.entity.Region;
import com.arg.smart.sys.req.ReqRegion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 行政区域
 * @author cgli
 * @date: 2023-05-06
 * @version: V1.0.0
 */
public interface RegionService extends IService<Region> {

    /**
     * 查询列表
     * @param queryWrapper
     * @return
     */
    List<Region> listRegion(ReqRegion queryWrapper);

    List<Region> listByPid(String pid);
}
