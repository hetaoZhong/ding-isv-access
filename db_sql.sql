CREATE DATABASE IF NOT EXISTS ding_isv_access DEFAULT CHARSET utf8mb4 COLLATE utf8_general_ci;

CREATE TABLE `isv_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pl',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(128) NOT NULL COMMENT '微应用套件key',
  `app_id` bigint(20) NOT NULL COMMENT 'appid,此id来自于开发者中心',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_app` (`suite_key`,`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='isv创建的app';

CREATE TABLE `isv_biz_lock` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `lock_key` varchar(256) NOT NULL COMMENT '锁key',
  `expire` datetime DEFAULT NULL COMMENT '过期时间,null表示不过期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_lock_key` (`lock_key`(191))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='db锁';

CREATE TABLE `isv_corp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '钉钉企业ID',
  `invite_code` varchar(64) DEFAULT NULL COMMENT '企业邀请码',
  `industry` varchar(256) DEFAULT NULL COMMENT '企业所属行业',
  `corp_name` varchar(256) DEFAULT NULL COMMENT '企业名称',
  `invite_url` varchar(1024) DEFAULT NULL COMMENT '企业邀请链接',
  `corp_logo_url` varchar(1024) DEFAULT NULL COMMENT '企业logo链接',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_id` (`corp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COMMENT='企业信息表';


CREATE TABLE `isv_corp_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `agent_id` bigint(20) NOT NULL COMMENT '钉钉企业使用的微应用ID',
  `agent_name` varchar(128) NOT NULL COMMENT '钉钉企业使用的微应用名称',
  `logo_url` varchar(1024) DEFAULT NULL COMMENT '钉钉企业使用的微应用图标',
  `app_id` bigint(20) NOT NULL COMMENT '钉钉企业使用的微应用原始ID',
  `corp_id` varchar(128) NOT NULL COMMENT '使用微应用的企业ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_app` (`corp_id`,`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COMMENT='企业微应用信息表';


CREATE TABLE `isv_corp_suite_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(255) NOT NULL COMMENT '企业corpid',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `permanent_code` varchar(255) NOT NULL COMMENT '临时授权码或永久授权码value',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_suite` (`corp_id`(191),`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COMMENT='企业对套件的授权记录';


CREATE TABLE `isv_corp_suite_auth_faile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `corp_id` varchar(100) NOT NULL COMMENT '企业id',
  `faile_info` varchar(256) DEFAULT NULL COMMENT '失败信息',
  `auth_faile_type` varchar(128) NOT NULL COMMENT '授权失败类型',
  `suite_push_type` varchar(128) NOT NULL COMMENT '推送类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_c_s_f_p` (`suite_key`,`corp_id`,`auth_faile_type`,`suite_push_type`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='企业对套件的授权失败记录';


CREATE TABLE `isv_corp_suite_callback` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '企业corpid',
  `suite_key` varchar(128) NOT NULL COMMENT '套件key',
  `callback_tag` varchar(1024) NOT NULL COMMENT '注册事件tag,json结构存储',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业注册回调事件';


CREATE TABLE `isv_corp_suite_jsapi_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `corp_id` varchar(100) NOT NULL COMMENT '钉钉企业id',
  `corp_jsapi_ticket` varchar(256) NOT NULL COMMENT '企业js_ticket',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`suite_key`,`corp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12206 DEFAULT CHARSET=utf8mb4 COMMENT='企业使用jsapi的js ticket表';


CREATE TABLE `isv_corp_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `corp_id` varchar(100) NOT NULL COMMENT '钉钉企业id',
  `corp_token` varchar(256) NOT NULL COMMENT '企业token',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`suite_key`,`corp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1823 DEFAULT CHARSET=utf8mb4 COMMENT='套件能够访问企业数据的accesstoken';


CREATE TABLE `isv_suite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_name` varchar(255) NOT NULL COMMENT '套件名字',
  `suite_key` varchar(100) NOT NULL COMMENT 'suite 的唯一key',
  `suite_secret` varchar(256) NOT NULL COMMENT 'suite的唯一secrect，与key对应',
  `encoding_aes_key` varchar(256) NOT NULL COMMENT '回调信息加解密参数',
  `token` varchar(128) NOT NULL COMMENT '已填写用于生成签名和校验毁掉请求的合法性',
  `event_receive_url` varchar(256) NOT NULL COMMENT '回调地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='套件信息表';


CREATE TABLE `isv_suite_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件suitekey',
  `ticket` varchar(100) NOT NULL COMMENT '套件ticket',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='用于接收推送的套件ticket';


CREATE TABLE `isv_suite_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `suite_token` varchar(256) NOT NULL COMMENT '套件token',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='套件的accesstoken表';


