<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#sysParam").submit(function() {
				return formSubmit($('#sysParam'),function(){
					alert("执行成功");
					loadMain('sysParam/init.do')
				});
			});
		});
	})();
</script>
<div class="page-header">系统参数</div>
<form id="sysParam" action="sysParam/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${sysParam.id }" >
	<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="module">子系统</label>
			<div class="col-sm-9">
				<input type="text" id="module" name="module" class="col-xs-10 col-sm-5" value="${sysParam.module }" placeholder="">
			</div>
		</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="describ">描述</label>
			<div class="col-sm-9">
				<input type="text" id="describ" name="describ" class="col-xs-10 col-sm-5" value="${sysParam.describ }" placeholder="">
			</div>
		</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="pname">参数名</label>
			<div class="col-sm-9">
				<input type="text" id="pname" name="pname" class="col-xs-10 col-sm-5" value="${sysParam.pname }" placeholder="">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="pvalue">参数值</label>
			<div class="col-sm-9">
				<textarea id="pvalue" name="pvalue" class="form-control" placeholder="" maxlength="100">${sysParam.pvalue}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="pindex">顺序</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="pindex" name="pindex" />
			</div>
	    	<script type="text/javascript">
	    		$('#pindex').ace_spinner({value:'${sysParam.pindex}',min:1,max:5,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
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
			&nbsp; &nbsp; &nbsp;
			<button class="btn btn-grey" type="button" onclick="loadMain('sysParam/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>