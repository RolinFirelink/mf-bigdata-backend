/*
Navicat MySQL Data Transfer

Source Server         : 49.234.45.35
Source Server Version : 50741
Source Host           : 49.234.45.35:3306
Source Database       : mf_market

Target Server Type    : MYSQL
Target Server Version : 50741
File Encoding         : 65001

Date: 2023-07-01 14:09:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_material_produce_with_produce_base
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_produce_with_produce_base`;
CREATE TABLE `sh_material_produce_with_produce_base` (
  `area` decimal(10,0) DEFAULT NULL COMMENT '种植面积',
  `yield` int(11) DEFAULT NULL COMMENT '产量',
  `base_id` bigint(20) NOT NULL COMMENT '基地ID',
  `produce_base_name` varchar(100) DEFAULT NULL COMMENT '基地名称',
  `flag` int(11) DEFAULT NULL COMMENT '区分字段',
  `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '统计时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基地和产品表（用于统计基地和产品的关系）';

-- ----------------------------
-- Records of sh_material_produce_with_produce_base
-- ----------------------------
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('1250', '123', '1', '123', '1', '2023-06-29 17:01:12');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('1250', '123', '2', '88', '1', '2023-06-29 17:01:12');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('12556464', '123', '1', '123', '1', '2023-06-30 11:18:15');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('1347', '187', '1', '哈哈哈哈哈', '1', '2023-06-30 12:39:45');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('1347', '187', '1', '哈哈哈哈哈', '1', '2023-06-30 12:41:42');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('134', '55', '2', '哈哈嗯', '1', '2023-06-30 12:41:42');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('1347', '187', '1', '哈哈哈哈哈', '1', '2023-06-30 14:39:51');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('134', '55', '2', '哈哈嗯', '1', '2023-06-30 14:39:51');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('236', '143', '1', '哈哈哈哈哈', '1', '2023-07-01 14:01:58');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('134', '55', '2', '哈哈嗯', '1', '2023-07-01 14:01:58');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('123', '44', '3', '哇哈哈', '2', '2023-07-01 14:01:58');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('123', '44', '4', '乐呵呵', '1', '2023-07-01 14:01:58');
INSERT INTO `sh_material_produce_with_produce_base` VALUES ('1111', '44', '5', '呀哈哈', '1', '2023-07-01 14:01:59');
