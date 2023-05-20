/*
 Navicat Premium Data Transfer

 Source Server         : 农产品大数据平台
 Source Server Type    : MySQL
 Source Server Version : 50741 (5.7.41-0ubuntu0.18.04.1)
 Source Host           : 49.234.45.35:3306
 Source Schema         : mf_market

 Target Server Type    : MySQL
 Target Server Version : 50741 (5.7.41-0ubuntu0.18.04.1)
 File Encoding         : 65001

 Date: 19/05/2023 10:06:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_material
-- ----------------------------
DROP TABLE IF EXISTS `sh_material`;
CREATE TABLE `sh_material`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '产品类型id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '产品名称',
  `model` varchar(50) NULL DEFAULT NULL COMMENT '型号',
  `standard` varchar(50) NULL DEFAULT NULL COMMENT '规格',
  `color` varchar(50) NULL DEFAULT NULL COMMENT '颜色',
  `unit` varchar(50) NULL DEFAULT NULL COMMENT '单位',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `expiry_num` int(10) NULL DEFAULT NULL COMMENT '保质期天数',
  `weight` decimal(24, 6) NULL DEFAULT NULL COMMENT '基础重量(kg)',
  `enabled` bit(1) NULL DEFAULT b'1' COMMENT '启用 0-禁用  1-启用',
  `extend_field` longtext NULL COMMENT '自定义扩展字段JSON结构',
  `enable_serial_number` tinyint(1) NULL DEFAULT 0 COMMENT '是否开启序列号，0否，1是',
  `enable_batch_number` tinyint(1) NULL DEFAULT 0 COMMENT '是否开启批号，0否，1是',
  `deleted_flag` tinyint(1) NULL DEFAULT 0 COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '归属组织id',
  `scale` int(10) NULL DEFAULT NULL COMMENT '种植规模(单位为㎡)',
  `flag` int(11) NULL DEFAULT NULL COMMENT '区分字段',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '产品表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
