<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#btsdHourLine").submit(function() {
				return formSubmit($('#btsdHourLine'));
			});
		});
	})();
</script>
<div class="page-header">收盘</div>
<form id="btsdHourLine" action="btsdHourLine/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${btsdHourLine.id }" >
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
			<button class="btn btn-grey" type="button" onclick="loadMain('btsdHourLine/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>