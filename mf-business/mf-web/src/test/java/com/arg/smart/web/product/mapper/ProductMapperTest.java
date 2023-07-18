package com.arg.smart.web.product.mapper;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.product.service.MaterialProduceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author czf
 * @description: MaterialProduceMapper测试类
 * @date: 2023/6/29 21:32
 * @version: V1.0.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ProductMapperTest {
    @Resource
    private MaterialProduceService materialProduceService;

    @Test
    public void selectMaterialProduceAndInsert() {
        materialProduceService.selectAndInsert();
    }

    @Test
    public void selectMaterialScaleAndInsert(){
        materialProduceService.selectScaleAndInsert();
    }
}
