package com.arg.smart.web.cargo.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductCirculationDataExcel {

    @ExcelProperty(value = "唯一ID",index = 0)
    private String id;
    @ExcelProperty(value = "生产日期",index = 1)
	private String dateManufacture;
    @ExcelProperty(value = "生产批次",index = 2)
	private String productionBatch;
    @ExcelProperty(value = "生产厂家",index = 3)
	private String manufacturer;
    @ExcelProperty(value = "发货时间",index = 4)
	private String deliveryTime;
    @ExcelProperty(value = "发货单位",index = 5)
	private String forwardingUnit;
    @ExcelProperty(value = "发货人",index = 6)
	private String shipper;
    @ExcelProperty(value = "发货人电话",index = 7)
	private String shipperPhoneNumber;
    @ExcelProperty(value = "发货地点",index = 8)
	private String shippingLocation;
    @ExcelProperty(value = "发货区域编码",index = 9)
	private String shippingAreaCode;
    @ExcelProperty(value = "收货时间",index = 10)
	private String receivingTime;
    @ExcelProperty(value = "收货单位",index = 11)
	private String consignee;
    @ExcelProperty(value = "收货人",index = 12)
	private String receiver;
    @ExcelProperty(value = "收货人电话",index = 13)
	private String receiverPhone;
    @ExcelProperty(value = "收货地点",index = 14)
	private String receivingLocation;
    @ExcelProperty(value = "收货区域编码",index = 15)
	private String receivingAreaCode;
    @ExcelProperty(value = "货运物流中转信息",index = 16)
	private String freightLogisticsTransferInformation;
    @ExcelProperty(value = "运输方式",index = 17)
	private String modeTransport;
    @ExcelProperty(value = "运输价格",index = 18)
	private String transportationPrice;
    @ExcelProperty(value = "运输时间",index = 19)
	private String transportationTime;
    @ExcelProperty(value = "时间单位",index = 20)
	private String timeUnit;
    @ExcelProperty(value = "运输数量",index = 21)
	private String transportationQuantity;
    @ExcelProperty(value = "货运批次号",index = 22)
	private String batchNumber;
    @ExcelProperty(value = "货运单位",index = 23)
	private String shippingUnit;
    @ExcelProperty(value = "货运单号",index = 24)
	private String oddNumbers;
    @ExcelProperty(value = "企业（承运商）ID",index = 25)
	private String companyId;
    @ExcelProperty(value = "自定义拓展字段JSON结构")
	private String extendField;
    @ExcelProperty(value = "删除标识")
	private String deleteFlag;
    @ExcelProperty(value = "订单ID",index = 26)
	private String orderId;
    @ExcelProperty(value = "业务类型（生产、销售等）",index = 27)
	private String businessType;
}
