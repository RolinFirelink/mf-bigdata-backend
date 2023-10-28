package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.SellData;
import com.arg.smart.web.docking.req.ReqSellData;
import com.arg.smart.web.docking.vo.SellDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网省份月销售数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface SellDataService extends IService<SellData> {

    List<SellData> list(ReqSellData reqSellData);

    List<SellDataVO> getSellData(ReqSellData reqSellData);
}
