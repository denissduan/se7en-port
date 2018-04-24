create table base_area (
	id integer primary key auto_increment ,
	describ varchar(400) comment '描述',
	pid int(11) comment '父节点',
	name varchar(80) comment '名称',
	lindex int(11) comment '左顺序',
	rindex int(11) comment '右顺序',
	level int(4) comment '层级'
);

create table base_menu (
	id integer primary key auto_increment comment '菜单',
	pid int(11) comment '父节点',
	name varchar(40) comment '名称',
	level int(4) comment '层级',
	icon varchar(40) comment '图标',
	url varchar(100) comment '链接',
	open int(2) comment '是否展开',
	data varchar(100) comment '节点数据',
	sindex int comment '显示顺序',
	state int(1) comment '状态',
	lindex int comment '左顺序',
	rindex int comment '右顺序'
);

INSERT INTO `base_menu` VALUES (1, 0, '系统', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (2, 1, '菜单', NULL, 'fa fa-folder-open', '/baseMenu/init.do', NULL, '', 1, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (3, 1, '区域', NULL, 'fa fa-th-large', '/baseArea/init.do', NULL, '', 2, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (6, 1, '组织', NULL, 'fa fa-th-list', '/baseUnit/init.do', NULL, '', 3, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (7, 1, '用户', NULL, 'fa fa-user', '/baseUser/init.do', NULL, '', 4, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (5, 1, '角色', NULL, 'fa fa-user', '/baseRole/init.do', NULL, '', 5, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (4, 1, '权限', NULL, 'fa fa-th', '/basePrivilege/init.do', NULL, '', 6, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (8, 1, '比特时代', NULL, 'fa fa-star-o', '', 1, '', 7, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (9, 8, '币种', NULL, 'fa fa-star-o', '/btsdCoin/init.do', NULL, '', 8, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (10, 8, '日线数据', NULL, 'fa fa-star', '/btsdDayLine/init.do', NULL, '', 9, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (11, 8, '时线数据', NULL, 'fa fa-star', '/btsdHourLine/init.do', NULL, '', 10, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (12, 8, '5分钟数据', NULL, 'fa fa-star', '/btsdMin5Line/init.do', NULL, '', 11, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (13, 8, '历史成交数据', NULL, 'fa fa-star', '/btsdHisTrade/init.do', NULL, '', 12, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (14, 8, '时线数据仓库', NULL, 'fa fa-star', '/dwBtsdHourLine/init.do', NULL, '', 13, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (15, 8, '5分线数据仓库', NULL, 'fa fa-star', '/dwBtsdMin5Line/init.do', NULL, '', 14, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (16, 8, '马尔可夫状态配置', NULL, 'fa fa-star', '/btsdHmmState/init.do', NULL, '', 15, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (17, 8, '马尔可夫模型', NULL, 'fa fa-star', '/hmmBtsd/init.do', NULL, '', 16, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (18, 8, '马尔可夫明细', NULL, 'fa fa-star', '/hmmBtsdDetail/init.do', NULL, '', 17, 1, NULL, NULL);
INSERT INTO `base_menu` VALUES (19, 8, '蜡烛图', NULL, 'fa fa-star', '/btsd/candleStick.do', NULL, '', 18, 1, NULL, NULL);

create table base_privilege (
	id integer primary key auto_increment comment '主键',
	code varchar(100) comment '代码',
	name varchar(100) comment '权限名称',
	icon varchar(100) comment '图标',
	css varchar(100) comment '样式',
	state int(2) comment ',启用,,禁用',
	create_time date comment '创建时间',
	update_time date comment '更新时间',
	describ varchar(200) comment '描述'
);

create table base_role (
	id integer primary key auto_increment comment '主键',
	name varchar(80) comment '角色名称',
	unit_id integer comment '所属部门',
	state int(2) comment ',启用,,禁用',
	create_time datetime comment '创建时间',
	create_user_id integer comment '创建人',
	update_time datetime comment '更新时间',
	update_user_id integer comment '最近修改人',
	describ varchar(200) comment '描述',
	sindex int(11) comment '显示顺序'
);

create table base_role_menu (
	id integer primary key auto_increment comment '主键',
	role_id integer comment '角色',
	menu_id integer comment '菜单',
	state int(2) comment ',启用,,禁用',
	create_time datetime comment '创建时间',
	create_user_id integer comment '创建人',
	update_time datetime comment '更新时间',
	update_user_id integer comment '最近修改人',
	describ varchar(200) comment '描述',
	sindex int(11) comment '显示顺序'
);

create table base_role_menu_privilege (
	id integer primary key auto_increment comment '主键',
	role_menu_id integer comment '角色菜单',
	privilege_id integer comment '权限',
	state int(2) comment ',启用,,禁用',
	create_time datetime comment '创建时间',
	create_user_id integer comment '创建人',
	update_time datetime comment '更新时间',
	update_user_id integer comment '最近修改人',
	describ varchar(200) comment '描述',
	sindex int(11) comment '显示顺序'
);

create table base_unit (
	id integer primary key auto_increment comment '组织',
	area_id integer comment '区域',
	state int(2) comment '1,启用,0,禁用',
	phone varchar(20) comment '办公电话',
	pid int(11) comment '父节点',
	name varchar(80) comment '名称',
	lindex int(11) comment '左顺序',
	rindex int(11) comment '右顺序',
	level int(4) comment '层级'
);

create table base_user (
	id integer primary key auto_increment ,
	name varchar(40) comment '用户名',
	password varchar(40) comment '登录密码',
	phone varchar(20) comment '号码',
	number varchar(40) comment '编号',
	unit_id integer comment '组织'
);

insert into base_user (id,name,password) values (1,'admin','3mDWs9PQ4S4TAG8gGT4A5Q==');

create table sys_param (
	id integer primary key auto_increment comment '系统参数',
	module varchar(20) comment '子系统',
	pname varchar(40) comment '参数名',
	pvalue varchar(100) comment '参数值',
	describ varchar(20) comment '描述',
	pindex int(5) comment '顺序'
);

-- ----------------------------
-- 虚拟币数据字典
-- ----------------------------
create table btsd_coin(
	id varchar(16) primary key comment '编码',
	name varchar(32) comment '名称',
  mk VARCHAR(16)  comment '市场'
);

insert into btsd_coin values ('btc', '比特币', 'CNY');
insert into btsd_coin values ('ltc', '莱特币', 'CNY');
insert into btsd_coin values ('doge', '狗狗币', 'CNY');
insert into btsd_coin values ('xrp', '瑞波币', 'CNY');
insert into btsd_coin values ('bts', '比特股', 'CNY');
insert into btsd_coin values ('xlm', '恒星币', 'CNY');
insert into btsd_coin values ('nxt', '未来币', 'CNY');
insert into btsd_coin values ('blk', '黑币', 'CNY');
insert into btsd_coin values ('xem', '新经币', 'CNY');
insert into btsd_coin values ('emc', '崛起币', 'CNY');
insert into btsd_coin values ('dash', '达世币', 'CNY');
insert into btsd_coin values ('vash', '微币', 'CNY');
insert into btsd_coin values ('xzc', '零币', 'CNY');
insert into btsd_coin values ('eac', '地球币', 'CNY');
insert into btsd_coin values ('bils', '比例股', 'CNY');
insert into btsd_coin values ('bost', '增长币', 'CNY');
insert into btsd_coin values ('ppc', '点点币', 'CNY');
insert into btsd_coin values ('zcc', '招财币', 'CNY');
insert into btsd_coin values ('xpm', '质数币', 'CNY');
insert into btsd_coin values ('ybc', '元宝币', 'CNY');
insert into btsd_coin values ('apc', '苹果币', 'CNY');
insert into btsd_coin values ('dgc', '数码币', 'CNY');
insert into btsd_coin values ('mec', '美卡币', 'CNY');
insert into btsd_coin values ('bec', '比奥币', 'CNY');
insert into btsd_coin values ('unc', '联合币', 'CNY');
insert into btsd_coin values ('ric', '黎曼币', 'CNY');
insert into btsd_coin values ('src', '安全币', 'CNY');
insert into btsd_coin values ('tag', '悬赏币', 'CNY');
insert into btsd_coin values ('mgc', '众合币', 'CNY');
insert into btsd_coin values ('hlb', '活力币', 'CNY');
insert into btsd_coin values ('ncs', '资产股', 'CNY');
insert into btsd_coin values ('xcn', '氪石币', 'CNY');
insert into btsd_coin values ('sys', '系统币', 'CNY');
insert into btsd_coin values ('med', '地中海币', 'CNY');
insert into btsd_coin values ('tmc', '时代币', 'CNY');
insert into btsd_coin values ('anc', '阿侬币', 'CNY');
insert into btsd_coin values ('ardr', '阿朵', 'CNY');
insert into btsd_coin values ('wdc', '世界币', 'CNY');
insert into btsd_coin values ('qrk', '夸克币', 'CNY');

create table btsd_day_line (
  id integer primary key auto_increment comment '主键',
	coin_id varchar(32)  comment '虚拟币代码',
	date bigint comment '日期',
	datefmt varchar(32)  comment '格式化日期',
	cjl double  comment '成交量',
	kp double  comment '开盘',
	zg double  comment '最高',
	zd double  comment '最低',
	sp double  comment '收盘'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币日线';

create table btsd_hour_line (
  id integer primary key auto_increment comment '主键',
	coin_id varchar(16)  comment '虚拟币代码',
	date bigint  comment '日期',
	datefmt varchar(32)  comment '格式化日期',
	cjl double  comment '成交量',
	kp double  comment '开盘',
	zg double  comment '最高',
	zd double  comment '最低',
	sp double  comment '收盘'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币时线';

create table btsd_min5_line (
  id integer primary key auto_increment comment '主键',
	coin_id varchar(16)  comment '虚拟币代码',
	date bigint  comment '日期',
	datefmt varchar(32)  comment '格式化日期',
	cjl double  comment '成交量',
	kp double  comment '开盘',
	zg double  comment '最高',
	zd double  comment '最低',
	sp double  comment '收盘'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币5分线';

create index index_btsd_min5_line_date on btsd_min5_line(date);
create index index_btsd_min5_line_cjl on btsd_min5_line(cjl);
create index index_btsd_min5_line_kp on btsd_min5_line(kp);
create index index_btsd_min5_line_zg on btsd_min5_line(zg);
create index index_btsd_min5_line_zd on btsd_min5_line(zd);
create index index_btsd_min5_line_sp on btsd_min5_line(sp);

create table btsd_his_trade (
  id integer primary key auto_increment comment '主键',
	coin_id varchar(16)  comment '虚拟币代码',
	date varchar(32)  comment '日期',
	price double  comment '成交价',
	volume double  comment '成交量',
	type int(2) comment ''
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币历史成交';

create index index_btsd_his_trade_date on btsd_his_trade(date);
create index index_btsd_his_trade_price on btsd_his_trade(price);
create index index_btsd_his_trade_volume on btsd_his_trade(volume);
create index index_btsd_his_trade_type on btsd_his_trade(type);

create table dw_btsd_hour_line (
    id integer primary key auto_increment comment '主键',
	coin_id varchar(16)  comment '虚拟币代码',
	date bigint  comment '日期',
	datefmt varchar(32)  comment '格式化日期',
	cjl double  comment '成交量',
	kp double  comment '开盘',
	zg double  comment '最高',
	zd double  comment '最低',
	sp double  comment '收盘',
	zgscale double  comment '最高占开盘比例',
	zdscale double  comment '最低占开盘比例',
	spscale double  comment '收盘占开盘比例'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币时线数据仓库';

create table dw_btsd_min5_line (
    id integer primary key auto_increment comment '主键',
	coin_id varchar(16)  comment '虚拟币代码',
	date bigint  comment '日期',
	datefmt varchar(32)  comment '格式化日期',
	cjl double  comment '成交量',
	kp double  comment '开盘',
	zg double  comment '最高',
	zd double  comment '最低',
	sp double  comment '收盘',
	zgscale double  comment '最高占开盘幅度',
	zdscale double  comment '最低占开盘幅度',
	spscale double  comment '收盘占开盘幅度'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币5分线数据仓库';

create table hmm_btsd (
    id integer primary key auto_increment comment '主键',
	type int(2) comment '模型类型1.日线2.时线3.五分线4.历史交易',
	coin_id varchar(16) comment '虚拟币代码',
	start_date bigint comment '模型建立起始日期',
	end_date bigint comment '模型建立结束日期',
	start_datefmt varchar(32) comment '模型建立起始格式化日期',
	end_datefmt varchar(32) comment '模型建立结束格式化日期',
	state_size int(2) comment '状态数量',
	trans_probability varchar(1024) comment '状态转移概率矩阵'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币Hmm';

create table hmm_btsd_detail (
    id integer primary key auto_increment comment '主键',
	hmm_id integer comment 'hmm',
	name varchar(32) comment '状态名称',
	state_index int(2) comment '序号',
	min_bound double comment '最小边界',
	max_bound double comment '最大边界',
	pi varchar(1024) comment '状态初始化概率',
	mean varchar(1024) comment '状态mean',
	covariance varchar(1024) comment '状态协方差'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币Hmm状态明细';

create table btsd_hmm_state (
    id integer primary key auto_increment comment '主键',
	type int(2) comment '模型类型 1.日线 2.时线3.五分线4.历史交易',
	coin_id varchar(16) comment '虚拟币代码',
	name varchar(32) comment '状态名称',
	no int(2) comment '编号',
	min_bound double comment '最小边界',
	max_bound double comment '最大边界'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '比特时代hmm状态配置';

insert into btsd_hmm_state (type, coin_id, name, no, min_bound, max_bound) values (2, '', 'GREATE', 0, 0.2, 10000);
insert into btsd_hmm_state (type, coin_id, name, no, min_bound, max_bound) values (2, '', 'HIGN', 1, 0.1, 0.2);
insert into btsd_hmm_state (type, coin_id, name, no, min_bound, max_bound) values (2, '', 'UP', 2, 0.03, 0.1);
insert into btsd_hmm_state (type, coin_id, name, no, min_bound, max_bound) values (2, '', 'SHOCK', 3, -0.03, 0.03);
insert into btsd_hmm_state (type, coin_id, name, no, min_bound, max_bound) values (2, '', 'DOWN', 4, -0.1, -0.03);
insert into btsd_hmm_state (type, coin_id, name, no, min_bound, max_bound) values (2, '', 'LOW', 5, -0.2, -0.1);
insert into btsd_hmm_state (type, coin_id, name, no, min_bound, max_bound) values (2, '', 'DOWN', 4, -10000, -0.2);