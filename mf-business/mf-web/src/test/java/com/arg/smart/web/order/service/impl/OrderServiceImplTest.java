package com.arg.smart.web.order.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.order.model.OrderCategory;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderService;
import com.arg.smart.web.order.vo.DurationQueryParam;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author GlowingTree
 * @Date 2023-6/8/2023-9:56 PM
 * @PackageName com.arg.smart.web.order.service.impl
 * @ClassName OrderServiceImplTest
 * @Description 订单主表服务类测试
 * @Version 1.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class OrderServiceImplTest extends TestCase {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderMapper orderMapper;

    private DurationQueryParam durationQueryParam;

    @Before
    public void init() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.durationQueryParam = DurationQueryParam.builder()
                    .startTime(sdf.parse("2023-05-29 00:00:00"))
                    .endTime(new Date())
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testList() {
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setCompanyName("呀哈哈");
        reqOrder.setCategory(OrderCategory.PRODUCTION_ORDER);
        PageResult<Order> list = orderService.list(reqOrder);
        assertNotNull(list);
        log.info("request order list: {}", list);
    }

    @Test
    public void testGetOrderCountByTime() {
        Long orderCount = orderService.getOrderCountByTime(ModuleFlag.CHICKEN
                , OrderCategory.PRODUCTION_ORDER
                , this.durationQueryParam);
        assertNotNull(orderCount);
        log.info("order count: {}", orderCount);
    }

    @Test
    public void testGetOrderCountByTransportMode() {
        Map<String, Object> map = orderService.getOrderCountByTransportMode(ModuleFlag.CHICKEN
                , OrderCategory.PRODUCTION_ORDER
                , this.durationQueryParam);
        assertNotNull(map);
        log.info("circulation data amount: {}", map);
    }

    @Test
    public void testGetOrderAmountByArea() {
        Map<String, Object> amount = orderService.getOrderAmountByArea(ModuleFlag.CHICKEN
                , OrderCategory.PRODUCTION_ORDER
                , this.durationQueryParam);
        assertNotNull(amount);
        log.info("location amount: {}", amount);
    }

    @Test
    public void testGetOrderTransportationAmount() {
        Map<String, Object> amount = orderService.getOrderTransportationAmount(ModuleFlag.CHICKEN
                , OrderCategory.PRODUCTION_ORDER
                , this.durationQueryParam);
        assertNotNull(amount);
        log.info("transportation company transport amount: {}", amount);
    }

    @Test
    public void testGetProductAvgPriceByArea() {
        Map<String, Object> price = orderService
                .getProductAvgPriceByArea(this.durationQueryParam, OrderCategory.SALE_ORDER, 17L);
        assertNotNull(price);
        log.info("average price: {}", price);
    }

}