/*
Navicat MySQL Data Transfer

Source Server         : 49.234.45.35
Source Server Version : 50741
Source Host           : 49.234.45.35:3306
Source Database       : mf_market

Target Server Type    : MYSQL
Target Server Version : 50741
File Encoding         : 65001

Date: 2023-07-01 18:38:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_article
-- ----------------------------
DROP TABLE IF EXISTS `sh_article`;
CREATE TABLE `sh_article` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `summary` varchar(1000) NOT NULL DEFAULT '' COMMENT '摘要',
  `author` varchar(50) DEFAULT '' COMMENT '作者',
  `source` varchar(50) DEFAULT '' COMMENT '来源',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '分类id',
  `type` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章类型（日报、周报等，对应分类管理id）',
  `cover_img` varchar(255) NOT NULL DEFAULT '' COMMENT '封面图片地址',
  `status` smallint(4) NOT NULL DEFAULT '0' COMMENT '状态,0->草稿箱,1->发布',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '是否置顶,0->不置顶,1->置顶',
  `content_model` smallint(4) NOT NULL DEFAULT '0' COMMENT '内容模式,0->标准模式,1->定制模式',
  `click_num` bigint(20) DEFAULT NULL COMMENT '点击数',
  `collect_num` int(11) NOT NULL DEFAULT '0' COMMENT '收藏量',
  `comment_num` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `like_num` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `download_num` int(11) NOT NULL DEFAULT '0' COMMENT '下载量',
  `share_num` int(11) NOT NULL DEFAULT '0' COMMENT '分享数',
  `allow_comment` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许评论',
  `allow_subscribe` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许订阅',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布开始期',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布结束期',
  `media_type` varchar(50) DEFAULT NULL COMMENT '信息类型,分为,article:文本;picture:图片类;vidio:视频类',
  `extend_attribute` longtext COMMENT '扩展属性 JSON结构',
  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '多租户组织ID',
  `place` varchar(50) DEFAULT NULL COMMENT '文章发布地域',
  `inclined` int(11) DEFAULT NULL COMMENT '倾向',
  `flag` int(11) DEFAULT NULL COMMENT '区分字段',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '0--未删除 1--已删除 DIC_NAME=DELETE_FLAG',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`) USING BTREE,
  KEY `idx_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- ----------------------------
-- Records of sh_article
-- ----------------------------
INSERT INTO `sh_article` VALUES ('1', '1百亿菜心的抱负|做大做强做优连州菜心省级现代农业产业园', '', '连州市融媒体中心', '连州市融媒体中心', '2', '0', '', '0', '1', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-19 23:29:21', '2023-05-19 23:29:23', '文本', null, '0', '广东省', '0', null, '0', 'admin', '2023-05-19 23:30:02', 'admin', '2023-05-24 19:13:16');
INSERT INTO `sh_article` VALUES ('2', '连州菜心，上人民日报了！', '无', '清远发布', 'www.thepaper.cn', '2', '0', '', '1', '1', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-21 01:30:05', '2023-05-21 01:30:05', null, null, '0', '广东省', '0', null, '0', 'admin', '2023-05-21 01:30:04', 'admin', '2023-05-24 19:13:20');
INSERT INTO `sh_article` VALUES ('3', '连州菜心，上人民日报了！', '无', '清远发布', '澎湃新闻·澎湃号·政务', '1', '0', '', '1', '1', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-03-15 15:00:00', '2024-04-01 23:00:07', '文本', null, '0', '广东省', '0', null, '0', 'admin', '2023-05-21 01:34:59', 'admin', '2023-05-29 22:45:49');
INSERT INTO `sh_article` VALUES ('4', '测试', '测试', '测试', '测试', '3', '0', '', '1', '0', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-21 02:15:58', '2023-05-21 02:16:00', '文本', null, '0', '广东省', '0', null, '0', 'admin', '2023-05-21 02:16:04', 'admin', '2023-05-24 09:28:03');
INSERT INTO `sh_article` VALUES ('5', '测试', '测试', '测试', '测试', '4', '0', '', '0', '1', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-21 02:17:10', '2023-05-27 00:06:00', '图片', null, '0', '广东省', '0', null, '0', 'admin', '2023-05-21 02:17:38', null, null);
INSERT INTO `sh_article` VALUES ('6', '测试', '测试', '测试', '测试', '5', '0', '', '1', '0', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-21 02:18:16', '2023-05-28 00:07:00', '图片', null, '0', '广东省', '0', null, '0', 'admin', '2023-05-21 02:18:33', 'admin', '2023-05-21 02:19:43');
INSERT INTO `sh_article` VALUES ('8', 'efs', 'sf', 'sdf', 'e', '5', '0', '', '1', '0', '0', '63', '3', '93', '93', '93', '3', '1', '1', '0', '2023-05-20 02:00:00', '2023-05-23 23:17:43', '文本', null, '0', '广东省', '0', null, '0', 'admin', '2023-05-23 23:18:31', null, null);
INSERT INTO `sh_article` VALUES ('9', 'dwda', 'ads', 'sdf', 'adad', '5', '0', '', '1', '0', '0', '63', '3', '0', '93', '93', '3', '1', '1', '0', '2023-05-22 13:00:00', '2023-05-26 00:18:00', null, null, '0', '广东省', '0', null, '0', 'admin', '2023-05-23 23:19:54', null, null);
INSERT INTO `sh_article` VALUES ('12', '1', '', '', '', '3', '0', '', '1', '0', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-24 22:33:38', '2023-05-24 22:33:38', null, null, '0', '广东省', '0', null, '0', 'admin', '2023-05-24 22:33:39', null, null);
INSERT INTO `sh_article` VALUES ('13', '大数据接口', '', '开发者', '', '4', '0', '', '1', '0', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-26 12:39:21', '2023-05-26 12:39:21', null, null, '0', '广东省', '0', null, '0', 'admin', '2023-05-26 12:39:22', 'admin', '2023-05-26 15:23:22');
INSERT INTO `sh_article` VALUES ('14', '分工', '', '123', '123', '4', '0', '', '1', '0', '1', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-26 13:16:49', '2023-05-26 13:16:49', null, null, '0', '广东省', '0', null, '0', 'admin', '2023-05-26 13:16:50', 'admin', '2023-06-21 11:35:06');
INSERT INTO `sh_article` VALUES ('15', '数据表整理', '', '', '', '4', '0', '', '1', '0', '0', null, '0', '0', '0', '0', '0', '1', '1', '0', '2023-05-27 18:00:29', '2023-05-27 18:00:29', null, null, '0', '广东省', '0', null, '0', 'admin', '2023-05-27 18:00:30', null, null);
