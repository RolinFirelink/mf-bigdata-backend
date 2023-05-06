/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : mf_oauth

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2023-05-06 15:37:36
*/

DROP DATABASE IF EXISTS `mf_oauth`;
CREATE DATABASE  `mf_oauth` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE `mf_oauth`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sso_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sso_client_details`;
CREATE TABLE `sso_client_details` (
  `client_id` varchar(128) NOT NULL COMMENT '用于唯一标识每一个客户端(client)；注册时必须填写(也可以服务端自动生成)，这个字段是必须的，实际应用也有叫app_key',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '客户端能访问的资源id集合，注册客户端时，根据实际需要可选择资源id，也可以根据不同的额注册流程，赋予对应的额资源id',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '注册填写或者服务端自动生成，实际应用也有叫app_secret, 必须要有前缀代表加密方式',
  `scope` varchar(200) DEFAULT NULL COMMENT '指定client的权限范围，比如读写权限，比如移动端还是web端权限',
  `authorized_grant_types` varchar(100) DEFAULT NULL COMMENT '可选值 授权码模式:authorization_code,密码模式:password,刷新token: refresh_token, 隐式模式: implicit: 客户端模式: client_credentials。支持多个用逗号分隔\n\n作者：谢海凡\n链接：https://www.jianshu.com/p/c1c6c966c3a7\n来源：简书\n著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。',
  `web_server_redirect_uri` varchar(500) DEFAULT NULL COMMENT '客户端重定向uri，authorization_code和implicit需要该值进行校验，注册时填写，',
  `authorities` varchar(256) DEFAULT NULL COMMENT '指定用户的权限范围，如果授权的过程需要用户登陆，该字段不生效，implicit和client_credentials需要',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '设置access_token的有效时间(秒),默认(606012,12小时)',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '设置refresh_token有效期(秒)，默认(606024*30, 30填)',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '默认false,适用于authorization_code模式,设置用户是否自动approval操作,设置true跳过用户确认授权操作页面，直接跳到redirect_uri',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户端信息';

-- ----------------------------
-- Records of sso_client_details
-- ----------------------------
INSERT INTO `sso_client_details` VALUES ('system', null, 'system', 'all', 'authorization_code,password,refresh_token', 'http://localhost:5281/oauth2.*', null, '28800', null, null, 'true');

-- ----------------------------
-- Table structure for sso_client_user
-- ----------------------------
DROP TABLE IF EXISTS `sso_client_user`;
CREATE TABLE `sso_client_user` (
  `client_id` varchar(128) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`client_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户端用户关系表';

-- ----------------------------
-- Records of sso_client_user
-- ----------------------------
INSERT INTO `sso_client_user` VALUES ('system', '1');
INSERT INTO `sso_client_user` VALUES ('system', '40062f1156ef42b9b3a341462c927fb6');
INSERT INTO `sso_client_user` VALUES ('system', '4ef9999a1cd0492db32c87d97659b963');
INSERT INTO `sso_client_user` VALUES ('system', 'f4056d9589a64146a7538f04c6bcc10f');

-- ----------------------------
-- Table structure for sso_menu
-- ----------------------------
DROP TABLE IF EXISTS `sso_menu`;
CREATE TABLE `sso_menu` (
  `id` varchar(36) NOT NULL COMMENT '菜单ID',
  `parent_id` varchar(36) NOT NULL DEFAULT '' COMMENT '父菜单ID',
  `client_id` varchar(128) NOT NULL COMMENT '客户端ID',
  `menu_code` varchar(200) DEFAULT NULL COMMENT '菜单编码',
  `menu_level` tinyint(4) DEFAULT NULL COMMENT '菜单级别',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `menu_icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `menu_sort` int(4) DEFAULT '0' COMMENT '菜单顺序',
  `menu_type` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '菜单类型（0目录 1菜单 2按钮）',
  `route_path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `permissions` varchar(200) DEFAULT NULL COMMENT '权限标识(多个标识逗号隔开)',
  `is_external` tinyint(1) DEFAULT '0' COMMENT '是否为外部链接（1是 0否）',
  `is_visible` tinyint(1) DEFAULT '1' COMMENT '菜单状态（1显示 0隐藏）',
  `active_menu` varchar(200) DEFAULT NULL COMMENT '当前激活菜单(当菜单隐藏时，设置当前激活的菜单项)',
  `is_keepalive` tinyint(1) DEFAULT NULL COMMENT '是否缓存(1是 0否)',
  `remark` varchar(500) DEFAULT '' COMMENT '描述',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_menu_code` (`menu_code`) USING BTREE COMMENT '菜单编码索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of sso_menu
-- ----------------------------
INSERT INTO `sso_menu` VALUES ('0526179a70fca38a69dd709dec2f1a81', '6e491486dc4cb475e4bd037d06ab2801', 'system', '0000300004', '2', 'Vben文档', 'ion:social-vimeo-outline', '5', '1', '/vben', 'https://doc.vvbin.cn/guide/introduction.html', null, '0', '1', null, '1', '', 'admin', '2022-12-15 09:14:09', 'admin', '2023-03-09 10:36:52');
INSERT INTO `sso_menu` VALUES ('08a567f09c8d90660c23f2b432e0e1d9', 'bea31d33d125895a9eaa827863341a91', 'system', '0000800001', '2', '聊天', 'ion:chatbubbles-outline', '1', '1', '/ai', '/chat/ai/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-02-08 16:46:12', 'admin', '2023-02-08 17:20:37');
INSERT INTO `sso_menu` VALUES ('0aa9f017545ec947a075f76e34c075c0', '', 'system', '00007', '1', '系统监控', 'ion:fitness-outline', '11', '0', '/monitor', null, null, '0', '1', null, null, '', 'admin', '2023-01-27 13:55:58', 'admin', '2023-05-06 11:02:55');
INSERT INTO `sso_menu` VALUES ('0f5a85a6fd5bdc9df26b826eec3c17f1', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200004', '2', '角色管理', 'ion:ios-key', '3', '1', '/role', '/sys/role/index.vue', null, '0', '1', null, '1', '', 'admin', '2022-11-30 17:32:14', 'admin', '2022-12-14 14:25:37');
INSERT INTO `sso_menu` VALUES ('101cb161536a5a80731a4d6db0b5eeac', 'c52a49da263d57d2c89edcbc9ca70a0a', 'system', '000020001000001', '3', '查询', '#', '1', '2', '', null, 'sys:online:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:20:38', '', null);
INSERT INTO `sso_menu` VALUES ('1a0aee8380c525e7c4802b1c4d587fa8', '6e491486dc4cb475e4bd037d06ab2801', 'system', '0000300001', '2', '接口地址', 'ion:book-sharp', '1', '1', '/swagger', 'http://localhost:8888/swagger-ui/index.html', null, '0', '1', null, '0', '', 'admin', '2022-12-14 10:25:31', 'admin', '2023-01-27 15:13:26');
INSERT INTO `sso_menu` VALUES ('1a73215261f568088e9adeef2dbd8e44', 'a988f38821885f8f8aaffa49d681aaac', 'system', '000020000100004', '3', '删除', '#', '4', '2', '', null, 'sys:menu:delete,sys:menu:query', '0', '1', null, null, '', 'admin', '2022-11-08 17:05:36', 'admin', '2022-12-20 11:34:00');
INSERT INTO `sso_menu` VALUES ('1d4a94508e926e493bb88b0e2c8bea4a', '', 'system', '00012', '1', '内容管理', 'simple-icons:craftcms', '2', '0', '/material', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:01:39', 'admin', '2023-05-06 11:03:25');
INSERT INTO `sso_menu` VALUES ('1e1b7d50ab93ffdeca33fe5b7006eb01', 'a43c057f48b54c9038719179cf9e284d', 'system', '000090000200001', '3', '查询', '#', '1', '2', '', null, 'sys:jobLog:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:11:32', '', null);
INSERT INTO `sso_menu` VALUES ('234dc900ad6502579a51784f9ddb05d5', '76f68d05f5054818762718ee85d6d0fe', 'system', '000010000100002', '3', '新增', '#', '2', '2', '', null, 'sys:workbench:insert', '0', '1', null, null, '', 'admin', '2023-03-10 17:15:10', '', null);
INSERT INTO `sso_menu` VALUES ('238132a09b6f761374dfd205b6388245', '9d5397b6ddb4d194a95b05f42b80445b', 'system', '000090000100001', '3', '查询', '#', '1', '2', '', null, 'sys:job:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:11:24', '', null);
INSERT INTO `sso_menu` VALUES ('25a5783ea03e26d7844b9b7370576236', 'cfbdf3ce5297cebf806ac116fc239558', 'system', '000020000800001', '3', '查询', '#', '1', '2', '', null, 'sys.log.query', '0', '1', null, null, '', 'admin', '2023-03-10 10:06:31', '', null);
INSERT INTO `sso_menu` VALUES ('2628fb3c4166d6469f06fcea9b9c0c55', 'c52a49da263d57d2c89edcbc9ca70a0a', 'system', '000020001000002', '3', '强制退出', '#', '2', '2', '', null, 'sys:online:revoke,sys:online:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:21:07', '', null);
INSERT INTO `sso_menu` VALUES ('268d140daddc00dc77823c7d7c2025fb', '76f68d05f5054818762718ee85d6d0fe', 'system', '000010000100001', '3', '查询', '#', '1', '2', '', null, 'sys:workbench:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:15:10', 'admin', '2023-03-10 17:15:25');
INSERT INTO `sso_menu` VALUES ('2a4e024fdc76063da32926c63ca9ead2', '', 'system', '00002', '1', '系统管理', 'ant-design:setting-outlined', '10', '0', '/system', null, null, '0', '1', null, null, '', 'admin', '2022-11-08 16:59:57', 'admin', '2023-05-06 11:02:49');
INSERT INTO `sso_menu` VALUES ('31eb5dc66b309c81dceeb06e216c22ec', '694e6ae530d622c15b50799837fe4b8c', 'system', '000020001400003', '3', '修改', '#', '3', '2', '', null, 'sys:region:update,sys:region:query', '0', '1', null, null, '', 'admin', '2023-05-06 14:21:00', '', null);
INSERT INTO `sso_menu` VALUES ('34bb93e41e8968a94796078fd890462f', '', 'system', '00013', '1', '产品管理', 'simple-icons:producthunt', '3', '0', '/material', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:01:39', 'admin', '2023-05-06 11:02:31');
INSERT INTO `sso_menu` VALUES ('369de1bef8d1e964414f25ec6d3156bc', '6aee07bfe60f4ee4021bfce397a8f4df', 'system', '000020001200003', '3', '修改', '#', '3', '2', '', null, 'sys:database:query,sys:database:update', '0', '1', null, null, '', 'admin', '2023-04-10 17:24:26', '', null);
INSERT INTO `sso_menu` VALUES ('3a74d165bcd286f102e10a1be8c23eef', '60efb66a88ab33b339718eb0d052a033', 'system', '000020000900004', '3', '状态修改', '#', '4', '2', '', null, 'sys:file:status,sys:file:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:51:47', '', null);
INSERT INTO `sso_menu` VALUES ('3c4b2d0f7558d7f45a29fd9c6a7edea7', '3d1efa154266719e6322808064df4b13', 'system', '000020001100001', '3', '查询', '#', '1', '2', '', null, 'sys:database:query', '0', '1', null, null, '', 'admin', '2023-04-05 21:55:09', '', null);
INSERT INTO `sso_menu` VALUES ('3d1efa154266719e6322808064df4b13', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200011', '2', '数据库', 'ion:server-outline', '11', '1', '/db-connect', '/sys/db-connect/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-04-05 21:55:09', 'admin', '2023-04-05 21:57:02');
INSERT INTO `sso_menu` VALUES ('3eeebb9f5fb02143ef9622f292faa7e0', '1d4a94508e926e493bb88b0e2c8bea4a', 'system', '0001200002', '2', '内容管理', 'ion:list-circle-outline', '2', '0', '/article-list', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:17:39', '', null);
INSERT INTO `sso_menu` VALUES ('43ca0b3ba70c7ba5a2f91882f618208b', '3d1efa154266719e6322808064df4b13', 'system', '000020001100004', '3', '删除', '#', '4', '2', '', null, 'sys:database:query,sys:database:delete', '0', '1', null, null, '', 'admin', '2023-04-05 21:55:09', '', null);
INSERT INTO `sso_menu` VALUES ('4527c6c05549e3594f135ac056faaece', '70943d8248fd8f77ade038d9afa0bf33', 'system', '0001100003', '2', '引导页', 'ant-design:format-painter-outlined', '19', '1', '/setup', '/demo/setup/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-05-05 22:12:53', '', null);
INSERT INTO `sso_menu` VALUES ('4bfec85ae3174915cd2a3e8ddd822220', '70943d8248fd8f77ade038d9afa0bf33', 'system', '0001100004', '2', '关于', 'simple-icons:aboutdotme', '21', '1', '/about', '/sys/about/index.vue', null, '0', '1', null, '0', '', 'admin', '2023-05-05 22:13:00', '', null);
INSERT INTO `sso_menu` VALUES ('4cabb05e97ea7a738a2f7ce3c9d224d8', '9d5397b6ddb4d194a95b05f42b80445b', 'system', '000090000100004', '3', '删除', '#', '4', '2', '', null, 'sys:job:delete,sys:job:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:11:24', '', null);
INSERT INTO `sso_menu` VALUES ('4ef7029abe93c11601678ba16dac406f', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200002', '2', '用户管理', 'ion:people-sharp', '4', '1', '/account', '/sys/account/index.vue', '', '0', '1', null, '1', '', 'admin', '2022-11-30 16:40:28', 'admin', '2023-05-05 21:50:45');
INSERT INTO `sso_menu` VALUES ('503e3ac379a2e17e99105b77a727e6db', '', 'system', '00001', '1', '驾驶舱', 'ant-design:appstore-outlined', '1', '0', '/dashboard', null, null, '0', '1', null, null, '', 'admin', '2022-11-08 16:53:57', 'admin', '2023-01-05 14:46:43');
INSERT INTO `sso_menu` VALUES ('5271166ced06a95d787dc049d3f19bd2', '3d1efa154266719e6322808064df4b13', 'system', '000020001100003', '3', '修改', '#', '3', '2', '', null, 'sys:database:query,sys:database:update', '0', '1', null, null, '', 'admin', '2023-04-05 21:55:09', '', null);
INSERT INTO `sso_menu` VALUES ('58efbcc5f46b95aeab069076031959e7', 'addeaf01bc278e216de75ad26a8f27b6', 'system', '000020000300003', '3', '修改', '#', '3', '2', '', null, 'sys:org:update,sys:org:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:28:45', 'admin', '2022-12-20 11:45:25');
INSERT INTO `sso_menu` VALUES ('5ad16d38964bf541b6417b07ddf33d9b', '6aee07bfe60f4ee4021bfce397a8f4df', 'system', '000020001200002', '3', '新增', '#', '2', '2', '', null, 'sys:database:query,sys:database:insert', '0', '1', null, null, '', 'admin', '2023-04-10 17:24:11', '', null);
INSERT INTO `sso_menu` VALUES ('5b543a83371c766788047a1a1907cffd', '9c6f4eff70d7b2048f63adf229c5d30d', 'system', '000110000200001', '3', '目录1', 'ion:folder-open-outline', '1', '0', '/menu1', null, null, '0', '1', null, null, '', 'admin', '2023-05-05 22:12:40', '', null);
INSERT INTO `sso_menu` VALUES ('5c723efc132b50c0284d79eaafed5a0f', '6e491486dc4cb475e4bd037d06ab2801', 'system', '0000300003', '2', 'AntDesign文档', 'ion:social-angular-outline', '4', '1', '/ant', 'https://2x.antdv.com/docs/vue/introduce-cn/', null, '1', '1', null, '1', '', 'admin', '2022-12-14 15:38:22', 'admin', '2023-03-09 10:36:45');
INSERT INTO `sso_menu` VALUES ('5e18c4ec8374cda3b7d56fae728bdc27', '', 'system', '00014', '1', '企业管理', 'simple-icons:compropago', '5', '0', '/company', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:04:30', '', null);
INSERT INTO `sso_menu` VALUES ('60efb66a88ab33b339718eb0d052a033', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200009', '2', '文件管理', 'ion:file-tray-full-outline', '9', '1', '/file', '/storage/sys-file/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-03-02 14:58:44', 'admin', '2023-04-11 22:23:20');
INSERT INTO `sso_menu` VALUES ('67dfbce31013ada62800425f72997962', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200006', '2', '字典项', 'ion:ios-menu', '6', '1', '/dict/:dictCode', '/sys/dict-item/index.vue', null, '0', '0', '0000200005', '0', '', 'admin', '2023-01-03 17:07:39', 'admin', '2023-05-06 15:07:10');
INSERT INTO `sso_menu` VALUES ('694e6ae530d622c15b50799837fe4b8c', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200014', '2', '区域管理', 'simple-icons:googlemaps', '8', '1', '/region', '/sys/region/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-05-06 14:18:21', 'admin', '2023-05-06 14:22:19');
INSERT INTO `sso_menu` VALUES ('6a38a3847b66cc690c3a2eacedb4e81f', '76f68d05f5054818762718ee85d6d0fe', 'system', '000010000100003', '3', '修改', '#', '3', '2', '', null, 'sys:workbench:update', '0', '1', null, null, '', 'admin', '2023-03-10 17:15:10', '', null);
INSERT INTO `sso_menu` VALUES ('6ac6bc8054107436e24356e3466f00db', '4ef7029abe93c11601678ba16dac406f', 'system', '000020000200001', '3', '查询', '#', '1', '2', '', null, 'sys:account:query', '0', '1', null, null, '', 'admin', '2022-11-30 16:54:15', 'admin', '2022-12-20 11:46:51');
INSERT INTO `sso_menu` VALUES ('6aee07bfe60f4ee4021bfce397a8f4df', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200012', '2', '数据源', 'ant-design:cloud-server-outlined', '12', '1', '/database', '/sys/database/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-04-05 21:55:21', '', null);
INSERT INTO `sso_menu` VALUES ('6e02e0e621140968dc62a2ce3dfa198d', '9d5397b6ddb4d194a95b05f42b80445b', 'system', '000090000100002', '3', '新增', '#', '2', '2', '', null, 'sys:job:insert,sys:job:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:11:24', '', null);
INSERT INTO `sso_menu` VALUES ('6e491486dc4cb475e4bd037d06ab2801', '', 'system', '00003', '1', '项目文档', 'ion:book-outline', '15', '0', '/doc', null, null, '1', '1', null, null, '', 'admin', '2022-11-08 17:08:24', 'admin', '2023-05-06 11:04:43');
INSERT INTO `sso_menu` VALUES ('6fd5cdaf86772d4db0587f3b9281f99b', 'a988f38821885f8f8aaffa49d681aaac', 'system', '000020000100003', '3', '修改', '#', '3', '2', '', null, 'sys:menu:update,sys:menu:query', '0', '1', null, null, '', 'admin', '2022-11-08 17:05:12', 'admin', '2022-12-20 11:33:37');
INSERT INTO `sso_menu` VALUES ('70943d8248fd8f77ade038d9afa0bf33', '', 'system', '00011', '1', '系统工具', 'ant-design:tool-outlined', '13', '0', '/tools', null, null, '0', '1', null, null, '', 'admin', '2023-03-29 10:26:55', 'admin', '2023-05-06 11:03:10');
INSERT INTO `sso_menu` VALUES ('731738ed9bbd2e36456b790dfadcb84e', '60efb66a88ab33b339718eb0d052a033', 'system', '000020000900001', '3', '查询', '#', '1', '2', '', null, 'sys:file:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:15:47', 'admin', '2023-03-10 10:17:31');
INSERT INTO `sso_menu` VALUES ('75882dc140444e061741fbd9f026dd2b', 'a988f38821885f8f8aaffa49d681aaac', 'system', '000020000100001', '3', '查询', '#', '1', '2', '', null, 'sys:menu:query', '0', '1', null, null, '', 'admin', '2022-11-08 17:04:16', 'admin', '2022-11-30 16:59:33');
INSERT INTO `sso_menu` VALUES ('76f149981f1c86fce81f2f4cdb9674b9', 'addeaf01bc278e216de75ad26a8f27b6', 'system', '000020000300001', '3', '查询', '#', '1', '2', '', null, 'sys:org:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:26:51', '', null);
INSERT INTO `sso_menu` VALUES ('76f68d05f5054818762718ee85d6d0fe', '503e3ac379a2e17e99105b77a727e6db', 'system', '0000100001', '2', '工作台', 'ant-design:calendar-outlined', '1', '1', '/workbench', '/dashboard/workbench/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-03-10 17:15:10', '', null);
INSERT INTO `sso_menu` VALUES ('79cbc9e257ee8f44db6b133c584ff86a', '3d1efa154266719e6322808064df4b13', 'system', '000020001100002', '3', '新增', '#', '2', '2', '', null, 'sys:database:query,sys:database:insert', '0', '1', null, null, '', 'admin', '2023-04-05 21:55:09', '', null);
INSERT INTO `sso_menu` VALUES ('7e690410346c4d3a1610d85e8c9f906b', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200007', '2', '个人设置', 'ion:person', '7', '1', '/account/setting', '/sys/account/setting/index.vue', null, '0', '1', '0000200002', '1', '', 'admin', '2023-01-04 18:14:47', 'admin', '2023-05-06 14:22:10');
INSERT INTO `sso_menu` VALUES ('7e87849f80699ad24292fd9908f5aeb8', '76f68d05f5054818762718ee85d6d0fe', 'system', '000010000100004', '3', '删除', '#', '4', '2', '', null, 'sys:workbench:delete', '0', '1', null, null, '', 'admin', '2023-03-10 17:15:10', 'admin', '2023-03-10 17:15:39');
INSERT INTO `sso_menu` VALUES ('80526081fb00ce5dbe629ef358231909', 'c9eb585420911ee18335d935d3872934', 'system', '000020000500001', '3', '查询', '#', '1', '2', '', null, 'sys:dict:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:00:35', '', null);
INSERT INTO `sso_menu` VALUES ('81ecfe903cb3116f00c367678059c87c', '6aee07bfe60f4ee4021bfce397a8f4df', 'system', '000020001200001', '3', '查询', '#', '1', '2', '', null, 'sys:database:query', '0', '1', null, null, '', 'admin', '2023-04-10 17:23:49', '', null);
INSERT INTO `sso_menu` VALUES ('896394f6e2443689c1608b1ea1ea63e9', '694e6ae530d622c15b50799837fe4b8c', 'system', '000020001400004', '3', '删除', '#', '4', '2', '', null, 'sys:region:delete,sys:region:query', '0', '1', null, null, '', 'admin', '2023-05-06 14:21:39', '', null);
INSERT INTO `sso_menu` VALUES ('8ad60664c7060f811559bde09a79dae5', '5b543a83371c766788047a1a1907cffd', 'system', '00011000020000100001', '4', '目录2', 'ion:folder-open-outline', '1', '0', '/menu2', null, null, '0', '1', null, null, '', 'admin', '2023-05-05 22:12:41', '', null);
INSERT INTO `sso_menu` VALUES ('91da61634285d620a2a9a8a0ff5c2708', '1d4a94508e926e493bb88b0e2c8bea4a', 'system', '0001200001', '2', '内容分类', 'ion:duplicate-sharp', '1', '0', '/art-category', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:16:52', '', null);
INSERT INTO `sso_menu` VALUES ('926c9b39e37070eb63bdd13908d7a1d5', '', 'system', '00015', '1', '报表系统', 'ion:apps-sharp', '6', '0', '/report', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:05:48', '', null);
INSERT INTO `sso_menu` VALUES ('967795af502129d318899a60716da84f', 'a988f38821885f8f8aaffa49d681aaac', 'system', '000020000100002', '3', '新增', '#', '2', '2', '', null, 'sys:menu:insert,sys:menu:query', '0', '1', null, null, '', 'admin', '2022-11-08 17:04:45', 'admin', '2022-12-20 11:30:37');
INSERT INTO `sso_menu` VALUES ('96f3a881b0654098ce95a696dda8025e', '34bb93e41e8968a94796078fd890462f', 'system', '0001300002', '2', '产品列表', 'ion:md-list', '2', '0', '/mat-list', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:19:33', '', null);
INSERT INTO `sso_menu` VALUES ('9a293c164762776e0a876323a3363dec', 'cfbdf3ce5297cebf806ac116fc239558', 'system', '000020000800002', '3', '删除', '#', '2', '2', '', null, 'sys.log.delete,sys.log.query', '0', '1', null, null, '', 'admin', '2023-03-10 10:07:06', '', null);
INSERT INTO `sso_menu` VALUES ('9b9139c09668bb22888201b7e8a812c4', '0f5a85a6fd5bdc9df26b826eec3c17f1', 'system', '000020000400003', '3', '修改', '#', '3', '2', '', null, 'sys:role:update,sys:role:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:58:08', 'admin', '2022-12-20 11:46:23');
INSERT INTO `sso_menu` VALUES ('9c6f4eff70d7b2048f63adf229c5d30d', '70943d8248fd8f77ade038d9afa0bf33', 'system', '0001100002', '2', '多级目录', 'ion:folder-open-outline', '6', '0', '/level', null, null, '0', '1', null, null, '', 'admin', '2023-05-05 22:12:40', '', null);
INSERT INTO `sso_menu` VALUES ('9d5397b6ddb4d194a95b05f42b80445b', 'ca0a3c9ae9cd551ee4e1b727861b7c78', 'system', '0000900001', '2', '任务管理', 'ion:caret-forward-circle-outline', '1', '1', '/job', '/scheduler/job/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-03-10 17:11:24', '', null);
INSERT INTO `sso_menu` VALUES ('9f46c219e3fc35b1c2ef3a95438b16bf', '4ef7029abe93c11601678ba16dac406f', 'system', '000020000200002', '3', '新增', '#', '2', '2', '', null, 'sys:account:insert,sys:account:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:03:02', 'admin', '2022-12-20 11:47:06');
INSERT INTO `sso_menu` VALUES ('a27822a74728632e0e0ed10d8285bf54', '4ef7029abe93c11601678ba16dac406f', 'system', '000020000200004', '3', '删除', '#', '4', '2', '', null, 'sys:account:delete,  sys:account:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:03:48', 'admin', '2022-12-20 11:47:15');
INSERT INTO `sso_menu` VALUES ('a2cdbac934bae3da9987df0655db2455', '6aee07bfe60f4ee4021bfce397a8f4df', 'system', '000020001200004', '3', '删除', '#', '4', '2', '', null, 'sys:database:query,sys:database:delete', '0', '1', null, null, '', 'admin', '2023-04-10 17:24:42', '', null);
INSERT INTO `sso_menu` VALUES ('a43c057f48b54c9038719179cf9e284d', 'ca0a3c9ae9cd551ee4e1b727861b7c78', 'system', '0000900002', '2', '任务日志', 'ion:ios-paper-outline', '2', '1', '/jobLog', '/scheduler/job-log/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-03-10 17:11:32', 'admin', '2023-04-11 22:24:06');
INSERT INTO `sso_menu` VALUES ('a606083b203d32915c4d0e649c7b7c6b', '70943d8248fd8f77ade038d9afa0bf33', 'system', '0001100001', '2', '代码生成', 'ion:code-slash-outline', '1', '1', '/code-build', '/sys/code-build/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-04-11 22:19:43', '', null);
INSERT INTO `sso_menu` VALUES ('a7145a05342033be0caa4a8f1e262f8a', '60efb66a88ab33b339718eb0d052a033', 'system', '000020000900003', '3', '删除', '#', '3', '2', '', null, 'sys:file:delete,sys:file:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:16:57', 'admin', '2023-03-10 10:17:49');
INSERT INTO `sso_menu` VALUES ('a988f38821885f8f8aaffa49d681aaac', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200001', '2', '菜单管理', 'ion:ios-menu', '1', '1', '/menu', '/sys/menu/index.vue', '', '0', '1', null, '1', '', 'admin', '2022-11-08 17:02:02', 'admin', '2022-12-14 14:25:17');
INSERT INTO `sso_menu` VALUES ('ad2ab62e13c7750dcb5b41b00cbdcf66', '9d5397b6ddb4d194a95b05f42b80445b', 'system', '000090000100003', '3', '修改', '#', '3', '2', '', null, 'sys:job:update,sys:job:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:11:24', '', null);
INSERT INTO `sso_menu` VALUES ('addeaf01bc278e216de75ad26a8f27b6', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200003', '2', '部门管理', 'ion:ios-people', '2', '1', '/org', '/sys/org/index.vue', '', '0', '1', null, '1', '', 'admin', '2022-11-30 17:22:12', 'admin', '2023-05-05 21:50:24');
INSERT INTO `sso_menu` VALUES ('b00593b27205166370c0f4c8ece55697', 'b8fbc33a036d724e2a2e993d5380890d', 'system', '0001700002', '2', '数据列表', 'ion:apps-outline', '2', '1', '/data-list', '', null, '0', '1', null, '1', '', 'admin', '2023-05-06 11:15:37', '', null);
INSERT INTO `sso_menu` VALUES ('b8fbc33a036d724e2a2e993d5380890d', '', 'system', '00017', '1', '数据采集管理', 'simple-icons:microsoftsharepoint', '2', '0', '/collection', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:10:41', '', null);
INSERT INTO `sso_menu` VALUES ('bcd18784374699438a215a9ab1e9b351', '8ad60664c7060f811559bde09a79dae5', 'system', '0001100002000010000100001', '5', '多级菜单', 'ant-design:appstore-outlined', '1', '1', '/menu3', '/demo/level/LevelMenu.vue', null, '0', '1', null, '1', '', 'admin', '2023-05-05 22:12:41', '', null);
INSERT INTO `sso_menu` VALUES ('bea31d33d125895a9eaa827863341a91', '', 'system', '00008', '1', 'ChatGpt', 'ion:chatbox-ellipses-outline', '1000', '0', '/chat', null, null, '0', '0', null, null, '', 'admin', '2023-02-08 16:45:13', 'admin', '2023-05-05 22:02:50');
INSERT INTO `sso_menu` VALUES ('c09aab8efd55f263dfced13eaa15edeb', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200013', '2', '动态表单', '#', '10', '1', '/dynamic', '', null, '0', '1', null, '1', '', 'admin', '2023-05-06 11:08:59', '', null);
INSERT INTO `sso_menu` VALUES ('c36420436629884000e73b158166f260', '9d5397b6ddb4d194a95b05f42b80445b', 'system', '000090000100005', '3', '执行', '#', '5', '2', '', null, 'sys:job:execute,sys:job:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:11:24', '', null);
INSERT INTO `sso_menu` VALUES ('c46042d6e6d16ea95df6461648833675', '4ef7029abe93c11601678ba16dac406f', 'system', '000020000200003', '3', '修改', '#', '3', '2', '', null, 'sys:account:update,sys:account:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:03:23', 'admin', '2022-12-17 22:26:35');
INSERT INTO `sso_menu` VALUES ('c487023e85c9aaf5510a03e8017b768c', '0f5a85a6fd5bdc9df26b826eec3c17f1', 'system', '000020000400004', '3', '删除', '#', '4', '2', '', null, 'sys:role:delete,sys:role:query', '0', '1', null, null, '', 'admin', '2022-11-30 18:02:10', 'admin', '2022-12-20 11:46:13');
INSERT INTO `sso_menu` VALUES ('c52a49da263d57d2c89edcbc9ca70a0a', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200010', '2', '在线用户', 'ion:wifi', '10', '1', '/online', '/sys/online/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-03-09 11:34:20', 'admin', '2023-03-09 15:19:05');
INSERT INTO `sso_menu` VALUES ('c9eb585420911ee18335d935d3872934', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200005', '2', '字典管理', 'ion:ios-list', '5', '1', '/dict', '/sys/dict/index.vue', null, '0', '1', null, null, '', 'admin', '2022-11-30 18:08:11', 'admin', '2022-12-14 14:26:05');
INSERT INTO `sso_menu` VALUES ('ca0a3c9ae9cd551ee4e1b727861b7c78', '', 'system', '00009', '1', '任务调度', 'ion:calendar-outline', '12', '0', '/scheduler', null, null, '0', '1', null, null, '', 'admin', '2023-02-14 17:13:20', 'admin', '2023-05-06 11:03:04');
INSERT INTO `sso_menu` VALUES ('cda6200dfb165526952bd96f9ecc7425', '694e6ae530d622c15b50799837fe4b8c', 'system', '000020001400002', '3', '新增', '#', '2', '2', '', null, 'sys:region:insert,sys:region:query', '0', '1', null, null, '', 'admin', '2023-05-06 14:20:19', '', null);
INSERT INTO `sso_menu` VALUES ('ce1a05cdedf2d0684574a30dd3ed14f9', '60efb66a88ab33b339718eb0d052a033', 'system', '000020000900002', '3', '上传', '#', '2', '2', '', null, 'sys:file:upload,sys:file:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:16:27', 'admin', '2023-03-10 10:17:40');
INSERT INTO `sso_menu` VALUES ('ce71591e16d47d6b4ff1d52c2bb83ae7', 'c9eb585420911ee18335d935d3872934', 'system', '000020000500003', '3', '修改', '#', '3', '2', '', null, 'sys:dict:update,sys:dict:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:01:46', '', null);
INSERT INTO `sso_menu` VALUES ('cfbdf3ce5297cebf806ac116fc239558', '2a4e024fdc76063da32926c63ca9ead2', 'system', '0000200008', '2', '日志管理', 'ion:ios-compose-outline', '8', '1', '/log', '/sys/sys-log/index.vue', null, '0', '1', null, '1', '', 'admin', '2023-01-08 22:17:26', 'admin', '2023-04-11 22:21:36');
INSERT INTO `sso_menu` VALUES ('d0df31b6e0ac95579c5eb5a313ca4769', '34bb93e41e8968a94796078fd890462f', 'system', '0001300001', '2', '产品分类', 'ion:list', '1', '0', '/mat-category', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:18:53', '', null);
INSERT INTO `sso_menu` VALUES ('d6a861bdeceae3a00cedcaf536f1f570', '5e18c4ec8374cda3b7d56fae728bdc27', 'system', '0001400001', '2', '企业组织管理', 'simple-icons:webcomponentsdotorg', '1', '1', '/company-list', null, null, '0', '1', null, '1', '', 'admin', '2023-05-06 13:34:51', '', null);
INSERT INTO `sso_menu` VALUES ('d9113a3f2cd8c888f48f28c7d07e8b3f', '694e6ae530d622c15b50799837fe4b8c', 'system', '000020001400001', '3', '查询', '#', '1', '2', '', null, 'sys:region:query', '0', '1', null, null, '', 'admin', '2023-05-06 14:19:10', '', null);
INSERT INTO `sso_menu` VALUES ('dc237e18a8652df63750a726b6e5505d', 'b8fbc33a036d724e2a2e993d5380890d', 'system', '0001700001', '2', '数据ETL', 'simple-icons:netlify', '1', '0', '/etl', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:14:44', '', null);
INSERT INTO `sso_menu` VALUES ('e159379c94b8fcc58ebc38cf8b322772', '0aa9f017545ec947a075f76e34c075c0', 'system', '0000700001', '2', '监控中心', 'ion:fitness-sharp', '1', '1', '/center', 'http://localhost:9223', null, '0', '1', null, '0', '', 'admin', '2023-01-27 13:56:32', 'admin', '2023-01-27 15:13:12');
INSERT INTO `sso_menu` VALUES ('e1996e92ac6cf37c0c2e40825a7af472', 'a43c057f48b54c9038719179cf9e284d', 'system', '000090000200002', '3', '删除', '#', '2', '2', '', null, 'sys:jobLog:delete,sys:jobLog:query', '0', '1', null, null, '', 'admin', '2023-03-10 17:11:32', '', null);
INSERT INTO `sso_menu` VALUES ('e48e7b98d2c0331ff6241514e97dad8b', 'c9eb585420911ee18335d935d3872934', 'system', '000020000500004', '3', '删除', '#', '4', '2', '', null, 'sys:dict:delete,sys:dict:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:02:10', '', null);
INSERT INTO `sso_menu` VALUES ('ebbaf4ed533cf70b933f88cf4be4fe23', '', 'system', '00016', '1', '数据分析', 'ion:analytics-sharp', '7', '0', '/analyse', null, null, '0', '1', null, null, '', 'admin', '2023-05-06 11:07:13', '', null);
INSERT INTO `sso_menu` VALUES ('ee3ae3a2161e8d58e2c62f340c3d7b55', 'addeaf01bc278e216de75ad26a8f27b6', 'system', '000020000300004', '3', '删除', '#', '4', '2', '', null, 'sys:org:delete,sys:org:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:29:49', 'admin', '2022-12-20 11:45:40');
INSERT INTO `sso_menu` VALUES ('f4a0ed4ca7a609aa8268399bdffcecfb', '0f5a85a6fd5bdc9df26b826eec3c17f1', 'system', '000020000400001', '3', '查询', '#', '1', '2', '', null, 'sys:role:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:53:00', '', null);
INSERT INTO `sso_menu` VALUES ('f634ba1d6d840fb1f945b4f811dd928d', 'c9eb585420911ee18335d935d3872934', 'system', '000020000500002', '3', '新增', '#', '2', '2', '', null, 'sys:dict:insert,sys:dict:query', '0', '1', null, null, '', 'admin', '2023-03-10 10:01:26', '', null);
INSERT INTO `sso_menu` VALUES ('f87d8b297eb3650834048dba7c8d2d89', 'addeaf01bc278e216de75ad26a8f27b6', 'system', '000020000300002', '3', '新增', '#', '2', '2', '', null, 'sys:org:insert,sys:org:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:27:41', 'admin', '2022-12-20 11:34:47');
INSERT INTO `sso_menu` VALUES ('fb5dac5b0b9b610ed1e996108d6445b0', '0f5a85a6fd5bdc9df26b826eec3c17f1', 'system', '000020000400002', '3', '新增', '#', '2', '2', '', null, 'sys:role:insert,sys:role:query', '0', '1', null, null, '', 'admin', '2022-11-30 17:54:39', 'admin', '2022-12-20 11:46:05');

-- ----------------------------
-- Table structure for sso_org
-- ----------------------------
DROP TABLE IF EXISTS `sso_org`;
CREATE TABLE `sso_org` (
  `id` varchar(36) NOT NULL COMMENT '组织ID',
  `parent_id` varchar(36) DEFAULT '0' COMMENT '父组织ID',
  `client_id` varchar(128) NOT NULL COMMENT '客户端ID',
  `org_code` varchar(200) DEFAULT '' COMMENT '组织编码',
  `org_level` tinyint(4) DEFAULT NULL COMMENT '组织级别',
  `org_name` varchar(30) DEFAULT '' COMMENT '组织名称',
  `org_sort` int(4) DEFAULT '0' COMMENT '排序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `org_code_index` (`org_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='组织结构表';

-- ----------------------------
-- Records of sso_org
-- ----------------------------
INSERT INTO `sso_org` VALUES ('3c8c6bc136bec3ea74f1e48237e83702', '950736539e99c0521531cc127d5b8712', 'system', '0000100004', '2', '研发二部', '2', 'mfish2', '18922222222', '22@qq.com', '研发二部', '0', '0', 'admin', '2023-03-10 17:54:44', null, null);
INSERT INTO `sso_org` VALUES ('49b4cfe17e0cf4b5472aadea0f63bc57', '', 'system', '00002', '1', '研发测试部', '3', 'test', '18933333333', 'test@qq.com', '研发测试部', '0', '0', 'admin', '2023-03-10 21:32:30', null, null);
INSERT INTO `sso_org` VALUES ('950736539e99c0521531cc127d5b8712', '', 'system', '00001', '1', '研发事业部', '1', 'mfish', '18911111111', 'manage@qq.com', '研发事业部', '0', '0', 'admin', '2023-03-10 17:49:37', null, null);
INSERT INTO `sso_org` VALUES ('a453767fa3907e320f458f254a4549ae', '49b4cfe17e0cf4b5472aadea0f63bc57', 'system', '0000200001', '2', '研发测试二部', '2', 'test2', '', '', '', '0', '0', 'admin', '2023-03-10 21:32:30', null, null);
INSERT INTO `sso_org` VALUES ('bb94731770f981fae7eec5cbb1b32bb3', '950736539e99c0521531cc127d5b8712', 'system', '0000100002', '2', '研发一部', '1', 'mfish1', '18922222222', '11@qq.com', '研发一部', '0', '0', 'admin', '2023-03-10 17:49:37', null, null);
INSERT INTO `sso_org` VALUES ('c48834a9114db0fa65164f9cb01bd509', 'e085fa0f1c15f46b5d0a9ff77658f93a', 'system', '000020000200001', '3', '测试岗1', '1', 'testgang1', '18922222222', '', '', '0', '0', 'admin', '2023-03-10 21:32:30', null, null);
INSERT INTO `sso_org` VALUES ('ce04ee45b0bad23d0492d58df92bcf0c', 'e085fa0f1c15f46b5d0a9ff77658f93a', 'system', '000020000200002', '3', '测试岗2', '2', 'testgang2', '', '', '', '0', '0', 'admin', '2023-03-10 21:32:30', null, null);
INSERT INTO `sso_org` VALUES ('e085fa0f1c15f46b5d0a9ff77658f93a', '49b4cfe17e0cf4b5472aadea0f63bc57', 'system', '0000200002', '2', '研发测试一部', '1', 'test1', '', '', '', '0', '0', 'admin', '2023-03-10 21:32:30', null, null);

-- ----------------------------
-- Table structure for sso_org_role
-- ----------------------------
DROP TABLE IF EXISTS `sso_org_role`;
CREATE TABLE `sso_org_role` (
  `role_id` varchar(36) NOT NULL COMMENT '角色ID',
  `org_id` varchar(36) NOT NULL COMMENT '组织ID',
  PRIMARY KEY (`role_id`,`org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色组织关联表';

-- ----------------------------
-- Records of sso_org_role
-- ----------------------------

-- ----------------------------
-- Table structure for sso_org_user
-- ----------------------------
DROP TABLE IF EXISTS `sso_org_user`;
CREATE TABLE `sso_org_user` (
  `org_id` varchar(36) NOT NULL COMMENT '组织ID',
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`org_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色用户关联表';

-- ----------------------------
-- Records of sso_org_user
-- ----------------------------
INSERT INTO `sso_org_user` VALUES ('3c8c6bc136bec3ea74f1e48237e83702', '40062f1156ef42b9b3a341462c927fb6');
INSERT INTO `sso_org_user` VALUES ('3c8c6bc136bec3ea74f1e48237e83702', '4ef9999a1cd0492db32c87d97659b963');
INSERT INTO `sso_org_user` VALUES ('49b4cfe17e0cf4b5472aadea0f63bc57', '97df2175b64a4b65a3cf5c1614d6bc00');
INSERT INTO `sso_org_user` VALUES ('950736539e99c0521531cc127d5b8712', '1');
INSERT INTO `sso_org_user` VALUES ('bb94731770f981fae7eec5cbb1b32bb3', 'f4056d9589a64146a7538f04c6bcc10f');

-- ----------------------------
-- Table structure for sso_role
-- ----------------------------
DROP TABLE IF EXISTS `sso_role`;
CREATE TABLE `sso_role` (
  `id` varchar(36) NOT NULL COMMENT '角色ID',
  `client_id` varchar(128) NOT NULL COMMENT '客户端ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) NOT NULL COMMENT '角色编码',
  `role_sort` int(4) DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0正常 1停用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sso_role
-- ----------------------------
INSERT INTO `sso_role` VALUES ('1', 'system', '超级管理员', 'superAdmin', '1', '0', '超级管理员', '0', 'admin', '2022-09-19 10:21:49', 'admin', '2023-05-06 11:15:46');
INSERT INTO `sso_role` VALUES ('210297727b74ecb505c1b4d97f76daee', 'system', '测试', 'test', '2', '0', '测试角色', '0', 'admin', '2022-11-29 18:37:32', 'admin', '2023-04-10 16:56:06');
INSERT INTO `sso_role` VALUES ('57ad11f7d8d94e2664f4d772a6dd9d7d', 'system', '测试1', 'test1', '3', '0', '测试角色1', '0', 'admin', '2022-11-30 12:09:59', 'admin', '2023-04-10 16:48:19');
INSERT INTO `sso_role` VALUES ('67e95f5e81b8da9a8f70db7540b7409d', 'system', '运维', 'operate', '4', '0', '运维角色', '0', 'admin', '2022-11-30 16:18:51', 'admin', '2023-02-22 16:59:27');

-- ----------------------------
-- Table structure for sso_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sso_role_menu`;
CREATE TABLE `sso_role_menu` (
  `role_id` varchar(36) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(36) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of sso_role_menu
-- ----------------------------
INSERT INTO `sso_role_menu` VALUES ('1', '0526179a70fca38a69dd709dec2f1a81');
INSERT INTO `sso_role_menu` VALUES ('1', '08a567f09c8d90660c23f2b432e0e1d9');
INSERT INTO `sso_role_menu` VALUES ('1', '0aa9f017545ec947a075f76e34c075c0');
INSERT INTO `sso_role_menu` VALUES ('1', '0f5a85a6fd5bdc9df26b826eec3c17f1');
INSERT INTO `sso_role_menu` VALUES ('1', '101cb161536a5a80731a4d6db0b5eeac');
INSERT INTO `sso_role_menu` VALUES ('1', '1a0aee8380c525e7c4802b1c4d587fa8');
INSERT INTO `sso_role_menu` VALUES ('1', '1a73215261f568088e9adeef2dbd8e44');
INSERT INTO `sso_role_menu` VALUES ('1', '1d4a94508e926e493bb88b0e2c8bea4a');
INSERT INTO `sso_role_menu` VALUES ('1', '1e1b7d50ab93ffdeca33fe5b7006eb01');
INSERT INTO `sso_role_menu` VALUES ('1', '234dc900ad6502579a51784f9ddb05d5');
INSERT INTO `sso_role_menu` VALUES ('1', '238132a09b6f761374dfd205b6388245');
INSERT INTO `sso_role_menu` VALUES ('1', '25a5783ea03e26d7844b9b7370576236');
INSERT INTO `sso_role_menu` VALUES ('1', '2628fb3c4166d6469f06fcea9b9c0c55');
INSERT INTO `sso_role_menu` VALUES ('1', '268d140daddc00dc77823c7d7c2025fb');
INSERT INTO `sso_role_menu` VALUES ('1', '2a4e024fdc76063da32926c63ca9ead2');
INSERT INTO `sso_role_menu` VALUES ('1', '34bb93e41e8968a94796078fd890462f');
INSERT INTO `sso_role_menu` VALUES ('1', '369de1bef8d1e964414f25ec6d3156bc');
INSERT INTO `sso_role_menu` VALUES ('1', '3a74d165bcd286f102e10a1be8c23eef');
INSERT INTO `sso_role_menu` VALUES ('1', '3c4b2d0f7558d7f45a29fd9c6a7edea7');
INSERT INTO `sso_role_menu` VALUES ('1', '3d1efa154266719e6322808064df4b13');
INSERT INTO `sso_role_menu` VALUES ('1', '43ca0b3ba70c7ba5a2f91882f618208b');
INSERT INTO `sso_role_menu` VALUES ('1', '4527c6c05549e3594f135ac056faaece');
INSERT INTO `sso_role_menu` VALUES ('1', '4bfec85ae3174915cd2a3e8ddd822220');
INSERT INTO `sso_role_menu` VALUES ('1', '4cabb05e97ea7a738a2f7ce3c9d224d8');
INSERT INTO `sso_role_menu` VALUES ('1', '4ef7029abe93c11601678ba16dac406f');
INSERT INTO `sso_role_menu` VALUES ('1', '503e3ac379a2e17e99105b77a727e6db');
INSERT INTO `sso_role_menu` VALUES ('1', '5271166ced06a95d787dc049d3f19bd2');
INSERT INTO `sso_role_menu` VALUES ('1', '58efbcc5f46b95aeab069076031959e7');
INSERT INTO `sso_role_menu` VALUES ('1', '5ad16d38964bf541b6417b07ddf33d9b');
INSERT INTO `sso_role_menu` VALUES ('1', '5b543a83371c766788047a1a1907cffd');
INSERT INTO `sso_role_menu` VALUES ('1', '5c723efc132b50c0284d79eaafed5a0f');
INSERT INTO `sso_role_menu` VALUES ('1', '5e18c4ec8374cda3b7d56fae728bdc27');
INSERT INTO `sso_role_menu` VALUES ('1', '60efb66a88ab33b339718eb0d052a033');
INSERT INTO `sso_role_menu` VALUES ('1', '67dfbce31013ada62800425f72997962');
INSERT INTO `sso_role_menu` VALUES ('1', '6a38a3847b66cc690c3a2eacedb4e81f');
INSERT INTO `sso_role_menu` VALUES ('1', '6ac6bc8054107436e24356e3466f00db');
INSERT INTO `sso_role_menu` VALUES ('1', '6aee07bfe60f4ee4021bfce397a8f4df');
INSERT INTO `sso_role_menu` VALUES ('1', '6e02e0e621140968dc62a2ce3dfa198d');
INSERT INTO `sso_role_menu` VALUES ('1', '6e491486dc4cb475e4bd037d06ab2801');
INSERT INTO `sso_role_menu` VALUES ('1', '6fd5cdaf86772d4db0587f3b9281f99b');
INSERT INTO `sso_role_menu` VALUES ('1', '70943d8248fd8f77ade038d9afa0bf33');
INSERT INTO `sso_role_menu` VALUES ('1', '731738ed9bbd2e36456b790dfadcb84e');
INSERT INTO `sso_role_menu` VALUES ('1', '75882dc140444e061741fbd9f026dd2b');
INSERT INTO `sso_role_menu` VALUES ('1', '76f149981f1c86fce81f2f4cdb9674b9');
INSERT INTO `sso_role_menu` VALUES ('1', '76f68d05f5054818762718ee85d6d0fe');
INSERT INTO `sso_role_menu` VALUES ('1', '79cbc9e257ee8f44db6b133c584ff86a');
INSERT INTO `sso_role_menu` VALUES ('1', '7e690410346c4d3a1610d85e8c9f906b');
INSERT INTO `sso_role_menu` VALUES ('1', '7e87849f80699ad24292fd9908f5aeb8');
INSERT INTO `sso_role_menu` VALUES ('1', '80526081fb00ce5dbe629ef358231909');
INSERT INTO `sso_role_menu` VALUES ('1', '81ecfe903cb3116f00c367678059c87c');
INSERT INTO `sso_role_menu` VALUES ('1', '8ad60664c7060f811559bde09a79dae5');
INSERT INTO `sso_role_menu` VALUES ('1', '926c9b39e37070eb63bdd13908d7a1d5');
INSERT INTO `sso_role_menu` VALUES ('1', '967795af502129d318899a60716da84f');
INSERT INTO `sso_role_menu` VALUES ('1', '9a293c164762776e0a876323a3363dec');
INSERT INTO `sso_role_menu` VALUES ('1', '9b9139c09668bb22888201b7e8a812c4');
INSERT INTO `sso_role_menu` VALUES ('1', '9c6f4eff70d7b2048f63adf229c5d30d');
INSERT INTO `sso_role_menu` VALUES ('1', '9d5397b6ddb4d194a95b05f42b80445b');
INSERT INTO `sso_role_menu` VALUES ('1', '9f46c219e3fc35b1c2ef3a95438b16bf');
INSERT INTO `sso_role_menu` VALUES ('1', 'a27822a74728632e0e0ed10d8285bf54');
INSERT INTO `sso_role_menu` VALUES ('1', 'a2cdbac934bae3da9987df0655db2455');
INSERT INTO `sso_role_menu` VALUES ('1', 'a43c057f48b54c9038719179cf9e284d');
INSERT INTO `sso_role_menu` VALUES ('1', 'a606083b203d32915c4d0e649c7b7c6b');
INSERT INTO `sso_role_menu` VALUES ('1', 'a7145a05342033be0caa4a8f1e262f8a');
INSERT INTO `sso_role_menu` VALUES ('1', 'a988f38821885f8f8aaffa49d681aaac');
INSERT INTO `sso_role_menu` VALUES ('1', 'ad2ab62e13c7750dcb5b41b00cbdcf66');
INSERT INTO `sso_role_menu` VALUES ('1', 'addeaf01bc278e216de75ad26a8f27b6');
INSERT INTO `sso_role_menu` VALUES ('1', 'b8fbc33a036d724e2a2e993d5380890d');
INSERT INTO `sso_role_menu` VALUES ('1', 'bcd18784374699438a215a9ab1e9b351');
INSERT INTO `sso_role_menu` VALUES ('1', 'bea31d33d125895a9eaa827863341a91');
INSERT INTO `sso_role_menu` VALUES ('1', 'c09aab8efd55f263dfced13eaa15edeb');
INSERT INTO `sso_role_menu` VALUES ('1', 'c36420436629884000e73b158166f260');
INSERT INTO `sso_role_menu` VALUES ('1', 'c46042d6e6d16ea95df6461648833675');
INSERT INTO `sso_role_menu` VALUES ('1', 'c487023e85c9aaf5510a03e8017b768c');
INSERT INTO `sso_role_menu` VALUES ('1', 'c52a49da263d57d2c89edcbc9ca70a0a');
INSERT INTO `sso_role_menu` VALUES ('1', 'c9eb585420911ee18335d935d3872934');
INSERT INTO `sso_role_menu` VALUES ('1', 'ca0a3c9ae9cd551ee4e1b727861b7c78');
INSERT INTO `sso_role_menu` VALUES ('1', 'ce1a05cdedf2d0684574a30dd3ed14f9');
INSERT INTO `sso_role_menu` VALUES ('1', 'ce71591e16d47d6b4ff1d52c2bb83ae7');
INSERT INTO `sso_role_menu` VALUES ('1', 'cfbdf3ce5297cebf806ac116fc239558');
INSERT INTO `sso_role_menu` VALUES ('1', 'e159379c94b8fcc58ebc38cf8b322772');
INSERT INTO `sso_role_menu` VALUES ('1', 'e1996e92ac6cf37c0c2e40825a7af472');
INSERT INTO `sso_role_menu` VALUES ('1', 'e48e7b98d2c0331ff6241514e97dad8b');
INSERT INTO `sso_role_menu` VALUES ('1', 'ebbaf4ed533cf70b933f88cf4be4fe23');
INSERT INTO `sso_role_menu` VALUES ('1', 'ee3ae3a2161e8d58e2c62f340c3d7b55');
INSERT INTO `sso_role_menu` VALUES ('1', 'f4a0ed4ca7a609aa8268399bdffcecfb');
INSERT INTO `sso_role_menu` VALUES ('1', 'f634ba1d6d840fb1f945b4f811dd928d');
INSERT INTO `sso_role_menu` VALUES ('1', 'f87d8b297eb3650834048dba7c8d2d89');
INSERT INTO `sso_role_menu` VALUES ('1', 'fb5dac5b0b9b610ed1e996108d6445b0');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '0526179a70fca38a69dd709dec2f1a81');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '08a567f09c8d90660c23f2b432e0e1d9');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '0aa9f017545ec947a075f76e34c075c0');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '101cb161536a5a80731a4d6db0b5eeac');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '1a0aee8380c525e7c4802b1c4d587fa8');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '1a73215261f568088e9adeef2dbd8e44');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '1e1b7d50ab93ffdeca33fe5b7006eb01');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '234dc900ad6502579a51784f9ddb05d5');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '238132a09b6f761374dfd205b6388245');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '25a5783ea03e26d7844b9b7370576236');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '2628fb3c4166d6469f06fcea9b9c0c55');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '268d140daddc00dc77823c7d7c2025fb');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '3a74d165bcd286f102e10a1be8c23eef');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '3c4b2d0f7558d7f45a29fd9c6a7edea7');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '3d1efa154266719e6322808064df4b13');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '43ca0b3ba70c7ba5a2f91882f618208b');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '4527c6c05549e3594f135ac056faaece');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '4bfec85ae3174915cd2a3e8ddd822220');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '4cabb05e97ea7a738a2f7ce3c9d224d8');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '503e3ac379a2e17e99105b77a727e6db');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '5271166ced06a95d787dc049d3f19bd2');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '5b543a83371c766788047a1a1907cffd');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '5c723efc132b50c0284d79eaafed5a0f');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '67dfbce31013ada62800425f72997962');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '6a38a3847b66cc690c3a2eacedb4e81f');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '6ac6bc8054107436e24356e3466f00db');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '6aee07bfe60f4ee4021bfce397a8f4df');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '6e02e0e621140968dc62a2ce3dfa198d');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '6e491486dc4cb475e4bd037d06ab2801');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '731738ed9bbd2e36456b790dfadcb84e');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '75882dc140444e061741fbd9f026dd2b');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '76f149981f1c86fce81f2f4cdb9674b9');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '76f68d05f5054818762718ee85d6d0fe');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '79cbc9e257ee8f44db6b133c584ff86a');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '7e690410346c4d3a1610d85e8c9f906b');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '7e87849f80699ad24292fd9908f5aeb8');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '80526081fb00ce5dbe629ef358231909');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '8ad60664c7060f811559bde09a79dae5');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '967795af502129d318899a60716da84f');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '9a293c164762776e0a876323a3363dec');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '9c6f4eff70d7b2048f63adf229c5d30d');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', '9f46c219e3fc35b1c2ef3a95438b16bf');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'a27822a74728632e0e0ed10d8285bf54');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'a43c057f48b54c9038719179cf9e284d');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'bcd18784374699438a215a9ab1e9b351');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'bea31d33d125895a9eaa827863341a91');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'c36420436629884000e73b158166f260');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'c487023e85c9aaf5510a03e8017b768c');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'c52a49da263d57d2c89edcbc9ca70a0a');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'ce1a05cdedf2d0684574a30dd3ed14f9');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'cfbdf3ce5297cebf806ac116fc239558');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'e159379c94b8fcc58ebc38cf8b322772');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'e1996e92ac6cf37c0c2e40825a7af472');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'e48e7b98d2c0331ff6241514e97dad8b');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'ee3ae3a2161e8d58e2c62f340c3d7b55');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'f4a0ed4ca7a609aa8268399bdffcecfb');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'f634ba1d6d840fb1f945b4f811dd928d');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'f87d8b297eb3650834048dba7c8d2d89');
INSERT INTO `sso_role_menu` VALUES ('210297727b74ecb505c1b4d97f76daee', 'fb5dac5b0b9b610ed1e996108d6445b0');
INSERT INTO `sso_role_menu` VALUES ('57ad11f7d8d94e2664f4d772a6dd9d7d', '9d5397b6ddb4d194a95b05f42b80445b');
INSERT INTO `sso_role_menu` VALUES ('57ad11f7d8d94e2664f4d772a6dd9d7d', 'a43c057f48b54c9038719179cf9e284d');
INSERT INTO `sso_role_menu` VALUES ('57ad11f7d8d94e2664f4d772a6dd9d7d', 'ca0a3c9ae9cd551ee4e1b727861b7c78');

-- ----------------------------
-- Table structure for sso_user
-- ----------------------------
DROP TABLE IF EXISTS `sso_user`;
CREATE TABLE `sso_user` (
  `id` varchar(36) NOT NULL COMMENT '唯一ID',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `old_password` varchar(200) DEFAULT NULL COMMENT '旧密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `head_img_url` varchar(255) DEFAULT NULL COMMENT '图片',
  `telephone` varchar(30) DEFAULT NULL COMMENT '电话',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` tinyint(1) DEFAULT '1' COMMENT '性别(1男0女)',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0正常 1停用',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标记(0未删除1删除)',
  `salt` varchar(255) DEFAULT NULL COMMENT '盐',
  `openid` varchar(50) DEFAULT NULL COMMENT '微信唯一id',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account_index` (`account`) USING BTREE,
  UNIQUE KEY `openid_index` (`openid`) USING BTREE,
  UNIQUE KEY `phone_index` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sso_user
-- ----------------------------
INSERT INTO `sso_user` VALUES ('1', 'admin', '18900000001', 'cgli@qq.com', '22d374999f108f1573aad145657ed698', '', '管理员', 'cefc882726394e3e8cd8df976d3c4eb5.png', '12512345678', '1984-08-08', '1', '0', '0', '452187570f682f2ddb35a216fd32460d', 'olbL54qA8qAccFNtModx6dM-Ha6w', '超级管理员', '', '2017-04-10 15:21:38', 'admin', '2023-05-06 11:12:12');
INSERT INTO `sso_user` VALUES ('40062f1156ef42b9b3a341462c927fb6', 'test', '18911111113', 'test@qq.com', 'ed22b13533c4459823c1654c8ddad3d6', 'e7bf117daf1732f94ba3590f7df80ba2', '测试', null, '02522222222', '2022-11-24', '1', '0', '0', '272cb4e5912be9cf6f371c13a28ea030', null, '我就是个搞测试的', 'admin', '2022-11-24 22:51:54', 'admin', '2023-04-10 16:50:17');
INSERT INTO `sso_user` VALUES ('4ef9999a1cd0492db32c87d97659b963', 'test1', '18911111111', 'test1@qq.com', '32a2927f98077a06f335d085a18e7ba1', 'ee508c5ee37a4b27e41ab9cc80af453b', '测试1', null, '02587654321', '2022-11-20', '0', '0', '0', '3952171ab8cb094c4abe55cc831d1c76', null, null, 'admin', '2022-11-26 22:06:09', 'admin', '2023-03-30 17:40:44');
INSERT INTO `sso_user` VALUES ('f4056d9589a64146a7538f04c6bcc10f', 'operate', '', 'operate@qq.com', '491fbb311dbf56ee0fa9261aba0cf5eb', 'b48905c015cfa5ec95dd2e7c8f9e810f', '运营', null, '0251233111', '2022-11-23', '1', '0', '0', '0662ab48ae6102c7caaa37700200ed7f', null, null, 'admin', '2022-11-24 23:33:10', 'admin', '2023-03-30 16:58:46');

-- ----------------------------
-- Table structure for sso_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sso_user_role`;
CREATE TABLE `sso_user_role` (
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  `role_id` varchar(36) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sso_user_role
-- ----------------------------
INSERT INTO `sso_user_role` VALUES ('1', '1');
INSERT INTO `sso_user_role` VALUES ('40062f1156ef42b9b3a341462c927fb6', '210297727b74ecb505c1b4d97f76daee');
INSERT INTO `sso_user_role` VALUES ('4ef9999a1cd0492db32c87d97659b963', '57ad11f7d8d94e2664f4d772a6dd9d7d');
INSERT INTO `sso_user_role` VALUES ('97df2175b64a4b65a3cf5c1614d6bc00', '210297727b74ecb505c1b4d97f76daee');
INSERT INTO `sso_user_role` VALUES ('f4056d9589a64146a7538f04c6bcc10f', '67e95f5e81b8da9a8f70db7540b7409d');
