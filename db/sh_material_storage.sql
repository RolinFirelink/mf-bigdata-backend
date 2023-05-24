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

 Date: 21/05/2023 20:05:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_material_storage
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_storage`;
CREATE TABLE `sh_material_storage`  (
  `id` bigint(20) NOT NULL COMMENT '唯一主键',
  `flag` int(11) NULL DEFAULT NULL COMMENT '区分字段',
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '自定义拓展JSON结构数据',
  `deleted_flag` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `owner_id` bigint(20) NULL DEFAULT NULL COMMENT '归属组织id',
  `material_id` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `material_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名字',
  `number` int(11) NULL DEFAULT NULL COMMENT '库存数量',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品库存表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
