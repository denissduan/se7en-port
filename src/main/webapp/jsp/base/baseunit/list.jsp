<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	//初始化
	(function(){
		$(function(){
			$.core.commonCapability({tableTarget : $("#grid"),pageTarget : $("#pageNav")});
		});
	})();</script>
<div class="page-header">	
	<form id="searchForm" action="baseUnit/query.do" method="post" class="form-inline" role="form">		
		<div class="form-group">
	    	<label class="control-label" for="phone">办公电话</label>
	    	<input type="text" id="phone" name="phone" class="form-control input-sm" placeholder="">
	  	</div>
		<div class="form-group">
	    	<label class="control-label" for="state">状态</label>
			<select id="state" name="state" size="1" class="form-control input-sm">
				<option value="1" >启用</option>
				<option value="0" >禁用</option>
			</select>
	  	</div>
	</form>
</div>
<table id="grid" class="table table-hover"></table>
<ul id="pageNav" class="pagination"></ul>