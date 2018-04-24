create table base_menu (
	id integer primary key auto_increment comment '菜单',
	pid int(11) comment '父节点',		
	name varchar(40) comment '名称',
	level int(4) comment '层级',
	icon varchar(40) comment '图标',
	url varchar(100) comment '链接',
	open int(2) comment '是否展开',	
	data varchar(100) comment '节点数据',
	sindex int comment '显示顺序',		
	state int(1) comment '状态',		
	lindex int comment '左顺序',		
	rindex int comment '右顺序'		
);