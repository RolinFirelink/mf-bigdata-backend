package com.arg.smart.web.company.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.entity.ProduceInfo;
import com.arg.smart.web.company.req.ReqProduceInfo;
import com.arg.smart.web.company.vo.ProductDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 企业生产信息表
 * @author cgli
 * @date: 2023-07-11
 * @version: V1.0.0
 */
public interface ProduceInfoService extends IService<ProduceInfo> {

    PageResult<ProduceInfo> list(ReqProduceInfo reqProduceInfo);

    Map<String, List<ProductDataVO>>getCXForCity(Integer flag, String...product);
}
