create table base_area (
	id integer primary key auto_increment ,
	describ varchar(400) comment '描述',
	pid int(11) comment '父节点',		
	name varchar(80) comment '名称',
	lindex int(11) comment '左顺序',		
	rindex int(11) comment '右顺序',		
	level int(4) comment '层级'		
);