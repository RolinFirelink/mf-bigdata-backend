package com.arg.smart.web.docking.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description: 惠农网产品信息表
 * @author cgli
 * @date: 2023-09-26
 * @version: V1.0.0
 */
@Data
@TableName("cn_market_info")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "cn_market_info对象", description = "惠农网产品信息表")
public class MarketInfo extends BaseEntity<Long> {

    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "")
	private String dateMonth;
    @ApiModelProperty(value = "")
	private Integer cate1Id;
    @ApiModelProperty(value = "")
	private String cate1Name;
    @ApiModelProperty(value = "")
	private Integer cate2Id;
    @ApiModelProperty(value = "")
	private String cate2Name;
    @ApiModelProperty(value = "")
	private Integer cate3Id;
    @ApiModelProperty(value = "")
	private String cate3Name;
    @ApiModelProperty(value = "")
	private Integer searchDegree;
    @ApiModelProperty(value = "")
	private Integer purchaseDegree;
    @ApiModelProperty(value = "")
	private Integer saleDegree;
    @ApiModelProperty(value = "")
	private Integer tradeDegree;
    @ApiModelProperty(value = "")
	private Integer totalDegree;
    @ApiModelProperty(value = "")
	private Integer searchDegreeRatio;
    @ApiModelProperty(value = "")
	private Integer purchaseDegreeRatio;
    @ApiModelProperty(value = "")
	private Integer saleDegreeRatio;
    @ApiModelProperty(value = "")
	private Integer tradeDegreeRatio;
    @ApiModelProperty(value = "")
	private Integer totalDegreeRatio;
    @ApiModelProperty(value = "")
	private Integer searchDegreeTongRatio;
    @ApiModelProperty(value = "")
	private Integer purchaseDegreeTongRatio;
    @ApiModelProperty(value = "")
	private Integer saleDegreeTongRatio;
    @ApiModelProperty(value = "")
	private Integer tradeDegreeTongRatio;
    @ApiModelProperty(value = "")
    private Integer totalDegreeTongRatio;
    @ApiModelProperty(value = "")
	private Integer deleteFlag;

    public MarketInfo(Map<String, Object> marketInfoMap) {
        this.setDateMonth((String) marketInfoMap.get("dateMonth"));
        this.setCate1Id((Integer) marketInfoMap.get("cate1Id"));
        this.setCate1Name((String) marketInfoMap.get("cate1Name"));
        this.setCate2Id((Integer) marketInfoMap.get("cate2Id"));
        this.setCate2Name((String) marketInfoMap.get("cate2Name"));
        this.setCate3Id((Integer) marketInfoMap.get("cate3Id"));
        this.setCate3Name((String) marketInfoMap.get("cate3Name"));
        this.setSearchDegree((Integer) marketInfoMap.get("searchDegree"));
        this.setPurchaseDegree((Integer) marketInfoMap.get("purchaseDegree"));
        this.setSaleDegree((Integer) marketInfoMap.get("saleDegree"));
        this.setTradeDegree((Integer) marketInfoMap.get("tradeDegree"));
        this.setTotalDegree((Integer) marketInfoMap.get("totalDegree"));
        this.setSearchDegreeRatio((Integer) marketInfoMap.get("searchDegreeRatio"));
        this.setPurchaseDegreeRatio((Integer) marketInfoMap.get("purchaseDegreeRatio"));
        this.setSaleDegreeRatio((Integer) marketInfoMap.get("saleDegreeRatio"));
        this.setTradeDegreeRatio((Integer) marketInfoMap.get("tradeDegreeRatio"));
        this.setTotalDegreeRatio((Integer) marketInfoMap.get("totalDegreeRatio"));
        this.setSearchDegreeTongRatio((Integer) marketInfoMap.get("searchDegreeTongRatio"));
        this.setPurchaseDegreeTongRatio((Integer) marketInfoMap.get("purchaseDegreeTongRatio"));
        this.setSaleDegreeTongRatio((Integer) marketInfoMap.get("saleDegreeTongRatio"));
        this.setTradeDegreeTongRatio((Integer) marketInfoMap.get("tradeDegreeTongRatio"));
        this.setTotalDegreeTongRatio((Integer) marketInfoMap.get("totalDegreeTongRatio"));
    }
}
