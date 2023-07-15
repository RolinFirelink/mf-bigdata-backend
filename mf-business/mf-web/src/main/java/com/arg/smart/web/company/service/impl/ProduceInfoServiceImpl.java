package com.arg.smart.web.company.service.impl;

import com.alibaba.nacos.common.utils.ArrayUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.entity.ProduceInfo;
import com.arg.smart.web.company.mapper.ProduceInfoMapper;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.req.ReqProduceInfo;
import com.arg.smart.web.company.service.ProduceInfoService;
import com.arg.smart.web.company.vo.ProductDataVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 企业生产信息表
 * @date: 2023-07-11
 * @version: V1.0.0
 */
@Service
public class ProduceInfoServiceImpl extends ServiceImpl<ProduceInfoMapper, ProduceInfo> implements ProduceInfoService {
    @Resource
    private ProductBaseMapper productBaseMapper;

    @Override
    public PageResult<ProduceInfo> list(ReqProduceInfo reqProduceInfo) {
        List<Long> companyId = null;
        if (reqProduceInfo.getCompanyName() != null && reqProduceInfo.getCompanyName() != "") {
            companyId = this.baseMapper.selectCompanyId(reqProduceInfo.getCompanyName());
        }
        System.out.println(companyId);
        LambdaQueryWrapper<ProduceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(companyId != null, ProduceInfo::getCompanyId, companyId)
                .eq(reqProduceInfo.getFlag() != null, ProduceInfo::getFlag, reqProduceInfo.getFlag());
        List<ProduceInfo> list = this.list(queryWrapper);
        System.out.println(list);
        //设置公司名字
        List<ProduceInfo> collect = list.stream().peek(item -> {
            item.setCompanyName(this.baseMapper.getNameById(item.getCompanyId()));
        }).collect(Collectors.toList());
        PageResult<ProduceInfo> pageResult = new PageResult<>(list);
        pageResult.setList(collect);
        return pageResult;
    }
    @Override
    public Map<String, List<ProductDataVO>> getCXForCity(Integer flag, String... products) {
        Map<String, List<ProductDataVO>> outputMap = new HashMap<>();

        outputMap.put("广州市", processCity("广州市", flag, products));
        outputMap.put("湛江市", processCity("湛江市", flag, products));
        outputMap.put("深圳市", processCity("深圳市", flag, products));
        outputMap.put("珠海市", processCity("珠海市",  flag, products));
        outputMap.put("汕头市", processCity("汕头市", flag, products));
        outputMap.put("佛山市", processCity("佛山市",  flag, products));
        outputMap.put("韶关市", processCity("韶关市",  flag, products));
        outputMap.put("肇庆市", processCity("肇庆市",  flag, products));
        outputMap.put("江门市", processCity("江门市", flag, products));
        outputMap.put("茂名市", processCity("茂名市", flag, products));
        outputMap.put("惠州市", processCity("惠州市", flag, products));
        outputMap.put("梅州市", processCity("梅州市", flag, products));
        outputMap.put("汕尾市", processCity("汕尾市", flag, products));
        outputMap.put("河源市", processCity("河源市", flag, products));
        outputMap.put("阳江市", processCity("阳江市", flag, products));
        outputMap.put("清远市", processCity("清远市", flag, products));
        outputMap.put("东莞市", processCity("东莞市", flag, products));
        outputMap.put("中山市", processCity("中山市", flag, products));
        outputMap.put("潮州市", processCity("潮州市", flag, products));
        outputMap.put("揭阳市", processCity("揭阳市", flag, products));
        outputMap.put("云浮市", processCity("云浮市", flag, products));
        return outputMap;
    }

    private List<ProductDataVO> processCity(String cityName, Integer flag, String... products) {
        List<String> idsByAddress = productBaseMapper.getCompanyId(cityName, flag);

        if (idsByAddress == null || idsByAddress.isEmpty()) {
            ProductDataVO cityData = new ProductDataVO("无", 0L);
            return Collections.singletonList(cityData);
        }

        QueryWrapper<ProduceInfo> queryWrapper = new QueryWrapper<ProduceInfo>().in("company_id", idsByAddress);
        List<ProduceInfo> allByBaseline = this.list(queryWrapper);
        Map<String, Long> productScaleMap = new HashMap<>();

        for (ProduceInfo produceInfo : allByBaseline) {
            String productName = produceInfo.getProduct();
            long scale = produceInfo.getScale();

            if (ArrayUtils.contains(products, productName)) {
                productScaleMap.put(productName, productScaleMap.getOrDefault(productName, 0L) + scale);
            } else {
                productScaleMap.put("其他", productScaleMap.getOrDefault("其他", 0L) + scale);
            }
        }

        List<ProductDataVO> cityProductDataList = new ArrayList<>();
        long totalScale = 0L;

        // 处理产品数据
        for (String product : products) {
            long scale = productScaleMap.getOrDefault(product, 0L);
            totalScale += scale;
            ProductDataVO productData = new ProductDataVO(product, scale);
            cityProductDataList.add(productData);
        }

        // 处理其他产品数据
        if (productScaleMap.containsKey("其他")) {
            long otherScale = productScaleMap.get("其他");
            totalScale += otherScale;
            ProductDataVO otherData = new ProductDataVO("其他品种", otherScale);
            cityProductDataList.add(otherData);
        }

        // 构建总计数据
        ProductDataVO totalData = new ProductDataVO("总计", totalScale);
        cityProductDataList.add(totalData);

        return cityProductDataList;
    }
}
