create table base_user (
	id integer primary key auto_increment ,
	name varchar(40) comment '用户名',
	password varchar(40) comment '登录密码',
	phone varchar(20) comment '号码',
	number varchar(40) comment '编号',
	unit_id integer comment '组织'
);