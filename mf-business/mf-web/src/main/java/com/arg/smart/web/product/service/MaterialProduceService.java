package com.arg.smart.web.product.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.*;
import com.arg.smart.web.product.req.ReqMaterialProduce;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;
import java.util.List;
import com.arg.smart.web.product.entity.vo.BaseProduceInfoVO;

/**
 * @author cgli
 * @description: 产品生产表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialProduceService extends IService<MaterialProduce> {
    Result<List<BaseProduceInfoVO>> fetchProduceInfo(Integer flag);

    List<MaterialProduceWithYear> ProduceScaleInfo(Integer flag);

    PageResult<MaterialProduce> list(ReqMaterialProduce reqMaterialProduce);

    List<MaterialProduceWithYear> getMaterialProductWithYears(Integer flag);

    List<MaterialProduceWithProduceBase> getMaterialProduceWithProduceBase(Integer flag);

    List<MaterialProduceWithProduceBase> getByProduceBaseIdAndFlag(Integer flag);

    void selectAndInsert();

    MaterialProduceWithCity queryByCity(Integer flag);

    void selectScaleAndInsert();

    List<EstimateTimeAndMarket> queryByEstimateTime(Integer flag, Date startTime, Date endTime);

    List<ProduceNameAndQuantity> getProduceQuantity(Integer flag);

    List<EstimateTimeAndMarket> getUnitQuantity(Integer flag);
}
