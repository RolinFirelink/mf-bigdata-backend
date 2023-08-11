package com.arg.smart.web.cms.service.impl;

import com.arg.smart.web.ProductData;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.service.ArticleGenerationService;
import com.arg.smart.web.cms.service.ArticleService;
import com.arg.smart.web.product.entity.vo.ProductPriceVO;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import io.micrometer.core.instrument.util.StringEscapeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
    import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ArticleGenerationServiceImpl implements ArticleGenerationService {

    @Resource
    private ProductPriceService productPriceService;

    @Resource
    private ArticleService articleService;

    @Override
    public boolean generatePriceDaily(Integer flag) {
        //产品map
        Map<Integer, String> productMap = new HashMap<>();
        productMap.put(1,"肉鸡");
        productMap.put(2,"柑橘");
        productMap.put(3,"兰花");
        productMap.put(4,"对虾");
        productMap.put(5,"菜心");
        productMap.put(7,"鸽子");



        ReqProductPrice reqProductPrice = new ReqProductPrice();
        reqProductPrice.setFlag(flag);
        LocalDate time = LocalDate.now().minusDays(1);
        reqProductPrice.setTime(time);
        //获取昨日各地的最高价格和最低价格
        ProductPriceVO dailyPriceInfo1 = productPriceService.getDailyPriceInfo(reqProductPrice);
        reqProductPrice.setRegion("广东");
        //获取昨日广东的最高价格和最低价格
        ProductPriceVO dailyPriceInfo2 = productPriceService.getDailyPriceInfo(reqProductPrice);
        reqProductPrice.setTime(time.minusDays(1));
        reqProductPrice.setRegion("");
        //获取前日各地的最高价格和最低价格
        ProductPriceVO dailyPriceInfo3 = productPriceService.getDailyPriceInfo(reqProductPrice);
        //获取广东前日的最高价格和最低价格
        reqProductPrice.setRegion("广东");
        ProductPriceVO dailyPriceInfo4 = productPriceService.getDailyPriceInfo(reqProductPrice);
        if(dailyPriceInfo1 == null || dailyPriceInfo1.getMinPrice() == null){
            return false;
        }
        Article article = new Article();
        article.setSource("自制");
        article.setFlag(flag);
        article.setCategoryId(7L);
        article.setStartTime(new Date());
        String date = time.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
        article.setTitle(date+productMap.get(flag)+ "价格日报");
        String content = "<h1 style=\"text-align: center;\"><span style=\"font-size: 24px;\">" +
                "<strong>"+article.getTitle()+ "</strong>" +
                "</span></h1>" +
                "<p><br></p><p style=\"text-align: center;\"> " +
                "<span style=\"color: rgb(140, 140, 140); font-size: 14px;\">发布时间："
                +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.getStartTime())+" &nbsp;文章来源：" +article.getSource()
                +"\n</span></p><p style=\"text-align: center;\"><br></p><p style=\"line-height: 1.5;\"><span style=\"font-family: 宋体;\"> &nbsp; &nbsp;据惠农网数据发布，"
                +time+productMap.get(flag)+"产地最低价格为"
                +dailyPriceInfo1.getMinPrice()+"元/斤" +
                "，同比昨日" +getLifting(dailyPriceInfo1.getMinPrice(),dailyPriceInfo3.getMinPrice())
                +"，最高价格为"+dailyPriceInfo1.getMaxPrice()+"元/斤" +
                "，同比昨日"+getLifting(dailyPriceInfo1.getMaxPrice(),dailyPriceInfo3.getMaxPrice())+"。";
        if(dailyPriceInfo2 != null && dailyPriceInfo2.getMinPrice() != null){
            content += "其中广东产地最低价格为"+dailyPriceInfo2.getMinPrice()+"元/斤，同比昨日"+getLifting(dailyPriceInfo2.getMinPrice(),dailyPriceInfo4.getMinPrice())+",最高价格为"+dailyPriceInfo2.getMaxPrice()+"元/斤，同比昨日"+getLifting(dailyPriceInfo2.getMaxPrice(),dailyPriceInfo4.getMaxPrice())+"</span></p><p><br></p>";
        }
        article.setContent(content);
        return articleService.saveArticle(article);
    }

    public String getLifting(BigDecimal price1, BigDecimal price2){
        BigDecimal difference = price1.subtract(price2);
        BigDecimal percentageDifference = difference.divide(price2, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        if(price1.compareTo(price2) == 0){
            return "持平";
        } else if(price1.compareTo(price2) > 0){
            return "增长" + percentageDifference.abs() + "%";
        } else {
            return "减低" + percentageDifference.abs() + "%";
        }
    }



}
