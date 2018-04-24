<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#baseUser").submit(function() {
				return formSubmit($('#baseUser'));
			});
		});
	})();
</script>
<div class="page-header"></div>
<form id="baseUser" action="baseUser/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${baseUser.id }" >
	<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="name">用户名</label>
			<div class="col-sm-9">
				<input type="text" id="name" name="name" class="col-xs-10 col-sm-5" value="${baseUser.name }" placeholder="">
			</div>
		</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="password">登录密码</label>
			<div class="col-sm-9">
				<input type="password" id="password" name="password" class="col-xs-10 col-sm-5" value="${baseUser.password }" placeholder="">
			</div>
		</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="phone">号码</label>
			<div class="col-sm-9">
				<input type="text" id="phone" name="phone" class="col-xs-10 col-sm-5" value="${baseUser.phone }" placeholder="">
			</div>
		</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="number">编号</label>
			<div class="col-sm-9">
				<input type="text" id="number" name="number" class="col-xs-10 col-sm-5" value="${baseUser.number }" placeholder="">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="unit">组织</label>
			<div class="col-sm-9">
				<input type="hidden" id="unit" name="unit.id" class="col-xs-10 col-sm-5" value="${baseUser.unit.id }" placeholder="">
				<input type="text" id="unit_el" class="col-xs-10 col-sm-5" value="${baseUser.unit.name }" placeholder="" readonly="readonly">
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
			<button class="btn btn-grey" type="button" onclick="loadMain('baseUser/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>