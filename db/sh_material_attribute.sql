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

 Date: 19/05/2023 10:06:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_material_attribute
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_attribute`;
CREATE TABLE `sh_material_attribute`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
  `attribute_field` varchar(50) NULL DEFAULT NULL COMMENT '属性字段',
  `attribute_name` varchar(50) NULL DEFAULT NULL COMMENT '属性名',
  `attribute_value` varchar(500) NULL DEFAULT NULL COMMENT '属性值',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '归属组织id',
  `deleted_flag` tinyint(1) NULL DEFAULT 0 COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `flag` int(11) NULL DEFAULT NULL COMMENT '区分字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '产品属性表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
