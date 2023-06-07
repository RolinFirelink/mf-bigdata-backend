package com.arg.smart.web.average.service.impl;

import com.arg.smart.web.average.entity.AveragePrice;
import com.arg.smart.web.average.mapper.AveragePriceMapper;
import com.arg.smart.web.average.req.ReqAveragePrice;
import com.arg.smart.web.average.service.AveragePriceService;
import com.arg.smart.web.average.vo.OrderVo;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: 均价表
 * @author cgli
 * @date: 2023-06-06
 * @version: V1.0.0
 */
@Service
public class AveragePriceServiceImpl extends ServiceImpl<AveragePriceMapper, AveragePrice> implements AveragePriceService {
    @Resource
    private RedisTemplate<String,List<AveragePrice>> redisTemplate;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ProductCirculationDataService productCirculationDataService;
    private static final String REDIS_MARK = "AVG_";

    @Override
    @Transactional
    public boolean timingSave() {
        //创建List与字符串的映射
        Map<Integer, List<OrderVo>> map = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            map.put(i,new ArrayList<>());
        }

        //获得前一天的所有销售订单数据
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getCategory,4);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime yesterdayStart = LocalDateTime.of(yesterday, LocalTime.MIN);
        LocalDateTime yesterdayEnd = LocalDateTime.of(yesterday, LocalTime.MAX);
        orderLambdaQueryWrapper.between(Order::getCreateTime,yesterdayStart,yesterdayEnd);

        List<Order> orderList = orderService.list(orderLambdaQueryWrapper);
        orderList.forEach(item -> {
            LambdaQueryWrapper<OrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(OrderDetail::getOrderId,item.getId());
            // TODO 目前存在无法查询到所有符合条件的数据的问题，怀疑是数据本身有问题
            List<OrderDetail> orderDetails = orderDetailService.list(detailLambdaQueryWrapper);
            LambdaQueryWrapper<ProductCirculationData> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProductCirculationData::getOrderId,item.getId());
            ProductCirculationData circulationData = productCirculationDataService.getOne(queryWrapper);
            // TODO 组装OrderVo的方式效率不高，后期要进行优化
            OrderVo orderVo = new OrderVo(item,orderDetails,circulationData);
            if(!orderDetails.isEmpty() && circulationData != null){
                map.get(item.getFlag()).add(orderVo);
            }
        });
        for (Map.Entry<Integer, List<OrderVo>> entry : map.entrySet()) {
            List<OrderVo> value = entry.getValue();
            if(value.isEmpty()){
                continue;
            }
            List<OrderVo> voList = new ArrayList<>();
            for (OrderVo vo : value) {
                if(vo.getProductCirculationData().getReceivingLocation().contains("广东")){
                    voList.add(vo);
                }
            }
            yesterday = LocalDate.now().minusDays(1);
            Date yesterdayDate = Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String unit = null;
            if(!voList.isEmpty()){
                unit = voList.get(0).getOrderDetails().get(0).getUnit();
            }
            save(new AveragePrice(null,voList.get(0).getOrder().getFlag(),
                    getAvg(voList),unit,yesterdayDate,"广东",0));
            if(!value.isEmpty()){
                unit = value.get(0).getOrderDetails().get(0).getUnit();
            }else {
                unit = null;
            }
            save(new AveragePrice(null,value.get(0).getOrder().getFlag(),
                    getAvg(value),unit,yesterdayDate,"全国",0));
        }
        return true;
    }

    @Override
    public List<AveragePrice> getList(ReqAveragePrice reqAveragePrice) {
        List<AveragePrice> averagePrices = redisTemplate.opsForValue().get(REDIS_MARK + reqAveragePrice.getFlag());
        if(averagePrices==null){
            LambdaQueryWrapper<AveragePrice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(AveragePrice::getFlag,reqAveragePrice.getFlag());
            List<AveragePrice> prices = list(lambdaQueryWrapper);
            long expirationTime = 24 * 60 * 60;
            redisTemplate.opsForValue().set(REDIS_MARK+reqAveragePrice.getFlag(),prices,expirationTime, TimeUnit.SECONDS);
            averagePrices = prices;
        }
        return averagePrices;
    }

    private BigDecimal getAvg(List<OrderVo> allList) {
        if(allList.isEmpty()){
            return BigDecimal.ZERO;
        }
        BigDecimal num = BigDecimal.ZERO;
        int times = 0;
        for (OrderVo item : allList) {
            List<OrderDetail> orderDetails = item.getOrderDetails();
            for (OrderDetail detail : orderDetails) {
                BigDecimal salesAmount = detail.getSalesAmount();
                num = num.add(salesAmount);
                times+=detail.getSalesQuantity();
            }
        }
        num = num.divide(new BigDecimal(times),BigDecimal.ROUND_CEILING);
        return num;
    }
}
