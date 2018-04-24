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
