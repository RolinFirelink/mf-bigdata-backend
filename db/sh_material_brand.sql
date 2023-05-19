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

 Date: 19/05/2023 10:07:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_material_brand
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_brand`;
CREATE TABLE `sh_material_brand`  (
  `id` bigint(20) NOT NULL COMMENT '唯一主键',
  `deleted_flag` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '品牌名',
  `description` varchar(1023) NULL DEFAULT NULL COMMENT '品牌描述',
  `values` varchar(255) NULL DEFAULT NULL COMMENT '品牌价值观',
  `website` varchar(512) NULL DEFAULT NULL COMMENT '品牌官网',
  `established_date` datetime NULL DEFAULT NULL COMMENT '品牌创立时间',
  `flag` int(11) NULL DEFAULT NULL COMMENT '区分字段',
  `company_name` varchar(255) NULL DEFAULT NULL COMMENT '品牌归属公司名',
  `company_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌归属公司id',
  `create_by` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(255) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '产品品牌表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
