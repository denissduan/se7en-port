create table btsd_his_trade (
    id integer primary key auto_increment comment '主键',
	coin_id varchar(16)  comment '虚拟币代码',
	date varchar(32)  comment '日期',
	price double  comment '成交价',
	volume double  comment '成交量',
	type int(2) comment ''
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币历史成交';
