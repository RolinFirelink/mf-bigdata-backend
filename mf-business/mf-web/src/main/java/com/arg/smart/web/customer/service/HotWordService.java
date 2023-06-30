package com.arg.smart.web.customer.service;

import com.arg.smart.web.customer.entity.HotWord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 热词表
 * @author zsj
 * @date: 2023-06-29
 * @version: V1.0.0
 */
public interface HotWordService extends IService<HotWord> {
    List<HotWord> publicList(Integer type);
}