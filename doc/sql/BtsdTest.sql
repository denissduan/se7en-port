create table btsd_test (
	name varchar(32)  comment '虚拟币名称',
	mk varchar(16)  comment '市场',
	unit_id integer  comment '组织',
	state int(2) comment '',
	create_time date  comment '创建时间',
	create_user_id integer  comment '创建人',
	update_time date  comment '更新时间',
	update_user_id integer  comment '最近修改人',
	describ varchar(200)  comment '描述',
	sindex int(11)  comment '显示顺序',
    id integer primary key auto_increment comment '主键',
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  comment '虚拟币';
