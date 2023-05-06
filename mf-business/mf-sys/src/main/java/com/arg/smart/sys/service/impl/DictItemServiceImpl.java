package com.arg.smart.sys.service.impl;

import com.arg.smart.sys.entity.DictItem;
import com.arg.smart.sys.mapper.DictItemMapper;
import com.arg.smart.sys.req.ReqDictItem;
import com.arg.smart.sys.service.DictItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 字典项
 * @author cgli
 * @date: 2023-01-03
 * @Version: V1.0.0
 */
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    @Override
    public List<DictItem> getDictItems(ReqDictItem reqDictItem) {
        return baseMapper.selectList(buildCondition(reqDictItem));
    }

    @Override
    public boolean deleteDictItemsByCode(String dictCode) {
        return baseMapper.delete(new LambdaQueryWrapper<DictItem>().eq(DictItem::getDictCode, dictCode)) > 0;
    }

    private LambdaQueryWrapper buildCondition(ReqDictItem reqDictItem) {
        return new LambdaQueryWrapper<DictItem>()
                .eq(reqDictItem.getDictCode() != null, DictItem::getDictCode, reqDictItem.getDictCode())
                .like(reqDictItem.getDictLabel() != null, DictItem::getDictLabel, reqDictItem.getDictLabel())
                .like(reqDictItem.getDictValue() != null, DictItem::getDictValue, reqDictItem.getDictValue())
                .eq(reqDictItem.getStatus() != null, DictItem::getStatus, reqDictItem.getStatus())
                .eq(reqDictItem.getDictId() != null, DictItem::getDictId, reqDictItem.getDictId())
                .orderByAsc(DictItem::getDictSort);
    }
}
