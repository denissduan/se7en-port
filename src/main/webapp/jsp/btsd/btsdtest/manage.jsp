<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<div class="page-header">
	<form id="searchForm" action="btsdTest/query.do" method="post" class="form-inline" role="form">
		<div class="form-group">
			<label class="control-label" for="id">主键</label>
			<input type="text" id="id" name="id" class="input-mini" placeholder="" />
			<script type="text/javascript">
				$('#id').ace_spinner({value:null,min:1 ,max: 256,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
			</script>
		</div>
		<div class="form-group">
			<label class="control-label" for="name">虚拟币名称</label>
			<input type="text" id="name" name="name" class="form-control input-sm" placeholder="">
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
<script type="text/javascript">
	(function(){
		/*新建虚拟币名称*/
		$('#newBtn').click(function(){
			loadMain('btsdTest/detail.do');
		})
		
		/*编辑虚拟币名称*/
		$('#editBtn').click(function(){
			if($.core.grids[0].checkSelect() == true){
				loadMain('btsdTest/detail.do?id=' + $.core.grids[0].curId);
			}
		})
		
		/*删除虚拟币名称*/
		$('#delBtn').click(function(){
			if($.core.grids[0].checkSelect() == true){
				confirm("确认删除?",function(){
					ajaxGet("btsdTest/delete.do?id="+$.core.grids[0].curId,function(data){
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
		});	
	})();
</script>