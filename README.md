

# ⛰项目依赖

* Mfish Version: 1.0.0
* Java Version: 1.8
* Spring Boot Version: 2.7.7
* Spring Boot Admin Version: 2.7.10
* Spring Cloud Version: 2021.0.5
* Spring Cloud Alibaba Version: 2021.0.4.0
* Alibaba Nacos Version: 2.2.0
* Mybatis-Plus Version: 3.5.2
* Swagger Fox Version: 3.0.0
* Swagger Core Version: 1.6.2
* PageHelper Boot Version: 1.4.6
* Druid Version: 1.2.15
* Fastjson Version: 2.0.22
* OAuth2 Version: 1.0.2
* Aliyun OSS Version: 3.16.0
* Oracle Version: 12.2.0.1



# 🏃快速开始



## 数据库配置



首先在本地创建项目所需的数据库，具体参照DB文件夹，选择好字符集：`utf8mb4`，排序规则：`utf8mb8_general_ci` 。然后在数据库下执行项目携带的SQL文件即可（包含各个表和表结构）



**数据库信息**

| 文件               | 描述           |
| ------------------ | -------------- |
| `mf_config.sql`    | nacos数据库    |
| `mf_oauth.sql`     | 认证数据库     |
| `mf_system.sql`    | 系统管理数据库 |
| `mf_scheduler.sql` | 调度中心数据库 |





**启动顺序**

1. 先启动nacos
2. 启动网关gateway,授权中心、和其他子应用
3. 启动前端



Swagger访问地址: http://localhost:8888/swagger-ui/index.html

启动项目即可访问本地Swagger，若要使用完整Web服务需要在本地启动前端服务，具体请看：[mf-bigdata-front](https://github.com/RolinFirelink/mf-bigdata-frontend)



# 😀介绍

大数据平台希望打造一个后端基于Spring Cloud Alibaba，注册中心、配置中心采用nacos且当前版本完成oauth2统一认证接入的农产品大数据平台

本项目为柑橘农药减施监控云服务平台的后端

**架构图**如下：

![image-20240301005427120](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301005427120.png)



## 🏭系统功能



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
   └─更多功能开发中......

```



## 🧱注意



本项目的后端分为前台和后台，查看源码时务必分清

有些模块如mf-code-create用于生成代码，简化开发，并不是项目本身必要之内容，只是放入该模块能简化开发而已



# ❤️贡献者

RolinFirelink、DarkVoid、cgli等



# 项目截图



![image-20240301010429483](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010429483.png)



![image-20240301010447183](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010447183.png)



![image-20240301010515145](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010515145.png)



![image-20240301010532675](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010532675.png)



![image-20240301010547820](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010547820.png)



![image-20240301010601771](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010601771.png)



![image-20240301010619125](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010619125.png)



![image-20240301010630588](https://rolin-typora.oss-cn-guangzhou.aliyuncs.com/image-20240301010630588.png)
