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

 Date: 21/05/2023 20:05:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_material_produce
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_produce`;
CREATE TABLE `sh_material_produce`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `company_id` bigint(20) NULL DEFAULT NULL COMMENT '公司id',
  `material_id` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `batch` int(11) NULL DEFAULT NULL COMMENT '生产批次',
  `production_scale` decimal(10, 2) NULL DEFAULT NULL COMMENT '生产规模',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规模单位',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `number` bigint(20) NULL DEFAULT NULL COMMENT '产品编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '产品类型id',
  `product_estimate` int(10) NULL DEFAULT NULL COMMENT '单位产量估算',
  `market_estimate` int(10) NULL DEFAULT NULL COMMENT '预计上市产量',
  `time_estimate` datetime NULL DEFAULT NULL COMMENT '预计上市时间',
  `base_id` bigint(20) NULL DEFAULT NULL COMMENT '基地id',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '生产数量',
  `is_sell` tinyint(1) NULL DEFAULT NULL COMMENT '是否卖出',
  `flag` int(11) NULL DEFAULT NULL COMMENT '区分字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品生产表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
