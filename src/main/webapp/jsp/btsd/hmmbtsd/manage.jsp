<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
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
		/*新建$prop.OwnedComments.get(0).Body*/
		$('#newBtn').click(function(){
			loadMain('hmmBtsd/detail.do');
		})
		
		/*编辑$prop.OwnedComments.get(0).Body*/
		$('#editBtn').click(function(){
			if($.core.grids[0].checkSelect() == true){
				loadMain('hmmBtsd/detail.do?id=' + $.core.grids[0].curId);
			}
		})
		
		/*删除$prop.OwnedComments.get(0).Body*/
		$('#delBtn').click(function(){
			if($.core.grids[0].checkSelect() == true){
				confirm("确认删除?",function(){
					ajaxGet("hmmBtsd/delete.do?id="+$.core.grids[0].curId,function(data){
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