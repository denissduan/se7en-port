create table base_privilege (
	id integer primary key auto_increment comment '主键',
	code varchar(100) comment '代码',
	name varchar(100) comment '权限名称',
	icon varchar(100) comment '图标',
	css varchar(100) comment '样式',
	state int(2) comment ',启用,,禁用',
	createTime date comment '创建时间',
	updateTime date comment '更新时间',		
	describ varchar(200) comment '描述'
);