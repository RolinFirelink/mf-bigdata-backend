package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.SupplyData;
import com.arg.smart.web.docking.req.ReqSupplyData;
import com.arg.smart.web.docking.vo.SupplyDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网省份月供应数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface SupplyDataService extends IService<SupplyData> {

    List<SupplyData> list(ReqSupplyData reqSupplyData);

    List<SupplyDataVO> getSupplyData(ReqSupplyData reqSupplyData);
}
