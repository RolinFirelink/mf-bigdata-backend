package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProduceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 企业生产信息表
 * @author cgli
 * @date: 2023-07-11
 * @version: V1.0.0
 */
public interface ProduceInfoMapper extends BaseMapper<ProduceInfo> {

    @Select("select " +
            "id " +
            "from sh_company " +
            "where company_name like CONCAT('%', #{companyName}, '%')")
    List<Long> selectCompanyId(String companyName);

    @Select("select company_name from sh_company where id = #{companyId}")
    String getNameById(Long companyId);
}
