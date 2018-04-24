<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#baseRole").submit(function() {
				return formSubmit($('#baseRole'));
			});
		});
	})();
</script>
<div class="page-header">用户角色</div>
<form id="baseRole" action="baseRole/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${baseRole.id }" >
	<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="name">角色名称</label>
			<div class="col-sm-9">
				<input type="text" id="name" name="name" class="col-xs-10 col-sm-5" value="${baseRole.name }" placeholder="">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="unit">所属部门</label>
			<div class="col-sm-9">
				<input type="hidden" id="unit" name="unit.id" class="col-xs-10 col-sm-5" value="${baseRole.unit.id }" placeholder="">
				<input type="text" id="unit_el" class="col-xs-10 col-sm-5" value="${baseRole.unit.name }" placeholder="" readonly="readonly">
				<span class="input-group-btn">
					<button class="btn btn-sm btn-light" type="button" onclick='relOpen("baseUnit/query.do","unit")'>
						<i class="ace-icon fa fa-hand-o-right bigger-110"></i>
					</button>
				</span>
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
			<button class="btn btn-grey" type="button" onclick="loadMain('baseRole/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>