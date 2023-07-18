package com.arg.smart.web.cargo.service;

import com.arg.smart.web.cargo.entity.FreightProgress;
import com.arg.smart.web.cargo.entity.vo.OrderInformationList;
import com.arg.smart.web.cargo.req.ReqFreightProgress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 货运进度表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
public interface FreightProgressService extends IService<FreightProgress> {

    List<FreightProgress> selectListByCondition(ReqFreightProgress reqFreightProgress);



}
