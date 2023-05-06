import { BaseEntity, PageResult } from "/@/api/model/BaseModel";

/**
 * @description: ${tableInfo.tableComment}
 * @author cgli
 * @date: ${.now?string["yyyy-MM-dd"]}
 * @version: V1.0.0
 */
export interface ${entityName} extends BaseEntity<<#if tableInfo.idType==''||tableInfo.idType=='String'>string<#else>number</#if>> {
<#list tableInfo.columns as fieldInfo>
  ${fieldInfo.fieldName}: <#if fieldInfo.type=='String'||fieldInfo.type='Date'>string<#elseif fieldInfo.type =='Boolean'>boolean<#else>number</#if>;
</#list>
}

export interface Req${entityName} {

}

export type ${entityName}PageModel = PageResult<${entityName}>;