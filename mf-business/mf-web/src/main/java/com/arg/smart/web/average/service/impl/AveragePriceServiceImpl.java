package com.arg.smart.web.average.service.impl;

import com.arg.smart.web.average.entity.AveragePrice;
import com.arg.smart.web.average.mapper.AveragePriceMapper;
import com.arg.smart.web.average.req.ReqAveragePrice;
import com.arg.smart.web.average.service.AveragePriceService;
import com.arg.smart.web.average.vo.AverageVo;
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
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 均价表
 * @date: 2023-06-06
 * @version: V1.0.0
 */
@Service
public class AveragePriceServiceImpl extends ServiceImpl<AveragePriceMapper, AveragePrice> implements AveragePriceService {
    @Resource
    private RedisTemplate<String, List<AveragePrice>> redisTemplate;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;

    @Resource

    private ProductCirculationDataService productCirculationDataService;
    private static final String REDIS_MARK = "avg_data:";

    @Override
    @Transactional
    public boolean timingSave() {
        //创建List与字符串的映射
        Map<Integer, List<AverageVo>> map = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            map.put(i, new ArrayList<>());
        }

        //获得前一天的所有销售订单数据
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getCategory, 4);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime yesterdayStart = LocalDateTime.of(yesterday, LocalTime.MIN);
        LocalDateTime yesterdayEnd = LocalDateTime.of(yesterday, LocalTime.MAX);
        orderLambdaQueryWrapper.between(Order::getCreateTime, yesterdayStart, yesterdayEnd);
        List<Order> orderList = orderService.list(orderLambdaQueryWrapper);
        orderList.forEach(item -> {
            LambdaQueryWrapper<OrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(detailLambdaQueryWrapper);
            LambdaQueryWrapper<ProductCirculationData> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProductCirculationData::getOrderId, item.getId());
            ProductCirculationData circulationData = productCirculationDataService.getOne(queryWrapper);
            // TODO 组装OrderVo的方式效率不高，后期要进行优化
            AverageVo averageVo = new AverageVo(item, orderDetails, circulationData);
            if (!orderDetails.isEmpty() && circulationData != null) {
                map.get(item.getFlag()).add(averageVo);
            }
        });
        for (Map.Entry<Integer, List<AverageVo>> entry : map.entrySet()) {
            List<AverageVo> value = entry.getValue();
            if (value.isEmpty()) {
                continue;
            }
            List<AverageVo> voList = new ArrayList<>();
            for (AverageVo vo : value) {
                if (vo.getProductCirculationData().getReceivingLocation().contains("广东")) {
                    voList.add(vo);
                }
            }
            yesterday = LocalDate.now().minusDays(1);
            Date yesterdayDate = Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String unit = null;
            if (!voList.isEmpty()) {
                unit = voList.get(0).getOrderDetails().get(0).getUnit();
            }
            save(new AveragePrice(null, voList.get(0).getOrder().getFlag(),
                    getAvg(voList), unit, yesterdayDate, "广东", 0));
            if (!value.isEmpty()) {
                unit = value.get(0).getOrderDetails().get(0).getUnit();
            } else {
                unit = null;
            }
            save(new AveragePrice(null, value.get(0).getOrder().getFlag(),
                    getAvg(value), unit, yesterdayDate, "全国", 0));
        }
        return true;
    }

    @Override
    public List<AveragePrice> getList(ReqAveragePrice reqAveragePrice) {
        String key = REDIS_MARK + reqAveragePrice.getFlag() + "_" + reqAveragePrice.getPlace();
        List<AveragePrice> averagePrices = redisTemplate.opsForValue().get(key);
        if (averagePrices == null || averagePrices.isEmpty()) {
            LambdaQueryWrapper<AveragePrice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(AveragePrice::getFlag, reqAveragePrice.getFlag());
            lambdaQueryWrapper.like(AveragePrice::getPlace, reqAveragePrice.getPlace());
            averagePrices = list(lambdaQueryWrapper);
            redisTemplate.opsForValue().set(key, averagePrices, 1, TimeUnit.DAYS);
        }
        return averagePrices;
    }

    @Override
    public boolean updateAvg(AveragePrice averagePrice) {
        boolean update = updateById(averagePrice);
        Boolean delete = redisTemplate.opsForValue().getOperations().delete(REDIS_MARK + averagePrice.getFlag());
        return update && Boolean.TRUE.equals(delete);
    }

    @Override
    public boolean removeAvg(String id) {
        AveragePrice averagePrice = getById(id);
        boolean remove = removeById(id);
        Boolean delete = redisTemplate.opsForValue().getOperations().delete(REDIS_MARK + averagePrice.getFlag());
        return remove && Boolean.TRUE.equals(delete);
    }

    @Override
    @Transactional
    public boolean removeAvgs(List<String> asList) {
        List<AveragePrice> averagePrices = asList.stream().map(this::getById).collect(Collectors.toList());
        boolean remove = removeByIds(asList);
        Set<Integer> set = new HashSet<>();
        for (AveragePrice item : averagePrices) {
            if (!set.contains(item.getFlag())) {
                remove = remove && Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(REDIS_MARK + item.getFlag()));
                set.add(item.getFlag());
            }
        }
        return remove;
    }

    private BigDecimal getAvg(List<AverageVo> allList) {
        if (allList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal num = BigDecimal.ZERO;
        int times = 0;
        for (AverageVo item : allList) {
            List<OrderDetail> orderDetails = item.getOrderDetails();
            for (OrderDetail detail : orderDetails) {
                BigDecimal salesAmount = detail.getSalesAmount();
                num = num.add(salesAmount);
                times += detail.getSalesQuantity();
            }
        }
        num = num.divide(new BigDecimal(times), RoundingMode.CEILING);
        return num;
    }

    @Override
    public String queryByTime(Integer time, Integer timeFlag, String place, Integer flag) {
        LocalDateTime startTime = null;
        LocalDateTime now = LocalDateTime.now();
        //根据年月日分类设定起始时间
        if (timeFlag == 0) {
            startTime = now.minusYears(time);
        } else if (timeFlag == 1) {
            startTime = now.minusMonths(time);
        } else if (timeFlag == 2) {
            startTime = now.minusWeeks(time);
        } else if (timeFlag == 3) {
            startTime = now.minusDays(time);
        }
        LambdaQueryWrapper<AveragePrice> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.between(AveragePrice::getAverageDate, startTime, now)
                .eq(flag != null, AveragePrice::getFlag, flag)
                .like(place != null, AveragePrice::getPlace, place);
        List<AveragePrice> list = this.list(queryWrapper);
        //将所有符合规定的数据加起来求平均
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        int size = 0;
        for (AveragePrice averagePrice : list) {
            result = result.add(averagePrice.getPrice());
            size++;
        }
        result = result.divide(new BigDecimal(size));
        return result + list.get(0).getUnit();
    }
}
