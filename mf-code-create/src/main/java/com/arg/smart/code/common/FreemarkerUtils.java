package com.arg.smart.code.common;

import com.arg.smart.code.config.properties.FreemarkerProperties;
import com.arg.smart.code.entity.CodeInfo;
import com.arg.smart.common.code.api.req.ReqCode;
import com.arg.smart.common.code.api.vo.CodeVo;
import com.arg.smart.common.core.exception.MyRuntimeException;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.FieldInfo;
import com.arg.smart.sys.api.entity.TableInfo;
import com.arg.smart.sys.api.remote.RemoteDbConnectService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author cgli
 * @description: 代码生成工具类
 * @date: 2022/8/24 15:30
 */
@Component
@Slf4j
@RefreshScope
public class FreemarkerUtils {
    @Resource
    Configuration fmConfig;
    @Resource
    FreemarkerProperties freemarkerProperties;
    @Resource
    RemoteDbConnectService remoteDbConnectService;
    @Value("${code.savePath}")
    String savePath;

    /**
     * 初始化请求参数
     *
     * @param reqCode
     * @return
     */
    private ReqCode initReqCode(ReqCode reqCode) {
        if (StringUtils.isEmpty(reqCode.getConnectId())) {
            throw new MyRuntimeException("错误:库名不允许为空");
        }
        if (StringUtils.isEmpty(reqCode.getTableName())) {
            throw new MyRuntimeException("错误:表名不允许为空");
        }
        if (StringUtils.isEmpty(reqCode.getPackageName())) {
            reqCode.setPackageName("com.arg.smart.web");
        }
        if (StringUtils.isEmpty(reqCode.getTableComment())) {
            reqCode.setTableComment(reqCode.getTableName());
            Result<PageResult<TableInfo>> result = remoteDbConnectService.getTableList(reqCode.getConnectId(), reqCode.getTableName(), new ReqPage().setPageNum(1).setPageSize(10000));
            if (!result.isSuccess()) {
                throw new MyRuntimeException(result.getMsg());
            }
            if (result.getData() == null || result.getData().getList() == null || result.getData().getList().isEmpty()) {
                throw new MyRuntimeException("错误:未获取到表信息");
            }
            TableInfo tableInfo = result.getData().getList().get(0);
            if (tableInfo != null && !StringUtils.isEmpty(tableInfo.getTableComment())) {
                reqCode.setTableComment(tableInfo.getTableComment());
            }
        }
        if (StringUtils.isEmpty(reqCode.getApiPrefix())) {
            int index = reqCode.getPackageName().lastIndexOf(".");
            if (index >= 0) {
                reqCode.setApiPrefix(reqCode.getPackageName().substring(index + 1));
            } else {
                reqCode.setApiPrefix(reqCode.getPackageName());
            }
        }
        if (StringUtils.isEmpty(reqCode.getEntityName())) {
            reqCode.setEntityName(reqCode.getTableName());
        }
        reqCode.setEntityName(StringUtils.toCamelBigCase(reqCode.getEntityName()));
        return reqCode;
    }

    /**
     * 获取代码
     *
     * @param reqCode
     * @return
     */
    public List<CodeVo> getCode(ReqCode reqCode) {
        initReqCode(reqCode);
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setPackageName(reqCode.getPackageName());
        codeInfo.setEntityName(reqCode.getEntityName());
        codeInfo.setApiPrefix(reqCode.getApiPrefix());
        Result<PageResult<FieldInfo>> result = remoteDbConnectService.getFieldList(reqCode.getConnectId(), reqCode.getTableName(), new ReqPage().setPageNum(1).setPageSize(10000));
        if (!result.isSuccess()) {
            throw new MyRuntimeException(result.getMsg());
        }
        if (result.getData() == null || result.getData().getList() == null || result.getData().getList().isEmpty()) {
            throw new MyRuntimeException("错误:未获取到字段信息");
        }
        List<FieldInfo> list = result.getData().getList();
        String idType = "";
        //缺省字段字段不需要生成,获取列时过滤掉
        for (int i = 0; i < list.size(); i++) {
            FieldInfo fieldInfo = list.get(i);
            String fieldName = fieldInfo.getFieldName();
            if (StringUtils.isEmpty(fieldName)) {
                continue;
            }
            fieldName = fieldName.toLowerCase(Locale.ROOT);
            if (DefaultField.values.contains(fieldName)) {
                //设置唯一ID类型
                if ("id".equals(fieldName)) {
                    idType = fieldInfo.getType();
                }
                list.remove(i--);
                continue;
            }
            fieldInfo.setFieldName(StringUtils.toCamelCase(fieldName));
        }
        codeInfo.setTableInfo(new TableInfo().setColumns(list).setTableName(reqCode.getTableName()).setTableComment(reqCode.getTableComment()).setIdType(idType));
        return getCode(codeInfo);
    }

    /**
     * 获取生成的代码并返回前端
     *
     * @param codeInfo 代码参数信息
     * @return
     */
    public List<CodeVo> getCode(CodeInfo codeInfo) {
        List<CodeVo> list = new ArrayList<>();
        Map<String, String> regexMap = buildRegexMap(codeInfo);
        for (String key : freemarkerProperties.getKeys()) {
            CodeVo codeVo = new CodeVo();
            String path = replaceVariable(key, regexMap);
            int index = path.lastIndexOf("/");
            if (path.length() <= index) {
                continue;
            }
            String tempName = path.substring(index + 1).replace(".ftl", "");
            codeVo.setName(tempName).setPath(path.substring(0, index));
//            if (key.contains("xml")) {
//                //xml需要转义后返回
//                codeVo.setCode(StringUtil.XMLEnc(buildCode(key, codeInfo)));
//                continue;
//            }
            codeVo.setCode(buildCode(key, codeInfo));
            list.add(codeVo);
        }
        return list;
    }

    /**
     * 构建正则替换Map
     *
     * @param codeInfo
     * @return
     */
    private Map<String, String> buildRegexMap(CodeInfo codeInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("\\$\\{entityName\\#?(?<param>.*?)}", codeInfo.getEntityName());
        map.put("\\$\\{apiPrefix\\#?(?<param>.*?)}", codeInfo.getApiPrefix());
        String path = codeInfo.getPackageName().replace(".", "/");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        map.put("\\$\\{packageName\\#?(?<param>.*?)}", path);
        return map;
    }

    /**
     * 参数替换
     *
     * @param key
     * @return
     */
    private String replaceVariable(String key, Map<String, String> mapValue) {
        for (Map.Entry<String, String> kv : mapValue.entrySet()) {
            Pattern pattern = Pattern.compile(kv.getKey());
            Matcher matcher = pattern.matcher(key);
            while (matcher.find()) {
                String value = matcher.group();
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                int index = value.indexOf("#");
                if (index <= 0) {
                    key = key.replace(value, MatchType.不处理.dealVariable(kv.getValue()));
                    continue;
                }
                key = key.replace(value, MatchType.getMatchType(value.substring(index + 1).replace("}", "")).dealVariable(kv.getValue()));
            }
        }
        return key;
    }

    /**
     * 模版代码构建
     *
     * @param template
     * @param codeInfo
     * @return
     */
    public String buildCode(String template, CodeInfo codeInfo) {
        StringWriter stringWriter = new StringWriter();
        try (Writer out = new BufferedWriter(stringWriter)) {
            Template temp = fmConfig.getTemplate(template, "utf-8");
            temp.process(codeInfo, out);
            out.flush();
        } catch (IOException | TemplateException e) {
            log.error("生成代码异常:" + e.getMessage(), e);
            throw new MyRuntimeException(e);
        }
        return stringWriter.toString();
    }

    /**
     * 保存代码到本地
     *
     * @param list
     * @return
     */
    public boolean saveCode(List<CodeVo> list) {
        savePath = savePath.replace("\\", "/");
        if (StringUtils.isEmpty(savePath)) {
            savePath = "/mfish/";
        } else if (!savePath.endsWith("/")) {
            savePath += "/";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH-mm-ss");
        String path = savePath + sdf.format(new Date());
        for (CodeVo code : list) {
            try {
                File file = new File(path + "/" + code.getPath(), code.getName());
                FileUtils.writeStringToFile(file, code.getCode(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("错误:文件保存异常", e);
            }
        }
        //压缩样例，暂不压缩
        //ZipUtils.toZip(path, true);
        return true;
    }

    /**
     * 下载代码
     *
     * @param reqCode 请求参数
     * @return
     */
    public byte[] downloadCode(ReqCode reqCode) {
        List<CodeVo> list = getCode(reqCode);
        ZipOutputStream zos;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            zos = new ZipOutputStream(outputStream);
            for (CodeVo codeVo : list) {
                zos.putNextEntry(new ZipEntry(codeVo.getPath() + "/" + codeVo.getName()));
                IOUtils.write(codeVo.getCode(), zos, StandardCharsets.UTF_8);
                zos.flush();
                zos.closeEntry();
            }
            //需要主动关闭后，再返回outputStream否则返回文件不全
            IOUtils.closeQuietly(zos);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new MyRuntimeException("错误:下载代码失败");
        }
    }

}