package com.arg.smart.sys.mapper;

import com.arg.smart.sys.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description: 字典
 * @author cgli
 * @date: 2023-01-03
 * @Version: V1.0.0
 */
public interface DictMapper extends BaseMapper<Dict> {
    Integer isDictCodeExist(@Param("id") String id, @Param("dictCode") String dictCode);

}
