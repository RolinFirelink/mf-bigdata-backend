/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : mf_scheduler

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2023-05-06 15:38:02
*/
DROP DATABASE IF EXISTS `mf_scheduler`;
CREATE DATABASE  `mf_scheduler` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE `mf_scheduler`;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('MfishClusteredScheduler', '08ad4b1288124f4b9712649b877aa556', 'DEFAULT', ' 5 * * ? * *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('MfishClusteredScheduler', '2cc03b7e0d64485aa13278e2995360bd', 'DEFAULT', '1 * * ? * *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('MfishClusteredScheduler', '8377500878874c03b4e4b24bf47e212d', 'DEFAULT', '30 * * ? * *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('MfishClusteredScheduler', '9d44af0c2f604c7fa355902ea4a9485c', 'DFAULT', '0 * * ? * *', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job`;
CREATE TABLE `qrtz_job` (
  `id` varchar(32) NOT NULL COMMENT '任务ID',
  `job_name` varchar(100) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(100) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组',
  `job_type` tinyint(1) DEFAULT NULL COMMENT '任务类型(0 本地任务 1 RPC远程调用任务 2 MQ消息任务)',
  `class_name` varchar(100) NOT NULL COMMENT '类名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `params` varchar(500) DEFAULT NULL COMMENT '调用参数',
  `allow_concurrent` tinyint(1) DEFAULT '0' COMMENT '允许并发执行（0不允许 1允许）',
  `misfire_handler` tinyint(1) DEFAULT '3' COMMENT '过期策略（1立即执行一次 2放弃执行 ）',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `time_zone` varchar(50) DEFAULT NULL COMMENT '时区',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `remark` varchar(1000) DEFAULT '' COMMENT '备注信息',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='定时调度任务';

-- ----------------------------
-- Records of qrtz_job
-- ----------------------------
INSERT INTO `qrtz_job` VALUES ('357b8d7dac67eaf0a1a0ce369ec25462', '远程调用测试', 'DEFAULT', '1', 'cn.com.mfish.common.oauth.api.remote.RemoteUserService', 'getUserById', '[\n  \"inner\",\n  \"1\"\n]', '0', '1', '1', 'Asia/Shanghai', '1', '远程调用用户接口测试', 'admin', '2023-02-24 23:09:49', 'admin', '2023-03-01 23:08:55');
INSERT INTO `qrtz_job` VALUES ('895baafe9caa9afd089f54d7c3932a25', '任务调度测试', 'DEFAULT', '2', 'cn.com.mfish.consume.job.ConsumerJob', 'test', '', '0', '1', '1', 'Asia/Shanghai', '1', '', 'admin', '2023-02-26 19:58:23', '????', '2023-03-10 15:55:13');
INSERT INTO `qrtz_job` VALUES ('908c3637e3c9914eb97f4576f187d668', '本地调用测试', 'DFAULT', '0', 'cn.com.mfish.scheduler.job.MfJob', 'test', '[\n  \"本地测试\"\n]', '0', '1', '1', 'Asia/Shanghai', '1', '本地单个参数测试', 'admin', '2023-02-24 23:02:42', 'admin', '2023-03-01 23:08:35');

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', 'aaa:[094917e92aab2c6d01e4c24653ffcb86]', 'aaa', '', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A234D6F6E204665622032372030393A34373A35342043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22616161222C226964225C3A223039343931376539326161623263366430316534633234363533666663623836222C226A6F6247726F7570225C3A22616161222C226A6F624E616D65225C3A22616161222C226A6F6254797065225C3A302C226D6574686F644E616D65225C3A22616161222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A22222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A302C2273756273637269626573225C3A5B5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30322D32372030395C3A34375C3A3534227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', 'aaa:[8ed4620f662f0e3e4923fb92fc8e7ba3]', 'aaaa', '', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A23467269204D61722031302031313A35303A33322043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22616161222C226372656174654279225C3A2274657374222C2263726561746554696D65225C3A22323032332D30332D31302031315C3A35305C3A3332222C226964225C3A223865643436323066363632663065336534393233666239326663386537626133222C226A6F6247726F7570225C3A2261616161222C226A6F624E616D65225C3A22616161222C226A6F6254797065225C3A302C226D6574686F644E616D65225C3A22616161222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A22222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A302C2273756273637269626573225C3A5B5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', 'aaa:[e7239446a3a13db135942e86fc35c1d2]', 'aaaa', '', 'cn.com.mfish.scheduler.execute.GeneralJobExecute', '1', '0', '0', '0', 0x230D0A23467269204D61722031302031323A33353A34352043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A312C22636C6173734E616D65225C3A22616161222C226964225C3A226537323339343436613361313364623133353934326538366663333563316432222C226A6F6247726F7570225C3A2261616161222C226A6F624E616D65225C3A22616161222C226A6F6254797065225C3A302C226D6574686F644E616D65225C3A22616161222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A225B5C5C6E20205C5C2261615C5C225C5C6E5D222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A312C2273756273637269626573225C3A5B5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2274657374222C2275706461746554696D65225C3A22323032332D30332D31302031325C3A33355C3A3435227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', '任务调度测试:[895baafe9caa9afd089f54d7c3932a25]', 'DEFAULT', '', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A23467269204D61722031302030393A34353A33322043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E636F6E73756D652E6A6F622E436F6E73756D65724A6F62222C226964225C3A223839356261616665396361613961666430383966353464376333393332613235222C226A6F6247726F7570225C3A2244454641554C54222C226A6F624E616D65225C3A225C75344546425C75353241315C75384330335C75354541365C75364434425C7538424435222C226A6F6254797065225C3A322C226D6574686F644E616D65225C3A2274657374222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A22222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A302C2273756273637269626573225C3A5B7B2263726F6E225C3A222035202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30332D30312031375C3A30325C3A3238222C226964225C3A223038616434623132383831323466346239373132363439623837376161353536222C226A6F624964225C3A223839356261616665396361613961666430383966353464376333393332613235222C22737461727454696D65225C3A22323032332D30332D30312031375C3A30325C3A3238222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30332D31302030395C3A34355C3A3332227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', '本地调用测试:[908c3637e3c9914eb97f4576f187d668]', 'DFAULT', '本地单个参数测试', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A23467269204665622032342032333A34343A34322043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E7363686564756C65722E6A6F622E4D664A6F62222C226964225C3A223930386333363337653363393931346562393766343537366631383764363638222C226A6F6247726F7570225C3A22444641554C54222C226A6F624E616D65225C3A225C75363732435C75353733305C75384330335C75373532385C75364434425C7538424435222C226A6F6254797065225C3A302C226D6574686F644E616D65225C3A2274657374222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A225B5C5C6E20205C5C225C75363732435C75353733305C75364434425C75384244355C5C225C5C6E5D222C227072696F72697479225C3A312C2272656D61726B225C3A225C75363732435C75353733305C75353335355C75344532415C75353343325C75363537305C75364434425C7538424435222C22737461747573225C3A302C2273756273637269626573225C3A5B7B2263726F6E225C3A2230202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32342032335C3A30315C3A3531222C226964225C3A223964343461663063326636303463376661333535393032656134613934383563222C226A6F624964225C3A223930386333363337653363393931346562393766343537366631383764363638222C22737461727454696D65225C3A22323032332D30322D32342032335C3A30315C3A3531222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30322D32342032335C3A34345C3A3432227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', '测试1:[895baafe9caa9afd089f54d7c3932a25]', 'DEFAULT', '', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A23467269204D61722031302030393A34343A32332043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E636F6E73756D652E6A6F622E436F6E73756D65724A6F62222C226964225C3A223839356261616665396361613961666430383966353464376333393332613235222C226A6F6247726F7570225C3A2244454641554C54222C226A6F624E616D65225C3A225C75364434425C753842443531222C226A6F6254797065225C3A322C226D6574686F644E616D65225C3A2274657374222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A22222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A312C2273756273637269626573225C3A5B7B2263726F6E225C3A222035202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30332D30312031375C3A30325C3A3238222C226964225C3A223038616434623132383831323466346239373132363439623837376161353536222C226A6F624964225C3A223839356261616665396361613961666430383966353464376333393332613235222C22737461727454696D65225C3A22323032332D30332D30312031375C3A30325C3A3238222C22737461747573225C3A317D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30332D31302030395C3A34345C3A3233227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', '测试:[895baafe9caa9afd089f54d7c3932a25]', 'DEFAULT', '', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A23576564204D61722030312031373A35313A35332043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E636F6E73756D652E6A6F622E436F6E73756D65724A6F62222C226964225C3A223839356261616665396361613961666430383966353464376333393332613235222C226A6F6247726F7570225C3A2244454641554C54222C226A6F624E616D65225C3A225C75364434425C7538424435222C226A6F6254797065225C3A322C226D6574686F644E616D65225C3A2274657374222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A22222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A302C2273756273637269626573225C3A5B7B2263726F6E225C3A222035202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30332D30312031375C3A30325C3A3238222C226964225C3A223038616434623132383831323466346239373132363439623837376161353536222C226A6F624964225C3A223839356261616665396361613961666430383966353464376333393332613235222C22737461727454696D65225C3A22323032332D30332D30312031375C3A30325C3A3238222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30332D30312031375C3A35315C3A3533227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', '测试新增:[09318e65982a338088c91f9d39f693a0]', 'group', '', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A23467269204D61722031302031313A34393A32362043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22616161222C226964225C3A223039333138653635393832613333383038386339316639643339663639336130222C226A6F6247726F7570225C3A2267726F7570222C226A6F624E616D65225C3A225C75364434425C75384244355C75363542305C7535383945222C226A6F6254797065225C3A302C226D6574686F644E616D65225C3A22616161222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A22222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A312C2273756273637269626573225C3A5B7B2263726F6E225C3A22302031202A203F202A202A222C22656E6454696D65225C3A22323032352D30332D31302031315C3A31395C3A3030222C226964225C3A226365393563646438326465333465653238643435326665343866646531326130222C226A6F624964225C3A223039333138653635393832613333383038386339316639643339663639336130222C22737461727454696D65225C3A22323032332D30332D31302031315C3A31395C3A3030222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2274657374222C2275706461746554696D65225C3A22323032332D30332D31302031315C3A34395C3A3236227D0D0A);
INSERT INTO `qrtz_job_details` VALUES ('MfishClusteredScheduler', '远程调用测试:[357b8d7dac67eaf0a1a0ce369ec25462]', 'DEFAULT', '远程调用用户接口测试', 'cn.com.mfish.scheduler.execute.DisallowConcurrentJobExecute', '1', '1', '0', '0', 0x230D0A2353756E204665622032362031393A35323A30342043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E636F6D6D6F6E2E6F617574682E6170692E72656D6F74652E52656D6F74655573657253657276696365222C226964225C3A223335376238643764616336376561663061316130636533363965633235343632222C226A6F6247726F7570225C3A2244454641554C54222C226A6F624E616D65225C3A225C75384644435C75374130425C75384330335C75373532385C75364434425C7538424435222C226A6F6254797065225C3A312C226D6574686F644E616D65225C3A226765745573657242794964222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A225B5C5C6E20205C5C22696E6E65725C5C222C5C5C6E20205C5C22315C5C225C5C6E5D222C227072696F72697479225C3A312C2272656D61726B225C3A225C75384644435C75374130425C75384330335C75373532385C75373532385C75363233375C75363341355C75353345335C75364434425C7538424435222C22737461747573225C3A312C2273756273637269626573225C3A5B7B2263726F6E225C3A2231202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32362031375C3A32315C3A3233222C226964225C3A223263633033623765306436343438356161313332373865323939353336306264222C226A6F624964225C3A223335376238643764616336376561663061316130636533363965633235343632222C22737461727454696D65225C3A22323032332D30322D32362031375C3A32315C3A3233222C22737461747573225C3A317D2C7B2263726F6E225C3A223330202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32342032335C3A30395C3A3330222C226964225C3A223833373735303038373838373463303362346534623234626634376532313264222C226A6F624964225C3A223335376238643764616336376561663061316130636533363965633235343632222C22737461727454696D65225C3A22323032332D30322D32342032335C3A30395C3A3330222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30322D32362031395C3A35325C3A3034227D0D0A);

-- ----------------------------
-- Table structure for qrtz_job_log
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_log`;
CREATE TABLE `qrtz_job_log` (
  `id` varchar(36) NOT NULL COMMENT '唯一ID',
  `job_id` varchar(36) NOT NULL COMMENT '任务ID',
  `subscribe_id` varchar(36) DEFAULT NULL COMMENT '订阅ID',
  `job_name` varchar(100) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(100) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组',
  `job_type` tinyint(1) DEFAULT NULL COMMENT '任务类型(0 本地任务 1 RPC远程调用任务 2 MQ消息任务)',
  `class_name` varchar(100) NOT NULL COMMENT '类名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `params` varchar(500) DEFAULT NULL COMMENT '调用参数',
  `cron` varchar(50) DEFAULT NULL COMMENT 'cron表达式',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `cost_time` int(11) DEFAULT NULL COMMENT '耗时(单位:ms)',
  `status` tinyint(1) DEFAULT '0' COMMENT '执行状态（0开始 1调度成功 2调度失败 3执行成功 4执行失败）',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度日志';

-- ----------------------------
-- Records of qrtz_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_subscribe
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_subscribe`;
CREATE TABLE `qrtz_job_subscribe` (
  `id` varchar(36) NOT NULL COMMENT '唯一ID',
  `job_id` varchar(36) DEFAULT NULL COMMENT '任务ID',
  `cron` varchar(50) DEFAULT NULL COMMENT 'cron表达式',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='任务订阅表';

-- ----------------------------
-- Records of qrtz_job_subscribe
-- ----------------------------
INSERT INTO `qrtz_job_subscribe` VALUES ('08ad4b1288124f4b9712649b877aa556', '895baafe9caa9afd089f54d7c3932a25', ' 5 * * ? * *', '2023-03-01 17:02:28', '2025-03-01 17:02:28', '0');
INSERT INTO `qrtz_job_subscribe` VALUES ('2cc03b7e0d64485aa13278e2995360bd', '357b8d7dac67eaf0a1a0ce369ec25462', '1 * * ? * *', '2023-02-26 17:21:23', '2025-02-26 17:21:23', '0');
INSERT INTO `qrtz_job_subscribe` VALUES ('8377500878874c03b4e4b24bf47e212d', '357b8d7dac67eaf0a1a0ce369ec25462', '30 * * ? * *', '2023-02-24 23:09:30', '2025-02-24 23:09:30', '0');
INSERT INTO `qrtz_job_subscribe` VALUES ('9d44af0c2f604c7fa355902ea4a9485c', '908c3637e3c9914eb97f4576f187d668', '0 * * ? * *', '2023-02-24 23:01:51', '2025-02-24 23:01:51', '1');

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('MfishClusteredScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('MfishClusteredScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('MfishClusteredScheduler', 'niting1682215259714', '1682231709405', '20000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('MfishClusteredScheduler', '08ad4b1288124f4b9712649b877aa556', 'DEFAULT', '任务调度测试:[895baafe9caa9afd089f54d7c3932a25]', 'DEFAULT', '暂无描述', '1678434965000', '1678434912136', '1', 'PAUSED', 'CRON', '1677661348000', '1740819748000', null, '1', 0x230D0A23467269204D61722031302030393A34353A33322043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E636F6E73756D652E6A6F622E436F6E73756D65724A6F62222C226964225C3A223839356261616665396361613961666430383966353464376333393332613235222C226A6F6247726F7570225C3A2244454641554C54222C226A6F624E616D65225C3A225C75344546425C75353241315C75384330335C75354541365C75364434425C7538424435222C226A6F6254797065225C3A322C226D6574686F644E616D65225C3A2274657374222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A22222C227072696F72697479225C3A312C2272656D61726B225C3A22222C22737461747573225C3A302C2273756273637269626573225C3A5B7B2263726F6E225C3A222035202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30332D30312031375C3A30325C3A3238222C226964225C3A223038616434623132383831323466346239373132363439623837376161353536222C226A6F624964225C3A223839356261616665396361613961666430383966353464376333393332613235222C22737461727454696D65225C3A22323032332D30332D30312031375C3A30325C3A3238222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30332D31302030395C3A34355C3A3332227D0D0A);
INSERT INTO `qrtz_triggers` VALUES ('MfishClusteredScheduler', '2cc03b7e0d64485aa13278e2995360bd', 'DEFAULT', '远程调用测试:[357b8d7dac67eaf0a1a0ce369ec25462]', 'DEFAULT', '暂无描述', '1677683341000', '1677683286585', '1', 'PAUSED', 'CRON', '1677403283000', '1740561683000', null, '1', 0x230D0A2353756E204665622032362031393A35323A30342043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E636F6D6D6F6E2E6F617574682E6170692E72656D6F74652E52656D6F74655573657253657276696365222C226964225C3A223335376238643764616336376561663061316130636533363965633235343632222C226A6F6247726F7570225C3A2244454641554C54222C226A6F624E616D65225C3A225C75384644435C75374130425C75384330335C75373532385C75364434425C7538424435222C226A6F6254797065225C3A312C226D6574686F644E616D65225C3A226765745573657242794964222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A225B5C5C6E20205C5C22696E6E65725C5C222C5C5C6E20205C5C22315C5C225C5C6E5D222C227072696F72697479225C3A312C2272656D61726B225C3A225C75384644435C75374130425C75384330335C75373532385C75373532385C75363233375C75363341355C75353345335C75364434425C7538424435222C22737461747573225C3A312C2273756273637269626573225C3A5B7B2263726F6E225C3A2231202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32362031375C3A32315C3A3233222C226964225C3A223263633033623765306436343438356161313332373865323939353336306264222C226A6F624964225C3A223335376238643764616336376561663061316130636533363965633235343632222C22737461727454696D65225C3A22323032332D30322D32362031375C3A32315C3A3233222C22737461747573225C3A317D2C7B2263726F6E225C3A223330202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32342032335C3A30395C3A3330222C226964225C3A223833373735303038373838373463303362346534623234626634376532313264222C226A6F624964225C3A223335376238643764616336376561663061316130636533363965633235343632222C22737461727454696D65225C3A22323032332D30322D32342032335C3A30395C3A3330222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30322D32362031395C3A35325C3A3034227D0D0A);
INSERT INTO `qrtz_triggers` VALUES ('MfishClusteredScheduler', '8377500878874c03b4e4b24bf47e212d', 'DEFAULT', '远程调用测试:[357b8d7dac67eaf0a1a0ce369ec25462]', 'DEFAULT', '暂无描述', '1677683370000', '1677683310000', '1', 'PAUSED', 'CRON', '1677251370000', '1740409770000', null, '1', 0x230D0A2353756E204665622032362031393A35323A30342043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E636F6D6D6F6E2E6F617574682E6170692E72656D6F74652E52656D6F74655573657253657276696365222C226964225C3A223335376238643764616336376561663061316130636533363965633235343632222C226A6F6247726F7570225C3A2244454641554C54222C226A6F624E616D65225C3A225C75384644435C75374130425C75384330335C75373532385C75364434425C7538424435222C226A6F6254797065225C3A312C226D6574686F644E616D65225C3A226765745573657242794964222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A225B5C5C6E20205C5C22696E6E65725C5C222C5C5C6E20205C5C22315C5C225C5C6E5D222C227072696F72697479225C3A312C2272656D61726B225C3A225C75384644435C75374130425C75384330335C75373532385C75373532385C75363233375C75363341355C75353345335C75364434425C7538424435222C22737461747573225C3A312C2273756273637269626573225C3A5B7B2263726F6E225C3A2231202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32362031375C3A32315C3A3233222C226964225C3A223263633033623765306436343438356161313332373865323939353336306264222C226A6F624964225C3A223335376238643764616336376561663061316130636533363965633235343632222C22737461727454696D65225C3A22323032332D30322D32362031375C3A32315C3A3233222C22737461747573225C3A317D2C7B2263726F6E225C3A223330202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32342032335C3A30395C3A3330222C226964225C3A223833373735303038373838373463303362346534623234626634376532313264222C226A6F624964225C3A223335376238643764616336376561663061316130636533363965633235343632222C22737461727454696D65225C3A22323032332D30322D32342032335C3A30395C3A3330222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30322D32362031395C3A35325C3A3034227D0D0A);
INSERT INTO `qrtz_triggers` VALUES ('MfishClusteredScheduler', '9d44af0c2f604c7fa355902ea4a9485c', 'DFAULT', '本地调用测试:[908c3637e3c9914eb97f4576f187d668]', 'DFAULT', '暂无描述', '1677683340000', '1677683280000', '1', 'PAUSED', 'CRON', '1677250911000', '1740409311000', null, '1', 0x230D0A23467269204665622032342032333A34343A34322043535420323032330D0A6A6F625F646174615F6D61703D7B22616C6C6F77436F6E63757272656E74225C3A302C22636C6173734E616D65225C3A22636E2E636F6D2E6D666973682E7363686564756C65722E6A6F622E4D664A6F62222C226964225C3A223930386333363337653363393931346562393766343537366631383764363638222C226A6F6247726F7570225C3A22444641554C54222C226A6F624E616D65225C3A225C75363732435C75353733305C75384330335C75373532385C75364434425C7538424435222C226A6F6254797065225C3A302C226D6574686F644E616D65225C3A2274657374222C226D69736669726548616E646C6572225C3A312C22706172616D73225C3A225B5C5C6E20205C5C225C75363732435C75353733305C75364434425C75384244355C5C225C5C6E5D222C227072696F72697479225C3A312C2272656D61726B225C3A225C75363732435C75353733305C75353335355C75344532415C75353343325C75363537305C75364434425C7538424435222C22737461747573225C3A302C2273756273637269626573225C3A5B7B2263726F6E225C3A2230202A202A203F202A202A222C22656E6454696D65225C3A22323032352D30322D32342032335C3A30315C3A3531222C226964225C3A223964343461663063326636303463376661333535393032656134613934383563222C226A6F624964225C3A223930386333363337653363393931346562393766343537366631383764363638222C22737461727454696D65225C3A22323032332D30322D32342032335C3A30315C3A3531222C22737461747573225C3A307D5D2C2274696D655A6F6E65225C3A22417369612F5368616E67686169222C227570646174654279225C3A2261646D696E222C2275706461746554696D65225C3A22323032332D30322D32342032335C3A34345C3A3432227D0D0A);
