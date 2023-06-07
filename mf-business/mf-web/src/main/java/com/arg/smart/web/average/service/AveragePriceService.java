package com.arg.smart.web.average.service;

import com.arg.smart.web.average.entity.AveragePrice;
import com.arg.smart.web.average.req.ReqAveragePrice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 均价表
 * @author cgli
 * @date: 2023-06-06
 * @version: V1.0.0
 */
public interface AveragePriceService extends IService<AveragePrice> {

    /**
     * 记录均价
     * @return
     */
    boolean timingSave();

    /**
     * 根据条件获取均价集合
     * @return
     */
    List<AveragePrice> getList(ReqAveragePrice reqAveragePrice);

    /**
     * 更新均价
     * @param averagePrice
     * @return
     */
    boolean updateAvg(AveragePrice averagePrice);

    /**
     * 删除均价
     * @param id
     * @return
     */
    boolean removeAvg(String id);
}
