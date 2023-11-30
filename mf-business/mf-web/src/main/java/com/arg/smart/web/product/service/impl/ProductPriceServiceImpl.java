package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.ProductPriceTrendData;
import com.arg.smart.web.product.entity.RougePrice;
import com.arg.smart.web.product.entity.vo.AreaAvgPriceAndSales;
import com.arg.smart.web.product.entity.vo.PriceTemp;
import com.arg.smart.web.product.entity.vo.ProductPriceTrend;
import com.arg.smart.web.product.entity.vo.ProductPriceVO;
import com.arg.smart.web.product.mapper.ProductPriceMapper;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.req.ReqRougePrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.arg.smart.web.product.service.RougePriceService;
import com.arg.smart.web.product.units.units;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 产品价格表
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Service
@Slf4j
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, ProductPrice> implements ProductPriceService {
    @Resource
    private ProductPriceMapper productPriceMapper;

    @Resource
    private ProductPriceService productPriceService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RougePriceService rougePriceService;

    private static String curUrl = "";

    private final String API_URL = "http://localhost:5000/predict_prices"; // 修改为正确的接口URL


    @Override
    public List<ProductPrice> queryList(ReqProductPrice reqProductPrice) {
        LambdaQueryWrapper<ProductPrice> queryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProductPrice.getFlag();
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String product = reqProductPrice.getProduct();
        String region = reqProductPrice.getRegion();
        Integer count = reqProductPrice.getCount();
        //按时间排序
        queryWrapper.orderByDesc(ProductPrice::getTime);
        if (flag != null) {
            queryWrapper.eq(ProductPrice::getFlag, flag);
        }
        if (product != null) {
            queryWrapper.like(ProductPrice::getProduct, product);
        }
        if (region != null) {
            queryWrapper.like(ProductPrice::getRegion, region);
        }
        if (startTime != null) {
            queryWrapper.ge(ProductPrice::getTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.le(ProductPrice::getTime, endTime);
        }
        if (count == null || count <= 0) {
            count = 20;
        }
        //排除掉产品名为兰花的
        queryWrapper.ne(ProductPrice::getProduct, "兰花");
        queryWrapper.last("limit " + count);
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cnhnbSave() {
        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\pachong\\new\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String Cx = "https://www.cnhnb.com/hangqing/caitai/";
        String Gj = "https://www.cnhnb.com/hangqing/cdlist-0-0-0-0-0-1/?k=%E6%9F%91%E6%A9%98";
        String Rg = "https://www.cnhnb.com/hangqing/?k=%E9%B8%BD%E5%AD%90";
        String Rj = "https://www.cnhnb.com/hangqing/cdlist-2001180-13743-0-0-0-1/";
        String Wj = "https://www.cnhnb.com/hangqing/cdlist-2001180-128035/";
        String Shj = "https://www.cnhnb.com/hangqing/cdlist-2001180-6087/";
        String Dx = "https://www.cnhnb.com/hangqing/?k=%E5%AF%B9%E8%99%BE";
        String Lh = "https://www.cnhnb.com/hangqing/cdlist-2001008-0-0-0-0-1/";

        String[] urls = {Cx, Gj, Rg, Rj, Wj, Shj, Dx, Lh};
        int[] arr = {5, 2, 7, 1, 1, 1, 4, 3};
        boolean judge = true;
        int i = 0;
        for (String url : urls) {
            chromeDriver.get(url);
            if (judge) {
                // 执行登录操作的代码,只进行一次
                try {
                    judge = false;
                    WebElement loginButton = chromeDriver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div/div[1]/div[1]/div/div[1]/div[2]/div[1]"));
                    loginButton.click();
                    Thread.sleep(3000);
                    WebElement element = chromeDriver.findElement(By.id("eye-iframe-sso-login"));
                    chromeDriver.switchTo().frame(element);
                    WebElement tabs = chromeDriver.findElement(By.className("tabs"));
                    WebElement pwdLogin = tabs.findElements(By.className("tab-item")).get(2);
                    pwdLogin.click();
                    WebElement loginModern = chromeDriver.findElement(By.className("login-modern"));
                    List<WebElement> elements = loginModern.findElements(By.cssSelector("form div.item.border-m-f"));
                    WebElement account = elements.get(2).findElement(By.cssSelector("input"));
                    WebElement password = elements.get(3).findElement(By.cssSelector("input"));
                    account.sendKeys("18319714791");
                    password.sendKeys("xue13680178322");
                    WebElement agree = loginModern.findElement(By.xpath("//*[@id=\"__layout\"]/div/div/div/div[1]/div[2]/div[2]/i"));
                    agree.click();
                    WebElement submit = loginModern.findElement(By.xpath("//*[@id=\"__layout\"]/div/div/div/div[1]/div[2]/form[2]/button"));
                    submit.submit();
                    chromeDriver.switchTo().defaultContent();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            do {
                try {
                    // TODO 爬虫期间会出现滑块验证,必须在5s等待时间里手动通过验证
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                curUrl = chromeDriver.getCurrentUrl();
                WebElement webElement = chromeDriver.findElement(By.cssSelector("div.quotation-content-list"));
                List<WebElement> elements = webElement.findElements(By.cssSelector("div ul li.market-list-item"));
                for (WebElement element : elements) {
                    String time = element.findElement(By.cssSelector("a span.time")).getText();
                    String product = element.findElement(By.cssSelector("a span.product")).getText();
                    String place = element.findElement(By.cssSelector("a span.place")).getText();
                    String price = element.findElement(By.cssSelector("a span.price")).getText();
                    String lifting;
                    boolean isFinsh = true;
                    try {
                        lifting = element.findElement(By.cssSelector("a span.lifting.risecolor")).getText();
                        isFinsh = false;
                    } catch (Exception e) {
                        lifting = "-";
                    }
                    if (isFinsh) {
                        try {
                            lifting = element.findElement(By.cssSelector("a span.lifting.fallcolor")).getText();
                        } catch (Exception e) {
                            lifting = "-";
                        }
                    }
                    ProductPrice pp = new ProductPrice();
                    pp.setTime(units.stringToDate(time));
                    pp.setProduct(product);
                    pp.setRegion(place);
                    pp.setPrice(units.stringToBdm(price));
                    pp.setUnit(units.stringToPriceAndUnit(price));
                    if (!lifting.equals("-")) {
                        pp.setLifting(BigDecimal.valueOf(Double.parseDouble(lifting.substring(0, lifting.length() - 1))));
                    }
                    pp.setFlag(arr[i]);
//                    System.out.println(pp);
                    save(pp);
                }
            } while (cnhnbNext(chromeDriver));
            i++;
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chromeDriver.quit();
        return true;
    }

    private static boolean cnhnbNext(ChromeDriver chromeDriver) {
        WebElement page = chromeDriver.findElement(By.cssSelector("button.btn-next"));
        WebElement element = chromeDriver.findElement(By.cssSelector("div.eye-pager"));
        List<WebElement> elements = element.findElements(By.cssSelector("a.number"));
        if (elements.size() == 1) {
            return false;
        }
        page.click();
        String currentUrl = chromeDriver.getCurrentUrl();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return !currentUrl.equals(curUrl);
    }

    @Override
    public List<PriceTemp> getPriceTemp(ReqProductPrice reqProductPrice) {
//        QueryWrapper<ProductPrice> queryWrapper = new QueryWrapper<>();
//        String region = reqProductPrice.getRegion();
//        queryWrapper.like(region != null, "region", region);
//        queryWrapper.groupBy("flag", "unit");
//        queryWrapper.select("flag", "max(time) as time,avg(price) as price");
//        List<ProductPrice> list = this.list(queryWrapper);
//        return list.stream().map(item -> {
//            BigDecimal price = item.getPrice();
//            BigDecimal lifting = item.getLifting();
//            BigDecimal lastPrice;
//            if (lifting == null) {
//                return new PriceTemp(item.getFlag(), 100, BigDecimal.ZERO, item.getUnit());
//            }
//            //上次的价格
//            lastPrice = price.divide(item.getLifting().divide(new BigDecimal(100)).add(new BigDecimal(1)), 2, BigDecimal.ROUND_DOWN);
//            //改变的价格
//            BigDecimal changePrice = price.subtract(lastPrice);
//            //指数
//            Integer temp = price.divide(lastPrice, 3, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).intValue();
//            return new PriceTemp(item.getFlag(), temp, changePrice, item.getUnit());
//        }).collect(Collectors.toList());
        //获取产品价格表每种品种的最细两天价格（正确情况大都是昨天和前天）
        String region = reqProductPrice.getRegion();
        if (region == null) {
            region = "";
        }
        List<Integer> flags = new ArrayList<>();
        flags.add(1);
        flags.add(2);
        flags.add(3);
        flags.add(4);
        flags.add(5);
        flags.add(8);
        List<ProductPrice> productPrices = this.getPublicTemp(flags, region);
        List<ProductPrice> routePrices = rougePriceService.getPriceTempData(region);
        productPrices.addAll(routePrices);
        log.info("{}", productPrices);
        List<PriceTemp> priceTemps = new ArrayList<>();
        for (int i = 1; i < productPrices.size(); i += 2) {
            //最新价格
            ProductPrice productPrice = productPrices.get(i - 1);
            //次新价格
            ProductPrice productPrice1 = productPrices.get(i);
            BigDecimal price = productPrice.getPrice();
            BigDecimal lastPrice = productPrice1.getPrice();
            BigDecimal changePrice = price.subtract(lastPrice);
            BigDecimal temp = BigDecimal.valueOf(100).add(changePrice.divide(lastPrice, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
            PriceTemp priceTemp = new PriceTemp(
                    productPrice.getFlag(),
                    temp.setScale(0, RoundingMode.HALF_UP).intValue(),
                    changePrice,
                    null
            );
            priceTemps.add(priceTemp);
        }
        return priceTemps;
    }

    @Override
    public List<String> regionList() {
        return baseMapper.regionList();
    }

    public List<AreaAvgPriceAndSales> selectAvgPriceAndSales(Integer flag, String product) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String yesterdayStr = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        QueryWrapper<ProductPrice> queryWrapper = Wrappers.query();
        queryWrapper.eq("time", yesterdayStr)
                .select("LEFT(region, 2) AS region_prefix", "AVG(price) as avg_price", "COUNT(*) as record_count")
                .eq("flag", flag);
        if (product != null && !product.equals("")) {
            queryWrapper.eq("product", product);
        }
        queryWrapper.groupBy("region_prefix")
                .orderByDesc("record_count")
                .last("LIMIT 5");
        List<Map<String, Object>> avgPriceList = baseMapper.selectMaps(queryWrapper);
        ArrayList<AreaAvgPriceAndSales> list = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : avgPriceList) {
            AreaAvgPriceAndSales areaAvgPriceAndSales = new AreaAvgPriceAndSales();
            String regionPrefix = (String) stringObjectMap.get("region_prefix");
            BigDecimal avgPrice = (BigDecimal) stringObjectMap.get("avg_price");
            areaAvgPriceAndSales.setRegion(regionPrefix);
            areaAvgPriceAndSales.setAvgPrice(avgPrice);
            list.add(areaAvgPriceAndSales);
        }
        return list;
    }

    @Override
    public List<ProductPriceVO> publicTrend(ReqProductPrice reqProductPrice) {
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String region = reqProductPrice.getRegion();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusDays(30);
        }
        if (region == null) {
            region = "广东";
        }
        Integer flag = reqProductPrice.getFlag();
        return baseMapper.publicTrend(flag, startTime, endTime, region);
    }

    @Override
    public List<ProductPriceTrendData> getProductPriceTrendData(ReqProductPrice reqProductPrice) {
        List<ProductPriceTrendData> res = new ArrayList<>();
        QueryWrapper<ProductPrice> queryWrapper = new QueryWrapper<>();
        String product = reqProductPrice.getProduct();
        String region = reqProductPrice.getRegion();
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        Integer flag = reqProductPrice.getFlag();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusDays(30);
        }
        queryWrapper.ne("flag", 7);
        queryWrapper.like(StringUtils.isNotEmpty(region), "region", region);
        queryWrapper.ge("time", startTime);
        queryWrapper.le("time", endTime);
        if (flag != null) {
            queryWrapper.eq("flag", flag).groupBy("time")
                    .select("time", "avg(price) as price","flag as product");
        } else {
            queryWrapper.groupBy("flag", "time")
                    .select("avg(price) as price", "time", "flag as product");
        }
        List<ProductPrice> list = this.list(queryWrapper);
        Map<String, List<ProductPrice>> collect = list.stream()
                .collect(Collectors.groupingBy(ProductPrice::getProduct));
        collect.forEach((productKey, productList) -> {
            ProductPriceTrendData productPriceTrendData = new ProductPriceTrendData();
            List<ProductPriceTrend> productPriceTrends = productList.stream()
                    .map(item -> new ProductPriceTrend(item.getTime(), item.getPrice()))
                    .collect(Collectors.toList());
            productPriceTrendData.setProduct(productKey);
            productPriceTrendData.setProductPriceTrends(productPriceTrends);
            res.add(productPriceTrendData);
        });
        ReqRougePrice reqRougePrice = new ReqRougePrice();
        BeanUtils.copyProperties(reqProductPrice, reqRougePrice);
        if(flag == null || flag == 7){
            List<ProductPriceTrend> priceTrend = rougePriceService.getPriceTrend(reqRougePrice);
            res.add(new ProductPriceTrendData("7", priceTrend));
        }
        return res;
    }

    @Override
    public ProductPriceVO getDailyPriceInfo(ReqProductPrice reqProductPrice) {
        String region = reqProductPrice.getRegion();
        if (region == null) {
            region = "";
        }
        LocalDate date = reqProductPrice.getTime();
        Integer flag = reqProductPrice.getFlag();
        return baseMapper.getDailyPriceInfo(flag, date, region);
    }

    @Override
    public List<ProductPrice> queryPage(ReqProductPrice reqProductPrice) {
        LambdaQueryWrapper<ProductPrice> queryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProductPrice.getFlag();
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String product = reqProductPrice.getProduct();
        String region = reqProductPrice.getRegion();
        //按时间排序
        queryWrapper.orderByDesc(ProductPrice::getTime);
        if (flag != null) {
            queryWrapper.eq(ProductPrice::getFlag, flag);
        }
        if (product != null) {
            queryWrapper.like(ProductPrice::getProduct, product);
        }
        if (region != null) {
            queryWrapper.like(ProductPrice::getRegion, region);
        }
        if (startTime != null) {
            queryWrapper.ge(ProductPrice::getTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.le(ProductPrice::getTime, endTime);
        }
        return list(queryWrapper);
    }

    @Override
    public List<ProductPriceTrendData> getProductPriceTrendDataForecast(ReqProductPrice reqProductPrice) {
        List<ProductPriceTrendData> productPriceTrendData = getProductPriceTrendData(reqProductPrice);
//        HttpHeaders headers = new HttpHeaders();
//        return productPriceTrendData.stream().peek(item->{
//            HttpEntity<List<ProductPriceTrend>> requestEntity = new HttpEntity<>(item.getProductPriceTrends(), headers);
//            ResponseEntity<ProductPriceTrend[]> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, ProductPriceTrend[].class);
//            ProductPriceTrend[] predictedPriceArray = responseEntity.getBody();
//            List<ProductPriceTrend> predictedPriceList = Arrays.asList(predictedPriceArray);
//            item.setProductPriceTrends(predictedPriceList);
//        }).collect(Collectors.toList());
        return productPriceTrendData.stream().peek(item -> {
            // 提取日期和值
//            List<Date> dates = new ArrayList<>();
//            List<BigDecimal> values = new ArrayList<>();
//            for (ProductPriceTrend ppt : item.getProductPriceTrends()) {
//                dates.add(ppt.getDate());
//                values.add(ppt.getValue());
//            }
//
//            WeightedObservedPoints obs = new WeightedObservedPoints();
//            for (int i = 0; i < dates.size(); i++) {
//                obs.add(i, values.get(i).doubleValue());
//            }
//
//            PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
//            double[] coefficients = fitter.fit(obs.toList());
//
//            // 预测未来5天的值并组合成List<PriceData>
//            List<ProductPriceTrend> predictedDataList = new ArrayList<>();
//            double lastDateIndex = (double) (dates.size() - 1); // 最后一个日期的索引
//            for (int i = 1; i <= 25; i++) {
//                double futureDateIndex = lastDateIndex + i; // 未来日期的索引
//                double predictedValue = evaluatePolynomial(coefficients, futureDateIndex);
//                Date futureDate = new Date(dates.get(dates.size() - 1).getTime() + i * 24 * 60 * 60 * 1000);
//                BigDecimal roundedValue = BigDecimal.valueOf(predictedValue).setScale(2, RoundingMode.HALF_UP);
//                predictedDataList.add(new ProductPriceTrend(futureDate, roundedValue));
//            }
            item.setProductPriceTrends(predictPrices(item.getProductPriceTrends()));

        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductPrice> avgPrice(ReqProductPrice reqProductPrice) {
        Integer flag = reqProductPrice.getFlag();
        String region = reqProductPrice.getRegion();
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        if (flag != null && flag == 7) {
            return rougePriceService.getPriceTrendByProduct(reqProductPrice);
        }
        String products = reqProductPrice.getProducts();
        QueryWrapper<ProductPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(flag != null, "flag", flag);
        queryWrapper.like(region != null, "region",region);
        if (products != null) {
            String[] product = products.split(";");
            queryWrapper.in("product", Arrays.asList(product));
        }
        queryWrapper.ge(startTime != null, "time", startTime).le(endTime != null, "time", endTime);
        queryWrapper.groupBy("time", "product");
        queryWrapper.select("avg(price) price", "time", "product");
        return this.list(queryWrapper);
    }

    @Override
    public List<ProductPrice> getPublicTemp(List<Integer> flags, String region) {
        return baseMapper.getPublicTemp(flags, region);
    }

    @Override
    public BigDecimal getAvgPriceByMonthAndRegion(String provinceName, String month,Integer flag) {
        QueryWrapper<ProductPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(flag!= null,"flag",flag);
        queryWrapper.like("region", provinceName).eq("DATE_FORMAT(time,'%Y-%m')", month);
        queryWrapper.select("avg(price) as price");
        ProductPrice one = this.getOne(queryWrapper);
        if (one == null) {
            return null;
        }
        return one.getPrice();
    }

    @Override
    public BigDecimal getAvgPriceByTimeAndRegion(Date startTime, Date endTime, String province,Integer flag) {
        QueryWrapper<ProductPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(flag!= null,"flag",flag);
        queryWrapper.like("region", province).
                ge(startTime != null, "time", startTime)
                .le(endTime != null, "time", endTime);
        queryWrapper.select("avg(price) as price");
        ProductPrice one = this.getOne(queryWrapper);
        if (one == null) {
            return null;
        }
        return one.getPrice();
    }

    // 评估多项式的值
    private static double evaluatePolynomial(double[] coefficients, double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public static List<ProductPriceTrend> predictPrices(List<ProductPriceTrend> inputPrices) {
        List<ProductPriceTrend> predictedPrices = new ArrayList<>();
        int windowSize = (inputPrices.size() < 10) ? 3 : 5;
        int count = 5;
        // 计算移动平均值并添加到predictedPrices
        for (int i = inputPrices.size() - count; i < inputPrices.size(); i++) {
            double sum = 0;
            int startIndex = Math.max(0, i - windowSize); // 确保不超出数组索引
            for (int j = startIndex; j < i; j++) {
                sum += inputPrices.get(j).getValue().doubleValue();
            }
            double avg = sum / count;
            Date lastDate = inputPrices.get(inputPrices.size() - 1).getDate();
            int day = i - (inputPrices.size() - count) + 1;
            Date nextDate = new Date(lastDate.getTime() + (long) day * 24 * 60 * 60 * 1000);
            double denominator = i - startIndex;
            double movingAverage = (denominator != 0) ? sum / denominator : avg; // Handle division by zero
            predictedPrices.add(new ProductPriceTrend(nextDate, new BigDecimal(movingAverage)));
        }
        return predictedPrices;
    }

}
