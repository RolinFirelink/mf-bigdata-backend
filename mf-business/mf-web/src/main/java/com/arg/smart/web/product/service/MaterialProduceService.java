package com.arg.smart.web.product.service;

<<<<<<< HEAD
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.MaterialProduceWithProduceBase;
import com.arg.smart.web.product.entity.report.MaterialProduceWithYear;
import com.arg.smart.web.product.req.ReqMaterialProduce;
=======
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.vo.BaseProduceInfoVO;
>>>>>>> 58c88111450b25884623ab7ab42a853f12f707e3
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import java.util.List;

/**
 * @description: 产品生产表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialProduceService extends IService<MaterialProduce> {
    Result<List<BaseProduceInfoVO>> fetchProduceInfo(Integer flag);

    Result<MaterialProduce> ProduceScaleInfo(Integer flag);

    PageResult<MaterialProduce> list(ReqMaterialProduce reqMaterialProduce);

    List<MaterialProduceWithYear> getMaterialProductWithYears(Integer flag);

    List<MaterialProduceWithProduceBase> getMaterialProduceWithProduceBase(Integer flag);

    List<MaterialProduceWithProduceBase> getByProduceBaseIdAndFlag(Integer flag);

    void selectAndInsert();
}
