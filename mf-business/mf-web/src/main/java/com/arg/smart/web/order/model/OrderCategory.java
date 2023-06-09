package com.arg.smart.web.order.model;

/**
 * @Author GlowingTree
 * @Date 2023-6/9/2023-10:00 AM
 * @PackageName com.arg.smart.web.order.model
 * @ClassName OrderCategory
 * @Description 订单类型
 * @Version 1.0
 */
public class OrderCategory {
    /**
     * 生产订单
     */
    public static final int PRODUCTION_ORDER = 1;
    /**
     * 出库订单
     */
    public static final int OUTBOUND_ORDER = 2;
    /**
     * 采购订单
     */
    public static final int PURCHASE_ORDER = 3;
    /**
     * 销售订单
     */
    public static final int SALE_ORDER = 4;
    /**
     * 企业退货订单
     */
    public static final int ENTERPRISE_RETURN_ORDER = 5;
    /**
     * 消费者退货订单
     */
    public static final int CONSUMER_RETURN_ORDER = 6;
}
