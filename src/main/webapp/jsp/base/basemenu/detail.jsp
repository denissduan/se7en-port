<%@ page isELIgnored="false" language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		var zTree;
		$(function(){
			var nodes = [{name:"图标", t:"请点击分页按钮", id:"1", count:${count}, page:1,maxPage:${maxPage}, pageSize:15, isParent:true,open:true}];
			zTree = $('#iconTree').asyncPageCapability({
				nodes : nodes,
				url : "baseMenu/icons.do",
				setting : {
					view : {
						nameIsHTML : true,
						showIcon : false
					}
				}
			});
			//下拉菜单图标
			zTree = $("#iconTree").slideTreeCapability({
				target : $('#icon'),
				setting:{
					callback: {
						onClick : function(e, treeId, treeNode) {
							var zTree = $.fn.zTree.getZTreeObj(treeId);
							var nodes = zTree.getSelectedNodes();
							if(nodes.length > 1){
								alert("只能选择一个分类");
								return;
							}
							var name = nodes[0].name;
							var value = 'fa ' + name.substr(name.indexOf('</span>') + 8);
							$("#icon").val(value);
							zTree.hideMenu();
						}
					}
				}
			});
			
			$("#baseMenu").submit(function() {
				return formSubmit($('#baseMenu'),function(data){
					if(data.sign == true){
						alert("保存成功");
						$.fn.zTree.getZTreeObj('baseMenuTree').reAsyncChildNodes(null, "refresh");
					}else{
						alert("保存失败");
					}
				});
			});
			
			$('#dlgTest').click(function(){
				relOpen('baseMenu/query.do');
			})
		});
	})();
</script>
<div class="page-header">菜单</div>
<form id="baseMenu" action="baseMenu/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${baseMenu.id }" />
	<input type="hidden" name="pid" value="${empty baseMenu.pid ? -1 : baseMenu.pid}" />
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="pid">父节点</label>
			<div class="col-sm-9">
				<input type="text" id="parentName" class="col-xs-10 col-sm-5" value="${parentName }"  readonly="readonly" />
			</div>
		</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="name">名称</label>
			<div class="col-sm-9">
				<input type="text" id="name" name="name" class="col-xs-10 col-sm-5" value="${baseMenu.name }" placeholder="" >
			</div>
		</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="icon">图标</label>
			<div class="col-sm-9">
				<input type="text" id="icon" name="icon" class="col-xs-10 col-sm-5" value="${baseMenu.icon }" placeholder="" readonly="readonly" >
				<div style="display: none;position: absolute;z-index: 999;">
					<ul id="iconTree" class="ztree" style="border:0px solid black;width:250px;height:300px;"></ul>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="url">链接</label>
			<div class="col-sm-9">
				<textarea id="url" name="url" class="form-control" placeholder="" maxlength="100">${baseMenu.url}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="open">是否展开</label>
			<div class="checkbox">
				<label>
					<input type="checkbox" id="open" name="open" ${baseMenu.open eq "1" ? "checked" : ""} value="${baseMenu.open }" onclick="$.core.checkClick(this)" class="ace">
					<span class="lbl"> 是否展开</span>
				</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="data">节点数据</label>
			<div class="col-sm-9">
				<textarea id="data" name="data" class="form-control" placeholder="" maxlength="1">${baseMenu.data}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="sindex">显示顺序</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="sindex" name="sindex" />
			</div>
	    	<script type="text/javascript">
	    		$('#sindex').ace_spinner({value:'${baseMenu.sindex}',min:1,max:11,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
		</div>
	<div class="clearfix form-actions">
		<div class="col-md-offset-3 col-md-9">
			<button class="btn btn-info" type="submit">
				<i class="ace-icon fa fa-check bigger-110"></i>
				提交
			</button>

			&nbsp; &nbsp; &nbsp;
			<button class="btn" type="reset">
				<i class="ace-icon fa fa-undo bigger-110"></i>
				重置
			</button>
		</div>
	</div>
</form>