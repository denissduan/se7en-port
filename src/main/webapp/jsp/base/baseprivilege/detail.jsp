<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#basePrivilege").submit(function() {
				return formSubmit($('#basePrivilege'));
			});
		});
	})();
</script>
<div class="page-header">按钮权限</div>
<form id="basePrivilege" action="basePrivilege/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${basePrivilege.id }" >
	<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="code">代码</label>
			<div class="col-sm-9">
				<textarea id="code" name="code" class="form-control" placeholder="" maxlength="100">${basePrivilege.code}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="name">权限名称</label>
			<div class="col-sm-9">
				<textarea id="name" name="name" class="form-control" placeholder="" maxlength="100">${basePrivilege.name}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="icon">图标</label>
			<div class="col-sm-9">
				<textarea id="icon" name="icon" class="form-control" placeholder="" maxlength="100">${basePrivilege.icon}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="css">样式</label>
			<div class="col-sm-9">
				<textarea id="css" name="css" class="form-control" placeholder="" maxlength="100">${basePrivilege.css}</textarea>
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
			<button class="btn btn-grey" type="button" onclick="loadMain('basePrivilege/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>