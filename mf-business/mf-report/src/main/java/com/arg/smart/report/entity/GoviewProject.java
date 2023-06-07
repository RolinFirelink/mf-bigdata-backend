package com.arg.smart.report.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
@TableName("t_goview_project")
@Data
public class GoviewProject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String projectName;

    private Integer state;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    private String createUserId;

    private Integer isDelete;

    private String indexImage;

    private String remarks;

}
