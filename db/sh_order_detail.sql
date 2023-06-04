/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50741 (5.7.41-log)
 Source Host           : localhost:3306
 Source Schema         : mf_market

 Target Server Type    : MySQL
 Target Server Version : 50741 (5.7.41-log)
 File Encoding         : 65001

 Date: 29/05/2023 04:50:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `sh_order_detail`;
CREATE TABLE `sh_order_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `order_id` bigint(20) NOT NULL COMMENT '父表编号',
  `material_id` bigint(20) NULL DEFAULT NULL COMMENT '产品编号',
  `material_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `sales_quantity` bigint(20) NULL DEFAULT NULL COMMENT '销售数量',
  `sales_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售单价',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计量单位',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_flag` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单数据明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sh_order_detail
-- ----------------------------
INSERT INTO `sh_order_detail` VALUES (1, 2, 1, '增城菜心', 5, 5.00, '元/斤', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (2, 2, 1, '红烧乳鸽', 5, 6.00, '元/斤', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (3, 3, 1, '1', 123, 123.00, '123', 'admin', '2023-05-24 14:05:04', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (4, 3, 123, '123', 123, 123.00, '123', 'admin', '2023-05-24 14:11:59', NULL, NULL, 0);
INSERT INTO `sh_order_detail` VALUES (5, 3, 123, NULL, 123, NULL, NULL, 'admin', '2023-05-24 14:12:46', 'admin', '2023-05-24 14:16:50', 1);
INSERT INTO `sh_order_detail` VALUES (6, 3, 123, NULL, NULL, NULL, NULL, 'admin', '2023-05-24 14:13:03', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (7, 3, 123, NULL, NULL, NULL, NULL, 'admin', '2023-05-24 14:13:57', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (8, 3, NULL, NULL, NULL, NULL, NULL, 'admin', '2023-05-24 14:15:25', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (9, 3, NULL, NULL, NULL, NULL, NULL, 'admin', '2023-05-24 14:16:30', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (10, 4, NULL, NULL, NULL, NULL, NULL, 'admin', '2023-05-24 14:16:33', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (11, 4, NULL, NULL, NULL, NULL, NULL, 'admin', '2023-05-24 14:16:37', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (12, 2, 2, '红烧乳鸽', 12, 35.00, '元/只', 'admin', '2023-05-24 20:32:05', NULL, NULL, 0);
INSERT INTO `sh_order_detail` VALUES (13, 2, NULL, NULL, NULL, NULL, NULL, 'admin', '2023-05-26 20:00:31', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (14, 3, 123, NULL, NULL, NULL, NULL, 'admin', '2023-05-28 22:26:56', NULL, NULL, 0);
INSERT INTO `sh_order_detail` VALUES (15, 8, 17, '增城迟菜心', 123, 123.52, '斤', 'admin', '2023-05-28 23:43:03', 'admin', '2023-05-29 04:32:32', 0);
INSERT INTO `sh_order_detail` VALUES (16, 8, NULL, NULL, NULL, NULL, NULL, 'admin', '2023-05-29 04:28:52', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (17, 8, 17, '增城迟菜心', NULL, NULL, NULL, 'admin', '2023-05-29 04:30:14', NULL, NULL, 1);
INSERT INTO `sh_order_detail` VALUES (18, 10, 22, '对虾', 1000, 10.00, '只', 'admin', '2023-05-29 04:46:43', NULL, NULL, 0);
INSERT INTO `sh_order_detail` VALUES (19, 10, 22, '对虾', 40, 200.00, '盒', 'admin', '2023-05-29 04:47:17', 'admin', '2023-05-29 04:47:24', 1);

SET FOREIGN_KEY_CHECKS = 1;
