<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<div class="page-header">	<form id="searchForm" action="sysParam/query.do" method="post" class="form-inline" role="form">		<div class="form-group">
	    	<label class="control-label" for="module">子系统</label>
	    	<input type="text" id="module" name="module" class="form-control input-sm" placeholder="">
	  	</div>
		<div class="form-group">
	    	<label class="control-label" for="pname">参数名</label>
	    	<input type="text" id="pname" name="pname" class="form-control input-sm" placeholder="">
	  	</div>
		<div class="form-group">
	    	<label class="control-label" for="describ">描述</label>
	    	<input type="text" id="describ" name="describ" class="form-control input-sm" placeholder="">
	  	</div>
	</form>
</div>
<div class="page-header">
	<button id="newBtn" class="btn btn-white" >
		<i class="ace-icon fa fa-plus bigger-120"></i>新增
	</button>
	<button id="editBtn" class="btn btn-white" >
		<i class="ace-icon fa fa-edit bigger-120"></i>修改
	</button>
	<button id="delBtn" class="btn btn-white" >
		<i class="ace-icon fa fa-times bigger-120"></i>删除
	</button>
	<button id="searchBtn" class="btn btn-sm btn-primary">
		<i class="ace-icon fa fa-search bigger-120"></i>查询
	</button>
</div>
<table id="grid" class="table table-hover"></table>
<ul id="pageNav" class="pagination"></ul>
<script type="text/javascript">
	(function(){
		//新建系统参数
		$('#newBtn').click(function(){
			loadMain('sysParam/detail.do');
		})
		//编辑系统参数
		$('#editBtn').click(function(){
			if($.core.grids[0].checkSelect() == true){
				loadMain('sysParam/detail.do?id=' + $.core.grids[0].curId);
			}
		})
		//删除系统参数
		$('#delBtn').click(function(){
			if($.core.grids[0].checkSelect() == true){
				confirm("确认删除?",function(){
					ajaxGet("sysParam/delete.do?id="+$.core.grids[0].curId,function(data){
						if(data.sign == true){
							alert("删除成功");
							refreshPage();
						}else{
							alert("删除失败");
						}
					});
				});
			}
		})
		//初始化
		$(function(){
			$.core.commonCapability({tableTarget : $("#grid"),pageTarget : $("#pageNav")});
		});	})();
</script>