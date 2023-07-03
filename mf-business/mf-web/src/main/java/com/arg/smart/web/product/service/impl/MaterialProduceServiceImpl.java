package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.vo.BaseProduceInfoVO;
import com.arg.smart.web.product.mapper.MaterialProduceMapper;
import com.arg.smart.web.product.service.MaterialProduceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @description: 产品生产表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialProduceServiceImpl extends ServiceImpl<MaterialProduceMapper, MaterialProduce> implements MaterialProduceService {
    @Resource
    private MaterialProduceService materialProduceService;

    @Resource
    private ProductBaseService productBaseService;


    @Override
    public Result<List<BaseProduceInfoVO>> fetchProduceInfo(Integer flag) {
        //查询生产信息列表

        List<MaterialProduce> produces = materialProduceService.list(
                new QueryWrapper<MaterialProduce>()
                        .groupBy("base_id")    //按基地分组
                        //.eq("create_time","max(create_time)")
                        .eq("flag", flag)//

        );
        Set<Long> baseIds = new HashSet<>();
        for (MaterialProduce produce : produces) {
            baseIds.add(produce.getBaseId());
        }
        //数据为空时直接返回
        if (baseIds.size() == 0) return Result.fail("没有生产信息");
        //查询基地信息
        List<ProductBase> bases = productBaseService.listByIds(baseIds);
        //
        Map<Long, ProductBase> map = new HashMap<>();
        for (ProductBase base : bases) {
            map.put(base.getId(), base);
        }
        //
        List<BaseProduceInfoVO> result = new ArrayList<>();
        for (MaterialProduce produce : produces) {
            if (!map.containsKey(produce.getBaseId())) break;    //不存在该基地信息
            ProductBase base = map.get(produce.getBaseId());
            result.add(new BaseProduceInfoVO(
                    base.getBaseName(),
                    base.getBaseNo(),
                    produce.getProductionScale(),
                    produce.getQuantity(),
                    produce.getTimeEstimate(),
                    base.getAddress()
            ));

        }

        return Result.ok(result);
    }



    @Override
    public Result<MaterialProduce> ProduceScaleInfo(Integer flag) {

        LambdaQueryWrapper<MaterialProduce> queryWrapper = new LambdaQueryWrapper<>();

       /* queryWrapper.select("DATE_FORMAT(time_estimate, '%Y-%m') AS month", "SUM(production_scale) AS totalScale")
                .eq("product_id", productId)
                .ge("time_estimate", LocalDateTime.now().minusMonths(12))
                .groupBy("DATE_FORMAT(time_estimate, '%Y-%m')");*/
        queryWrapper.select("DATE_FORMAT(time_estimate, '%Y-%m') AS month", "SUM(production_scale) AS totalScale")
                .eq("product_id", productId)
                .ge("time_estimate", LocalDateTime.now().minusMonths(12))
                .groupBy("DATE_FORMAT(time_estimate, '%Y-%m')");
        return materialProduceMapper.getProductMonthlyScale(wrapper);
        return materialProduceMapper.getProductMonthlyScale(wrapper);
    }

}
