package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.AreaAvgPriceAndSales;
import com.arg.smart.web.product.entity.vo.PriceTemp;
import com.arg.smart.web.product.mapper.ProductPriceMapper;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.arg.smart.web.product.units.units;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, ProductPrice> implements ProductPriceService {

    private static String curUrl = "";

    @Override
    public List<ProductPrice> queryList(ReqProductPrice reqProductPrice) {
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
            queryWrapper.gt(ProductPrice::getTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.lt(ProductPrice::getTime, endTime);
        }
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
        int[] arr = {5,2,7,1,1,1,4,3};
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
                curUrl=chromeDriver.getCurrentUrl();
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
                         isFinsh=false;
                    }catch (Exception e){
                        lifting = "-";
                    }
                    if(isFinsh){
                        try {
                            lifting = element.findElement(By.cssSelector("a span.lifting.fallcolor")).getText();
                        }catch (Exception e){
                            lifting = "-";
                        }
                    }
                    ProductPrice pp = new ProductPrice();
                    pp.setTime(units.stringToDate(time));
                    pp.setProduct(product);
                    pp.setRegion(place);
                    pp.setPrice(units.stringToBdm(price));
                    pp.setUnit(units.stringToPriceAndUnit(price));
                    if(!lifting.equals("-")){
                        pp.setLifting(BigDecimal.valueOf(Double.parseDouble(lifting.substring(0,lifting.length()-1))));
                    }
                    pp.setFlag(arr[i]);
//                    System.out.println(pp);
                    save(pp);
                }
            }while (cnhnbNext(chromeDriver));
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
        if(elements.size()==1){
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
    public List<PriceTemp> getPriceTemp() {
        List<ProductPrice> list = baseMapper.getMaxTimePrice();
        return list.stream().map(item -> {
            BigDecimal price = item.getPrice();
            //上次的价格
            BigDecimal lastPrice = price.divide(item.getLifting().divide(new BigDecimal(100)).add(new BigDecimal(1)), 2, BigDecimal.ROUND_DOWN);
            //改变的价格
            BigDecimal changePrice = price.subtract(lastPrice);
            //指数
            Integer temp = price.divide(lastPrice, 3, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).intValue();
            return new PriceTemp(item.getFlag(), temp, changePrice, item.getUnit());
        }).collect(Collectors.toList());
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
    public List<ProductPrice> publicTrend(ReqProductPrice reqProductPrice) {
        QueryWrapper<ProductPrice> queryWrapper = new QueryWrapper<>();
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusDays(30);
        }
        queryWrapper.ge("time", startTime);
        queryWrapper.le("time", endTime);
        Integer flag = reqProductPrice.getFlag();
        queryWrapper.eq("flag", flag);
        queryWrapper.groupBy("time").groupBy("unit");
        queryWrapper.select("max(price) as maxPrice","min(price) as minPrice", "time", "unit");
        return this.list(queryWrapper);
    }
}
