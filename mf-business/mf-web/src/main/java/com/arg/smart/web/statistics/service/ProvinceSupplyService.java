package com.arg.smart.web.statistics.service;

import com.arg.smart.web.statistics.entity.ProvinceSupply;
import com.arg.smart.web.statistics.req.ReqProvinceSupply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 省份供应表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
public interface ProvinceSupplyService extends IService<ProvinceSupply> {

    List<ProvinceSupply> list(ReqProvinceSupply reqProvinceSupply);
}
