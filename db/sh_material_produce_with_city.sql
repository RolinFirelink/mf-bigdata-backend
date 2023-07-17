/*
Navicat MySQL Data Transfer

Source Server         : 49.234.45.35
Source Server Version : 50741
Source Host           : 49.234.45.35:3306
Source Database       : mf_market

Target Server Type    : MYSQL
Target Server Version : 50741
File Encoding         : 65001

Date: 2023-07-01 14:10:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_material_produce_with_city
-- ----------------------------
DROP TABLE IF EXISTS `sh_material_produce_with_city`;
CREATE TABLE `sh_material_produce_with_city` (
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `production_scale` decimal(10,2) DEFAULT NULL COMMENT '种植规模',
  `flag` int(11) DEFAULT NULL COMMENT '区分字段',
  `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='广东省各个城市的种植规模统计表';

-- ----------------------------
-- Records of sh_material_produce_with_city
-- ----------------------------
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', null, '134.00', '1', '2023-06-30 22:31:06');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '亚.波', '236.00', '1', '2023-06-30 22:31:06');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '广州市', '1234.00', '1', '2023-06-30 22:31:06');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '广州市', '123.00', '2', '2023-06-30 22:31:06');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', null, '134.00', '1', '2023-07-01 13:42:33');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '亚.波', '236.00', '1', '2023-07-01 13:42:33');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '广州市', '1234.00', '1', '2023-07-01 13:42:33');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '广州市', '123.00', '2', '2023-07-01 13:42:33');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', null, '134.00', '1', '2023-07-01 14:02:00');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '亚.波', '236.00', '1', '2023-07-01 14:02:00');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '广州市', '1234.00', '1', '2023-07-01 14:02:00');
INSERT INTO `sh_material_produce_with_city` VALUES ('千亩', '广州市', '123.00', '2', '2023-07-01 14:02:00');
