USE mf_system;
ALTER table sys_storage ADD COLUMN `business_id` VARCHAR (32) NOT NULL default '1' COMMENT '业务id';
 ALTER table sys_storage  ADD COLUMN `business_type` VARCHAR (50) default 'system'  COMMENT '业务类型(业务表名)';