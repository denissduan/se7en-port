<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#btsdTest").submit(function() {
				return formSubmit($('#btsdTest'));
			});
		});
	})();
</script>
<div class="page-header">市场</div>
<form id="btsdTest" action="btsdTest/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${btsdTest.id }" >
	    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="id">主键</label>
        <div class="col-sm-9">
            <input type="text" class="input-mini" id="id" name="id" />
        </div>
        <script type="text/javascript">
            $('#id').ace_spinner({value:'${btsdtest.id}',min:1 ,max: 256,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
        </script>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="name">虚拟币名称</label>
        <div class="col-sm-9">
            <input type="text" id="name" name="name" class="col-xs-10 col-sm-5" value="${btsdtest.name }" placeholder="">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="mk">市场</label>
        <div class="col-sm-9">
            <input type="text" id="mk" name="mk" class="col-xs-10 col-sm-5" value="${btsdtest.mk }" placeholder="">
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
			<button class="btn btn-grey" type="button" onclick="loadMain('btsdTest/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>