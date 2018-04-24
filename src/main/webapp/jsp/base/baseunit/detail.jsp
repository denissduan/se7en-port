<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#baseUnit").submit(function() {
				return formSubmit($('#baseUnit'),function(data){
					if(data.sign == true){
						alert("保存成功");
						$.fn.zTree.getZTreeObj('baseUnitTree').reAsyncChildNodes(null, "refresh");
					}else{
						alert("保存失败");
					}
				});
			});
		});
	})();
</script>
<div class="page-header">组织表单</div>
<form id="baseUnit" action="baseUnit/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${baseUnit.id }" />
	<input type="hidden" name="pid" value="${empty baseUnit.pid ? 1 : baseUnit.pid}" />
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="pid">父节点</label>
		<div class="col-sm-9">
			<input type="text" id="parentName" class="col-xs-10 col-sm-5" value="${parentName }"  readonly="readonly" />
		</div>
	</div>
	<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="name">名称</label>
			<div class="col-sm-9">
				<input type="text" id="name" name="name" class="col-xs-10 col-sm-5" value="${baseUnit.name }" placeholder="">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="area">区域</label>
			<div class="col-sm-9">
				<input type="hidden" id="area" name="area.id" class="col-xs-10 col-sm-5" value="${baseUnit.area.id }" placeholder="">
				<input type="text" id="area_el" class="col-xs-10 col-sm-5" value="${baseUnit.area.name }" placeholder="" readonly="readonly">
				<span class="input-group-btn">
					<button class="btn btn-sm btn-light" type="button" onclick='relOpen("baseArea/query.do","area")'>
						<i class="ace-icon fa fa-hand-o-right bigger-110"></i>
					</button>
				</span>
			</div>
		</div>		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="state">状态</label>
			<div class="col-sm-9">
				<select id="state" name="state" size="1">
					<option value="1" ${baseUnit.state eq "1" ? "selected" : ""}>启用</option>
					<option value="0" ${baseUnit.state eq "0" ? "selected" : ""}>禁用</option>
				</select>
			</div>
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
			&nbsp; &nbsp; &nbsp;
			<button class="btn" type="button" onclick="loadPage($('#menuInfo'),'baseUnit/list.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>