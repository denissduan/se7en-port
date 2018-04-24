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
