/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : mf_market

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2023-05-06 15:56:38
*/

DROP DATABASE IF EXISTS `mf_market`;
CREATE DATABASE  `mf_market` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE `mf_market`;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bi_report
-- ----------------------------
DROP TABLE IF EXISTS `bi_report`;
CREATE TABLE `bi_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `report_code` varchar(100) DEFAULT NULL COMMENT '报表编码',
  `report_group` varchar(100) DEFAULT NULL COMMENT '分组',
  `report_type` varchar(20) DEFAULT NULL COMMENT '报表类型',
  `report_image` varchar(512) DEFAULT NULL COMMENT '报表缩略图',
  `report_desc` varchar(255) DEFAULT NULL COMMENT '报表描述',
  `report_author` varchar(512) DEFAULT NULL COMMENT '报表作者',
  `download_count` bigint(11) DEFAULT '0' COMMENT '报表下载次数',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(8) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNIQUE_REPORT_CODE` (`report_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生成报表记录表';

-- ----------------------------
-- Records of bi_report
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_dashboard
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_dashboard`;
CREATE TABLE `bi_report_dashboard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '看板id',
  `report_code` varchar(50) NOT NULL COMMENT '报表编码',
  `title` varchar(254) DEFAULT NULL COMMENT '看板标题',
  `width` bigint(20) DEFAULT NULL COMMENT '宽度px',
  `height` bigint(20) DEFAULT NULL COMMENT '高度px',
  `background_color` varchar(24) DEFAULT NULL COMMENT '背景色',
  `background_image` varchar(254) DEFAULT NULL COMMENT '背景图片',
  `preset_line` varchar(4096) DEFAULT NULL COMMENT '工作台中的辅助线',
  `refresh_seconds` int(11) DEFAULT NULL COMMENT '自动刷新间隔秒',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT ' 0--未删除 1--已删除 DIC_NAME=DEL_FLAG',
  `sort` int(11) DEFAULT '0' COMMENT '排序，降序',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNIQUE_REPORT_CODE` (`report_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='看板记录表';

-- ----------------------------
-- Records of bi_report_dashboard
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_dashboard_widget
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_dashboard_widget`;
CREATE TABLE `bi_report_dashboard_widget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组件id',
  `report_code` varchar(50) NOT NULL COMMENT '报表编码',
  `type` varchar(50) DEFAULT NULL COMMENT '组件类型，参考字典DASHBOARD_PANEL_TYPE',
  `setup` text COMMENT '组件的渲染属性json',
  `data` text COMMENT '组件的数据属性json',
  `collapse` text COMMENT '组件的配置属性json',
  `position` text COMMENT '组件的大小位置属性json',
  `options` text COMMENT 'options配置项',
  `refresh_seconds` int(11) DEFAULT NULL COMMENT '自动刷新间隔秒',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT ' 0--未删除 1--已删除 DIC_NAME=DEL_FLAG',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序，图层的概念',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='看板组件表';

-- ----------------------------
-- Records of bi_report_dashboard_widget
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_data_set
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_data_set`;
CREATE TABLE `bi_report_data_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set_code` varchar(50) DEFAULT NULL COMMENT '数据集编码',
  `set_name` varchar(100) DEFAULT NULL COMMENT '数据集名称',
  `set_desc` varchar(255) DEFAULT NULL COMMENT '数据集描述',
  `source_code` varchar(50) DEFAULT NULL COMMENT '数据源编码',
  `dyn_sentence` varchar(2048) DEFAULT NULL COMMENT '动态查询sql或者接口中的请求体',
  `case_result` text COMMENT '结果案例',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  `set_type` varchar(10) DEFAULT NULL COMMENT '数据集分类',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_set_code` (`set_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据集管理';

-- ----------------------------
-- Records of bi_report_data_set
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_data_set_param
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_data_set_param`;
CREATE TABLE `bi_report_data_set_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set_code` varchar(50) DEFAULT NULL COMMENT '数据集编码',
  `param_name` varchar(50) DEFAULT NULL COMMENT '参数名',
  `param_desc` varchar(100) DEFAULT NULL COMMENT '参数描述',
  `param_type` varchar(255) DEFAULT NULL COMMENT '参数类型，字典=',
  `sample_item` varchar(1080) DEFAULT NULL COMMENT '参数示例项',
  `required_flag` int(1) DEFAULT '1' COMMENT '0--非必填 1--必填 DIC_NAME=REQUIRED_FLAG',
  `validation_rules` varchar(2048) DEFAULT NULL COMMENT 'js校验字段值规则，满足校验返回 true',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据集查询参数';

-- ----------------------------
-- Records of bi_report_data_set_param
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_data_set_transform
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_data_set_transform`;
CREATE TABLE `bi_report_data_set_transform` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set_code` varchar(50) DEFAULT NULL COMMENT '数据集编码',
  `transform_type` varchar(50) DEFAULT NULL COMMENT '数据转换类型，DIC_NAME=TRANSFORM_TYPE; js，javaBean，字典转换',
  `transform_script` varchar(10800) DEFAULT NULL COMMENT '数据转换脚本,处理逻辑',
  `order_num` int(2) DEFAULT NULL COMMENT '排序,执行数据转换顺序',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据集数据转换';

-- ----------------------------
-- Records of bi_report_data_set_transform
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_data_source
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_data_source`;
CREATE TABLE `bi_report_data_source` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_code` varchar(100) DEFAULT NULL COMMENT '数据源编码',
  `source_name` varchar(100) DEFAULT NULL COMMENT '数据源名称',
  `source_desc` varchar(255) DEFAULT NULL COMMENT '数据源描述',
  `source_type` varchar(50) DEFAULT NULL COMMENT '数据源类型 DIC_NAME=SOURCE_TYPE; mysql，orace，sqlserver，elasticsearch，接口，javaBean，数据源类型字典中item-extend动态生成表单',
  `source_config` varchar(2048) DEFAULT NULL COMMENT '数据源连接配置json：关系库{ jdbcUrl:'''', username:'''', password:'''' } ES{ hostList:''ip1:9300,ip2:9300,ip3:9300'', clusterName:''elasticsearch_cluster'' }  接口{ apiUrl:''http://ip:port/url'', method:'''' } javaBean{ beanNamw:''xxx'' }',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_source_code` (`source_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据源管理';

-- ----------------------------
-- Records of bi_report_data_source
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_excel
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_excel`;
CREATE TABLE `bi_report_excel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_code` varchar(100) DEFAULT NULL COMMENT '报表编码',
  `set_codes` varchar(255) DEFAULT NULL COMMENT '数据集编码，以|分割',
  `set_param` varchar(1024) DEFAULT NULL COMMENT '数据集查询参数',
  `json_str` text COMMENT '报表json串',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNIQUE_REPORT_CODE` (`report_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据导出excel';

-- ----------------------------
-- Records of bi_report_excel
-- ----------------------------

-- ----------------------------
-- Table structure for bi_report_share
-- ----------------------------
DROP TABLE IF EXISTS `bi_report_share`;
CREATE TABLE `bi_report_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `share_code` varchar(50) DEFAULT NULL COMMENT '分享编码，系统生成，默认UUID',
  `share_valid_type` int(2) DEFAULT NULL COMMENT '分享有效期类型，DIC_NAME=SHARE_VAILD',
  `share_valid_time` datetime DEFAULT NULL COMMENT '分享有效期',
  `share_token` varchar(255) DEFAULT NULL COMMENT '分享token',
  `share_url` varchar(100) DEFAULT NULL COMMENT '分享url',
  `share_password` varchar(10) DEFAULT NULL COMMENT '分享码',
  `report_code` varchar(50) DEFAULT NULL COMMENT '报表编码',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNIQUE_SHARE_CODE` (`share_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报表分享记录表';

-- ----------------------------
-- Records of bi_report_share
-- ----------------------------

-- ----------------------------
-- Table structure for sh_article
-- ----------------------------
DROP TABLE IF EXISTS `sh_article`;
CREATE TABLE `sh_article` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `summary` varchar(1000) NOT NULL DEFAULT '' COMMENT '摘要',
  `author` varchar(50) DEFAULT '' COMMENT '作者',
  `source` varchar(50) DEFAULT '' COMMENT '来源',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '分类id',
  `cover_img` varchar(255) NOT NULL DEFAULT '' COMMENT '封面图片地址',
  `status` smallint(4) NOT NULL DEFAULT '0' COMMENT '状态,0->草稿箱,1->发布',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '是否置顶,0->不置顶,1->置顶',
  `content_model` smallint(4) NOT NULL DEFAULT '0' COMMENT '内容模式,0->标准模式,1->定制模式',
  `click_num` bigint(20) DEFAULT NULL COMMENT '点击数',
  `collect_num` int(11) NOT NULL DEFAULT '0' COMMENT '收藏量',
  `comment_num` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `like_num` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `download_num` int(11) NOT NULL DEFAULT '0' COMMENT '下载量',
  `share_num` int(11) NOT NULL DEFAULT '0' COMMENT '分享数',
  `allow_comment` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许评论',
  `allow_subscribe` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许订阅',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布开始期',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布结束期',
  `media_type` varchar(50) DEFAULT NULL COMMENT '信息类型,分为,article:文本;picture:图片类;vidio:视频类',
  `extend_attribute` longtext COMMENT '扩展属性 JSON结构',
  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '多租户组织ID',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- ----------------------------
-- Records of sh_article
-- ----------------------------

-- ----------------------------
-- Table structure for sh_article_category
-- ----------------------------
DROP TABLE IF EXISTS `sh_article_category`;
CREATE TABLE `sh_article_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '文章数',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级ID',
  `parent_ids` varchar(2000) DEFAULT '' COMMENT '一串IDS组合',
  `parent_names` varchar(2000) DEFAULT '' COMMENT '一串IDS组合名称',
  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '多租户组织ID',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类';

-- ----------------------------
-- Records of sh_article_category
-- ----------------------------

-- ----------------------------
-- Table structure for sh_article_content
-- ----------------------------
DROP TABLE IF EXISTS `sh_article_content`;
CREATE TABLE `sh_article_content` (
  `id` bigint(20) unsigned NOT NULL COMMENT '文章ID',
  `content` longtext COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='文章内容表';

-- ----------------------------
-- Records of sh_article_content
-- ----------------------------

-- ----------------------------
-- Table structure for sh_company
-- ----------------------------
DROP TABLE IF EXISTS `sh_company`;
CREATE TABLE `sh_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '公司名称',
  `license_no` varchar(255) NOT NULL DEFAULT '' COMMENT '营执照编码',
  `juridical_person` varchar(50) DEFAULT NULL COMMENT '法人',
  `juridical_phone` varchar(30) DEFAULT NULL COMMENT '法人电话',
  `contacts` varchar(100) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `fax` varchar(30) DEFAULT NULL COMMENT '传真',
  `address` varchar(50) DEFAULT NULL COMMENT '公司地址',
  `description` varchar(500) DEFAULT NULL COMMENT '备注说明',
  `isystem` tinyint(1) DEFAULT '1' COMMENT '是否系统自带 0==系统 1==非系统',
  `type` varchar(20) DEFAULT NULL COMMENT '分类类型（生产商、供应商、经销商）',
  `enabled` bit(1) DEFAULT b'1' COMMENT '启用',
  `tax_num` varchar(50) DEFAULT NULL COMMENT '纳税人识别号',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '开户行',
  `account` varchar(50) DEFAULT NULL COMMENT '银行账号',
  `org_id` bigint(20) DEFAULT NULL COMMENT '归属组织id',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业信息表';

-- ----------------------------
-- Records of sh_company
-- ----------------------------

-- ----------------------------
-- Table structure for sh_dynamic_dict
-- ----------------------------
DROP TABLE IF EXISTS `sh_dynamic_dict`;
CREATE TABLE `sh_dynamic_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动编码',
  `name` varchar(50) NOT NULL COMMENT '业务名称',
  `index_name` varchar(100) DEFAULT NULL COMMENT 'ES索引名称',
  `business_handler` varchar(30) DEFAULT 'defaultProteusHandler' COMMENT '业务逻辑处理器',
  `business_type` varchar(30) DEFAULT NULL COMMENT '业务类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '业务备注',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `table_type` varchar(50) DEFAULT NULL COMMENT '表名分类名称',
  `dictionary_code` varchar(100) DEFAULT NULL COMMENT '字典code',
  `form_script` longtext COMMENT '表单脚本',
  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织ID',
  `create_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `business_type` (`business_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务类型配置字典表';

-- ----------------------------
-- Records of sh_dynamic_dict
-- ----------------------------

-- ----------------------------
-- Table structure for sh_form
-- ----------------------------
DROP TABLE IF EXISTS `sh_form`;
CREATE TABLE `sh_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_name` varchar(255) NOT NULL DEFAULT '' COMMENT '表单名称',
  `business_type` varchar(50) NOT NULL DEFAULT '' COMMENT '表单业务类型：order统一下单，商品表单',
  `business_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '表单关联业务id',
  `enable_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用0禁用,1启用',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属用户id',
  `json_data` longtext COMMENT 'json',
  `org_id` bigint(20) DEFAULT NULL COMMENT '归属组织id',
  `deleted_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_business_id` (`business_id`,`business_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自定义表单';

-- ----------------------------
-- Records of sh_form
-- ----------------------------

-- ----------------------------
-- Table structure for sh_form_data
-- ----------------------------
DROP TABLE IF EXISTS `sh_form_data`;
CREATE TABLE `sh_form_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '表单id',
  `scene` varchar(255) NOT NULL DEFAULT '' COMMENT '场景:文章，商品等',
  `form_data` longtext COMMENT '表单数据',
  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '多租户组织ID',
  `deleted_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_form_id` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统表单数据';

-- ----------------------------
-- Records of sh_form_data
-- ----------------------------

-- ----------------------------
-- Table structure for sh_form_dynamic_config
-- ----------------------------
DROP TABLE IF EXISTS `sh_form_dynamic_config`;
CREATE TABLE `sh_form_dynamic_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动编码',
  `title` varchar(255) DEFAULT NULL COMMENT '字段中文标题',
  `column_name` varchar(50) DEFAULT NULL COMMENT '字段英文代码',
  `column_type` varchar(20) DEFAULT '' COMMENT '字段类型：input，select。。。',
  `dynamic_dict_id` bigint(20) unsigned NOT NULL COMMENT '业务Id引用dynamic_dict',
  `idx` int(10) DEFAULT '100' COMMENT '字段排序号',
  `rules` varchar(500) DEFAULT NULL COMMENT '校验规则',
  `def_value` varchar(255) DEFAULT '' COMMENT '默认值',
  `styles` varchar(500) DEFAULT '' COMMENT '样式',
  `option_source` varchar(400) DEFAULT NULL COMMENT '数据来源',
  `remark` varchar(400) DEFAULT '' COMMENT '备注',
  `other_content` text COMMENT '其他内容',
  `allow_export` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否导出：0否1是',
  `allow_shows` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否展现：0否1是',
  `allow_list_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '列表是否展现：0否1是',
  `allow_empty` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允放为空',
  `allow_write` tinyint(1) DEFAULT '0' COMMENT '是否写入：0否,1是',
  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '多租户组织ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_idx` (`column_name`,`dynamic_dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态表单配置数据列项';

-- ----------------------------
-- Records of sh_form_dynamic_config
-- ----------------------------

-- ----------------------------
-- Table structure for sh_market_quotation
-- ----------------------------
DROP TABLE IF EXISTS `sh_market_quotation`;
CREATE TABLE `sh_market_quotation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` bigint(20) NOT NULL COMMENT '产品id',
  `company_id` bigint(20) DEFAULT NULL COMMENT '归属公司id',
  `region_id` varchar(50) DEFAULT NULL COMMENT '行政区域ID',
  `bar_code` varchar(50) DEFAULT NULL COMMENT '产品条码',
  `amount` decimal(20,3) DEFAULT NULL COMMENT '数量',
  `price` decimal(20,3) DEFAULT NULL COMMENT '价格',
  `unit` varchar(50) DEFAULT NULL COMMENT '计量单位',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `org_id` bigint(20) DEFAULT NULL COMMENT '归属组织id',
  PRIMARY KEY (`id`),
  KEY `idx_material_id` (`material_id`) USING BTREE,
  KEY `idx_company_id` (`company_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='行情价格流水';

-- ----------------------------
-- Records of sh_market_quotation
-- ----------------------------

-- ----------------------------
-- Table structure for sh_market_quotation_stat
-- ----------------------------
DROP TABLE IF EXISTS `sh_market_quotation_stat`;
CREATE TABLE `sh_market_quotation_stat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stat_date` bigint(20) DEFAULT NULL COMMENT '统计日期使用时间序列如：20230301',
  `material_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `region_id` varchar(50) DEFAULT NULL COMMENT '行政区域ID',
  `region_name` varchar(50) DEFAULT NULL COMMENT '行政区域',
  `company_id` bigint(20) DEFAULT NULL COMMENT '归属公司id',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `material_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `unit` varchar(50) DEFAULT NULL COMMENT '计量单位',
  `production_amount` decimal(20,3) DEFAULT NULL COMMENT '生产数量',
  `purchase_amount` decimal(20,3) DEFAULT NULL COMMENT '采购数量',
  `wholesale_amount` decimal(20,3) DEFAULT NULL COMMENT '销售数量',
  `production_price` decimal(20,3) DEFAULT NULL COMMENT '生产价格',
  `purchase_price` decimal(20,3) DEFAULT NULL COMMENT '采购价格',
  `wholesale_price` decimal(20,3) DEFAULT NULL COMMENT '销售价格',
  `low_price` decimal(20,3) DEFAULT NULL COMMENT '最低售价',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='按日统计产品行情价格';

-- ----------------------------
-- Records of sh_market_quotation_stat
-- ----------------------------

-- ----------------------------
-- Table structure for sh_material
-- ----------------------------
DROP TABLE IF EXISTS `sh_material`;
CREATE TABLE `sh_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '产品类型id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '产品名称',
  `model` varchar(50) DEFAULT NULL COMMENT '型号',
  `standard` varchar(50) DEFAULT NULL COMMENT '规格',
  `color` varchar(50) DEFAULT NULL COMMENT '颜色',
  `unit` varchar(50) DEFAULT NULL COMMENT '单位',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `expiry_num` int(10) DEFAULT NULL COMMENT '保质期天数',
  `weight` decimal(24,6) DEFAULT NULL COMMENT '基础重量(kg)',
  `enabled` bit(1) DEFAULT b'1' COMMENT '启用 0-禁用  1-启用',
  `extend_field` longtext COMMENT '自定义扩展字段JSON结构',
  `enable_serial_number` tinyint(1) DEFAULT '0' COMMENT '是否开启序列号，0否，1是',
  `enable_batch_number` tinyint(1) DEFAULT '0' COMMENT '是否开启批号，0否，1是',
  `deleted_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `org_id` bigint(20) DEFAULT NULL COMMENT '归属组织id',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';

-- ----------------------------
-- Records of sh_material
-- ----------------------------

-- ----------------------------
-- Table structure for sh_material_attribute
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_attribute`;
CREATE TABLE `sh_material_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attribute_field` varchar(50) DEFAULT NULL COMMENT '属性字段',
  `attribute_name` varchar(50) DEFAULT NULL COMMENT '属性名',
  `attribute_value` varchar(500) DEFAULT NULL COMMENT '属性值',
  `org_id` bigint(20) DEFAULT NULL COMMENT '归属组织id',
  `deleted_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品属性表';

-- ----------------------------
-- Records of sh_material_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for sh_material_category
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_category`;
CREATE TABLE `sh_material_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `serial_no` varchar(100) DEFAULT NULL COMMENT '编号',
  `category_level` smallint(6) NOT NULL DEFAULT '0' COMMENT '等级',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级id',
  `sort` varchar(10) DEFAULT '0' COMMENT '显示顺序',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `org_id` bigint(20) DEFAULT NULL COMMENT '归属组织id',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品类型表';

-- ----------------------------
-- Records of sh_material_category
-- ----------------------------

-- ----------------------------
-- Table structure for sh_organization
-- ----------------------------
DROP TABLE IF EXISTS `sh_organization`;
CREATE TABLE `sh_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_no` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `org_abr` varchar(20) DEFAULT NULL COMMENT '机构简称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父机构id',
  `sort` int(11) DEFAULT '0' COMMENT '机构显示顺序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `deleted_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='多租户机构表';

-- ----------------------------
-- Records of sh_organization
-- ----------------------------

-- ----------------------------
-- Table structure for t_data_tracer
-- ----------------------------
DROP TABLE IF EXISTS `t_data_tracer`;
CREATE TABLE `t_data_tracer` (
  `data_tracer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_id` bigint(20) NOT NULL COMMENT '各种单据的id',
  `type` int(11) NOT NULL COMMENT '单据类型',
  `content` text COMMENT '操作内容',
  `diff_old` text COMMENT '差异：旧的数据',
  `diff_new` text COMMENT '差异：新的数据',
  `extra_data` text COMMENT '额外信息',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_type` int(11) NOT NULL COMMENT '用户类型：1 后管用户 ',
  `user_name` varchar(50) NOT NULL COMMENT '用户名称',
  `ip` varchar(50) DEFAULT NULL,
  `user_agent` varchar(2000) DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`data_tracer_id`),
  KEY `order_id_order_type` (`data_id`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='各种单据操作记录';

-- ----------------------------
-- Records of t_data_tracer
-- ----------------------------
INSERT INTO `t_data_tracer` VALUES ('1', '49', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:27:33', '2022-10-22 14:27:33');
INSERT INTO `t_data_tracer` VALUES ('2', '50', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:29:56', '2022-10-22 14:29:56');
INSERT INTO `t_data_tracer` VALUES ('3', '51', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:30:46', '2022-10-22 14:30:46');
INSERT INTO `t_data_tracer` VALUES ('4', '52', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:33:03', '2022-10-22 14:33:03');
INSERT INTO `t_data_tracer` VALUES ('5', '53', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:34:56', '2022-10-22 14:34:56');
INSERT INTO `t_data_tracer` VALUES ('6', '54', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:36:10', '2022-10-22 14:36:10');
INSERT INTO `t_data_tracer` VALUES ('7', '55', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:37:57', '2022-10-22 14:37:57');
INSERT INTO `t_data_tracer` VALUES ('8', '56', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:40:45', '2022-10-22 14:40:45');
INSERT INTO `t_data_tracer` VALUES ('9', '57', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:46:00', '2022-10-22 14:46:00');
INSERT INTO `t_data_tracer` VALUES ('10', '58', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:47:12', '2022-10-22 14:47:12');
INSERT INTO `t_data_tracer` VALUES ('11', '58', '2', '', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:47:26', '2022-10-22 14:47:26');
INSERT INTO `t_data_tracer` VALUES ('12', '59', '2', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 14:50:12', '2022-10-22 14:50:12');
INSERT INTO `t_data_tracer` VALUES ('13', '17', '3', '新增', null, null, null, '44', '1', '卓大', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36 Edg/106.0.1370.47', '2022-10-22 14:57:36', '2022-10-22 14:57:36');
INSERT INTO `t_data_tracer` VALUES ('14', '18', '3', '新增', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 17:03:35', '2022-10-22 17:03:35');
INSERT INTO `t_data_tracer` VALUES ('15', '2', '3', '新增银行:<br/>银行信息ID:26<br/>账户名称:\"1024创新实验室\"<br/>禁用状态:false<br/>开户银行:\"工商银行\"<br/>备注:\"基本户\"<br/>账号:\"1024\"<br/>是否对公:true', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 17:58:43', '2022-10-22 17:58:43');
INSERT INTO `t_data_tracer` VALUES ('16', '2', '3', '新增银行:<br/>银行信息ID:27<br/>账户名称:\"1024创新实验室\"<br/>禁用状态:false<br/>开户银行:\"建设银行\"<br/>备注:\"其他户\"<br/>账号:\"10241\"<br/>是否对公:false', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 17:59:19', '2022-10-22 17:59:19');
INSERT INTO `t_data_tracer` VALUES ('17', '2', '3', '新增发票：<br/>禁用状态:false<br/>开户行:\"中国银行\"<br/>备注:\"\"<br/>银行账户:\"1024lab\"<br/>开票抬头:\"1024创新实验室\"<br/>纳税人识别号:\"1024lab\"', null, null, null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 17:59:35', '2022-10-22 17:59:35');
INSERT INTO `t_data_tracer` VALUES ('18', '2', '3', '修改企业信息', '统一社会信用代码:\"1024lab\"<br/>详细地址:\"1024大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"卓大\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg\"<br/>联系人电话:\"18637925892\"<br/>企业名称:\"1024创新实验室\"<br/>邮箱:\"lab1024@163.com\"', '统一社会信用代码:\"1024lab\"<br/>详细地址:\"1024大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"卓大\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg\"<br/>联系人电话:\"18637925892\"<br/>企业名称:\"1024创新实验室1\"<br/>邮箱:\"lab1024@163.com\"', null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 17:59:49', '2022-10-22 17:59:49');
INSERT INTO `t_data_tracer` VALUES ('19', '2', '3', '修改企业信息', '统一社会信用代码:\"1024lab\"<br/>详细地址:\"1024大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"卓大\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg\"<br/>联系人电话:\"18637925892\"<br/>企业名称:\"1024创新实验室1\"<br/>邮箱:\"lab1024@163.com\"', '统一社会信用代码:\"1024lab\"<br/>详细地址:\"1024大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"卓大\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg\"<br/>联系人电话:\"18637925892\"<br/>企业名称:\"1024创新实验室\"<br/>邮箱:\"lab1024@163.com\"', null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36', '2022-10-22 17:59:52', '2022-10-22 17:59:52');
INSERT INTO `t_data_tracer` VALUES ('20', '2', '3', '修改企业信息', '统一社会信用代码:\"1024lab\"<br/>详细地址:\"1024大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"卓大\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg\"<br/>联系人电话:\"18637925892\"<br/>企业名称:\"1024创新实验室\"<br/>邮箱:\"lab1024@163.com\"', '统一社会信用代码:\"1024lab\"<br/>详细地址:\"1024大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"卓大\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg\"<br/>联系人电话:\"18637925892\"<br/>企业名称:\"X创新实验室\"<br/>邮箱:\"lab1024@163.com\"', null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36', '2023-04-30 00:40:34', '2023-04-30 00:40:34');
INSERT INTO `t_data_tracer` VALUES ('21', '1', '3', '修改企业信息', '营业执照:\"public/common/852b7e19bef94af39c1a6156edf47cfb_20221022170332_jpg\"<br/>统一社会信用代码:\"1024lab_block\"<br/>详细地址:\"区块链大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"开云\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/f4a76fa720814949a610f05f6f9545bf_20221022170256_jpg\"<br/>联系人电话:\"18637925892\"<br/>企业名称:\"1024创新区块链实验室\"', '营业执照:\"public/common/852b7e19bef94af39c1a6156edf47cfb_20221022170332_jpg\"<br/>统一社会信用代码:\"1024lab_block\"<br/>详细地址:\"区块链大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"开云\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/f4a76fa720814949a610f05f6f9545bf_20221022170256_jpg\"<br/>联系人电话:\"17637925588\"<br/>企业名称:\"1024创新区块链实验室\"', null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36', '2023-05-04 20:55:14', '2023-05-04 20:55:14');
INSERT INTO `t_data_tracer` VALUES ('22', '1', '3', '修改企业信息', '营业执照:\"public/common/852b7e19bef94af39c1a6156edf47cfb_20221022170332_jpg\"<br/>统一社会信用代码:\"1024lab_block\"<br/>详细地址:\"区块链大楼\"<br/>区县名称:\"洛龙区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"洛阳市\"<br/>删除状态:false<br/>联系人:\"开云\"<br/>省份名称:\"河南省\"<br/>企业logo:\"public/common/f4a76fa720814949a610f05f6f9545bf_20221022170256_jpg\"<br/>联系人电话:\"17637925588\"<br/>企业名称:\"1024创新区块链实验室\"', '营业执照:\"public/common/852b7e19bef94af39c1a6156edf47cfb_20221022170332_jpg\"<br/>统一社会信用代码:\"1024lab_block\"<br/>详细地址:\"区块链大楼\"<br/>区县名称:\"东城区\"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:\"北京市\"<br/>删除状态:false<br/>联系人:\"开云\"<br/>省份名称:\"北京市\"<br/>企业logo:\"public/common/f4a76fa720814949a610f05f6f9545bf_20221022170256_jpg\"<br/>联系人电话:\"17637925588\"<br/>企业名称:\"1024创新区块链实验室\"', null, '1', '1', '管理员', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36', '2023-05-04 20:55:47', '2023-05-04 20:55:47');

-- ----------------------------
-- Table structure for t_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback` (
  `feedback_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `feedback_content` text COMMENT '反馈内容',
  `feedback_attachment` varchar(500) DEFAULT NULL COMMENT '反馈图片',
  `user_id` bigint(20) NOT NULL COMMENT '创建人id',
  `user_type` int(11) NOT NULL COMMENT '创建人用户类型',
  `user_name` varchar(50) NOT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='意见反馈';

-- ----------------------------
-- Records of t_feedback
-- ----------------------------
INSERT INTO `t_feedback` VALUES ('1', '希望增加微信公众号消息提醒功能', 'public/feedback/609fd595e8a9416b992c3a00e37cc8e0_20221022133322_jpg', '44', '1', '卓大', '2022-10-22 13:33:25', '2022-10-22 13:33:25');
INSERT INTO `t_feedback` VALUES ('2', '顶部菜单希望能尽快实现', '', '44', '1', '卓大', '2022-10-22 13:34:20', '2022-10-22 13:34:20');
INSERT INTO `t_feedback` VALUES ('3', 'app版本什么时候能推出？', '', '1', '1', '管理员', '2022-10-22 13:35:13', '2022-10-22 13:35:13');

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_status` int(11) DEFAULT NULL COMMENT '商品状态:[1:预约中,2:售卖中,3:售罄]',
  `category_id` int(11) NOT NULL COMMENT '商品类目',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名称',
  `place` varchar(255) DEFAULT NULL COMMENT '产地',
  `price` decimal(10,2) unsigned NOT NULL COMMENT '价格',
  `shelves_flag` tinyint(3) unsigned NOT NULL COMMENT '上架状态',
  `deleted_flag` tinyint(3) unsigned NOT NULL COMMENT '删除状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='商品';

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('1', '1', '353', 'Mote60', 'BEI_JING', '9999.00', '1', '0', null, '2022-10-21 19:57:49', '2021-09-01 22:25:30');
INSERT INTO `t_goods` VALUES ('7', '1', '352', 'iphone15 pro', 'LUO_YANG', '50000.00', '1', '0', '备注', '2022-10-21 19:58:07', '2022-09-15 14:17:11');
INSERT INTO `t_goods` VALUES ('8', '1', '352', 'iphone14', 'ZHENG_ZHOU', '150.00', '0', '0', '', '2022-10-21 19:12:49', '2022-10-21 19:00:11');

-- ----------------------------
-- Table structure for t_help_doc
-- ----------------------------
DROP TABLE IF EXISTS `t_help_doc`;
CREATE TABLE `t_help_doc` (
  `help_doc_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `help_doc_catalog_id` bigint(20) NOT NULL COMMENT '类型1公告 2动态',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content_text` text NOT NULL COMMENT '文本内容',
  `content_html` text NOT NULL COMMENT 'html内容',
  `attachment` varchar(1000) DEFAULT NULL COMMENT '附件',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `page_view_count` int(11) NOT NULL DEFAULT '0' COMMENT '页面浏览量，传说中的pv',
  `user_view_count` int(11) NOT NULL DEFAULT '0' COMMENT '用户浏览量，传说中的uv',
  `author` varchar(1000) DEFAULT NULL COMMENT '作者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`help_doc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助文档';

-- ----------------------------
-- Records of t_help_doc
-- ----------------------------

-- ----------------------------
-- Table structure for t_help_doc_catalog
-- ----------------------------
DROP TABLE IF EXISTS `t_help_doc_catalog`;
CREATE TABLE `t_help_doc_catalog` (
  `help_doc_catalog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '帮助文档目录',
  `name` varchar(1000) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `parent_id` bigint(20) NOT NULL COMMENT '父级id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`help_doc_catalog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助文档-目录';

-- ----------------------------
-- Records of t_help_doc_catalog
-- ----------------------------

-- ----------------------------
-- Table structure for t_help_doc_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_help_doc_relation`;
CREATE TABLE `t_help_doc_relation` (
  `relation_id` bigint(20) NOT NULL COMMENT '关联id',
  `relation_name` varchar(255) DEFAULT NULL COMMENT '关联名称',
  `help_doc_id` bigint(20) NOT NULL COMMENT '文档id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`relation_id`,`help_doc_id`),
  UNIQUE KEY `uni_menu_help_doc` (`relation_id`,`help_doc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助文档-关联表';

-- ----------------------------
-- Records of t_help_doc_relation
-- ----------------------------

-- ----------------------------
-- Table structure for t_help_doc_view_record
-- ----------------------------
DROP TABLE IF EXISTS `t_help_doc_view_record`;
CREATE TABLE `t_help_doc_view_record` (
  `help_doc_id` bigint(20) NOT NULL COMMENT '通知公告id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `page_view_count` int(11) DEFAULT '0' COMMENT '查看次数',
  `first_ip` varchar(255) DEFAULT NULL COMMENT '首次ip',
  `first_user_agent` varchar(1000) DEFAULT NULL COMMENT '首次用户设备等标识',
  `last_ip` varchar(255) DEFAULT NULL COMMENT '最后一次ip',
  `last_user_agent` varchar(1000) DEFAULT NULL COMMENT '最后一次用户设备等标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`help_doc_id`,`user_id`),
  UNIQUE KEY `uk_notice_employee` (`help_doc_id`,`user_id`) USING BTREE COMMENT '资讯员工'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助文档-查看记录';

-- ----------------------------
-- Records of t_help_doc_view_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `notice_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `notice_type_id` bigint(20) NOT NULL COMMENT '类型1公告 2动态',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `all_visible_flag` tinyint(1) NOT NULL COMMENT '是否全部可见',
  `scheduled_publish_flag` tinyint(1) NOT NULL COMMENT '是否定时发布',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  `content_text` text NOT NULL COMMENT '文本内容',
  `content_html` text NOT NULL COMMENT 'html内容',
  `attachment` varchar(1000) DEFAULT NULL COMMENT '附件',
  `page_view_count` int(11) NOT NULL DEFAULT '0' COMMENT '页面浏览量，传说中的pv',
  `user_view_count` int(11) NOT NULL DEFAULT '0' COMMENT '用户浏览量，传说中的uv',
  `source` varchar(1000) DEFAULT NULL COMMENT '来源',
  `author` varchar(1000) DEFAULT NULL COMMENT '作者',
  `document_number` varchar(1000) DEFAULT NULL COMMENT '文号，如：1024创新实验室发〔2022〕字第36号',
  `deleted_flag` tinyint(1) NOT NULL DEFAULT '0',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COMMENT='通知';

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('49', '1', 'Spring Boot 3.0.0 首个 RC 发布', '1', '0', '2022-10-22 14:27:34', 'Spring Boot 3.0.0 首个 RC 发布\nSpring Boot 3.0 首个 RC 已发布，此外还为两个分支发布了更新：2.7.5 & 2.6.13。\n3.0.0-RC1\n发布公告写道，此版本包含 135 项功能增强、文档改进、依赖升级和 Bugfix。\nSpring Boot 3.0 的开发工作始于实验性的 Spring Native，旨在为 GraalVM 原生镜像提供支持。在该版本中，开发者现在可以使用标准 Spring Boot Maven 或 Gradle 插件将 Spring Boot 应用程序转换为原生可执行文件，而无需任何特殊配置。\n此版本还在参考文档中添加新内容来解释 AOT 处理背后的概念以及如何开始生成第一个 GraalVM 原生镜像。\n除此之外，Spring Boot 3.0 还完成了迁移到 JakartaEE 9 的工作，以及将使用的 Java 版本升级到 Java 17。\n其他新特性：\n为 Spring Data JDBC 提供更灵活的自动配置为 Prometheus 示例提供自动配置增强 Log4j2 功能，包括配置文件支持和环境属性查找\n详情查看 Release Note。\nSpring Boot 2.7.5 和 2.6.13 的更新内容主要是修复错误，优化文档和升级依赖，详情查看 Release Note (2.7.5、2.6.13)。\n相关链接\nSpring Boot 的详细介绍：点击查看Spring Boot 的下载地址：点击下载', '<h1 style=\"text-indent: 0px; text-align: start;\"><a href=\"https://www.oschina.net/news/214401/spring-boot-3-0-0-rc1-released\" target=\"_blank\">Spring&nbsp;Boot&nbsp;3.0.0&nbsp;首个&nbsp;RC&nbsp;发布</a></h1><p>Spring&nbsp;Boot&nbsp;3.0 首个&nbsp;RC 已发布，此外还为两个分支发布了更新：2.7.5 & 2.6.13。</p><p>3.0.0-RC1</p><p>发布公告写道，此版本包含 135&nbsp;项功能增强、文档改进、依赖升级和&nbsp;Bugfix。</p><p>Spring&nbsp;Boot&nbsp;3.0&nbsp;的开发工作始于实验性的&nbsp;Spring&nbsp;Native，旨在为&nbsp;GraalVM&nbsp;原生镜像提供支持。在该版本中，开发者现在可以使用标准&nbsp;Spring&nbsp;Boot&nbsp;Maven&nbsp;或&nbsp;Gradle&nbsp;插件将&nbsp;Spring&nbsp;Boot&nbsp;应用程序转换为原生可执行文件，而无需任何特殊配置。</p><p>此版本还在参考文档中添加新内容来解释 AOT&nbsp;处理背后的概念以及如何开始生成第一个&nbsp;GraalVM&nbsp;原生镜像。</p><p>除此之外，Spring&nbsp;Boot&nbsp;3.0&nbsp;还完成了迁移到 JakartaEE&nbsp;9&nbsp;的工作，以及将使用的&nbsp;Java&nbsp;版本升级到&nbsp;Java&nbsp;17。</p><p>其他新特性：</p><p>为&nbsp;Spring&nbsp;Data&nbsp;JDBC&nbsp;提供更灵活的自动配置为&nbsp;Prometheus&nbsp;示例提供自动配置增强&nbsp;Log4j2&nbsp;功能，包括配置文件支持和环境属性查找</p><p>详情查看&nbsp;Release&nbsp;Note。</p><p>Spring&nbsp;Boot&nbsp;2.7.5&nbsp;和&nbsp;2.6.13&nbsp;的更新内容主要是修复错误，优化文档和升级依赖，详情查看&nbsp;Release&nbsp;Note&nbsp;(2.7.5、2.6.13)。</p><p>相关链接</p><p>Spring&nbsp;Boot&nbsp;的详细介绍：点击查看Spring&nbsp;Boot&nbsp;的下载地址：点击下载</p>', '', '0', '0', '开源中国', '卓大', null, '0', '1', '2022-10-22 14:27:33', '2022-10-22 14:27:33');
INSERT INTO `t_notice` VALUES ('50', '1', 'Oracle 推出 JDK 8 的直接替代品', '1', '0', '2022-10-22 14:29:56', 'Oracle 推出 JDK 8 的直接替代品\n来源: OSCHINA\n编辑: 白开水不加糖\n2022-10-20 08:14:29\n 0\n为了向传统的 Java 8 服务器工作负载提供 Java 17 级别的性能，Oracle 宣布推出 Java SE Subscription Enterprise Performance Pack (Enterprise Performance Pack)。并声称这是 JDK 8 的直接替代品，现已在 MyOracleSupport 上面向所有 Java SE 订阅客户和 Oracle 云基础设施 (OCI) 用户免费提供。\n“Enterprise Performance Pack 为 JDK 8 用户提供了在 JDK 8 和 JDK 17 发布之间的 7 年时间里，为 Java 带来的重大内存管理和性能改进。这些改进包括：现代垃圾回收算法、紧凑字符串、增强的可观察性和数十种其他优化。”\nJava 8 发布于 2014 年，和 Java 17 一样都是长期支持 (LTS) 版本；尽管发布距今已有近九年的历史，但仍被很多开发人员和组织所广泛应用。New Relic 发布的一份 “2022 年 Java 生态系统状况报告” 数据表明，Java 8 仍被 46.45% 的 Java 应用程序在生产中使用。\n根据介绍，Enterprise Performance Pack 在 Intel 和基于 Arm 的系统（如 Ampere Altra）上支持 headless Linux 64 位工作负载。\nOracle 方面称，使用 Enterprise Performance Pack 的客户将可以立即看到以或接近内存或 CPU 容量运行的 JDK 8 工作负载的好处。在 Oracle 自己的产品和云服务进行的测试表明，高负载应用程序的内存和性能都提高了大约 40%。即使没有接近容量运行的 JDK 8 应用程序，也可以会看到高达 5% 的性能提升。\n虽然 Enterprise Performance Pack 中包含的许多改进可以通过默认选项获得，但 Oracle 建议用户还是自己研究文档，以最大限度地提高性能并最大限度地降低内存使用率。例如，通过启用可扩展的低延迟 ZGC 垃圾收集器来提高应用程序响应能力，需要通过 -XX:+UseZGC 选项。', '<h3>Oracle&nbsp;推出&nbsp;JDK&nbsp;8&nbsp;的直接替代品</h3><p>来源:&nbsp;OSCHINA</p><p>编辑: 白开水不加糖</p><p>2022-10-20&nbsp;08:14:29</p><p> 0</p><p>为了向传统的&nbsp;Java&nbsp;8&nbsp;服务器工作负载提供&nbsp;Java&nbsp;17&nbsp;级别的性能，Oracle 宣布推出&nbsp;Java&nbsp;SE&nbsp;Subscription&nbsp;Enterprise&nbsp;Performance&nbsp;Pack&nbsp;(Enterprise&nbsp;Performance&nbsp;Pack)。并声称这是 JDK&nbsp;8&nbsp;的直接替代品，现已在 MyOracleSupport 上面向所有&nbsp;Java&nbsp;SE&nbsp;订阅客户和&nbsp;Oracle&nbsp;云基础设施&nbsp;(OCI)&nbsp;用户免费提供。</p><p>“Enterprise&nbsp;Performance&nbsp;Pack&nbsp;为&nbsp;JDK&nbsp;8&nbsp;用户提供了在&nbsp;JDK&nbsp;8&nbsp;和&nbsp;JDK&nbsp;17&nbsp;发布之间的&nbsp;7&nbsp;年时间里，为&nbsp;Java&nbsp;带来的重大内存管理和性能改进。这些改进包括：现代垃圾回收算法、紧凑字符串、增强的可观察性和数十种其他优化。”</p><p>Java&nbsp;8&nbsp;发布于&nbsp;2014&nbsp;年，和&nbsp;Java&nbsp;17&nbsp;一样都是长期支持&nbsp;(LTS)&nbsp;版本；尽管发布距今已有近九年的历史，但仍被很多开发人员和组织所广泛应用。New&nbsp;Relic&nbsp;发布的一份 “2022&nbsp;年&nbsp;Java&nbsp;生态系统状况报告”&nbsp;数据表明，Java&nbsp;8&nbsp;仍被&nbsp;46.45%&nbsp;的&nbsp;Java&nbsp;应用程序在生产中使用。</p><p>根据介绍，Enterprise&nbsp;Performance&nbsp;Pack&nbsp;在&nbsp;Intel&nbsp;和基于&nbsp;Arm&nbsp;的系统（如&nbsp;Ampere&nbsp;Altra）上支持 headless&nbsp;Linux&nbsp;64&nbsp;位工作负载。</p><p>Oracle 方面称，使用&nbsp;Enterprise&nbsp;Performance&nbsp;Pack&nbsp;的客户将可以立即看到以或接近内存或&nbsp;CPU&nbsp;容量运行的&nbsp;JDK&nbsp;8&nbsp;工作负载的好处。在&nbsp;Oracle&nbsp;自己的产品和云服务进行的测试表明，高负载应用程序的内存和性能都提高了大约&nbsp;40%。即使没有接近容量运行的&nbsp;JDK&nbsp;8&nbsp;应用程序，也可以会看到高达&nbsp;5%&nbsp;的性能提升。</p><p>虽然&nbsp;Enterprise&nbsp;Performance&nbsp;Pack&nbsp;中包含的许多改进可以通过默认选项获得，但 Oracle 建议用户还是自己研究文档，以最大限度地提高性能并最大限度地降低内存使用率。例如，通过启用可扩展的低延迟&nbsp;ZGC&nbsp;垃圾收集器来提高应用程序响应能力，需要通过&nbsp;-XX:+UseZGC&nbsp;选项。</p>', '', '0', '0', 'OSChina', '卓大', null, '0', '1', '2022-10-22 14:29:56', '2022-10-22 14:29:56');
INSERT INTO `t_notice` VALUES ('51', '1', 'Spring Framework 6.0.0 RC2 发布', '1', '0', '2022-10-22 14:30:46', 'Spring Framework 6.0.0 RC2 发布\nSpring Framework 6.0.0 发布了第二个 RC 版本。\n新特性\n确保可以在构建时评估 classpath 检查 #29352为 JPA 持久化回调引入 Register 反射提示 #29348检查 @RegisterReflectionForBinding 是否至少指定一个类 #29346为 AOT 引擎设置引入 builder API #29341支持检测正在进行的 AOT 处理 #29340重新组织 HTTP Observation 类型 #29334支持在没有 java.beans.Introspector 的前提下，执行基本属性判断 #29320为BindingReflectionHintsRegistrar 添加 Kotlin 数据类组件支持 #29316将 HttpServiceFactory 和 RSocketServiceProxyFactory 切换到 builder 模型，以便优先进行可编程配置 #29296引入基于 GraalVM FieldValueTransformer API 的 PreComputeFieldFeature#29081在 TestContext 框架中引入 SPI 来处理 ApplicationContext 故障 #28826SimpleEvaluationContext 支持禁用 array 分配 #28808DateTimeFormatterRegistrar 支持默认回退到 ISO 解析 #26985\nSpring Framework 6.0 作为重大更新，要求使用 Java 17 或更高版本，并且已迁移到 Jakarta EE 9+（在 jakarta 命名空间中取代了以前基于 javax 的 EE API），以及对其他基础设施的修改。基于这些变化，Spring Framework 6.0 支持最新 Web 容器，如 Tomcat 10 / Jetty 11，以及最新的持久性框架 Hibernate ORM 6.1。这些特性仅可用于 Servlet API 和 JPA 的 jakarta 命名空间变体。\n值得一提的是，开发者可通过此版本在基于 Spring 的应用中体验 “虚拟线程”（JDK 19 中的预览版 “Project Loom”），查看此文章了解更多细节。现在提供了自定义选项来插入基于虚拟线程的 Executor 实现，目标是在 Project Loom 正式可用时提供 “一等公民” 的配置选项。\n除了上述的变化，Spring Framework 6.0 还包含许多其他改进和特性，例如：\n提供基于 @HttpExchange 服务接口的 HTTP 接口客户端对 RFC 7807 问题详细信息的支持Spring HTTP 客户端提供基于 Micrometer 的可观察性……\n详情查看 Release Note。\n按照发布计划，Spring Framework 6.0 将于 11 月正式 GA。', '<h1 style=\"text-indent: 0px; text-align: start;\"><a href=\"https://www.oschina.net/news/214472/spring-framework-6-0-0-rc2-released\" target=\"_blank\">Spring&nbsp;Framework&nbsp;6.0.0&nbsp;RC2&nbsp;发布</a></h1><p style=\"text-indent: 0px; text-align: left;\">Spring&nbsp;Framework&nbsp;6.0.0&nbsp;发布了<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fspring.io%2Fblog%2F2022%2F10%2F20%2Fspring-framework-6-0-0-rc2-available-now\" target=\"_blank\">第二个&nbsp;RC&nbsp;版本</a>。</p><p style=\"text-indent: 0px; text-align: left;\"><strong>新特性</strong></p><ul style=\"text-indent: 0px; text-align: left;\"><li>确保可以在构建时评估&nbsp;classpath&nbsp;检查&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29352\" target=\"_blank\">#29352</a></li><li>为&nbsp;JPA&nbsp;持久化回调引入&nbsp;Register&nbsp;反射提示&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29348\" target=\"_blank\">#29348</a></li><li>检查&nbsp;<span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>@RegisterReflectionForBinding</code></span>&nbsp;是否至少指定一个类&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29346\" target=\"_blank\">#29346</a></li><li>为&nbsp;AOT&nbsp;引擎设置引入&nbsp;builder&nbsp;API&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29341\" target=\"_blank\">#29341</a></li><li>支持检测正在进行的&nbsp;AOT&nbsp;处理&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29340\" target=\"_blank\">#29340</a></li><li>重新组织&nbsp;HTTP&nbsp;Observation&nbsp;类型&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29334\" target=\"_blank\">#29334</a></li><li>支持在没有&nbsp;java.beans.Introspector&nbsp;的前提下，执行基本属性判断&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29320\" target=\"_blank\">#29320</a></li><li>为<span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>BindingReflectionHintsRegistrar</code></span>&nbsp;添加&nbsp;Kotlin&nbsp;数据类组件支持&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29316\" target=\"_blank\">#29316</a></li><li>将&nbsp;HttpServiceFactory&nbsp;和&nbsp;RSocketServiceProxyFactory&nbsp;切换到&nbsp;builder&nbsp;模型，以便优先进行可编程配置&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29296\" target=\"_blank\">#29296</a></li><li>引入基于&nbsp;GraalVM&nbsp;<span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>FieldValueTransformer</code></span>&nbsp;API&nbsp;的&nbsp;<span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>PreComputeFieldFeature</code></span><a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F29081\" target=\"_blank\">#29081</a></li><li>在&nbsp;TestContext&nbsp;框架中引入&nbsp;SPI&nbsp;来处理&nbsp;ApplicationContext&nbsp;故障&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F28826\" target=\"_blank\">#28826</a></li><li>SimpleEvaluationContext&nbsp;支持禁用&nbsp;array&nbsp;分配&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F28808\" target=\"_blank\">#28808</a></li><li>DateTimeFormatterRegistrar&nbsp;支持默认回退到&nbsp;ISO&nbsp;解析&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Fissues%2F26985\" target=\"_blank\">#26985</a></li></ul><p style=\"text-indent: 0px; text-align: left;\"><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">Spring&nbsp;Framework&nbsp;6.0&nbsp;作为重大更新，要求</span><span style=\"color: rgb(51, 51, 51);\"><strong>使用&nbsp;Java&nbsp;17&nbsp;或更高版本</strong></span><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">，并且已迁移到&nbsp;Jakarta&nbsp;EE&nbsp;9+（在&nbsp;</span><span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>jakarta</code></span><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">&nbsp;命名空间中取代了以前基于&nbsp;</span><span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>javax</code></span><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">&nbsp;的&nbsp;EE&nbsp;API），以及对其他基础设施的修改。基于这些变化，Spring&nbsp;Framework&nbsp;6.0&nbsp;支持最新&nbsp;Web&nbsp;容器，如&nbsp;</span><a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Ftomcat.apache.org%2Fwhichversion.html\" target=\"_blank\">Tomcat&nbsp;10</a><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">&nbsp;/&nbsp;</span><a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fwww.eclipse.org%2Fjetty%2Fdownload.php\" target=\"_blank\">Jetty&nbsp;11</a><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">，以及最新的持久性框架&nbsp;</span><a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fhibernate.org%2Form%2Freleases%2F6.1%2F\" target=\"_blank\">Hibernate&nbsp;ORM&nbsp;6.1</a><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">。这些特性仅可用于&nbsp;Servlet&nbsp;API&nbsp;和&nbsp;JPA&nbsp;的&nbsp;jakarta&nbsp;命名空间变体。</span></p><p style=\"text-indent: 0px; text-align: left;\">值得一提的是，开发者可通过此版本在基于&nbsp;Spring&nbsp;的应用中体验&nbsp;“虚拟线程”（JDK&nbsp;19&nbsp;中的预览版&nbsp;“Project&nbsp;Loom”），<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fspring.io%2Fblog%2F2022%2F10%2F11%2Fembracing-virtual-threads\" target=\"_blank\">查看此文章</a>了解更多细节。现在提供了自定义选项来插入基于虚拟线程的&nbsp;<span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>Executor</code></span>&nbsp;实现，目标是在&nbsp;Project&nbsp;Loom&nbsp;正式可用时提供&nbsp;“一等公民”&nbsp;的配置选项。</p><p style=\"text-indent: 0px; text-align: left;\">除了上述的变化，Spring&nbsp;Framework&nbsp;6.0&nbsp;还包含许多其他改进和特性，例如：</p><ul style=\"text-indent: 0px; text-align: left;\"><li>提供基于&nbsp;<span style=\"color: rgb(51, 51, 51); font-size: 13px;\"><code>@HttpExchange</code></span>&nbsp;服务接口的&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fdocs.spring.io%2Fspring-framework%2Fdocs%2F6.0.0-RC1%2Freference%2Fhtml%2Fintegration.html%23rest-http-interface\" target=\"_blank\">HTTP&nbsp;接口客户端</a></li><li>对&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fdocs.spring.io%2Fspring-framework%2Fdocs%2F6.0.0-RC1%2Freference%2Fhtml%2Fweb.html%23mvc-ann-rest-exceptions\" target=\"_blank\">RFC&nbsp;7807&nbsp;问题详细信息</a>的支持</li><li>Spring&nbsp;HTTP&nbsp;客户端提供基于&nbsp;Micrometer&nbsp;的可观察性</li><li>……</li></ul><p style=\"text-indent: 0px; text-align: left;\"><a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-framework%2Freleases%2Ftag%2Fv6.0.0-RC2\" target=\"_blank\">详情查看&nbsp;Release&nbsp;Note</a>。</p><p style=\"text-indent: 0px; text-align: left;\">按照发布计划，Spring&nbsp;Framework&nbsp;6.0&nbsp;将于&nbsp;11&nbsp;月正式&nbsp;GA。</p>', '', '0', '0', 'CSDN', '罗伊', null, '0', '1', '2022-10-22 14:30:45', '2022-10-22 14:30:45');
INSERT INTO `t_notice` VALUES ('52', '1', 'Windows Terminal 正式成为 Windows 11 默认终端', '1', '0', '2022-10-22 14:33:03', '今年 7 月 ，微软在 Windows 11 的 Beta 版本测试了将系统默认终端设置为 Windows Terminal 。如今该设置已登录稳定版本，从 Windows 11 22H2 版本开始，Windows Terminal 将正式成为 Windows 11 的默认设置。\n默认终端是在打开命令行应用程序时默认启动的终端模拟器。从 Windows 诞生之日起，其默认终端一直是 Windows 控制台主机 conhost.exe。此次更新则意味着，以后 Windows 11 的所有命令行应用程序都将在 Windows Terminal 中自动打开。\nWindows Terminal 拥有非常多现代化的功能，毕竟它很新（ 2019 年 5 月在 Microsoft Build 上首次发布），吸取了很多现代终端的灵感。它支持多选项卡和窗格、命令面板等现代化的 UI 和操作方式，以及大量的自定义选项，比如目录、配置文件图标、自定义背景图像、配色方案、字体和透明度。\n当然，如果不想用 Windows Terminal，用户也可以在 Windows 设置中的 隐私和安全 > 开发人员页面和 Windows 终端设置 中调整默认终端设置，（此更新使用 “让 Windows 决定” 作为默认选择，即默认采用 Windows Terminal） 。\n此外，如果在更新之前就已设置其他默认终端，此次更新不会覆盖你的偏好。\n关于 Windows 11 默认终端的更多详情可查看微软博客。', '<p style=\"text-indent: 0px; text-align: left;\">今年&nbsp;7&nbsp;月&nbsp;，微软在&nbsp;Windows&nbsp;11&nbsp;的&nbsp;Beta&nbsp;版本<a href=\"https://www.oschina.net/news/204429/wt-default-terminal-in-win11-beta-channel\" target=\"\">测试</a>了将系统默认终端设置为&nbsp;Windows&nbsp;Terminal&nbsp;。如今该设置已登录稳定版本，从&nbsp;Windows&nbsp;11&nbsp;22H2&nbsp;版本开始，Windows&nbsp;Terminal&nbsp;将<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fdevblogs.microsoft.com%2Fcommandline%2Fwindows-terminal-is-now-the-default-in-windows-11%2F\" target=\"_blank\">正式成为</a>&nbsp;Windows&nbsp;11&nbsp;的默认设置。</p><p style=\"text-indent: 0px; text-align: left;\">默认终端是在打开命令行应用程序时默认启动的终端模拟器。从&nbsp;Windows&nbsp;诞生之日起，其默认终端一直是&nbsp;Windows&nbsp;控制台主机&nbsp;conhost.exe。此次更新则意味着，以后&nbsp;Windows&nbsp;11&nbsp;的所有命令行应用程序都将在&nbsp;Windows&nbsp;Terminal&nbsp;中自动打开。</p><p style=\"text-indent: 0px; text-align: left;\">Windows&nbsp;Terminal&nbsp;拥有非常多现代化的功能，毕竟它<span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\">很新（&nbsp;2019&nbsp;年&nbsp;5&nbsp;月在&nbsp;Microsoft&nbsp;Build&nbsp;上首次发布），吸取了很多现代终端的灵感。它支持多</span>选项卡和窗格、命令面板等现代化的&nbsp;UI&nbsp;和操作方式，以及大量的自定义选项，比如目录、配置文件图标、自定义背景图像、配色方案、字体和透明度。</p><p style=\"text-indent: 0px; text-align: left;\">当然，如果不想用&nbsp;Windows&nbsp;Terminal，用户也可以在&nbsp;Windows&nbsp;设置中的&nbsp;<em>隐私和安全&nbsp;&gt;&nbsp;开发人员页面和&nbsp;Windows&nbsp;终端设置&nbsp;</em>中调整默认终端设置，（此更新使用&nbsp;“让&nbsp;Windows&nbsp;决定”&nbsp;作为默认选择，即默认采用&nbsp;Windows&nbsp;Terminal）&nbsp;。</p><p style=\"text-indent: 0px; text-align: left;\">此外，如果在更新之前就已设置其他默认终端，此次更新<strong>不会覆盖</strong>你的偏好。</p><p style=\"text-indent: 0px; text-align: left;\">关于&nbsp;Windows&nbsp;11&nbsp;默认终端的更多详情可查看<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fdevblogs.microsoft.com%2Fcommandline%2Fwindows-terminal-is-now-the-default-in-windows-11%2F\" target=\"_blank\">微软博客</a>。</p>', '', '0', '0', '开源中国', '善逸', null, '0', '1', '2022-10-22 14:33:03', '2022-10-22 14:33:03');
INSERT INTO `t_notice` VALUES ('53', '1', 'TypeScript 诞生 10 周年', '1', '0', '2022-10-22 14:34:56', 'TypeScript 已经诞生 10 年了。10 年前 ——2012 年 10 月 1 日，TypeScript 首次公开亮相。当时主导 TypeScript 开发的 Anders Hejlsberg 这样描述 TypeScript：\n它是 JavaScript 的类型化超集，可被编译成常用的 JavaScript。TypeScript 还可以通过启用丰富的工具体验来极大地帮助提升生产力，与此同时开发者保持不变维护现有的代码，并继续使用喜爱的 JavaScript 库。TypeScript is a typed superset of JavaScript that compiles to idiomatic (normal) JavaScript, can dramatically improve your productivity by enabling rich tooling experiences, all while maintaining your existing code and continuing to use the same JavaScript libraries you already love.\n微软在博客中回顾了 TypeScript 刚亮相时受到的评价，大多数人对它都是持怀疑态度，毕竟这对于许多 JavaScript 开发者来说，试图将静态类型引入 JavaScript 是一个笑话 —— 或是邪恶的阴谋。反对者则直言这是十分愚蠢的想法，他们认为当时已存在可以编译为 JavaScript 的强类型语言，例如 C#、Java 和 C++。他们还吐槽主导 TypeScript 开发的 Anders Hejlsberg 对静态类型有 “迷之执着”。\n当时微软意识到 JavaScript 未来将会被应用到无数场景，而且他们公司内部团队在处理复杂的 JavaScript 代码库时面临着巨大的挑战，所以他们觉得有必要创造强大的工具来帮助编写 JavaScript—— 尤其是针对大型 JavaScript 项目。基于此需求，TypeScript 也确定了自己的定位和特性，它是 JavaScript 的超集，将类型检查和静态分析、显式接口和最佳实践结合到单一语言和编译器中。通过在 JavaScript 上构建，TypeScript 能够更接近目标运行时，同时仅添加支持大型应用程序和大型团队所需的语法糖。\n团队还坚持 TypeScript 要能够与现有的 JavaScript 无缝交互，与 JavaScript 共同进化，并且看上去也和 JavaScript 类似。\nTypeScript 诞生之初的部分设计目标：\n不会对已有的程序增加运行时开销与当前和未来的 ECMAScript 提案保持一致保留所有 JavaScript 代码的运行时行为避免添加表达式类型的语法 (expression-level syntax)使用一致、完全可擦除的结构化类型系统……\n这些目标指导着 TypeScript 的发展方向：关注类型系统，成为 JavaScript 的类型检查器，只添加类型检查所需的语法，避免添加新的运行时语法和行为。\n微软提到，TypeScript 拥有如今的繁荣生态离不开一个重要属性：开源。TypeScript 一开始就是免费且开源 —— 语言规范和编译器都是开源项目，并且以真正开放的方式来运作。事实上，微软当时对外展现出的姿态并不是现在的 “拥抱开源”，所以他们内部并没真正认识到 TypeScript 的开源是如何帮助它走向成功。因此有人认为，TypeScript 在很大程度上引导微软开始更多地转向开源。\n现在，TypeScript 仍在积极发展和迭代改进，并被全球数百万开发者使用。在诸多编程语言排名、指数或开发者调查中，TypeScript 一直位居前列，也是最受欢迎和最常用的编程语言。', '<p style=\"text-indent: 0px; text-align: start;\">TypeScript&nbsp;已经诞生&nbsp;10&nbsp;年了。10&nbsp;年前&nbsp;——2012&nbsp;年&nbsp;10&nbsp;月&nbsp;1&nbsp;日，TypeScript&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fweb.archive.org%2Fweb%2F20121003001910%2Fhttps%3A%2F%2Fblogs.msdn.com%2Fb%2Fsomasegar%2Farchive%2F2012%2F10%2F01%2Ftypescript-javascript-development-at-application-scale.aspx\" target=\"_blank\"><strong>首次公开亮相</strong></a>。当时主导&nbsp;TypeScript&nbsp;开发的&nbsp;Anders&nbsp;Hejlsberg&nbsp;这样描述&nbsp;TypeScript：</p><blockquote style=\"text-indent: 0px; text-align: left;\">它是&nbsp;JavaScript&nbsp;的类型化超集，可被编译成常用的&nbsp;JavaScript。TypeScript&nbsp;还可以通过启用丰富的工具体验来极大地帮助提升生产力，与此同时开发者保持不变维护现有的代码，并继续使用喜爱的&nbsp;JavaScript&nbsp;库。TypeScript&nbsp;is&nbsp;a&nbsp;typed&nbsp;superset&nbsp;of&nbsp;JavaScript&nbsp;that&nbsp;compiles&nbsp;to&nbsp;idiomatic&nbsp;(normal)&nbsp;JavaScript,&nbsp;can&nbsp;dramatically&nbsp;improve&nbsp;your&nbsp;productivity&nbsp;by&nbsp;enabling&nbsp;rich&nbsp;tooling&nbsp;experiences,&nbsp;all&nbsp;while&nbsp;maintaining&nbsp;your&nbsp;existing&nbsp;code&nbsp;and&nbsp;continuing&nbsp;to&nbsp;use&nbsp;the&nbsp;same&nbsp;JavaScript&nbsp;libraries&nbsp;you&nbsp;already&nbsp;love.</blockquote><p style=\"text-indent: 0px; text-align: left;\">微软在博客中回顾了&nbsp;TypeScript&nbsp;刚亮相时受到的评价，大多数人对它都是持怀疑态度，毕竟这对于许多&nbsp;JavaScript&nbsp;开发者来说，试图将静态类型引入&nbsp;JavaScript&nbsp;是一个笑话&nbsp;——&nbsp;或是邪恶的阴谋。反对者则直言这是十分愚蠢的想法，他们认为当时已存在可以编译为&nbsp;JavaScript&nbsp;的强类型语言，例如&nbsp;C#、Java&nbsp;和&nbsp;C++。他们还吐槽主导&nbsp;TypeScript&nbsp;开发的&nbsp;Anders&nbsp;Hejlsberg&nbsp;对静态类型有&nbsp;“迷之执着”。</p><p style=\"text-indent: 0px; text-align: start;\">当时微软意识到&nbsp;JavaScript&nbsp;未来将会被应用到无数场景，而且他们公司内部团队在处理复杂的&nbsp;JavaScript&nbsp;代码库时面临着巨大的挑战，所以他们觉得有必要创造强大的工具来帮助编写&nbsp;JavaScript——&nbsp;尤其是针对大型&nbsp;JavaScript&nbsp;项目。基于此需求，TypeScript&nbsp;也确定了自己的定位和特性，它是&nbsp;JavaScript&nbsp;的超集，将类型检查和静态分析、显式接口和最佳实践结合到单一语言和编译器中。通过在&nbsp;JavaScript&nbsp;上构建，TypeScript&nbsp;能够更接近目标运行时，同时仅添加支持大型应用程序和大型团队所需的语法糖。</p><p style=\"text-indent: 0px; text-align: start;\">团队还坚持&nbsp;TypeScript&nbsp;要能够与现有的&nbsp;JavaScript&nbsp;无缝交互，与&nbsp;JavaScript&nbsp;共同进化，并且看上去也和&nbsp;JavaScript&nbsp;类似。</p><p style=\"text-indent: 0px; text-align: start;\">TypeScript&nbsp;诞生之初的部分<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fmicrosoft%2FTypeScript%2Fwiki%2FTypeScript-Design-Goals%2F53ffa9b1802cd8e18dfe4b2cd4e9ef5d4182df10\" target=\"_blank\"><strong>设计目标</strong></a>：</p><ul style=\"text-indent: 0px; text-align: left;\"><li>不会对已有的程序增加运行时开销</li><li>与当前和未来的&nbsp;ECMAScript&nbsp;提案保持一致</li><li>保留所有&nbsp;JavaScript&nbsp;代码的运行时行为</li><li>避免添加表达式类型的语法&nbsp;(expression-level&nbsp;syntax)</li><li>使用一致、完全可擦除的结构化类型系统</li><li>……</li></ul><p style=\"text-indent: 0px; text-align: start;\">这些目标指导着&nbsp;TypeScript&nbsp;的发展方向：关注类型系统，成为&nbsp;JavaScript&nbsp;的类型检查器，只添加类型检查所需的语法，避免添加新的运行时语法和行为。</p><p style=\"text-indent: 0px; text-align: start;\">微软提到，TypeScript&nbsp;拥有如今的繁荣生态离不开一个重要属性：<strong>开源</strong>。TypeScript&nbsp;一开始就是免费且开源&nbsp;——<span style=\"color: rgb(51, 51, 51);\">&nbsp;语言规范和编译器都是开源项目，</span>并且以真正开放的方式来运作。事实上，微软当时对外展现出的姿态并不是现在的&nbsp;“拥抱开源”，所以他们内部并没真正认识到&nbsp;TypeScript&nbsp;的开源是如何帮助它走向成功。因此有人认为，TypeScript&nbsp;在很大程度上引导微软开始更多地转向开源。</p><p style=\"text-indent: 0px; text-align: start;\">现在，TypeScript&nbsp;仍在积极发展和迭代改进，并被全球数百万开发者使用。在诸多编程语言排名、指数或开发者调查中，TypeScript&nbsp;一直位居前列，也是最受欢迎和最常用的编程语言。</p>', '', '0', '0', '开源中国', '开云', null, '0', '1', '2022-10-22 14:34:56', '2022-10-22 14:34:56');
INSERT INTO `t_notice` VALUES ('54', '1', 'JetBrains Fleet 公测，下一代 IDE', '1', '0', '2022-10-22 14:36:10', 'JetBrains 宣布首次公共预览 Fleet，所有人都可以使用。Fleet 是由 JetBrains 打造的下一代 IDE，于 2021 年首次正式推出。它是一个新的分布式多语言编辑器和 IDE，基于 JetBrains 在后端的 IntelliJ 平台，采用了全新的用户界面和分布式架构从头开始构建。\n下载 Fleet：https://www.jetbrains.com.cn/fleet/download/\n\n公告表示，自从最初宣布 Fleet 以来，有超过 137,000 人报名参加私人预览；官方最初之所以决定从封闭式预览开始，是为了能够以渐进的方式处理反馈。现如今，JetBrains Fleet 仍处于起步阶段，还有大量的工作要做。其向公众开放预览的原因有两个方面：“首先，我们认为让所有注册者再等下去是不对的，但单独邀请这么多人对我们来说也缺乏意义。面向公众开放预览对我们来说更容易。第二，也是最重要的，我们一直是一家以开放态度打造产品的公司。我们不希望 Fleet 在这方面有任何不同。”\nJetBrains 方面提供了一个图表，以显示 Fleet 目前提供支持的语言和技术，以及每个技术的状态。但值得注意的是，Fleet 仍处于早期阶段，有些事情可能无法按预期工作；所以即使有些东西被列为受支持的，也有可能存在问题。\n同时 JetBrains 也强调称，他们并不打算取代其现有的 IDE。\n因此，请不要期望在 Fleet 中看到与我们的 IDE（如 IntelliJ IDEA）完全相同的功能。尽管我们会继续开发 Fleet，我们 IDE 的所有功能也不会出现在其中。Fleet 是我们为开发者提供不同用户体验的一个机会。话虽如此，我们确实希望听到你认为 Fleet 还缺少什么功能的反馈，例如特定的重构选项、工具集成等。我们现有的 IDE 将继续发展。我们对其有很多计划，包括性能改进、新的用户界面、远程开发等等。最后，Fleet 还在底层采用了我们现有工具的智慧，所以这些工具都不会消失。\nJetBrains 透露，在未来几个月他们将致力于稳定 Fleet，并尽可能地解决得到的反馈。同时，将在以下领域开展工作：\n为插件作者提供 API 支持和 SDK–鉴于 Fleet 有一个分布式架构，我们需要努力为插件作者简化工作。 虽然我们保证会为扩展 Fleet 提供一个平台，但也请求大家在这方面多一点耐心。 性能 – 我们希望 Fleet 不仅在内存占用方面，而且在响应时间方面都能表现出色。 有很多地方我们仍然可以提高性能，我们将在这些方面努力。 主题和键盘地图 – 我们知道许多开发者已经习惯了他们现有的编辑器和 IDE，当他们转移到新的 IDE 时，往往会想念他们以前的键盘绑定和主题。 我们将致力于增加对更多主题和键盘映射的支持。 我们当然也会致力于 Vim 的模拟。\n更多详情可查看官方博客。', '<p style=\"text-indent: 0px; text-align: left;\">JetBrains&nbsp;<a href=\"https://my.oschina.net/u/5494143/blog/5584325\" target=\"\">宣布</a>首次公共预览&nbsp;<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fwww.jetbrains.com.cn%2Ffleet%2F\" target=\"_blank\">Fleet</a>，所有人都可以使用。Fleet&nbsp;是由&nbsp;JetBrains&nbsp;打造的下一代&nbsp;IDE，于&nbsp;2021&nbsp;年首次正式<a href=\"https://my.oschina.net/u/5494143/blog/5332934\" target=\"\">推出</a>。它是一个新的分布式多语言编辑器和&nbsp;IDE，基于&nbsp;JetBrains&nbsp;在后端的&nbsp;IntelliJ&nbsp;平台，采用了全新的用户界面和分布式架构从头开始构建。</p><p style=\"text-indent: 0px; text-align: left;\"><strong>下载&nbsp;Fleet：</strong><a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fwww.jetbrains.com.cn%2Ffleet%2Fdownload%2F\" target=\"_blank\">https://www.jetbrains.com.cn/fleet/download/</a></p><p style=\"text-indent: 0px; text-align: left;\"><br></p><p style=\"text-indent: 0px; text-align: left;\">公告表示，自从最初宣布&nbsp;Fleet&nbsp;以来，有超过&nbsp;137,000&nbsp;人报名参加私人预览；官方最初之所以决定从封闭式预览开始，是为了能够以渐进的方式处理反馈。现如今，JetBrains&nbsp;Fleet&nbsp;仍处于起步阶段，还有大量的工作要做。其向公众开放预览的原因有两个方面：“首先，我们认为让所有注册者再等下去是不对的，但单独邀请这么多人对我们来说也缺乏意义。面向公众开放预览对我们来说更容易。第二，也是最重要的，我们一直是一家以开放态度打造产品的公司。我们不希望&nbsp;Fleet&nbsp;在这方面有任何不同。”</p><p style=\"text-indent: 0px; text-align: left;\">JetBrains&nbsp;方面提供了一个<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fjb.gg%2Ffleet-feature-matrix\" target=\"_blank\">图表</a>，以显示&nbsp;Fleet&nbsp;目前提供支持的语言和技术，以及每个技术的状态。但值得注意的是，Fleet&nbsp;仍处于早期阶段，有些事情可能无法按预期工作；所以即使有些东西被列为受支持的，也有可能存在问题。</p><p style=\"text-indent: 0px; text-align: left;\">同时&nbsp;JetBrains&nbsp;也强调称，他们并不打算取代其现有的&nbsp;IDE。</p><blockquote style=\"text-indent: 0px; text-align: left;\">因此，请不要期望在&nbsp;Fleet&nbsp;中看到与我们的&nbsp;IDE（如&nbsp;IntelliJ&nbsp;IDEA）完全相同的功能。尽管我们会继续开发&nbsp;Fleet，我们&nbsp;IDE&nbsp;的所有功能也不会出现在其中。Fleet&nbsp;是我们为开发者提供不同用户体验的一个机会。话虽如此，我们确实希望听到你认为&nbsp;Fleet&nbsp;还缺少什么功能的反馈，例如特定的重构选项、工具集成等。我们现有的&nbsp;IDE&nbsp;将继续发展。我们对其有很多计划，包括性能改进、新的用户界面、远程开发等等。最后，Fleet&nbsp;还在底层采用了我们现有工具的智慧，所以这些工具都不会消失。</blockquote><p style=\"text-indent: 0px; text-align: start;\">JetBrains&nbsp;透露，在未来几个月他们将致力于稳定&nbsp;Fleet，并尽可能地解决得到的反馈。同时，将在以下领域开展工作：</p><ul style=\"text-indent: 0px; text-align: left;\"><li><strong>为插件作者提供&nbsp;API&nbsp;支持和&nbsp;SDK</strong>–鉴于&nbsp;Fleet&nbsp;有一个<a href=\"https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fblog.jetbrains.com%2Fzh-hans%2Ffleet%2F2022%2F01%2Ffleet-below-deck-part-i-architecture-overview%2F\" target=\"_blank\">分布式架构</a>，我们需要努力为插件作者简化工作。&nbsp;虽然我们保证会为扩展&nbsp;Fleet&nbsp;提供一个平台，但也请求大家在这方面多一点耐心。&nbsp;</li><li><strong>性能</strong>&nbsp;–&nbsp;我们希望&nbsp;Fleet&nbsp;不仅在内存占用方面，而且在响应时间方面都能表现出色。&nbsp;有很多地方我们仍然可以提高性能，我们将在这些方面努力。&nbsp;</li><li><strong>主题和键盘地图</strong>&nbsp;–&nbsp;我们知道许多开发者已经习惯了他们现有的编辑器和&nbsp;IDE，当他们转移到新的&nbsp;IDE&nbsp;时，往往会想念他们以前的键盘绑定和主题。&nbsp;我们将致力于增加对更多主题和键盘映射的支持。&nbsp;我们当然也会致力于&nbsp;Vim&nbsp;的模拟。</li></ul><p style=\"text-indent: 0px; text-align: left;\">更多详情可<a href=\"https://my.oschina.net/u/5494143/blog/5584325\" target=\"\">查看官方博客</a>。</p>', '', '0', '0', 'CSDN', '开云', null, '0', '1', '2022-10-22 14:36:10', '2022-10-22 14:36:10');
INSERT INTO `t_notice` VALUES ('57', '2', '关于疫情防控上班通知', '1', '0', '2022-06-28 14:46:01', '近期，国内部分地区疫情频发，多地疫情出现外溢，为有效降低我市疫情输入和传播风险，洛阳市疾病预防控制中心发布疫情防控公众提示：\n一、所有入（返）洛阳人员均需提前3天向目的地社区（村居）、酒店宾馆、接待单位等所属网格进行报备，或通过“洛阳即时通系统”进行自主报备，配合做好健康码和行程码查验、核酸检测、隔离观察和健康监测等相关疫情防控措施。\n二、倡导广大群众减少跨地市出行，避免人群大范围流动引发的疫情传播扩散风险。\n三、对7天内有高风险区旅居史的人员，采取7天集中隔离医学观察；对7天内有中风险区旅居史的人员，采取7天居家隔离医学观察，如不具备居家隔离医学观察条件的，采取集中隔离医学观察。\n四、对疫情发生地出现一定范围社区传播或已实施大范围社区管控措施，基于对疫情输入风险研判结果，对近7天内来自疫情发生地所在县（市、区）的流入人员，参照中风险区旅居史人员的防控要求采取相应措施。\n五、对所有省外入（返）洛阳人员，须持有48小时内核酸检测阴性证明，抵达后进行“5天3检”，每次检测间隔24小时。推广“落地检”，按照“自愿免费即采即走，不限制流动”的原则，抵达我市后，立即进行1次核酸检测。\n六、加强重点机构场所疫情防控，坚持非必要不举办，对确需举办的培训、会展、文艺演出等大型聚集性活动，查验48小时内核酸检测阴性证明；建筑工地等人员密集型单位，查验外省（区、市）返岗人员48小时内核酸检测阴性证明；养老机构、儿童福利机构等查验探访人员48小时内核酸检测阴性证明；对进入宾馆、酒店和旅游景区等人流密集场所时，查验48小时内核酸检测阴性证明。\n七、近期有外出旅行史的人员，请密切关注疫情发生地区公布的病例和无症状感染者流调轨迹信息和中高风险区信息。有涉疫风险的人员要立即向社区（村）、住宿宾馆和单位报告，配合落实隔离医学观察。\n八、发热病人、健康码“黄码”等人员要履行个人防护责任，主动配合健康监测和核酸检测，在未排除感染风险前不出行。\n', '<p style=\"text-indent: 0px; text-align: justify;\">近期，国内部分地区疫情频发，多地疫情出现外溢，为有效降低我市疫情输入和传播风险，洛阳市疾病预防控制中心发布疫情防控公众提示：</p><p style=\"text-indent: 0px; text-align: justify;\">一、所有入（返）洛阳人员均需提前3天向目的地社区（村居）、酒店宾馆、接待单位等所属网格进行报备，或通过“洛阳即时通系统”进行自主报备，配合做好健康码和行程码查验、核酸检测、隔离观察和健康监测等相关疫情防控措施。</p><p style=\"text-indent: 0px; text-align: justify;\">二、倡导广大群众减少跨地市出行，避免人群大范围流动引发的疫情传播扩散风险。</p><p style=\"text-indent: 0px; text-align: justify;\">三、对7天内有高风险区旅居史的人员，采取7天集中隔离医学观察；对7天内有中风险区旅居史的人员，采取7天居家隔离医学观察，如不具备居家隔离医学观察条件的，采取集中隔离医学观察。</p><p style=\"text-indent: 0px; text-align: justify;\">四、对疫情发生地出现一定范围社区传播或已实施大范围社区管控措施，基于对疫情输入风险研判结果，对近7天内来自疫情发生地所在县（市、区）的流入人员，参照中风险区旅居史人员的防控要求采取相应措施。</p><p style=\"text-indent: 0px; text-align: justify;\">五、对所有省外入（返）洛阳人员，须持有48小时内核酸检测阴性证明，抵达后进行“5天3检”，每次检测间隔24小时。推广“落地检”，按照“自愿免费即采即走，不限制流动”的原则，抵达我市后，立即进行1次核酸检测。</p><p style=\"text-indent: 0px; text-align: justify;\">六、加强重点机构场所疫情防控，坚持非必要不举办，对确需举办的培训、会展、文艺演出等大型聚集性活动，查验48小时内核酸检测阴性证明；建筑工地等人员密集型单位，查验外省（区、市）返岗人员48小时内核酸检测阴性证明；养老机构、儿童福利机构等查验探访人员48小时内核酸检测阴性证明；对进入宾馆、酒店和旅游景区等人流密集场所时，查验48小时内核酸检测阴性证明。</p><p style=\"text-indent: 0px; text-align: justify;\">七、近期有外出旅行史的人员，请密切关注疫情发生地区公布的病例和无症状感染者流调轨迹信息和中高风险区信息。有涉疫风险的人员要立即向社区（村）、住宿宾馆和单位报告，配合落实隔离医学观察。</p><p style=\"text-indent: 0px; text-align: justify;\">八、发热病人、健康码“黄码”等人员要履行个人防护责任，主动配合健康监测和核酸检测，在未排除感染风险前不出行。</p><p style=\"text-indent: 0px; text-align: justify;\"><br></p>', '', '0', '0', '行政部', '卓大', '1024创新实验室发〔2022〕字第40号', '0', '1', '2022-10-22 14:53:36', '2022-10-22 14:46:00');
INSERT INTO `t_notice` VALUES ('58', '2', '办公室消杀关键位置通知', '1', '0', '2022-05-19 14:47:13', '开展消毒消杀是杀灭病源、切断疫情传播的有效手段，是防控疫情的重要措施。为了切实将新型冠状病毒肺炎疫情防控工作落到实处，守护好辖区居民及工作人员的身体健康和生命安全，青山镇高度重视新型冠状病毒肺炎的消杀工作，将采购的防护服，防护面罩，一次性手套，口罩，84消毒液，酒精消毒液以及喷雾工具等消毒消杀物资，分发到镇级各站所各村（社区），全镇开展消杀工作。', '<p><span style=\"color: rgb(93, 93, 93); background-color: rgb(247, 247, 247);\">开展消毒消杀是杀灭病源、切断疫情传播的有效手段，是防控疫情的重要措施。为了切实将新型冠状病毒肺炎疫情防控工作落到实处，守护好辖区居民及工作人员的身体健康和生命安全，青山镇高度重视新型冠状病毒肺炎的消杀工作，将采购的防护服，防护面罩，一次性手套，口罩，84消毒液，酒精消毒液以及喷雾工具等消毒消杀物资，分发到镇级各站所各村（社区），全镇开展消杀工作。</span></p>', '', '0', '0', '行政部', '卓大', '1024创新实验室发〔2022〕字第26号', '0', '1', '2022-10-22 14:53:30', '2022-10-22 14:47:12');
INSERT INTO `t_notice` VALUES ('59', '2', '十月份人事任命通知', '1', '0', '2022-09-22 14:50:12', '1024创新实验室发〔2022〕字第36号\n1024创新实验室发〔2022〕字第36号\n1024创新实验室发〔2022〕字第36号\n1024创新实验室发〔2022〕字第36号\n1024创新实验室发〔2022〕字第36号\n1024创新实验室发〔2022〕字第36号', '<p>1024创新实验室发〔2022〕字第36号</p><p>1024创新实验室发〔2022〕字第36号</p><p>1024创新实验室发〔2022〕字第36号</p><p>1024创新实验室发〔2022〕字第36号</p><p>1024创新实验室发〔2022〕字第36号</p><p>1024创新实验室发〔2022〕字第36号</p>', '', '0', '0', '销售部', '卓大', '1024创新实验室发〔2022〕字第30号', '0', '1', '2022-10-22 14:53:18', '2022-10-22 14:50:11');

-- ----------------------------
-- Table structure for t_notice_type
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_type`;
CREATE TABLE `t_notice_type` (
  `notice_type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知类型',
  `notice_type_name` varchar(1000) NOT NULL COMMENT '类型名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='通知类型';

-- ----------------------------
-- Records of t_notice_type
-- ----------------------------
INSERT INTO `t_notice_type` VALUES ('1', '公告', '2022-08-16 20:29:15', '2022-08-16 20:29:15');
INSERT INTO `t_notice_type` VALUES ('2', '通知', '2022-08-16 20:29:20', '2022-08-16 20:29:20');

-- ----------------------------
-- Table structure for t_notice_view_record
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_view_record`;
CREATE TABLE `t_notice_view_record` (
  `notice_id` bigint(20) NOT NULL COMMENT '通知公告id',
  `employee_id` bigint(20) NOT NULL COMMENT '员工id',
  `page_view_count` int(11) DEFAULT '0' COMMENT '查看次数',
  `first_ip` varchar(255) DEFAULT NULL COMMENT '首次ip',
  `first_user_agent` varchar(1000) DEFAULT NULL COMMENT '首次用户设备等标识',
  `last_ip` varchar(255) DEFAULT NULL COMMENT '最后一次ip',
  `last_user_agent` varchar(1000) DEFAULT NULL COMMENT '最后一次用户设备等标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_id`,`employee_id`),
  UNIQUE KEY `uk_notice_employee` (`notice_id`,`employee_id`) USING BTREE COMMENT '资讯员工'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知查看记录';

-- ----------------------------
-- Records of t_notice_view_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_notice_visible_range
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_visible_range`;
CREATE TABLE `t_notice_visible_range` (
  `notice_id` bigint(20) NOT NULL COMMENT '资讯id',
  `data_type` tinyint(4) NOT NULL COMMENT '数据类型1员工 2部门',
  `data_id` bigint(20) NOT NULL COMMENT '员工or部门id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_notice_data` (`notice_id`,`data_type`,`data_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知可见范围';

-- ----------------------------
-- Records of t_notice_visible_range
-- ----------------------------

-- ----------------------------
-- Table structure for t_oa_bank
-- ----------------------------
DROP TABLE IF EXISTS `t_oa_bank`;
CREATE TABLE `t_oa_bank` (
  `bank_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '银行信息ID',
  `bank_name` varchar(255) NOT NULL COMMENT '开户银行',
  `account_name` varchar(255) NOT NULL COMMENT '账户名称',
  `account_number` varchar(255) NOT NULL COMMENT '账号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `business_flag` tinyint(1) NOT NULL COMMENT '是否对公',
  `enterprise_id` bigint(20) NOT NULL COMMENT '企业ID',
  `disabled_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用状态',
  `deleted_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_user_name` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`bank_id`),
  KEY `idx_enterprise_id` (`enterprise_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='OA银行信息\n';

-- ----------------------------
-- Records of t_oa_bank
-- ----------------------------
INSERT INTO `t_oa_bank` VALUES ('26', '工商银行', '1024创新实验室', '1024', '基本户', '1', '2', '0', '0', '1', '管理员', '2022-10-22 17:58:43', '2022-10-22 17:58:43');
INSERT INTO `t_oa_bank` VALUES ('27', '建设银行', '1024创新实验室', '10241', '其他户', '0', '2', '0', '0', '1', '管理员', '2022-10-22 17:59:19', '2022-10-22 17:59:19');

-- ----------------------------
-- Table structure for t_oa_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `t_oa_enterprise`;
CREATE TABLE `t_oa_enterprise` (
  `enterprise_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '企业ID',
  `enterprise_name` varchar(255) NOT NULL COMMENT '企业名称',
  `enterprise_logo` varchar(255) DEFAULT NULL COMMENT '企业logo',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型（1:有限公司;2:合伙公司）',
  `unified_social_credit_code` varchar(255) NOT NULL COMMENT '统一社会信用代码',
  `contact` varchar(100) NOT NULL COMMENT '联系人',
  `contact_phone` varchar(100) NOT NULL COMMENT '联系人电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `province_name` varchar(100) DEFAULT NULL COMMENT '省份名称',
  `city` varchar(100) DEFAULT NULL COMMENT '市',
  `city_name` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `district` varchar(100) DEFAULT NULL COMMENT '区县',
  `district_name` varchar(100) DEFAULT NULL COMMENT '区县名称',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `business_license` varchar(255) DEFAULT NULL COMMENT '营业执照',
  `disabled_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用状态',
  `deleted_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_user_name` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`enterprise_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='OA企业模块\r\n';

-- ----------------------------
-- Records of t_oa_enterprise
-- ----------------------------
INSERT INTO `t_oa_enterprise` VALUES ('1', '1024创新区块链实验室', 'public/common/f4a76fa720814949a610f05f6f9545bf_20221022170256_jpg', '1', '1024lab_block', '开云', '17637925588', null, '110000', '北京市', '110100', '北京市', '110101', '东城区', '区块链大楼', 'public/common/852b7e19bef94af39c1a6156edf47cfb_20221022170332_jpg', '0', '0', '1', '管理员', '2021-10-22 17:03:35', '2022-10-22 17:04:18');
INSERT INTO `t_oa_enterprise` VALUES ('2', 'X创新实验室', 'public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg', '1', '1024lab', '卓大', '18637925892', 'lab1024@163.com', '410000', '河南省', '410300', '洛阳市', '410311', '洛龙区', '1024大楼', null, '0', '0', '44', '卓大', '2022-10-22 14:57:36', '2022-10-22 17:03:57');

-- ----------------------------
-- Table structure for t_oa_enterprise_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_oa_enterprise_employee`;
CREATE TABLE `t_oa_enterprise_employee` (
  `enterprise_employee_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `enterprise_id` varchar(100) NOT NULL COMMENT '订单ID',
  `employee_id` varchar(100) NOT NULL COMMENT '货物名称',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`enterprise_employee_id`),
  UNIQUE KEY `uk_enterprise_employee` (`enterprise_id`,`employee_id`) USING BTREE,
  KEY `idx_employee_id` (`employee_id`) USING BTREE,
  KEY `idx_enterprise_id` (`enterprise_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8mb4 COMMENT='企业关联的员工';

-- ----------------------------
-- Records of t_oa_enterprise_employee
-- ----------------------------
INSERT INTO `t_oa_enterprise_employee` VALUES ('154', '2', '2', '2022-10-22 17:57:50', '2022-10-22 17:57:50');
INSERT INTO `t_oa_enterprise_employee` VALUES ('155', '2', '44', '2022-10-22 17:57:50', '2022-10-22 17:57:50');

-- ----------------------------
-- Table structure for t_oa_invoice
-- ----------------------------
DROP TABLE IF EXISTS `t_oa_invoice`;
CREATE TABLE `t_oa_invoice` (
  `invoice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '发票信息ID',
  `invoice_heads` varchar(255) NOT NULL COMMENT '开票抬头',
  `taxpayer_identification_number` varchar(255) NOT NULL COMMENT '纳税人识别号',
  `account_number` varchar(255) NOT NULL COMMENT '银行账户',
  `bank_name` varchar(255) NOT NULL COMMENT '开户行',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enterprise_id` bigint(20) NOT NULL COMMENT '企业ID',
  `disabled_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用状态',
  `deleted_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_user_name` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`invoice_id`),
  KEY `idx_enterprise_id` (`enterprise_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='OA发票信息\n';

-- ----------------------------
-- Records of t_oa_invoice
-- ----------------------------
INSERT INTO `t_oa_invoice` VALUES ('15', '1024创新实验室', '1024lab', '1024lab', '中国银行', '', '2', '0', '0', '1', '管理员', '2022-10-22 17:59:35', '2022-10-22 17:59:35');

-- ----------------------------
-- Table structure for t_reload_result
-- ----------------------------
DROP TABLE IF EXISTS `t_reload_result`;
CREATE TABLE `t_reload_result` (
  `tag` varchar(255) NOT NULL,
  `identification` varchar(255) NOT NULL COMMENT '运行标识',
  `args` varchar(255) DEFAULT NULL,
  `result` tinyint(3) unsigned NOT NULL COMMENT '是否成功 ',
  `exception` text,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='reload结果';

-- ----------------------------
-- Records of t_reload_result
-- ----------------------------

-- ----------------------------
-- Table structure for t_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `t_serial_number`;
CREATE TABLE `t_serial_number` (
  `serial_number_id` int(11) NOT NULL,
  `business_name` varchar(50) NOT NULL COMMENT '业务名称',
  `format` varchar(50) DEFAULT NULL COMMENT '格式[yyyy]表示年,[mm]标识月,[dd]表示日,[nnn]表示三位数字',
  `rule_type` varchar(20) NOT NULL COMMENT '规则格式。none没有周期, year 年周期, month月周期, day日周期',
  `init_number` int(10) unsigned NOT NULL COMMENT '初始值',
  `step_random_range` int(10) unsigned NOT NULL COMMENT '步长随机数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `last_number` bigint(20) DEFAULT NULL COMMENT '上次产生的单号, 默认为空',
  `last_time` datetime DEFAULT NULL COMMENT '上次产生的单号时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`serial_number_id`),
  UNIQUE KEY `key_name` (`business_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单号生成器定义表';

-- ----------------------------
-- Records of t_serial_number
-- ----------------------------
INSERT INTO `t_serial_number` VALUES ('1', '订单编号', 'DK[yyyy][mm][dd]NO[nnnnn]', 'day', '1000', '10', 'DK20201101NO321', '1082', '2022-06-24 15:20:40', '2022-06-24 15:20:18', '2021-02-19 14:37:50');
INSERT INTO `t_serial_number` VALUES ('2', '合同编号', 'HT[yyyy][mm][dd][nnnnn]-CX', 'none', '1', '1', '', null, null, '2022-06-24 14:48:05', '2021-08-12 20:40:37');

-- ----------------------------
-- Table structure for t_serial_number_record
-- ----------------------------
DROP TABLE IF EXISTS `t_serial_number_record`;
CREATE TABLE `t_serial_number_record` (
  `serial_number_id` int(11) NOT NULL,
  `record_date` date NOT NULL COMMENT '记录日期',
  `last_number` bigint(20) NOT NULL DEFAULT '0' COMMENT '最后更新值',
  `last_time` datetime NOT NULL COMMENT '最后更新时间',
  `count` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新次数',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  KEY `uk_generator` (`serial_number_id`,`record_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='serial_number记录表';

-- ----------------------------
-- Records of t_serial_number_record
-- ----------------------------
INSERT INTO `t_serial_number_record` VALUES ('1', '2022-03-02', '8581', '2022-03-03 14:37:11', '500', '2022-03-03 14:37:46', '2022-03-03 14:36:43');
INSERT INTO `t_serial_number_record` VALUES ('1', '2022-03-03', '7053', '2022-03-03 14:38:48', '1500', '2022-03-03 14:47:32', '2022-03-03 14:38:19');
INSERT INTO `t_serial_number_record` VALUES ('1', '2022-06-24', '1082', '2022-06-24 15:16:13', '15', '2022-06-24 15:20:18', '2022-06-24 15:15:51');
