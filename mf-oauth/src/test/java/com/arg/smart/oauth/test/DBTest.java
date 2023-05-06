package com.arg.smart.oauth.test;

import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.mapper.SsoUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author cgli
 * @description: 测试数据库请求
 * @date: 2022/11/24 23:10
 */
@Slf4j
@SpringBootTest
@ComponentScan(basePackages = "com.arg.smart.oauth")
@RunWith(SpringRunner.class)
public class DBTest {
    @Resource
    SsoUserMapper ssoUserMapper;

    @Test
    public void getUser() {
        SsoUser ssoUser = ssoUserMapper.getUserById("40062f1156ef42b9b3a341462c927fb6");
        System.out.println(ssoUser);
    }
}
