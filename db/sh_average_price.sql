/*
 Navicat Premium Data Transfer

 Target Server Type    : MySQL
 Target Server Version : 50741 (5.7.41-0ubuntu0.18.04.1)
 File Encoding         : 65001

 Date: 07/06/2023 12:28:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_average_price
-- ----------------------------
DROP TABLE IF EXISTS `sh_average_price`;
CREATE TABLE `sh_average_price`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
  `flag` int(11) NULL DEFAULT NULL COMMENT '区分字段',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '均价数值',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '均价单位',
  `average_date` datetime NULL DEFAULT NULL COMMENT '均价日期',
  `place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '均价地区',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_flag` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '均价表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
