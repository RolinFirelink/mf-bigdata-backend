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

 Date: 29/05/2023 04:50:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_order
-- ----------------------------
DROP TABLE IF EXISTS `sh_order`;
CREATE TABLE `sh_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `category` smallint(6) NULL DEFAULT NULL COMMENT '订单类型，1=生产订单，2=出库订单，3=采购订单，4=销售订单，5=企业退货订单，\r\n6=消费者退货订单（生产订单指生产制造企业加工的订单数据或基地采收记录、出库订单就是出库记录、\r\n采购订单是批发商或零售商向厂家采购的数据、销售订单是消费者购买订单、退货订单是消费者或批发商、零售商退货的订单）',
  `vendor_id` bigint(20) NULL DEFAULT NULL COMMENT '供应企业编号',
  `vendor_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应企业名称',
  `buyer_id` int(11) NULL DEFAULT NULL COMMENT '采购商ID',
  `buyer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采购商名称',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '订单状态：1：未开始、2、进行中、3、已完成',
  `start_time` datetime NULL DEFAULT NULL COMMENT '订单创建时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '订单完成时间',
  `flag` smallint(6) NULL DEFAULT NULL COMMENT '模块编号，用于区分不同模块的数据，1=肉鸡、2=柑橘、3=兰花、4=对虾、5=菜心、6=预制菜',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_flag` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单数据主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sh_order
-- ----------------------------
INSERT INTO `sh_order` VALUES (8, 3, 9, '呀哈哈公司', 8, '广东俊杰农产品有限公司', 1, '2023-05-29 01:32:45', '2023-05-29 01:32:43', 1, '', 'admin', '2023-05-28 23:42:58', 'admin', '2023-05-29 03:00:56', 0);
INSERT INTO `sh_order` VALUES (9, 2, 9, '呀哈哈公司', 0, '', 1, '2023-05-29 01:56:17', '2023-05-29 01:56:15', 2, NULL, 'admin', '2023-05-29 01:56:06', 'admin', '2023-05-29 04:40:21', 0);
INSERT INTO `sh_order` VALUES (10, 1, 9, '呀哈哈公司', NULL, NULL, 1, '2023-05-29 03:01:07', '2023-05-30 03:01:07', 1, NULL, 'admin', '2023-05-29 03:01:15', NULL, NULL, 0);
INSERT INTO `sh_order` VALUES (11, 1, 9, '呀哈哈公司', NULL, NULL, 1, '2023-05-29 03:01:21', '2023-05-29 03:01:23', 1, NULL, 'admin', '2023-05-29 03:01:25', NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
