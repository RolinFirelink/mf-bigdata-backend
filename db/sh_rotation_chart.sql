/*
Navicat MySQL Data Transfer

Source Server         : 49.234.45.35
Source Server Version : 50741
Source Host           : 49.234.45.35:3306
Source Database       : mf_market

Target Server Type    : MYSQL
Target Server Version : 50741
File Encoding         : 65001

Date: 2023-06-11 12:17:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_rotation_chart
-- ----------------------------
DROP TABLE IF EXISTS `sh_rotation_chart`;
CREATE TABLE `sh_rotation_chart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `img_url` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `path` varchar(500) DEFAULT NULL COMMENT '跳转路径',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '状态（是否启用）',
  `extend_attribute` longtext,
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='轮播图图片';

-- ----------------------------
-- Records of sh_rotation_chart
-- ----------------------------
INSERT INTO `sh_rotation_chart` VALUES ('2', null, null, '0', '0', null, null, '2023-06-02 12:23:02', null, null);
INSERT INTO `sh_rotation_chart` VALUES ('3', null, null, '1', '0', null, null, '2023-06-02 12:32:33', null, null);
INSERT INTO `sh_rotation_chart` VALUES ('4', null, 'http://localhost:8886/', '1', '0', null, null, '2023-06-02 12:33:26', null, '2023-06-02 12:34:42');
INSERT INTO `sh_rotation_chart` VALUES ('5', null, 'http://localhost:8886/', '1', '0', null, null, '2023-06-02 12:39:04', null, null);
INSERT INTO `sh_rotation_chart` VALUES ('6', null, 'http://localhost:8886/', '1', '0', null, null, '2023-06-02 12:40:04', null, null);
INSERT INTO `sh_rotation_chart` VALUES ('7', null, 'http://localhost:8886/', '1', '0', null, null, '2023-06-02 12:42:02', null, null);
INSERT INTO `sh_rotation_chart` VALUES ('8', null, 'http://localhost:8886/', '1', '0', null, null, '2023-06-02 12:42:13', null, null);
INSERT INTO `sh_rotation_chart` VALUES ('9', 'hhhhhhhhahahahah', 'http://localhost:8886/', '1', '0', null, null, '2023-06-02 12:42:23', null, null);
INSERT INTO `sh_rotation_chart` VALUES ('10', 'https://pic.quanjing.com/m5/vx/QJ9132009227.jpg@%21350h', 'http://localhost:8886/', '1', '0', null, null, '2023-06-02 12:44:31', null, null);
