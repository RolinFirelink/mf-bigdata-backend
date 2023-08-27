package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.ProductMarketNums;
import com.arg.smart.web.product.mapper.ProductMarketNumsMapper;
import com.arg.smart.web.product.service.ProductMarketNumsService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description: 批发市场采购量表
 * @author cgli
 * @date: 2023-07-30
 * @version: V1.0.0
 */
@Service
public class ProductMarketNumsServiceImpl extends ServiceImpl<ProductMarketNumsMapper, ProductMarketNums> implements ProductMarketNumsService {
    private static String curUrl = "";
    private final int maxRetries = 10;
    private final long initialDelayMillis = 1000; // 初始延迟1秒
    private int retries = 0;
    private static final String PATH = "D:\\programmNoDelete\\chromedriver-win32\\chromedriver.exe";

    @Override
    public void purchaseScheduledSave() {
        while (retries < maxRetries) {
            if (saveByPurchase()) {
                log.debug("惠农网采购大厅爬虫添加成功");
                break;

            }

            retries++;
            log.debug("惠农网采购大厅爬虫添加失败,尝试重新爬取 (重试次数: {"+retries+"}/{"+maxRetries+"})");

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
            log.debug("尝试十次仍然失败,跳过惠农网采购大厅信息的爬取");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveByPurchase() {
        System.getProperties().setProperty("webdriver.chrome.driver", PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        String format = "yyyy-MM-dd HH:mm";
        String[] ss = {"单次","每天","每周","每月"};
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            String Rj = "https://www.cnhnb.com/purchase/ji/";
            String Gj = "https://www.cnhnb.com/purchase/ganju/";
            String Jj = "https://www.cnhnb.com/purchase/jinju/";
            String Cz = "https://www.cnhnb.com/purchase/chengzi/";
            String Lh = "https://www.cnhnb.com/purchase/lanhua/";
            String Cx = "https://www.cnhnb.com/purchase/caitai/";
            String Dx = "https://www.cnhnb.com/purchase/xiamiao-0-0-8475-0-1/";
            String Jcy = "https://www.cnhnb.com/purchase/changyuo-0-0-2616-0-1/";
            int[] arr = {1,2,2,2,3,5,4,8};
            String[] urls = {Rj,Gj,Jj,Cz,Lh,Cx,Dx,Jcy};
            boolean judge = true;
            for (int k = 0; k < urls.length; k++) {
                String url = urls[k];
                chromeDriver.get(url);

                if (judge) {
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
                }

                do {
                    Thread.sleep(3000);
                    curUrl=chromeDriver.getCurrentUrl();
                    WebElement all = chromeDriver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div/div[2]/div/div[3]/div[1]"));
                    WebElement window;
                    try {
                        window = all.findElement(By.cssSelector("div div ul.eye-flex.eye-flex-wrap.purchase-hot-item.eye-renderer"));
                    }catch (Exception e){
                        break;
                    }
                    List<WebElement> elements = window.findElements(By.cssSelector("li"));
                    for (int i = 0; i < elements.size(); i++) {
                        WebElement element = elements.get(i);
                        WebElement purchase = element.findElement(By.className("purchase-cell"));
                        String name = purchase.findElement(By.cssSelector("div.cateName.cateName2")).getText();
                        String[] splits = name.split(" ");
                        if(splits.length>1){
                            name=splits[splits.length-1];
                        }
                        String numString = purchase.findElement(By.cssSelector("div.qty.qty2")).getText();
                        String numS = numString.substring(0, numString.length()-1);
                        String numUnit = numString.substring(numString.length() - 1);

                        double num;
                        try {
                            num = Double.parseDouble(numS);
                        }catch (Exception e){
                            numUnit = numString.substring(numString.length()-2);
                            numS = numString.substring(0,numString.length()-2);
                            num = Double.parseDouble(numS);
                        }
                        String place = purchase.findElement(By.cssSelector("div.scopeFullName.scopeFullName2")).getText();
                        String publisher = purchase.findElement(By.cssSelector("div.linkName.linkName2")).getText();
                        WebElement href = purchase.findElement(By.cssSelector("div.moreCell.fs14.green.moreCell2"));
                        href.click();
                        Thread.sleep(3000);
                        // 获取当前窗口句柄
                        String originalHandle = chromeDriver.getWindowHandle();
                        // 获取所有窗口句柄
                        Set<String> handles = chromeDriver.getWindowHandles();
                        // 切换到新窗口
                        for (String handle : handles) {
                            if (!handle.equals(originalHandle)) {
                                chromeDriver.switchTo().window(handle);
                                break;
                            }
                        }
                        WebElement content = chromeDriver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div/div[2]/div/div[2]/div[2]"));
                        List<WebElement> left = content.findElements(By.cssSelector("div div.purchase-tr"));
                        String purchaseTime = null;
                        String purchaseRound = null;
                        String receiptPlace = null;
                        String additionalNotes = null;
                        Integer viewNums = null;
                        String quality = null;
                        for (int m = 0; m < left.size(); m++) {
                            WebElement webElement = left.get(m);
                            WebElement titleEle = webElement.findElement(By.cssSelector("div.purchase-td-title"));
                            WebElement ele = webElement.findElement(By.cssSelector("div.purchase-td"));
                            String title = titleEle.getText();
                            String text = ele.getText();
                            if(title.contains("采购数量")){
                                //处理购买轮次
                                int index = findFirstDigitIndex(text);
                                if(index!=-1){
                                    purchaseRound = text.substring(0, index);
                                }
                            } else if (title.contains("规格品质")) {
                                quality = text;
                            } else if (title.contains("收货地")){
                                receiptPlace=text;
                            } else if (title.contains("补充说明")){
                                additionalNotes=text;
                            } else if (title.contains("发布采购时间")){
                                purchaseTime=text;
                            } else if (title.contains("浏览次数")){
                                viewNums = Integer.parseInt(text.substring(0,text.length()-1));
                            }
                        }

                        // 关闭新窗口
                        chromeDriver.close();
                        // 返回原窗口
                        chromeDriver.switchTo().window(originalHandle);
                        ProductMarketNums pmn = new ProductMarketNums();
                        pmn.setQuality(quality);
                        pmn.setViewNums(viewNums);
                        pmn.setAdditionalNotes(additionalNotes);
                        pmn.setReceiptPlace(receiptPlace);
                        for (int m = 0; m < ss.length; m++) {
                            String s = ss[m];
                            if(s.equals(purchaseRound)){
                                pmn.setPurchaseRound(m);
                                break;
                            }
                        }
                        Date dateTime = null;
                        if(purchaseTime!=null){
                            dateTime = sdf.parse(purchaseTime);
                        }
                        pmn.setPurchaseTime(dateTime);
                        pmn.setName(name);
                        pmn.setFlag(arr[k]);
                        pmn.setUnit(numUnit);
                        pmn.setExpectedSource(place);
                        pmn.setPublisher(publisher);
                        pmn.setPurchaseNums(num);
                        this.save(pmn);
                    }
                }while (cnhnbNext(chromeDriver));
            }

            Thread.sleep(3000);
            chromeDriver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean cnhnbNext(ChromeDriver chromeDriver) {
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
        if(currentUrl.equals(curUrl)){
            return false;
        }
        return true;
    }

    private int findFirstDigitIndex(String str) {
        int n = str.length();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                return i;
            }
        }
        return -1;
    }
}
