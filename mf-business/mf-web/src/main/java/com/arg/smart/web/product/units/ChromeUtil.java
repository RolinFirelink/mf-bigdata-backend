package com.arg.smart.web.product.units;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author Rolin
 * @create 2023/11/10 14:58
 */
public class ChromeUtil {
    public static ChromeDriver getChromeDriver() {
        // 本地测试，本地需配置同版本的chromedriver和Chrome，测试时保持Chrome网页运行
        // chrome会自动更新，需注意保持版本不变
        // 快速入门教学：https://blog.csdn.net/chenjxj123/article/details/121802904
        System.setProperty("webdriver.chrome.driver", "/usr/local/chromeDriver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1366,768));
        return driver;
    }
}