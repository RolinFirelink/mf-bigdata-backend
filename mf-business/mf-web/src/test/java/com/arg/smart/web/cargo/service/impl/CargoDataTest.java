package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.service.CarrierTransportationVolumeDataListService;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.product.service.ProductPriceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class CargoDataTest {

    @Resource
    private CarrierTransportationVolumeDataListService carrierTransportationVolumeDataListService;
    @Resource
    private ProductCirculationDataService orderInformationListMapper;
    @Resource
    private ProductPriceService productPriceService;

    @Test
    public void testUpdateData(){
        carrierTransportationVolumeDataListService.updateData();
    }

    @Test
    public void testSelectData(){
        carrierTransportationVolumeDataListService.list(3);
    }

    @Test
    public void testSelectOfOrderInformationList(){
        List<ProductCirculationData> orderInformationList = orderInformationListMapper.selectOfOrderInformationList(3);
    }

}
