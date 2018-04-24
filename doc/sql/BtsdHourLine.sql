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
