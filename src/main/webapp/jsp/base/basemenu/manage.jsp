<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		var zTree,menuDiv;
		
		/**
		 * 隐藏菜单
		 */
		function hideRMenu() {
			var rMenu = $("#ztree_rmenu");
			if (rMenu.length > 0)
				rMenu.css({
					"visibility" : "hidden"
				});
		}
	
		//新增菜单
		function newBaseMenu(){
			hideRMenu();
			var newNode = {
				name : "新增菜单"
			};
			if (zTree.getSelectedNodes()[0]) {
				var pNode = zTree.getSelectedNodes()[0];
				if(pNode.id == null){	//父节点是新增节点,不能继续新增
					alert("请先保存该节点");
					return;
				}
				newNode.pid = pNode.id;
				newNode = zTree.addNodes(pNode, newNode)[0];
			} else {
				newNode.pid = -1;
				newNode = zTree.addNodes(null, newNode)[0];
			}
			
			loadPage($('#menuInfo'),'baseMenu/detail.do?pid=' + newNode.pid ,{'parentName' : getParentName(newNode)});
		}
		
		//编辑菜单
		function editBaseMenu(){
			hideRMenu();
			var node = zTree.getSelectedNodes()[0];
			var url = 'baseMenu/detail.do?id=' + node.id;
			//存在拖拽父节点
			if(node.getParentNode() != null){
				url += '&pid=' + node.getParentNode().id;
			}
			if(node != null){
				loadPage($('#menuInfo'),url ,{'parentName' : getParentName(node)});
			}
		}
		
		//删除菜单
		function delBaseMenu(){
			hideRMenu();
			var id = zTree.getSelectedNodes()[0].id;
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length > 0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。";
					alert(msg);
					return;
				}
				confirm("确定删除?",function(){
					ajaxGet("baseMenu/delete.do?id="+id,function(data){
						if(data.sign == true){
							alert("删除成功");
							$.fn.zTree.getZTreeObj('baseMenuTree').reAsyncChildNodes(null, "refresh");
						}else{
							alert("删除失败,请检查是否是板块id重复");
						}
					});
				});
			}
		}
		
		function getParentName(node){
			var str = node.getParentNode() == null ? '' : node.getParentNode().name;
			if(str.indexOf('</span>') != -1){
				str = str.substr(str.indexOf('</span>') + 8);
			}
			return str;
		}
		
		/**
		 * 拖拽节点释放响应事件
		 */
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy){
			editBaseMenu();
		}
	
		$(function(){
			var rets = $('#baseMenuTree').rightMenuTreeCapability({
				menus : [
					{id : "newBlock",name : "新增菜单",click: newBaseMenu,showType : "both"},
					{id : "editBlock",name : "编辑菜单",click: editBaseMenu,showType : "node"},
					{id : "delBlock",name : "删除菜单",click: delBaseMenu,showType : "node"}
				],
				setting : {
					async : {
						enable : true,
						url : "baseMenu/nodes.do"
					},
					edit : {
						enable : true,
						showRemoveBtn : false,
						showRenameBtn : false
					},
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "pid",
							rootPId : 1
						}
					},
					callback : {
						onClick : editBaseMenu
					},
					view : {
						nameIsHTML : true,
						showIcon : false
					}
				}
			});
			zTree = rets[0];
			menuDiv = rets[1];
			
			$('#baseMenuTree').dragTreeCapability({
				setting : {
					callback : {
						onDrop : onDrop,
						onNodeCreated : function(event, treeId, treeNode){
							var treeObj = $.fn.zTree.getZTreeObj(treeId);
							var name = treeNode.name;
							if(treeNode.name.indexOf('</span>') == -1){
								name = "<span class='ace-icon " + treeNode.icon + "' style='font-family:FontAwesome;'></span> " + treeNode.name;
							}
							treeNode.name = name;
							treeObj.updateNode(treeNode);
						}
					}
				}
			});
			
			loadPage($('#menuInfo'),'baseMenu/detail.do');
		})
	})();
</script>
<div class="row">
	<div class="col-sm-4">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="widget-title lighter smaller">菜单树</h4>
			</div>

			<div class="widget-body">
				<div class="widget-main padding-8">
					<ul class="ztree" id="baseMenuTree" ></ul>
				</div>
			</div>
		</div>
	</div>

	<div class="col-sm-8">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="widget-title lighter smaller">菜单信息</h4>
			</div>

			<div class="widget-body">
				<div class="widget-main padding-8">
					<div id="menuInfo" ></div>
				</div>
			</div>
		</div>
	</div>
</div>