package com.arg.smart.common.code.api.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cgli
 * @description: 代码生成参数
 * @date: 2022/12/23 19:39
 */
@Data
@ApiModel("代码生成参数")
public class ReqCode {
    @ApiModelProperty("数据库连接ID(必传 通过数据库列表接口查询)")
    private String connectId;
    @ApiModelProperty("表名(必传)")
    private String tableName;
    @ApiModelProperty("表描述(不传会获取数据库表中的中文描述，如果也为空则使用表名)")
    private String tableComment;
    @ApiModelProperty("项目包名(不传使用默认包名 com.arg.smart.web)")
    private String packageName;
    @ApiModelProperty("实体类名(不传会使用表名驼峰化)")
    private String entityName;
    @ApiModelProperty("接口路径前缀 例如:/oauth2/user接口前缀为oauth2(不传会使用packageName，最底层包名 例如:com.arg.smart.sys包会使用sys)")
    private String apiPrefix;
}
