# 大数据管理平台后端



## 架构图
![](https://oscimg.oschina.net/oscnet/up-63e6a3ba5667370d5bf2ef4d9401e007972.png)

## 项目介绍
大数据平台希望打造一个平台。
* 后端基于Spring Cloud Alibaba
* 注册中心、配置中心采用nacos
* 当前版本完成oauth2统一认证接入
* 持续进行功能完善


## 功能模块
```
├─驾驶舱
│  ├─工作台
├─系统管理
│  ├─菜单管理
│  ├─组织管理
│  ├─角色管理
│  ├─帐号管理
│  ├─字典管理
│  ├─个人信息
│  ├─日志管理
│  ├─文件管理
│  ├─在线用户
│  ├─数据库
│  └─数据源
├─系统监控
│  ├─监控中心
├─任务调度
│  ├─任务管理
│  ├─任务日志
├─项目文档
│  ├─接口地址
│  ├─Github地址
│  ├─Gitee地址
│  ├─AntDesign文档
│  └─Vben文档
├─多级目录
├─系统工具
│  ├─代码生成
├─图形编辑器
├─引导页
├─关于
└─其他模块 
   └─更多功能开发中。。

```

### 一期目标:(脚手架完成)

1.基础框架搭建  
2.业务代码自动生成  
3.基础权限功能  
4.完成基础系统管理功能  
5.能够通过生成代码快速完成业务管理平台搭建满足程序员采用脚手架快速二开的需求

### 二期目标:

1.真正的大数据平台设计开发....  
......

#### 项目截图



![](https://oscimg.oschina.net/oscnet/up-cb060c85cfc867df4ea6c1be4ac65d64d74.png)

![](https://oscimg.oschina.net/oscnet/up-93645a610cf9dd0266580e0870ff497b946.png)



![](https://oscimg.oschina.net/oscnet/up-57d93c91b93340387c44d5d30e984e914d7.png)

![](https://oscimg.oschina.net/oscnet/up-0ff2d7b640896b9a9156af832baebcb313f.png)

![](https://oscimg.oschina.net/oscnet/up-81d9f856cdd794843d172c47874b69ff503.png)

![](https://oscimg.oschina.net/oscnet/up-c26c5a79214ed2e242512d0f5f4accca63b.png)

![](https://oscimg.oschina.net/oscnet/up-36d63fb4e8dd0a0844ff64a8f4c28682296.png)

![](https://oscimg.oschina.net/oscnet/up-434781fa769d2da21e396bfccbbe13c8f15.png)

![](https://oscimg.oschina.net/oscnet/up-7b2eeb5e679f75d889a841de61f9845c026.png)

![](https://oscimg.oschina.net/oscnet/up-c413a81f353a0175bbbd09cc32a7fb8d5bf.png)

![](https://oscimg.oschina.net/oscnet/up-736398ce5030ce21b6dda45ba9f24af4a72.png)
#### 数据库信息

|文件|描述|
|---|---|
|`mf_config.sql`| nacos数据库 |
|`mf_oauth.sql`| 认证数据库 |
|`mf_system.sql`| 系统管理数据库 |
|`mf_scheduler.sql`| 调度中心数据库 |
    
#### 启动顺序
1、先启动nacos
2、启动网关gateway,授权中心、和其他子应用
3、启动前端

#### swagger访问地址

http://localhost:8888/swagger-ui/index.html
