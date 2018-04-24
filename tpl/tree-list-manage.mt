[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
import com.se7en.service.sloth.md.searchForm
%]

[%script type="Class" name="tree-list-manage" file="WebContent/jsp/[%package.namespace.name%]/[%name.lowerCase()%]/manage.jsp" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%getAllAttributes().put("attrs")%][%name.firstLower().put("varName")%]
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
		 * 隐藏
		 */
		function hideRMenu() {
			var rMenu = $("#ztree_rmenu");
			if (rMenu.length > 0)
				rMenu.css({
					"visibility" : "hidden"
				});
		}
	
		//新增[%if (ownedComment){%][%ownedComment.body%][%}%]
		function new[%name.capitalize()%](){
			hideRMenu();
			var newNode = {
				name : "新增[%if (ownedComment){%][%ownedComment.body%][%}%]"
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
			
			loadPage($('#menuInfo'),'[%name.firstLower()%]/detail.do?pid=' + newNode.pid ,{'parentName' : getParentName(newNode)});
		}
		
		//编辑[%if (ownedComment){%][%ownedComment.body%][%}%]
		function edit[%name.capitalize()%](){
			hideRMenu();
			var node = zTree.getSelectedNodes()[0];
			var url = '[%name.firstLower()%]/detail.do?id=' + node.id;
			//存在拖拽父节点
			if(node.getParentNode() != null){
				url += '&pid=' + node.getParentNode().id;
			}
			if(node != null){
				loadPage($('#menuInfo'),url ,{'parentName' : getParentName(node)});
			}
		}
		
		//删除[%if (ownedComment){%][%ownedComment.body%][%}%]
		function del[%name.capitalize()%](){
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
					ajaxGet("[%name.firstLower()%]/delete.do?id="+id,function(data){
						if(data.sign == true){
							alert("删除成功");
							$.fn.zTree.getZTreeObj('[%name.firstLower()%]Tree').reAsyncChildNodes(null, "refresh");
						}else{
							alert("删除失败,请检查是否是板块id重复");
						}
					});
				});
			}
		}
		
		function getParentName(node){
			return node.getParentNode() == null ? '' : node.getParentNode().name;
		}
	
		$(function(){
			
			var rets = $('#[%name.firstLower()%]Tree').rightMenuTreeCapability({
				menus : [
					{id : "new[%name.capitalize()%]",name : "新增[%if (ownedComment){%][%ownedComment.body%][%}%]",click: new[%name.capitalize()%],showType : "both"},
					{id : "edit[%name.capitalize()%]",name : "编辑[%if (ownedComment){%][%ownedComment.body%][%}%]",click: edit[%name.capitalize()%],showType : "node"},
					{id : "del[%name.capitalize()%]",name : "删除[%if (ownedComment){%][%ownedComment.body%][%}%]",click: del[%name.capitalize()%],showType : "node"}
				],
				setting : {
					async : {
						enable : true,
						url : "[%name.firstLower()%]/nodes.do"
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
						onClick : edit[%name.capitalize()%]
					}
				}
			});
			zTree = rets[0];
			menuDiv = rets[1];
			
			//层级切换
			$('#[%name.firstLower()%]Tree').dragTreeCapability({
				setting : {
					callback : {
						onDrop : onDrop
					}
				}
			});
			
			function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy){
				edit[%name.capitalize()%]();
			}
			
			loadPage($('#menuInfo'),'[%name.firstLower()%]/list.do');
		})
	})();
</script>
<div class="row">
	<div class="col-sm-3">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="widget-title lighter smaller">[%if (ownedComment){%][%ownedComment.body%][%}%]树</h4>
			</div>

			<div class="widget-body">
				<div class="widget-main padding-8">
					<ul class="ztree" id="[%name.firstLower()%]Tree" ></ul>
				</div>
			</div>
		</div>
	</div>

	<div class="col-sm-9">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="widget-title lighter smaller">[%if (ownedComment){%][%ownedComment.body%][%}%]信息</h4>
			</div>

			<div class="widget-body">
				<div class="widget-main padding-8">
					<div id="menuInfo" ></div>
				</div>
			</div>
		</div>
	</div>
</div>