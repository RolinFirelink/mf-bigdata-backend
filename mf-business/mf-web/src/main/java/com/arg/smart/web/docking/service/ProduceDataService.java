package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.ProduceData;
import com.arg.smart.web.docking.req.ReqProduceData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网产量数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface ProduceDataService extends IService<ProduceData> {

    List<ProduceData> list(ReqProduceData reqProduceData);

}
