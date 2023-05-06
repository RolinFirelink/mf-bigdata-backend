package ${packageName}.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
<#list tableInfo.columns as fieldInfo>
<#if fieldInfo.type=='Date'>
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
<#break>
</#if>
</#list>

/**
 * @description: ${tableInfo.tableComment}
 * @author cgli
 * @date: ${.now?string["yyyy-MM-dd"]}
 * @version: V1.0.0
 */
@Data
@TableName("${tableInfo.tableName}")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "${tableInfo.tableName}对象", description = "${tableInfo.tableComment}")
public class ${entityName} extends BaseEntity<<#if tableInfo.idType==''>String<#else>${tableInfo.idType}</#if>> {
    <#if tableInfo.idType=='String'>
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private ${tableInfo.idType} id;
    <#elseif tableInfo.idType!=''>
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private ${tableInfo.idType} id;
    </#if>
    <#list tableInfo.columns as fieldInfo>
	<#if !fieldInfo.isPrimary>
      <#if fieldInfo.type =='Date'>
        <#if fieldInfo.dbType =='DATE'>
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
          <#elseif fieldInfo.dbType =='DATETIME'>
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        </#if>
      </#if>
    </#if>
    @ApiModelProperty(value = "${fieldInfo.comment}")
	private <#if fieldInfo.type=='java.sql.Blob'>byte[]<#else>${fieldInfo.type}</#if> ${fieldInfo.fieldName};
	</#list>
}
