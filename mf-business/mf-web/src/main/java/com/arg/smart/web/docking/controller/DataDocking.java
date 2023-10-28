package com.arg.smart.web.docking.controller;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.web.docking.entity.*;
import com.arg.smart.web.docking.req.ReqDocking;
import com.arg.smart.web.docking.service.*;
import com.arg.smart.web.docking.utils.DateUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zsj
 * @description: 数据对接
 * @date: 2023-09-11
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "数据对接")
@RestController
@RequestMapping("/dataDocking/public")
public class DataDocking {


//    private static String accessTokenId = "i6CDvsKy";

    private static String accessTokenId = "BkSOzEKX";
//    private static String accessTokenSecret = "c28bf73980b04904b9bb7cca30f0489b";

    private static String accessTokenSecret = "96999fd9196c4950b061c699b3dab315";

    //测试环境
    private static String url2 = "https://openapi.cnhnkj.cn";

    //生产环境
    private static String url = "https://openapi.cnhnb.com";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ProductPriceDataService productPriceDataService;

    @Resource
    private ProduceDataService produceDataService;

    @Resource
    private MarketPriceDataService marketPriceDataService;

    @Resource
    private InquiryDataService inquiryDataService;

    @Resource
    private InquiryDataGuangdongService inquiryDataGuangdongService;

    @Resource
    private SellDataService sellDataService;

    @Resource
    private SellDataGuangdongService sellDataGuangdongService;

    @Resource
    private SupplyDataService supplyDataService;

    @Resource
    private SupplyDataGuangDongService supplyDataGuangDongService;

    @Resource
    private MarketInfoService marketInfoService;

    private Map<String,Integer> productMap;



    @ApiOperation(value = "惠农网数据对接", notes = "惠农网数据对接")
    @GetMapping
    public void docking(ReqDocking reqDocking
    ) {
        productMap = new HashMap<>();
        productMap.put("鸡",1);
        productMap.put("柑桔",2);
        productMap.put("橙子",2);
        productMap.put("兰花",3);
        productMap.put("对虾",4);
        productMap.put("菜心",5);
        Integer type = reqDocking.getType();
        String cateName = reqDocking.getCateName();
        String dateMonth = reqDocking.getDateMonth();
        String endDate = reqDocking.getEndDate();
        String endYear = reqDocking.getEndYear();
        String startDate = reqDocking.getStartDate();
        Integer pageNum = reqDocking.getPageNum();
        Integer pageSize = reqDocking.getPageSize();
        String startYear = reqDocking.getStartYear();
        String yearDate = reqDocking.getYearDate();
        String startMonth = reqDocking.getStartMonth();
        String endMonth = reqDocking.getEndMonth();
        String collectDate = reqDocking.getCollectDate();
        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accessTokenId", accessTokenId);
        headers.set("accessTokenSecret", accessTokenSecret);
        if(type == 1){
            //行情数据
            price(headers, cateName,collectDate);
        }
        if(type == 2){
            //批发市场数据
            marketPrice(headers, startDate, endDate, cateName, pageNum, pageSize);
        }
        if(type == 3){
            //产量
            production(headers, startYear, endYear, cateName);
        }
        if(type == 4){
            //销量
            sale(headers, startMonth, endMonth, cateName);
        }
        if(type ==5){
            //供应
            supply(headers, startMonth, endMonth, cateName);
        }
        if(type == 6){
            //采购
            procurement(headers, startMonth, endMonth, cateName);
        }
        if(type == 7){
            //洞察
            insight(headers, dateMonth, yearDate, cateName);
        }
    }
    //行情
    private void price(HttpHeaders headers, String cateName, String date) {
        Integer flag = productMap.get(cateName);
        // 构建请求体JSON对象
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("cateName", cateName);
        requestBodyMap.put("sourceFrom", 11);
        if(date != null){
            requestBodyMap.put("collectDate",date);
        }
        // 将请求体JSON对象转为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting request body to JSON", e);
        }

        // 封装请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发送POST请求并获取响应
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url + "/market-platform/openapi/billing/market/areainfo", requestEntity, JSONObject.class);

        // 获取响应数据
        JSONObject responseBody = responseEntity.getBody();
        log.info("{}", responseEntity);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) responseBody.get("data");
        List<ProductPriceData> collect = mapList.stream().map(item -> {
            ProductPriceData productPriceData = new ProductPriceData();
            String collectDate = (String) item.get("collectDate");
            try {
                productPriceData.setCollectdate(DateUtils.parseStringToDate(collectDate, "yyyy-MM-dd"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            //如果breedName为空则以cateName为产品名
            String breedName = (String) item.get("breedName");
            if (StringUtils.isBlank(breedName)) {
                breedName = (String) item.get("cateName");
            }
            productPriceData.setProduct(breedName);
            Double avgPrice = (Double) item.get("avgPrice");
            Double maxPrice = (Double) item.get("maxPrice");
            Double minPrice = (Double) item.get("minPrice");
            productPriceData.setPrice(new BigDecimal(avgPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
            productPriceData.setMaxPrice(new BigDecimal(maxPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
            productPriceData.setMinPrice(new BigDecimal(minPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
            productPriceData.setUnit((String) item.get("unit"));
            String provinceName = (String) item.get("provinceName");
            String cityName = (String) item.get("cityName");
            String areaName = (String) item.get("areaName");
            productPriceData.setProvinceName(provinceName);
            productPriceData.setCityName(cityName);
            productPriceData.setFlag(flag);
            productPriceData.setAreaName(areaName);
            productPriceData.setRegion(provinceName + cityName + areaName);
            return productPriceData;
        }).collect(Collectors.toList());
        productPriceDataService.saveBatch(collect);
    }

    //批发市场价格
    private void marketPrice(HttpHeaders headers, String startDate, String endDate, String cateName, Integer pageNum, Integer pageSize) {
        Integer flag = productMap.get(cateName);
        // 封装请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String urlValue = url + "/market-platform/openapi/billing/gd/pifa/price";
        urlValue += "?cateName=" + cateName;
        if (startDate != null) {
            urlValue += "&startDate=" + startDate;
        }
        if (endDate != null) {
            urlValue += "&endDate=" + endDate;
        }
        if (pageNum != null) {
            urlValue += "&pageNum=" + pageNum;
        }
        if (pageSize != null) {
            urlValue += "&pageSize=" + pageSize;
        }
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(urlValue, requestEntity, JSONObject.class);
        JSONObject responseBody = responseEntity.getBody();
        log.info("批发市场数据：{}",responseBody);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) responseBody.get("data");
        List<MarketPriceData> collect = mapList.stream().map(item -> {
            MarketPriceData marketPriceData = new MarketPriceData();
            String date = (String) item.get("date");
            try {
                marketPriceData.setDate(DateUtils.parseStringToDate(date, "yyyy-MM-dd"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Double avgPrice = (Double) item.get("avgPrice");
            String marketName = (String) item.get("marketName");
            String unit = (String) item.get("unit");
            marketPriceData.setAvgPrice(new BigDecimal(avgPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
            marketPriceData.setMarketName(marketName);
            marketPriceData.setFlag(flag);
            marketPriceData.setUnit(unit);
            return marketPriceData;
        }).collect(Collectors.toList());
        marketPriceDataService.saveBatch(collect);
    }

    //产量
    public void production(HttpHeaders headers, String startYear, String endYear, String cateName) {
        Integer flag = productMap.get(cateName);
        // 封装请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String urlValue = url + "/market-platform/openapi/billing/gd/produce/list";
        urlValue += "?cateName=" + cateName;
        if (startYear != null) {
            urlValue += "&startYear=" + startYear;
        }
        if (endYear != null) {
            urlValue += "&endYear=" + endYear;
        }
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(urlValue, requestEntity, JSONObject.class);
        JSONObject responseBody = responseEntity.getBody();
        log.info("产量数据：{}",responseBody);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) responseBody.get("data");
        List<ProduceData> collect = mapList.stream().map(item -> {
            ProduceData produceData = new ProduceData();
            String dateYear = (String) item.get("dateYear");
            String statArea = (String) item.get("statArea");
            String statUnit = (String) item.get("statUnit");
            Double statQty = (Double) item.get("statQty");
            produceData.setDateYear(Integer.valueOf(dateYear));
            produceData.setFlag(flag);
            produceData.setStarQty(statQty);
            produceData.setStatUnit(statUnit);
            produceData.setStartArea(statArea);
            return produceData;
        }).collect(Collectors.toList());
        produceDataService.saveBatch(collect);
    }

    //销售
    public void sale(HttpHeaders headers, String startMonth, String endMonth, String cateName) {
        Integer flag = productMap.get(cateName);
        // 封装请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String urlValue = url + "/market-platform/openapi/billing/gd/sell/list";
        urlValue += "?cateName=" + cateName;
        if (startMonth != null) {
            urlValue += "&startDate=" + startMonth;
        }
        if (endMonth != null) {
            urlValue += "&endDate=" + endMonth;
        }
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(urlValue, requestEntity, JSONObject.class);
        JSONObject responseBody = responseEntity.getBody();
        log.info("销售数据：{}",responseBody);
        Map<String, Object> map = (Map<String, Object>) responseBody.get("data");
        List<Map<String, Object> > salesVolumeList = (List<Map<String, Object>>) map.get("salesVolumeList");
        List<Map<String, Object> > salesVolumeCustomizeList = (List<Map<String, Object>>) map.get("salesVolumeCustomizeList");
        List<SellData> collect = salesVolumeList.stream().map(item -> {
            SellData sellData = new SellData();
            sellData.setFlag(flag);
            String data = (String) item.get("date");
            String provinceName = (String) item.get("provinceName");
            Double cntRatio = (Double) item.get("cntRatio");
            sellData.setDate(data);
            sellData.setCntRatio(cntRatio);
            sellData.setProvinceName(provinceName);
            return sellData;
        }).collect(Collectors.toList());
        sellDataService.saveBatch(collect);

        List<SellDataGuangdong> collect2 = salesVolumeCustomizeList.stream().map(item -> {
            SellDataGuangdong sellDataGuangdong = new SellDataGuangdong();
            sellDataGuangdong.setFlag(flag);
            String data = (String) item.get("date");
            String provinceName = (String) item.get("provinceName");
            Double cntRatio = (Double) item.get("cntRatio");
            String saleProvinceName = (String) item.get("saleProvinceName");
            String buyProvinceName = (String) item.get("buyProvinceName");
            sellDataGuangdong.setDate(data);
            sellDataGuangdong.setCntRatio(cntRatio);
            sellDataGuangdong.setSaleProvinceName(provinceName);
            sellDataGuangdong.setSaleProvinceName(buyProvinceName);
            sellDataGuangdong.setBuyProvinceName(saleProvinceName);
            return sellDataGuangdong;
        }).collect(Collectors.toList());
        sellDataGuangdongService.saveBatch(collect2);
    }

    //供应
    public void supply(HttpHeaders headers, String startMonth, String endMonth, String cateName) {
        Integer flag = productMap.get(cateName);
        // 封装请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String urlValue = url + "/market-platform/openapi/billing/gd/supply/list";
        urlValue += "?cateName=" + cateName;
        if (startMonth != null) {
            urlValue += "&startDate=" + startMonth;
        }
        if (endMonth != null) {
            urlValue += "&endDate=" + endMonth;
        }
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(urlValue, requestEntity, JSONObject.class);
        JSONObject responseBody = responseEntity.getBody();
        log.info("供应数据：{}",responseBody);
        Map<String, Object> map = (Map<String, Object>) responseBody.get("data");
        List<Map<String, Object> > supplyVolumeList = (List<Map<String, Object>>) map.get("salesVolumeList");
        List<Map<String, Object> > supplyVolumeCustomizeList = (List<Map<String, Object>>) map.get("salesVolumeCustomizeList");
        List<SupplyData> collect = supplyVolumeList.stream().map(item -> {
            SupplyData supplyData = new SupplyData();
            supplyData.setFlag(flag);
            String data = (String) item.get("date");
            String provinceName = (String) item.get("provinceName");
            Double cntRatio = (Double) item.get("cntRatio");
            supplyData.setDate(data);
            supplyData.setCntRatio(cntRatio);
            supplyData.setProvinceName(provinceName);
            supplyData.setProduct(cateName);
            return supplyData;
        }).collect(Collectors.toList());
        supplyDataService.saveBatch(collect);

        List<SupplyDataGuangDong> collect2 = supplyVolumeCustomizeList.stream().map(item -> {
            SupplyDataGuangDong supplyDataGuangDong = new SupplyDataGuangDong();
            supplyDataGuangDong.setFlag(flag);
            String data = (String) item.get("date");
            String provinceName = (String) item.get("provinceName");
            Double cntRatio = (Double) item.get("cntRatio");
            supplyDataGuangDong.setDate(data);
            supplyDataGuangDong.setCntRatio(cntRatio);
            supplyDataGuangDong.setProvinceName(provinceName);
            return supplyDataGuangDong;
        }).collect(Collectors.toList());
        supplyDataGuangDongService.saveBatch(collect2);
    }

    //采购
    public void procurement(HttpHeaders headers, String startMonth, String endMonth, String cateName) {
        Integer flag = productMap.get(cateName);
        // 封装请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String urlValue = url + "/market-platform/openapi/billing/gd/inquiry/list";
        urlValue += "?cateName=" + cateName;
        if (startMonth != null) {
            urlValue += "&startDate=" + startMonth;
        }
        if (endMonth != null) {
            urlValue += "&endDate=" + endMonth;
        }
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(urlValue, requestEntity, JSONObject.class);
        JSONObject responseBody = responseEntity.getBody();
        log.info("采购数据：{}",responseBody);
        Map<String, Object> map = (Map<String, Object>) responseBody.get("data");
        List<Map<String, Object> > inquiryVolumeList = (List<Map<String, Object>>) map.get("salesVolumeList");
        List<Map<String, Object> > inquiryVolumeCustomizeList = (List<Map<String, Object>>) map.get("salesVolumeCustomizeList");
        List<InquiryData> collect = inquiryVolumeList.stream().map(item -> {
            InquiryData inquiryData = new InquiryData();
            inquiryData.setFlag(flag);
            String data = (String) item.get("date");
            String provinceName = (String) item.get("provinceName");
            Double cntRatio = (Double) item.get("cntRatio");
            inquiryData.setProduct(cateName);
            inquiryData.setDate(data);
            inquiryData.setCntRatio(cntRatio);
            inquiryData.setProvinceName(provinceName);
            return inquiryData;
        }).collect(Collectors.toList());
        inquiryDataService.saveBatch(collect);

        List<InquiryDataGuangdong> collect2 = inquiryVolumeCustomizeList.stream().map(item -> {
            InquiryDataGuangdong inquiryDataGuangdong = new InquiryDataGuangdong();
            inquiryDataGuangdong.setFlag(flag);
            inquiryDataGuangdong.setProduct(cateName);
            String data = (String) item.get("date");
            String provinceName = (String) item.get("provinceName");
            Double cntRatio = (Double) item.get("cntRatio");
            inquiryDataGuangdong.setDate(data);
            inquiryDataGuangdong.setCntRatio(cntRatio);
            inquiryDataGuangdong.setProvinceName(provinceName);
            return inquiryDataGuangdong;
        }).collect(Collectors.toList());
        inquiryDataGuangdongService.saveBatch(collect2);
    }

    //洞察
    public void insight(HttpHeaders headers, String dateMonth, String yearDate, String cateName) {
        // 封装请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String urlValue = url + "/market-platform/openapi/billing/gd/insight";
        urlValue += "?cateName=" + cateName;
        if (dateMonth != null) {
            urlValue += "&dateMonth=" + dateMonth;
        }
        if (yearDate != null) {
            urlValue += "&yearDate=" + yearDate;
        }
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(urlValue, requestEntity, JSONObject.class);
        JSONObject responseBody = responseEntity.getBody();
        log.info("洞察数据：{}",responseBody);
        Map<String,Object> map = (Map<String, Object>) responseBody.get("data");
        Map<String,Object> marketInfoMap = (Map<String, Object>) map.get("marketInfo");
        MarketInfo marketInfo = new MarketInfo(marketInfoMap);
        marketInfoService.save(marketInfo);
    }
}
