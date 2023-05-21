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

 Date: 21/05/2023 20:05:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_material_brand_record
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_brand_record`;
CREATE TABLE `sh_material_brand_record`  (
  `id` bigint(20) NOT NULL COMMENT '唯一主键',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `material_id` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '品牌产品中间表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
