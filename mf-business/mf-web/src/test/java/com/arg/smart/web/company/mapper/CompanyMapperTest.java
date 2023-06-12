package com.arg.smart.web.company.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.customer.entity.CustomerBehavior;
import com.arg.smart.web.customer.service.CustomerBehaviorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lbz
 * @description: ConpanyMapper的测试类
 * @date: 2023/5/19 16:31
 * @version: V1.0.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class CompanyMapperTest {
    @Resource
    private CompanyMapper companyMapper;

    @Test
    public void selectListByCompanyTypeTest() {

    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CustomerBehaviorService customerBehaviorService;

    @Test
    public void execute() {
        // 获取行为类型为1的数据项
        QueryWrapper<CustomerBehavior> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 1);
        List<CustomerBehavior> list = customerBehaviorService.list(wrapper);

        // 统计keywords数量并保存到redis中
        HyperLogLogOperations<String, String> opsForHyperLogLog = redisTemplate.opsForHyperLogLog();
        for (CustomerBehavior behavior : list) {
            if (StringUtils.isNotBlank(behavior.getExtendInfo())) {
                JSONObject jsonObject = JSON.parseObject(behavior.getExtendInfo());
                if (jsonObject.containsKey("keywords")) {
                    String keywords = jsonObject.getString("keywords");
                    opsForHyperLogLog.add(keywords, String.valueOf(behavior.getId()));
                }
            }
        }


    }
}
