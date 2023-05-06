package com.arg.smart.scheduler.test;

import com.arg.smart.common.scheduler.config.utils.InvokeUtils;
import com.arg.smart.sys.api.entity.SysLog;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 参数生成测试
 * @author cgli
 * @date: 2023/2/14 10:57
 */
public class BuildParamTest {
    @Test
    public void buildParam() {
        List<InvokeUtils.InvokeParams> list = new ArrayList<>();
        SysLog sysLog = new SysLog();
        sysLog.setTitle("aaaa");
        list.add(new InvokeUtils.InvokeParams().setType(String.class.getName()).setValue("inner"));
        list.add(new InvokeUtils.InvokeParams().setType(sysLog.getClass().getName()).setValue(sysLog));
        System.out.println(JSON.toJSONString(list));
    }
}
