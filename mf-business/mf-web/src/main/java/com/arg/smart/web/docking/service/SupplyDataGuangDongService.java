package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.SupplyDataGuangDong;
import com.arg.smart.web.docking.req.ReqSupplyDataGuangDong;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网广东月供应数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface SupplyDataGuangDongService extends IService<SupplyDataGuangDong> {

    List<SupplyDataGuangDong> list(ReqSupplyDataGuangDong reqSupplyDataGuangDong);
}
