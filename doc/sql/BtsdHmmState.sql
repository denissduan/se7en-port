create table btsd_hmm_state (
    id integer primary key auto_increment comment '主键',
	type int(2) comment '模型类型1.日线2.时线3.五分线4.历史交易',
	coin_id varchar(16) comment '虚拟币代码',
	name varchar(32) comment '状态名称',
	no int(2) comment '编号',
	min_bound double comment '最小边界',
	max_bound double comment '最大边界'
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '比特时代hmm状态配置';
