create table sys_param (
	id integer primary key auto_increment comment '系统参数',
	module varchar(20) comment '子系统',
	pname varchar(40) comment '参数名',
	pvalue varchar(100) comment '参数值',
	describ varchar(20) comment '描述',
	pindex int(5) comment '顺序'		
);