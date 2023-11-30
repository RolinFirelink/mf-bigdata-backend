package com.arg.smart.sys.mapper;

import com.arg.smart.sys.entity.DictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description: 字典项
 * @author cgli
 * @date: 2023-01-03
 * @Version: V1.0.0
 */
public interface DictItemMapper extends BaseMapper<DictItem> {

    @Select("select count(*) from sys_dict_item where dict_code = 'mk_product_type'")
    Integer getProductTypeCount();
}
