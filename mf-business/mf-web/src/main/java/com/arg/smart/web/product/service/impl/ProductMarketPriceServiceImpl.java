package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.ProductMarketPrice;
import com.arg.smart.web.product.mapper.ProductMarketPriceMapper;
import com.arg.smart.web.product.req.ReqProductMarketPrice;
import com.arg.smart.web.product.service.ProductMarketPriceService;
import com.arg.smart.web.product.units.units;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @description: 产品批发价格表
 * @author cgli
 * @date: 2023-07-08
 * @version: V1.0.0
 */
@Service
public class ProductMarketPriceServiceImpl extends ServiceImpl<ProductMarketPriceMapper, ProductMarketPrice> implements ProductMarketPriceService {

    private final int maxRetries = 10;
    private final long initialDelayMillis = 1000; // 初始延迟1秒
    private int retries = 0;
    private static final String PATH = "/usr/local/chromeDriver/chromedriver";

    @Override
    public void nongQingScheduledSave() {
        while (retries < maxRetries) {
            if (nongQingSave()) {
                log.debug("农情网信息爬虫添加成功");
                break;

            }

            retries++;
            log.debug("农情网信息爬虫添加失败,尝试重新爬取 (重试次数: {"+retries+"}/{"+maxRetries+"})");

            // 指数退避 - 每次重试时增加延迟时间
            long delay = initialDelayMillis * (long) Math.pow(2, retries);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("等待重试时发生中断：", e);
            }
        }

        if (retries >= maxRetries) {
            log.debug("尝试十次仍然失败,跳过农情网信息的爬取");
        }
    }

    @Override
    public boolean nongQingSave() {

        String ct = "https://www.nqing.com/variety/ctAE02020/";
        String dx = "https://www.nqing.com/variety/dx/";
//        String dsx = "https://www.nqing.com/variety/dsx/";
//        String jwx = "https://www.nqing.com/variety/jwx/";
//        String lx = "https://www.nqing.com/variety/lx/";
        String gg = "https://www.nqing.com/variety/gg/";
        String jg = "https://www.nqing.com/variety/jg/";
        String mj = "https://www.nqing.com/variety/mj/";
        String shgj = "https://www.nqing.com/variety/shgj/";
        String shj = "https://www.nqing.com/variety/shj/";
        String stj = "https://www.nqing.com/variety/stj/";
        String Jcy = "https://www.nqing.com/variety/cy/";
        String[] urls = {ct,dx,gg,jg,mj,shgj,shj,stj,Jcy};
//        String[] urls = {dsx,lx,jwx};
        int[] arr = {5,4,2,2,2,1,1,2,8};
//        int[] arr = {4,4,4};
        Document document = null;
        for (int l = 0; l < urls.length; l++) {
            String url = urls[l];
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements elements = document.selectXpath("//*[@id=\"text\"]");
            Elements p = elements.get(0).getElementsByTag("p");
            String name = null;
            for (int i = 0; i < p.size(); i++) {
                String text = p.get(i).text();
                ProductMarketPrice pmp = new ProductMarketPrice();
                pmp.setFlag(arr[l]);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(String.valueOf(LocalDateTime.now()));
                    pmp.setRecordDate(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(i==0){
                    name = text.substring(4, text.lastIndexOf("批"));
                }else {
                    pmp.setName(name);
                    String[] split = text.split("，");
                    for (int k = 0; k < split.length; k++) {
                        String s = split[k];
                        if(k==0){
                            String[] strings = s.split("：");
                            pmp.setMarket(strings[0]);
                            int end = strings[2].indexOf("/");
                            String unit = strings[2].substring(end+1);
                            if(unit.contains("。")){
                                unit = unit.substring(0,2);
                            }
                            String low = strings[2].substring(0,end-1);
                            double bot = Double.parseDouble(low);
                            pmp.setUnit(unit);
                            BigDecimal bottomPrice = BigDecimal.valueOf(bot);
                            pmp.setBottomPrice(bottomPrice);
                        }else {
                            int end = s.indexOf("/");
                            int start = s.indexOf("：");
                            String price = s.substring(start + 1, end - 1);
                            double v = Double.parseDouble(price);
                            if(s.contains("最高")){
                                pmp.setTopPrice(BigDecimal.valueOf(v));
                            }else if(s.contains("最低")){
                                pmp.setBottomPrice(BigDecimal.valueOf(v));
                            }else {
                                pmp.setAveragePrice(BigDecimal.valueOf(v));
                            }
                        }
                    }
                    save(checkUnit(pmp));
                }
            }
        }
        return true;
    }

    @Override
    public void foodScheduledSave() {
        while (retries < maxRetries) {
            if (foodSave()) {
                log.debug("食品商务网信息爬虫添加成功");
                break;

            }

            retries++;
            log.debug("食品商务网信息爬虫添加失败,尝试重新爬取 (重试次数: {"+retries+"}/{"+maxRetries+"})");

            // 指数退避 - 每次重试时增加延迟时间
            long delay = initialDelayMillis * (long) Math.pow(2, retries);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("等待重试时发生中断：", e);
            }
        }

        if (retries >= maxRetries) {
            log.debug("尝试十次仍然失败,跳过食品商务网信息的爬取");
        }
    }

    private ProductMarketPrice checkUnit(ProductMarketPrice pmp) {
        String unit = pmp.getUnit();
        if("公斤".equals(unit)){
            pmp.setUnit("斤");
            BigDecimal averagePrice = pmp.getAveragePrice();
            BigDecimal topPrice = pmp.getTopPrice();
            BigDecimal bottomPrice = pmp.getBottomPrice();
            if(averagePrice!=null){
                pmp.setAveragePrice(averagePrice.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
            }
            if(topPrice!=null){
                pmp.setTopPrice(topPrice.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
            }
            if(bottomPrice!=null){
                pmp.setBottomPrice(bottomPrice.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
            }
        }
        return pmp;
    }

    @Override
    public boolean foodSave() {
        String Cx = "https://price.21food.cn/product/939.html";
        String Rj = "https://price.21food.cn/product/1505.html";
        String Mj = "https://price.21food.cn/product/196.html";
        String Jcy = "https://price.21food.cn/product/265.html";
        String[] urls = {Cx,Rj,Mj,Jcy};
        int[] arr = {5,1,2,8};
        for (int l = 0; l < urls.length; l++) {
            String url = urls[l];
            Document document = null;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements elements = document.selectXpath("/html/body/div[2]/div[3]/div/div[2]/div[1]/div[2]/div[2]/ul");
            Elements tr = elements.get(0).getElementsByTag("tr");
            for (int i = 0; i < tr.size(); i++) {
                Element element = tr.get(i);
                ProductMarketPrice pmp = new ProductMarketPrice();
                String text = element.text();
                String[] split = text.split(" ");
                if(split.length==7){
                    for (int j = 2; j < split.length-1; j++) {
                        String temp = split[j];
                        split[j] = split[j+1];
                        split[j+1] = temp;
                    }
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(split[5]);
                    if(isDateOneDayBefore(date)){
                        pmp.setRecordDate(date);
                    }else {
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pmp.setName(split[0]);
                pmp.setMarket(split[1].trim());
                String trim = split[2].trim();
                int mid = trim.indexOf("/");
                String unit = trim.substring(mid+1);
                BigDecimal topPrice = units.stringToBdm(split[2]);
                BigDecimal bottomPrice = units.stringToBdm(split[3]);
                BigDecimal averagePrice = units.stringToBdm(split[4]);
                pmp.setTopPrice(topPrice);
                pmp.setBottomPrice(bottomPrice);
                pmp.setAveragePrice(averagePrice);
                pmp.setUnit(unit);
                pmp.setFlag(arr[l]);
                save(checkUnit(pmp));
            }
        }
        return true;
    }

    public static boolean isDateOneDayBefore(Date date) {
        if(date==null){
            return false;
        }
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 将Date转换为LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 获取当前日期的前一天
        LocalDate oneDayBeforeCurrentDate = currentDate.minusDays(1);

        // 比较日期
        return localDate.equals(oneDayBeforeCurrentDate);
    }

    @Override
    public void mofcomScheduledSave() {
        while (retries < maxRetries) {
            if (mofcomSave()) {
                log.debug("农产品商务信息爬虫添加成功");
                break;

            }

            retries++;
            log.debug("农产品商务信息爬虫添加失败,尝试重新爬取 (重试次数: {"+retries+"}/{"+maxRetries+"})");

            // 指数退避 - 每次重试时增加延迟时间
            long delay = initialDelayMillis * (long) Math.pow(2, retries);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("等待重试时发生中断：", e);
            }
        }

        if (retries >= maxRetries) {
            log.debug("尝试十次仍然失败,跳过农产品商务信息的爬取");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean mofcomSave() {
        System.getProperties().setProperty("webdriver.chrome.driver",PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        String Ct = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E8%8F%9C%E8%8B%94&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
//        String Dx = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E5%AF%B9%E8%99%BE&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
//        String Gg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E5%B9%BF%E6%9F%91&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
//        String Lg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E8%8A%A6%E6%9F%91&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
//        String Mg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E8%9C%9C%E6%A1%94&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
//        String Wg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E6%B2%83%E6%9F%91&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
//        String Shj = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E4%B8%89%E9%BB%84%E9%B8%A1&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
//        String Wj = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E4%B9%8C%E9%B8%A1&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String X = "https://nc.mofcom.gov.cn/jghq/priceList?craftName=%E8%99%BE&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Jcy = "http://nc.mofcom.gov.cn/jghq/priceSearchList?mq=%E9%B2%B3%E9%B1%BC&q=%2B%E9%B2%B3%E9%B1%BC&sort=PUBLISH_TIME+desc";
//        String[] urls = {Ct,Dx,Gg,Lg,Mg,Wg,Shj,Wj};
        String[] urls = {X,Jcy};
//        int[] arr = {5,4,2,2,2,2,1,1};
        int[] arr = {4,8};

        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            chromeDriver.get(url);
            do {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<WebElement> elements = chromeDriver.findElements(By.xpath("//*[@id=\"showList\"]/table/tbody"));
                WebElement webElement = elements.get(0);
                List<WebElement> trs = webElement.findElements(By.tagName("tr"));
                for (WebElement tr : trs) {
                    ProductMarketPrice pmp = new ProductMarketPrice();
                    List<WebElement> tds = tr.findElements(By.tagName("td"));
                    pmp.setFlag(arr[i]);
                    boolean judge = true;
                    for (int j = 0; j < tds.size(); j++) {
                        String text = tds.get(j).getText();
                        if(j==0){
                            Date date = units.stringToDate(text);
                            if(isDateOneDayBefore(date)){
                                pmp.setRecordDate(date);
                            }else {
                                judge=false;
                                break;
                            }
                        }else if(j==1){
                            pmp.setName(text);
                        }else if(j==2){
                            BigDecimal averagePrice = units.stringToBdm(text);
                            pmp.setAveragePrice(averagePrice);
                            String unit = units.stringToUnit(text);
                            pmp.setUnit(unit);
                            pmp.setAveragePrice(averagePrice);
                        }else if(j==3){
                            pmp.setMarket(text);
                        }
                    }
                    if(judge) {
                        save(checkUnit(pmp));
                    }
                }
            }while (hasNext(chromeDriver));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        chromeDriver.quit();
        return true;
    }

    private boolean hasNext(ChromeDriver chromeDriver) {
        WebElement element = null;
        try {
            element = chromeDriver.findElement(By.cssSelector("a.j-page-jump.last.pagenxt"));
        }catch (Exception e){
            return false;
        }
        String text = element.getText();
        if(text.contains("下")){
            element.click();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public PageResult<ProductMarketPrice> list(ReqProductMarketPrice reqProductMarketPrice) {
        LambdaQueryWrapper<ProductMarketPrice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(reqProductMarketPrice.getMarket() != null, ProductMarketPrice::getMarket, reqProductMarketPrice.getMarket())
                .eq(reqProductMarketPrice.getFlag() != null, ProductMarketPrice::getFlag, reqProductMarketPrice.getFlag());
        if (reqProductMarketPrice.getStartTime() != null && reqProductMarketPrice.getEndTime() != null) {
            queryWrapper.between(ProductMarketPrice::getRecordDate, reqProductMarketPrice.getStartTime(), reqProductMarketPrice.getEndTime());
        }
        return new PageResult<>(this.list(queryWrapper));
    }
}
