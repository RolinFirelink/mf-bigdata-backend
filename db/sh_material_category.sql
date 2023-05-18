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

 Date: 17/05/2023 21:09:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_material_category
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_category`;
CREATE TABLE `sh_material_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `serial_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
  `category_level` smallint(6) NOT NULL DEFAULT 0 COMMENT '等级',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '上级id',
  `sort` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '显示顺序',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted_flag` tinyint(1) NULL DEFAULT 0 COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '归属组织id',
  `flag` int(11) NULL DEFAULT NULL COMMENT '区分字段',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品类型表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
