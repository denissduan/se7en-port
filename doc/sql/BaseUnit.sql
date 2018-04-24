create table base_unit (
	id integer primary key auto_increment comment '组织',
	area_id integer comment '区域',
		
	state int(2) comment '1,启用,0,禁用',
		
	phone varchar(20) comment '办公电话',
	pid int(11) comment '父节点',		
	name varchar(80) comment '名称',
	lindex int(11) comment '左顺序',		
	rindex int(11) comment '右顺序',		
	level int(4) comment '层级'		
);