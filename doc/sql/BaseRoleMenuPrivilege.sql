create table base_role_menu_privilege (
	id integer primary key auto_increment comment '主键',
	role_menu_id integer comment '角色菜单',
	privilege_id integer comment '权限',
		
	state int(2) comment ',启用,,禁用',
		
	create_time datetime comment '创建时间',		
	create_user_id integer comment '创建人',
	update_time datetime comment '更新时间',		
	update_user_id integer comment '最近修改人',
	describ varchar(200) comment '描述',
	sindex int(11) comment '显示顺序'		
);