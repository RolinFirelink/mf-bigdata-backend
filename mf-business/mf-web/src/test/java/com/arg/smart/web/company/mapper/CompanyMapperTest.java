package com.arg.smart.web.company.mapper;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.company.entity.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: ConpanyMapper的测试类
 * @author lbz
 * @date: 2023/5/19 16:31
 * @version: V1.0.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyMapperTest {
        @Resource
        private CompanyMapper companyMapper;

        @Test
        public void selectListByCompanyTypeTest(){
            List<Company> companies = companyMapper.selectListByCompanyType(1);
            for(Company company : companies){
                System.out.println(company);
            }

    }
}
