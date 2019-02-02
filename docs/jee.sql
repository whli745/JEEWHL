/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : jee

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2019-01-31 22:17:12
*/

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
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

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
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

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
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

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
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for tl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `tl_job_log`;
CREATE TABLE `tl_job_log` (
  `tl_job_log_id` varchar(64) NOT NULL,
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组',
  `job_class` varchar(200) NOT NULL COMMENT '任务类',
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '运行时间',
  `duration` int(11) DEFAULT NULL COMMENT '总耗时（秒）',
  `msg` varchar(4000) DEFAULT NULL COMMENT '任务信息',
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`tl_job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for tl_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `tl_sys_log`;
CREATE TABLE `tl_sys_log` (
  `tl_sys_log_id` varchar(64) NOT NULL COMMENT '编号',
  `type` varchar(255) DEFAULT NULL COMMENT '操作类型(增加、修改、删除)',
  `table_name` varchar(4000) DEFAULT NULL COMMENT '被操作的表',
  `operation_detail` varchar(4000) DEFAULT NULL COMMENT '操作详细信息',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `ip` varchar(50) DEFAULT NULL COMMENT '操作ip',
  `host_name` varchar(200) DEFAULT NULL COMMENT '操作主机',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`tl_sys_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of tl_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for tr_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tr_sys_role_menu`;
CREATE TABLE `tr_sys_role_menu` (
  `ts_sys_role_id` varchar(64) NOT NULL DEFAULT '' COMMENT '角色编号',
  `ts_sys_menu_id` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

-- ----------------------------
-- Records of tr_sys_role_menu
-- ----------------------------
INSERT INTO `tr_sys_role_menu` VALUES ('1', '0');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '1');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '2');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'CA42B39FD2864EEA9553FABF839E7A6D');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '2A98FA51182041B6B669809DDB30BC19');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '3CC81ADE1F5D4EEC862AB39911013B8A');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '3351F558EADA11E7A6D0507B9DC552FD');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'AF95DBF4EAE511E7A6D0507B9DC552FD');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'C00E62F9EF7C11E79CCA507B9DC552FD');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '151554F4EF7D11E79CCA507B9DC552FD');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '6B1C3270EF7D11E79CCA507B9DC552FD');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'BBC5667467A74EDCAB1FAE40053841A8');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'A4FA9839EF9C11E79CCA507B9DC552FD');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '9F6451C6EFAF11E7A72A507B9DC552FD');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'b5a1b55bfa3a4a86a5db06a6dc681156');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '8542004A6DE744E2BCD9758B05AE4BC7');
INSERT INTO `tr_sys_role_menu` VALUES ('1', '832C1B309E114648AD0C37A2AB813F09');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'D2F1639EC6BF4E9AA6C217358CC7A952');
INSERT INTO `tr_sys_role_menu` VALUES ('1', 'C86B1BAB08484E939FF9CF504118A022');

-- ----------------------------
-- Table structure for tr_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tr_sys_user_role`;
CREATE TABLE `tr_sys_user_role` (
  `ts_sys_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编号',
  `ts_sys_role_id` varchar(64) NOT NULL DEFAULT '' COMMENT '角色编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of tr_sys_user_role
-- ----------------------------
INSERT INTO `tr_sys_user_role` VALUES ('1', '2');
INSERT INTO `tr_sys_user_role` VALUES ('1', '1');
INSERT INTO `tr_sys_user_role` VALUES ('904B86A1CCE34246AA3A4B7705A0F9FC', '2');

-- ----------------------------
-- Table structure for ts_sys_area
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_area`;
CREATE TABLE `ts_sys_area` (
  `ts_sys_area_id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` int(10) NOT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`ts_sys_area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of ts_sys_area
-- ----------------------------

-- ----------------------------
-- Table structure for ts_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_dict`;
CREATE TABLE `ts_sys_dict` (
  `ts_sys_dict_id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `name` varchar(100) NOT NULL COMMENT '标签名',
  `sort` int(11) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `edit` int(1) DEFAULT '1' COMMENT '是否前端可编辑',
  PRIMARY KEY (`ts_sys_dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of ts_sys_dict
-- ----------------------------
INSERT INTO `ts_sys_dict` VALUES ('0158F8C50EEC4A16BCE86CA0B448CC9F', '8D0519F3C1BB460DA1995259CD1F2B51', 'BUTTON', '按钮', '3', 'whli', '2019-01-17 16:04:51', 'admin', '2019-01-17 16:04:51', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('025BBD7CBC7141BB8C10E5034BFFC978', 'A34B385AC67A45AB903154E11FD3ECF6', 'WAITING', '正常', '1', 'whli', '2019-01-17 16:05:58', 'admin', '2019-01-17 16:05:58', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('06725ED554894D6FA59F047AD1360AB0', null, 'OPERATION_TYPE', '系统操作类型', '3', 'whli', '2019-01-17 16:05:16', 'admin', '2019-01-17 16:05:16', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('09383702FCD2492686C75A47B47CBE20', '8D0519F3C1BB460DA1995259CD1F2B51', 'HTML', '页面', '2', 'whli', '2019-01-17 16:04:44', 'admin', '2019-01-17 16:04:44', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('1768563673054E4A8AACF4280DFD7DFF', 'BEB9203BCD6542B5A79E978A5728C0AA', '1', '是', '2', 'whli', '2019-01-17 16:04:24', 'admin', '2019-01-17 16:04:24', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('1D16F4B8B86A43408703F624258DAF89', '3C5AAE60B8EF439FA374B95093D6008B', 'DEFAULT', '默认任务组', '1', 'whli', '2019-01-20 11:05:41', 'admin', '2019-01-20 11:05:41', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('3C5AAE60B8EF439FA374B95093D6008B', null, 'JOB_GROUP', '定时任务组', '4', 'whli', '2019-01-17 16:05:49', 'admin', '2019-01-17 16:05:49', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('4761B91E60C74BB98CF796AD125DA102', '06725ED554894D6FA59F047AD1360AB0', 'UPDATE', '修改', '2', 'whli', '2019-01-17 16:05:27', 'admin', '2019-01-17 16:05:27', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('4CC61A3FCA4C44C7B199E7CD2F8EC50C', '8D0519F3C1BB460DA1995259CD1F2B51', 'TAB', '标签', '1', 'whli', '2019-01-17 16:04:38', 'admin', '2019-01-17 16:04:38', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('8C0C80CA8566459C93073BB1A30937E0', 'A34B385AC67A45AB903154E11FD3ECF6', 'PAUSED', '暂停', '3', 'whli', '2019-01-17 16:06:07', 'admin', '2019-01-17 16:06:07', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('8D0519F3C1BB460DA1995259CD1F2B51', null, 'MENU_TYPE', '菜单类型', '2', 'whli', '2019-01-17 16:04:33', 'admin', '2019-01-17 16:04:33', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('8ED6DC5C898044F88E276005C1F296D7', '06725ED554894D6FA59F047AD1360AB0', 'ADD', '新增', '1', 'whli', '2019-01-17 16:05:22', 'admin', '2019-01-17 16:05:22', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('A34B385AC67A45AB903154E11FD3ECF6', null, 'JOB_STATUS', '定时任务状态', '5', 'whli', '2019-01-17 16:05:53', 'admin', '2019-01-17 16:05:53', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('A40D9C43EF8111E79CCA507B9DC552FD', '75ACAE9DEF8111E79CCA507B9DC552FD', 'administration', '行政', null, 'whli', '2018-01-02 13:56:14', 'whli', '2018-01-02 13:56:14', '', '1', '1');
INSERT INTO `ts_sys_dict` VALUES ('B60F59ADEF8111E79CCA507B9DC552FD', '75ACAE9DEF8111E79CCA507B9DC552FD', 'production', '生产', null, 'whli', '2018-01-02 13:56:45', 'whli', '2018-01-02 13:56:45', '', '1', '1');
INSERT INTO `ts_sys_dict` VALUES ('B74AF0D8467E4A7BA135DCAA658E2BC8', 'BEB9203BCD6542B5A79E978A5728C0AA', '0', '否', '1', 'whli', '2019-01-17 16:04:17', 'admin', '2019-01-17 16:04:17', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('BEB9203BCD6542B5A79E978A5728C0AA', null, 'YES_NO', '是与否', '1', 'whli', '2019-01-17 15:52:24', 'admin', '2019-01-17 15:52:24', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('BF6BF12DC20F41F9883C9266CDE95994', 'A34B385AC67A45AB903154E11FD3ECF6', 'ACQUIRED', '运行中', '2', 'whli', '2019-01-17 16:06:02', 'admin', '2019-01-17 16:06:02', '', '1', '0');
INSERT INTO `ts_sys_dict` VALUES ('F6AEA18451CA422FA71BD2396F9BF182', '06725ED554894D6FA59F047AD1360AB0', 'DELETE', '删除', '3', 'whli', '2019-01-17 16:05:32', 'admin', '2019-01-17 16:05:32', '', '1', '0');

-- ----------------------------
-- Table structure for ts_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_menu`;
CREATE TABLE `ts_sys_menu` (
  `ts_sys_menu_id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `menu_type` varchar(3) DEFAULT NULL,
  `sort` int(10) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`ts_sys_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of ts_sys_menu
-- ----------------------------
INSERT INTO `ts_sys_menu` VALUES ('0', null, 'JEEWHL', '1', '1', null, null, null, null, 'whli', '2018-12-28 11:20:44', 'whli', '2018-12-28 11:20:54', null, '1');
INSERT INTO `ts_sys_menu` VALUES ('1', '0', '系统管理', '1', '1', '', '', 'fa-desktop', '', 'whli', '2017-12-25 13:34:35', 'whli', '2017-12-27 19:09:49', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('151554F4EF7D11E79CCA507B9DC552FD', '1', '部门管理', '1', '5', '../system/SysOffice/SysOfficeList.html', 'TAB', 'fa-group', '', 'whli', '2018-01-02 13:23:37', 'whli', '2018-08-16 15:26:03', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('2', '1', '菜单管理', '1', '1', '../system/SysMenu/SysMenuList.html', 'TAB', 'fa-align-justify', '', 'whli', '2017-12-25 14:32:03', 'whli', '2018-08-16 15:20:21', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('2A98FA51182041B6B669809DDB30BC19', '2', '编辑', null, '2', 'btn_edit', 'BUTTON', '', null, 'whli', '2018-08-17 11:17:24', '', '2018-08-17 11:17:24', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('3351F558EADA11E7A6D0507B9DC552FD', '1', '用户管理', '1', '2', '../system/SysUser/SysUserList.html', 'TAB', 'fa-user', '', 'whli', '2017-12-27 15:47:35', 'whli', '2018-08-16 15:20:27', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('3CC81ADE1F5D4EEC862AB39911013B8A', '2', '删除', null, '3', 'btn_delete', 'BUTTON', '', null, 'whli', '2018-08-17 11:17:44', '', '2018-08-17 11:17:44', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('6B1C3270EF7D11E79CCA507B9DC552FD', '1', '区域管理', '1', '6', '../system/SysArea/SysAreaList.html', 'TAB', 'fa-map-marker', '', 'whli', '2018-01-02 13:26:01', 'whli', '2018-08-16 15:26:18', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('832C1B309E114648AD0C37A2AB813F09', '8542004A6DE744E2BCD9758B05AE4BC7', '系统日志', null, '1', '../system/SysLog/SysLogList.html', 'TAB', '', null, 'whli', '2018-12-28 13:42:36', null, null, '', '1');
INSERT INTO `ts_sys_menu` VALUES ('8542004A6DE744E2BCD9758B05AE4BC7', '0', '在线监控', null, '10', '', null, 'fa-hdd-o', null, 'whli', '2019-01-30 14:48:10', 'admin', '2019-01-30 14:48:10', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('9F6451C6EFAF11E7A72A507B9DC552FD', 'A4FA9839EF9C11E79CCA507B9DC552FD', '流程模型', '1', '1', '../oa/Model/ModelList.html', 'TAB', 'fa-level-down', '', 'whli', '2019-01-25 16:20:42', 'admin', '2019-01-25 16:20:42', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('A4FA9839EF9C11E79CCA507B9DC552FD', '0', 'OA管理', '1', '7', '', '', 'fa-anchor', '', 'whli', '2018-01-02 17:09:33', 'whli', '2018-01-22 14:33:31', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('AF95DBF4EAE511E7A6D0507B9DC552FD', '1', '角色管理', '1', '3', '../system/SysRole/SysRoleList.html', 'TAB', 'fa-child', '', 'whli', '2017-12-27 17:09:48', 'whli', '2018-08-16 15:25:43', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('b5a1b55bfa3a4a86a5db06a6dc681156', 'A4FA9839EF9C11E79CCA507B9DC552FD', '流程定义', '1', '2', '../oa/Process/ProcessList.html', 'TAB', 'fa-caret-square-o-right', '', 'whli', '2019-01-25 16:20:31', 'admin', '2019-01-25 16:20:31', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('BBC5667467A74EDCAB1FAE40053841A8', '1', '定时任务', null, '7', '../job/SysJob/SysJobList.html', 'TAB', '', null, 'whli', '2019-01-15 16:04:36', 'admin', '2019-01-15 16:04:36', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('C00E62F9EF7C11E79CCA507B9DC552FD', '1', '字典管理', '1', '4', '../system/SysDict/SysDictList.html', 'TAB', 'fa-book', '', 'whli', '2018-01-02 13:21:14', 'whli', '2018-08-16 15:21:32', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('C86B1BAB08484E939FF9CF504118A022', '8542004A6DE744E2BCD9758B05AE4BC7', 'Druid监控', null, '3', '/druid', 'HTML', '', null, 'admin', '2019-01-30 15:12:20', 'admin', '2019-01-30 15:12:20', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('CA42B39FD2864EEA9553FABF839E7A6D', '2', '新增', null, '1', 'btn_add', 'BUTTON', '', null, 'whli', '2018-08-17 11:17:06', '', '2018-08-17 11:17:06', '', '1');
INSERT INTO `ts_sys_menu` VALUES ('D2F1639EC6BF4E9AA6C217358CC7A952', '8542004A6DE744E2BCD9758B05AE4BC7', '定时任务日志', null, '2', '../job/JobLog/JobLogList.html', 'TAB', '', null, 'whli', '2019-01-15 16:04:25', 'admin', '2019-01-15 16:04:25', '', '1');

-- ----------------------------
-- Table structure for ts_sys_office
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_office`;
CREATE TABLE `ts_sys_office` (
  `ts_sys_office_id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` int(10) NOT NULL COMMENT '排序',
  `type` varchar(100) NOT NULL COMMENT '机构类型',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `deputy_person` varchar(64) DEFAULT NULL COMMENT '副负责人',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`ts_sys_office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of ts_sys_office
-- ----------------------------
INSERT INTO `ts_sys_office` VALUES ('791968da43204d96bdd048fdbbe1373b', '', '人力资源', '1', 'A40D9C43EF8111E79CCA507B9DC552FD', '', '', '', '', '', 'whli', '2018-01-19 14:02:59', 'whli', '2018-01-19 14:32:06', '', '0');

-- ----------------------------
-- Table structure for ts_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_role`;
CREATE TABLE `ts_sys_role` (
  `ts_sys_role_id` varchar(64) NOT NULL COMMENT '编号',
  `no` varchar(100) NOT NULL COMMENT '角色名称',
  `name` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`ts_sys_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of ts_sys_role
-- ----------------------------
INSERT INTO `ts_sys_role` VALUES ('1', 'administrator', '系统管理员', 'whli', '2019-01-04 09:51:17', 'whli', '2018-08-17 13:43:06', '', '1');
INSERT INTO `ts_sys_role` VALUES ('2', 'user', '普通用户', 'whli', '2019-01-04 09:51:24', 'whli', '2018-08-17 13:43:10', '', '1');

-- ----------------------------
-- Table structure for ts_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_user`;
CREATE TABLE `ts_sys_user` (
  `ts_sys_user_id` varchar(64) NOT NULL COMMENT '编号',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) NOT NULL COMMENT '邮箱',
  `phone` varchar(200) NOT NULL COMMENT '电话',
  `photo` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` timestamp NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`ts_sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of ts_sys_user
-- ----------------------------
INSERT INTO `ts_sys_user` VALUES ('1', null, 'admin', 'B9D11B3BE25F5A1A7DC8CA04CD310B28', '001', '超级管理员', '914164901@qq.com', '13800000001', '', '127.0.0.1', '2018-01-22 16:25:13', 'whli', '2017-12-24 15:10:14', 'admin', '2019-01-30 15:39:26', '', '1');
INSERT INTO `ts_sys_user` VALUES ('904B86A1CCE34246AA3A4B7705A0F9FC', null, 'zhangsan', 'C1CB843F3929978AF615FE7DFBF532CB', '002', '张三', '623374047@qq.com', '13800000002', '', null, null, 'admin', '2019-01-30 15:40:19', null, null, '', '0');
