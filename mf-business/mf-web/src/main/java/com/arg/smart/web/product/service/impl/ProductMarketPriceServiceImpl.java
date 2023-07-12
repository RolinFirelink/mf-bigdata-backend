package com.arg.smart.web.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.arg.smart.web.product.entity.ProductMarketPrice;
import com.arg.smart.web.product.mapper.ProductMarketPriceMapper;
import com.arg.smart.web.product.service.ProductMarketPriceService;
import com.arg.smart.web.product.units.units;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private static String curUrl = "";

    @Override
    public boolean nongQingSave() {
        String ct = "https://www.nqing.com/variety/ctAE02020/";
        String dx = "https://www.nqing.com/variety/dx/";
        String gg = "https://www.nqing.com/variety/gg/";
        String jg = "https://www.nqing.com/variety/jg/";
        String mj = "https://www.nqing.com/variety/mj/";
        String shgj = "https://www.nqing.com/variety/shgj/";
        String shj = "https://www.nqing.com/variety/shj/";
        String stj = "https://www.nqing.com/variety/stj/";
        String[] urls = {ct,dx,gg,jg,mj,shgj,shj,stj};
        int[] arr = {5,4,2,2,2,1,1,2};
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
                            pmp.setBottomPrice(BigDecimal.valueOf(bot));
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
                    save(pmp);
                }
            }
        }
        return true;
    }

    @Override
    public boolean foodSave() {
        String Cx = "https://price.21food.cn/product/939.html";
        String Rj = "https://price.21food.cn/product/1505.html";
        String Mj = "https://price.21food.cn/product/196.html";
        String[] urls = {Cx,Rj,Mj};
        int[] arr = {5,1,2};
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
                pmp.setName(split[0]);
                pmp.setMarket(split[1].trim());
                String trim = split[2].trim();
                int mid = trim.indexOf("/");
                String unit = trim.substring(mid+1);
                pmp.setTopPrice(units.stringToBdm(split[2]));
                pmp.setBottomPrice(units.stringToBdm(split[3]));
                pmp.setAveragePrice(units.stringToBdm(split[4]));
                pmp.setUnit(unit);
                pmp.setFlag(arr[l]);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(split[5]);
                    pmp.setRecordDate(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                save(pmp);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean mofcomSave() {
        System.getProperties().setProperty("webdriver.chrome.driver","D:\\pachong\\new\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String Ct = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E8%8F%9C%E8%8B%94&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Dx = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E5%AF%B9%E8%99%BE&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Gg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E5%B9%BF%E6%9F%91&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Lg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E8%8A%A6%E6%9F%91&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Mg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E8%9C%9C%E6%A1%94&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Wg = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E6%B2%83%E6%9F%91&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Shj = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E4%B8%89%E9%BB%84%E9%B8%A1&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String Wj = "http://nc.mofcom.gov.cn/jghq/priceList?craftName=%E4%B9%8C%E9%B8%A1&pIndex=&eudName=&queryDateType=0&timeRange=undefined";
        String[] urls = {Ct,Dx,Gg,Lg,Mg,Wg,Shj,Wj};
        int[] arr = {5,4,2,2,2,2,1,1};

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
                    for (int j = 0; j < tds.size(); j++) {
                        String text = tds.get(j).getText();
                        if(j==0){
                            pmp.setRecordDate(units.stringToDate(text));
                        }else if(j==1){
                            pmp.setName(text);
                        }else if(j==2){
                            pmp.setAveragePrice(units.stringToBdm(text));
                            pmp.setUnit(units.stringToUnit(text));
                        }else if(j==3){
                            pmp.setMarket(text);
                        }
                    }
                    save(pmp);
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

    @Override
    public boolean jsonAdd(MultipartFile file) {
        List<ProductMarketPrice> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ProductMarketPrice pmp = JSON.parseObject(line, ProductMarketPrice.class);
                list.add(pmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveBatch(list);
        return true;
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
        int[] arr = {5,2,1,1,1,1,4,3};
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
                    ProductMarketPrice pmp = new ProductMarketPrice();
                    pmp.setRecordDate(units.stringToDate(time));
                    pmp.setName(product);
                    pmp.setMarket(place);
                    pmp.setAveragePrice(units.stringToBdm(price));
                    pmp.setUnit(units.stringToUnit(price));
                    pmp.setFlag(arr[i]);
                    save(pmp);
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

    /**
     * 爬取农情网产品数据,未进行过滤
     * @return
     */
//    @Override
//    public boolean crawlerSave() {
//        String url = "https://www.nqing.com/";
//        Document document = null;
//        try {
//            document = Jsoup.connect(url).get();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Elements elements = document.selectXpath("/html/body/div/div/div[1]/div/ul[1]");
//        Element element = elements.get(0);
//        Elements elementsByClass = element.getElementsByClass("provinceLi text-truncate pb-1");
//        List<String> list = new ArrayList<>();
//        for (Element byClass : elementsByClass) {
//            String href = "https://www.nqing.com"+byClass.children().get(0).attr("href");
//            list.add(href);
//        }
//        for (String s : list) {
//            Document document2 = null;
//            try {
//                document2 = Jsoup.connect(s).get();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Elements elements2 = document2.selectXpath("/html/body/div/div/nav[1]");
//            Elements elementsByClass2 = elements2.get(0).getElementsByClass("p-3");
//            List<String> list2 = new ArrayList<>();
//            List<String> list3 = new ArrayList<>();
//            for (Element element2 : elementsByClass2) {
//                String href = "https://www.nqing.com"+element2.attr("href");
//                list2.add(href);
//                list3.add(element2.text());
//            }
//            for (int i = 0; i < list2.size(); i++) {
//                String s1 = list2.get(i);
//                Document document3 = null;
//                try {
//                    document3 = Jsoup.connect(s1).get();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                Elements elements3 = document3.selectXpath("//*[@id=\"text\"]");
//                Elements p = elements3.get(0).getElementsByTag("p");
//                for (int j = 0; j < p.size(); j++) {
//                    if(j==0){
//                        continue;
//                    }
//                    ProductMarketPrice productMarketPrice = new ProductMarketPrice();
//                    String[] split = p.get(j).text().split("，");
//                    productMarketPrice.setMarket(list3.get(i));
//                    for (int k = 0; k < split.length; k++) {
//                        String s2 = split[k];
//                        if(!s2.contains("批发价格")){
//                            int first = s2.indexOf("：");
//                            if(k==0){
//                                int last = s2.lastIndexOf("：");
//                                if(last==-1){
//                                    continue;
//                                }
//                                String productName = s2.substring(0,first);
//                                String lowString = s2.substring(last+1);
//                                String[] strings = lowString.split("/");
//                                double lowPrice = Double.parseDouble(strings[0].substring(0, strings[0].length() - 1));
//                                String unit = strings[1];
//                                productMarketPrice.setName(productName);
//                                productMarketPrice.setBottomPrice(BigDecimal.valueOf(lowPrice));
//                                productMarketPrice.setUnit(unit);
//                            }else {
//                                String substring = s2.substring(first + 1);
//                                double price = Double.parseDouble(substring.substring(0, substring.indexOf("/") - 1));
//                                if(s2.contains("最高")){
//                                    productMarketPrice.setTopPrice(BigDecimal.valueOf(price));
//                                }else {
//                                    productMarketPrice.setAveragePrice(BigDecimal.valueOf(price));
//                                }
//                            }
//                        }
//                    }
//                    productMarketPrice.setFlag(1);
//                    productMarketPrice.setRecordDate(LocalDateTime.now());
//                    save(productMarketPrice);
//                }
//            }
//        }
//        return true;
//    }


}
