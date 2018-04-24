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
