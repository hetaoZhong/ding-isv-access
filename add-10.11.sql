CREATE TABLE `isv_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pl',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(128) NOT NULL COMMENT '微应用套件key',
  `app_id` bigint(20) NOT NULL COMMENT '服务窗appid,此id来自于开发者中心',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_app` (`suite_key`,`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='isv创建的服务窗app';


CREATE TABLE `isv_corp_suite_jsapi_channel_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `corp_id` varchar(100) NOT NULL COMMENT '钉钉企业id',
  `corp_channel_jsapi_ticket` varchar(256) NOT NULL COMMENT '企业服务窗js_ticket',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`suite_key`,`corp_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='企业使用服务窗jsapi的js ticket表';


CREATE TABLE `isv_corp_channel_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `agent_id` bigint(20) NOT NULL COMMENT '钉钉企业使用的服务窗应用ID',
  `agent_name` varchar(128) NOT NULL COMMENT '钉钉企业使用的服务窗应用名称',
  `logo_url` varchar(1024) DEFAULT NULL COMMENT '钉钉企业使用的服务窗应用图标',
  `app_id` bigint(20) NOT NULL COMMENT '钉钉企业使用的服务窗应用原始ID',
  `corp_id` varchar(128) NOT NULL COMMENT '使用微应用的企业ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_app` (`corp_id`,`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='企业服务窗应用信息表';

ALTER TABLE `isv_corp_suite_auth`
MODIFY COLUMN `permanent_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '临时授权码或永久授权码value' AFTER `suite_key`,
ADD COLUMN `ch_permanent_code`  varchar(255) NULL COMMENT '企业服务窗永久授权码' AFTER `permanent_code`;


CREATE TABLE `isv_corp_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `corp_id` varchar(255) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  `dept_parent_id` bigint(20) DEFAULT NULL,
  `dept_sub_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `org_dept_manager` text NOT NULL,
  `auto_create_group` tinyint(4) NOT NULL,
  `auto_add_user` tinyint(4) NOT NULL,
  `dept_group_owner` varchar(255) NOT NULL,
  `dept_order` int(11) NOT NULL,
  `dept_hiding` tinyint(4) NOT NULL,
  `dept_perimit_list` text NOT NULL,
  `outer_dept` tinyint(4) NOT NULL,
  `outer_permit_user` text NOT NULL,
  `outer_permit_dept` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_corp_dept` (`corp_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业部门详情表'





