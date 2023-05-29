package com.arg.smart.web.customer.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.web.customer.entity.CustomerBehavior;
import com.arg.smart.web.customer.mapper.CustomerBehaviorMapper;
import com.arg.smart.web.customer.service.CustomerBehaviorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class CustomerBehaviorScheduledTask {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CustomerBehaviorService customerBehaviorService;

    // 每天00:00执行一次
    @Scheduled(cron = "0 0 0 * * ?")
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
