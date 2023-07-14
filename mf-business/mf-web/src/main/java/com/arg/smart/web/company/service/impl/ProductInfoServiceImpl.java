package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.ProductInfo;
import com.arg.smart.web.company.mapper.ProductInfoMapper;

import com.arg.smart.web.company.service.ProductInfoService;
import com.arg.smart.web.company.vo.ProductVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Resource
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductVo> getProductInfoarea(Integer flag,String city) {
        List<String> keywords = new ArrayList<>();
        if(city.equals("清远市")){
            //清远市1
            keywords.add("连州");keywords.add("清新");keywords.add("英德");
            keywords.add("连州");keywords.add("佛冈");keywords.add("阳山");
            keywords.add("连山");keywords.add("连南");
        }
        else if(city.equals("肇庆市")){
            //肇庆市2
            keywords.add("端州");keywords.add("鼎湖");keywords.add("高要");
            keywords.add("广宁");keywords.add("怀集");keywords.add("封开");
            keywords.add("德庆");
        }else if(city.equals("韶关市")){
            //韶关市3
            keywords.add("浈江");keywords.add("武江");keywords.add("曲江");
            keywords.add("始兴");keywords.add("仁化");keywords.add("翁源");
            keywords.add("乳源瑶族");keywords.add("新丰");
        }else if(city.equals("云浮市")){
            //云浮市4
            keywords.add("云城");keywords.add("云安");keywords.add("新兴");
            keywords.add("郁南");
        }else if(city.equals("河源市")){
            //河源市5
            keywords.add("源城");keywords.add("紫金");keywords.add("龙川");
            keywords.add("连平");keywords.add("和平");keywords.add("东源");
            keywords.add("新市");
        }else if(city.equals("梅州市")){
            //梅州市6
            keywords.add("梅江");keywords.add("梅县");keywords.add("大浦");
            keywords.add("丰顺");keywords.add("五华");keywords.add("平远");
            keywords.add("蕉岭");
        }else if(city.equals("汕头市")){
            //汕头市7
            keywords.add("金平");keywords.add("龙湖");keywords.add("澄海");
            keywords.add("汕头市潮阳");keywords.add("潮南");keywords.add("南澳");
            keywords.add("濠江");
        }else if(city.equals("揭阳市")){
            //揭阳市8
            keywords.add("榕城");keywords.add("揭东");keywords.add("揭西");
            keywords.add("普宁");keywords.add("惠来");keywords.add("东山");
            keywords.add("陆河");
        }else if(city.equals("潮州市")){
            //潮州市9
            keywords.add("湘桥");keywords.add("潮安");keywords.add("饶平");
            keywords.add("枫溪");keywords.add("东山");keywords.add("潮州");
            keywords.add("南海");keywords.add("潮州市潮阳");
        }else if(city.equals("惠州市")){
            //惠州市10
            keywords.add("惠城");keywords.add("惠阳");keywords.add("惠东");
            keywords.add("博罗");keywords.add("龙门");keywords.add("大亚湾");
        }else if(city.equals("东莞市")){
            //东莞市11
            keywords.add("莞城");keywords.add("南城");keywords.add("东城");
            keywords.add("万江");keywords.add("石碣");keywords.add("石龙");
            keywords.add("麻涌");keywords.add("道滘");keywords.add("沙田");
            keywords.add("长安");keywords.add("中堂");keywords.add("高埗");
            keywords.add("樟木头");keywords.add("大岭山");keywords.add("塘厦");
            keywords.add("凤岗");keywords.add("清溪");keywords.add("谢岗");
            keywords.add("横沥");keywords.add("厚街");keywords.add("虎门");
            keywords.add("黄江");keywords.add("大朗");keywords.add("沙坪");
            keywords.add("钟屋");keywords.add("望牛墩");keywords.add("石排");
            keywords.add("石龙");keywords.add("高要");keywords.add("明城");
            keywords.add("东坑");keywords.add("洪梅");
        }else if(city.equals("广州市")){
            //广州市12
            keywords.add("越秀");keywords.add("海珠");keywords.add("天河");
            keywords.add("白云");keywords.add("黄埔");keywords.add("番禺");
            keywords.add("花都");keywords.add("南沙");keywords.add("从化");
            keywords.add("增城");
        }else if(city.equals("佛山市")){
            //佛山市13
            keywords.add("禅城");keywords.add("南海");keywords.add("顺德");
            keywords.add("三水");keywords.add("高明");
        }else if(city.equals("中山市")){
            //中山市14
            keywords.add("蓬江");keywords.add("江海");keywords.add("新会");
            keywords.add("台山");keywords.add("开平");keywords.add("鹤山");
            keywords.add("恩平");
        }else if(city.equals("珠海市")){
            //珠海市15
            keywords.add("香洲");keywords.add("斗门");keywords.add("金湾");
        }else if(city.equals("江门市")){
            //江门市16
            keywords.add("蓬江");keywords.add("江海");keywords.add("新会");
            keywords.add("台山");keywords.add("开平");keywords.add("鹤山");
            keywords.add("恩平");
        }else if(city.equals("阳江市")){
            //阳江市17
            keywords.add("江城");keywords.add("阳东");keywords.add("阳西");
            keywords.add("阳春");
        }else if(city.equals("茂名市")){
            //茂名市18
            keywords.add("茂南");keywords.add("茂港");keywords.add("高州");
            keywords.add("化州");keywords.add("信宜");
        }else if(city.equals("湛江市")){
            //湛江市19
            keywords.add("赤坎");keywords.add("霞山");keywords.add("坡头");
            keywords.add("麻章");keywords.add("遂溪");keywords.add("徐闻");
            keywords.add("廉江");keywords.add("雷州");keywords.add("吴川");
        }else if(city.equals("揭阳市")){
            //揭阳市20
            keywords.add("连州");keywords.add("清新");keywords.add("英德");
            keywords.add("连山");
        }else if(city.equals("汕尾市")){
            //汕尾市21
            keywords.add("海丰");keywords.add("陆河");keywords.add("陆丰");
        }

        List<ProductInfo> productInfos = productInfoMapper.selectList(new QueryWrapper<ProductInfo>()
                .eq("flag", flag));// 根据reqProductInfo查询ProductInfo列表的逻辑

        List<ProductVo> productVos = new ArrayList<>();
        Set<String> baseNames = new HashSet<>();
        for (ProductInfo productInfo : productInfos) {
            String baseName = productInfoMapper.getNameById(productInfo.getCompanyId());
            if (baseName != null && !baseName.isEmpty() && !baseNames.contains(baseName)) {
                for (String keyword : keywords) {
                    if (baseName.contains(keyword)) {
                        ProductVo productVo = new ProductVo();
                        // 设置其他属性
                        productVo.setBaseName(baseName);
                        productVo.setIphoneNumber(productInfoMapper.getIphoneNumberById(productInfo.getCompanyId()));
                        System.out.println(productInfo.getEstimatedLaunchDate());
                        productVo.setEstimatedLaunchDate(productInfo.getEstimatedLaunchDate());
                        productVo.setProjectedProduction(productInfo.getProjectedProduction());
                        productVo.setProductUnit(productInfo.getProductUnit());
                        productVo.setLatLongItude(productInfoMapper.getlatByareaCode(productInfoMapper.getAreaCodeById(productInfo.getCompanyId()))
                        +"° "+productInfoMapper.getlngByareaCode(productInfoMapper.getAreaCodeById(productInfo.getCompanyId()))+"° ");
                        productVos.add(productVo);
                        baseNames.add(baseName);
                        break;
                    }
                }
            }
        }

        return productVos;
    }

}
/*if(area=="清远市"){
        //清远市1
        keywords.add("连州");keywords.add("清新");keywords.add("英德");
        keywords.add("连州");keywords.add("佛冈");keywords.add("阳山");
        keywords.add("连山");keywords.add("连南");
        }
        else if(area=="肇庆市"){
        //肇庆市2
        keywords.add("端州");keywords.add("鼎湖");keywords.add("高要");
        keywords.add("广宁");keywords.add("怀集");keywords.add("封开");
        keywords.add("德庆");
        }else if(area=="韶关市"){
        //韶关市3
        keywords.add("浈江");keywords.add("武江");keywords.add("曲江");
        keywords.add("始兴");keywords.add("仁化");keywords.add("翁源");
        keywords.add("乳源瑶族");keywords.add("新丰");
        }else if(area=="云浮市"){
        //云浮市4
        keywords.add("云城");keywords.add("云安");keywords.add("新兴");
        keywords.add("郁南");
        }else if(area=="河源市"){
        //河源市5
        keywords.add("源城");keywords.add("紫金");keywords.add("龙川");
        keywords.add("连平");keywords.add("和平");keywords.add("东源");
        keywords.add("新市");
        }else if(area=="梅州市"){
        //梅州市6
        keywords.add("梅江");keywords.add("梅县");keywords.add("大浦");
        keywords.add("丰顺");keywords.add("五华");keywords.add("平远");
        keywords.add("蕉岭");
        }else if(area=="汕头市"){
        //汕头市7
        keywords.add("金平");keywords.add("龙湖");keywords.add("澄海");
        keywords.add("汕头市潮阳");keywords.add("潮南");keywords.add("南澳");
        keywords.add("濠江");
        }else if(area=="揭阳市"){
        //揭阳市8
        keywords.add("榕城");keywords.add("揭东");keywords.add("揭西");
        keywords.add("普宁");keywords.add("惠来");keywords.add("东山");
        keywords.add("陆河");
        }else if(area=="潮州市"){
        //潮州市9
        keywords.add("湘桥");keywords.add("潮安");keywords.add("饶平");
        keywords.add("枫溪");keywords.add("东山");keywords.add("潮州");
        keywords.add("南海");keywords.add("潮州市潮阳");
        }else if(area=="惠州市"){
        //惠州市10
        keywords.add("惠城");keywords.add("惠阳");keywords.add("惠东");
        keywords.add("博罗");keywords.add("龙门");keywords.add("大亚湾");
        }else if(area=="东莞市"){
        //东莞市11
        keywords.add("莞城");keywords.add("南城");keywords.add("东城");
        keywords.add("万江");keywords.add("石碣");keywords.add("石龙");
        keywords.add("麻涌");keywords.add("道滘");keywords.add("沙田");
        keywords.add("长安");keywords.add("中堂");keywords.add("高埗");
        keywords.add("樟木头");keywords.add("大岭山");keywords.add("塘厦");
        keywords.add("凤岗");keywords.add("清溪");keywords.add("谢岗");
        keywords.add("横沥");keywords.add("厚街");keywords.add("虎门");
        keywords.add("黄江");keywords.add("大朗");keywords.add("沙坪");
        keywords.add("钟屋");keywords.add("望牛墩");keywords.add("石排");
        keywords.add("石龙");keywords.add("高要");keywords.add("明城");
        keywords.add("东坑");keywords.add("洪梅");
        }else if(area=="广州市"){
        //广州市12
        keywords.add("越秀");keywords.add("海珠");keywords.add("天河");
        keywords.add("白云");keywords.add("黄埔");keywords.add("番禺");
        keywords.add("花都");keywords.add("南沙");keywords.add("从化");
        keywords.add("增城");
        }else if(area=="佛山市"){
        //佛山市13
        keywords.add("禅城");keywords.add("南海");keywords.add("顺德");
        keywords.add("三水");keywords.add("高明");
        }else if(area=="中山市"){
        //中山市14
        keywords.add("蓬江");keywords.add("江海");keywords.add("新会");
        keywords.add("台山");keywords.add("开平");keywords.add("鹤山");
        keywords.add("恩平");
        }else if(area=="珠海市"){
        //珠海市15
        keywords.add("香洲");keywords.add("斗门");keywords.add("金湾");
        }else if(area=="江门市"){
        //江门16
        keywords.add("蓬江");keywords.add("江海");keywords.add("新会");
        keywords.add("台山");keywords.add("开平");keywords.add("鹤山");
        keywords.add("恩平");
        }else if(area=="阳江市"){
        //阳江市17
        keywords.add("江城");keywords.add("阳东");keywords.add("阳西");
        keywords.add("阳春");
        }else if(area=="茂名市"){
        //茂名市18
        keywords.add("茂南");keywords.add("茂港");keywords.add("高州");
        keywords.add("化州");keywords.add("信宜");
        }else if(area=="湛江市"){
        //湛江市19
        keywords.add("赤坎");keywords.add("霞山");keywords.add("坡头");
        keywords.add("麻章");keywords.add("遂溪");keywords.add("徐闻");
        keywords.add("廉江");keywords.add("雷州");keywords.add("吴川");
        }else if(area=="连州市"){
            keywords.add("连州");keywords.add("清新");keywords.add("英德");
            keywords.add("连山");
            System.out.println("111111");
        }else if(area=="汕尾市"){
            keywords.add("海丰");keywords.add("陆河");keywords.add("陆丰");
        }*/