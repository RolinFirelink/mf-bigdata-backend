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

import java.util.List;

/**
 * @description: 批发市场采购量表
 * @author cgli
 * @date: 2023-07-29
 * @version: V1.0.0
 */
@Service
public class ProductMarketNumsServiceImpl extends ServiceImpl<ProductMarketNumsMapper, ProductMarketNums> implements ProductMarketNumsService {

    private static String curUrl = "";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveByPurchase() {
        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\pachong\\new\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        try {
            String Rj = "https://www.cnhnb.com/purchase/ji/";
            String Gj = "https://www.cnhnb.com/purchase/ganju/";
            String Jj = "https://www.cnhnb.com/purchase/jinju/";
            String Cz = "https://www.cnhnb.com/purchase/chengzi/";
            String Lh = "https://www.cnhnb.com/purchase/lanhua/";
            String Cx = "https://www.cnhnb.com/purchase/caitai/";
            String Dx = "https://www.cnhnb.com/purchase/xiamiao-0-0-8475-0-1/";
            int[] arr = {1,2,2,2,3,5,4};
            String[] urls = {Rj,Gj,Jj,Cz,Lh,Cx,Dx};
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
                        ProductMarketNums pmn = new ProductMarketNums();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        if(currentUrl.equals(curUrl)){
            return false;
        }
        return true;
    }
}
