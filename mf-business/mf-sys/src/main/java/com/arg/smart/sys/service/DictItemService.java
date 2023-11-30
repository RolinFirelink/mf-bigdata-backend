package com.arg.smart.sys.service;

import com.arg.smart.sys.entity.DictItem;
import com.arg.smart.sys.req.ReqDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 字典项
 * @author cgli
 * @date: 2023-01-03
 * @Version: V1.0.0
 */
public interface DictItemService extends IService<DictItem> {
    /**
     * 获取字典项
     *
     * @param reqDictItem
     * @return
     */
    List<DictItem> getDictItems(ReqDictItem reqDictItem);

    /**
     * 通过code删除字典项
     * @param dictCode
     * @return
     */
    boolean deleteDictItemsByCode(String dictCode);

    Integer getProductTypeCount();
}
