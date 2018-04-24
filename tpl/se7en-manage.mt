[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
import com.se7en.service.sloth.md.searchForm
%]

[%script type="Class" name="manage-se7en" file="WebContent/jsp/[%package.namespace.name%]/[%name.lowerCase()%]/manage.jsp" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%attribute.put("attrs")%][%name.firstLower().put("varName")%]
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML >
<html xmlns="http://www.w3.org/1999/xhtml" >
	<head>
		<base href="<%=basePath%>">
		<title>se7en</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link rel="stylesheet" type="text/css" href="js/fancybox/fancybox.css">
		<script type="text/javascript" src="js/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="js/fancybox/fancybox.pack.js"></script>
		<script type="text/javascript" src="js/se7en/core.js"></script>
		<script type="text/javascript">
			//新建[%if (ownedComment){%][%ownedComment.body%][%}%]
			function new[%name.capitalize()%](){
				winOpen('[%name.firstLower()%]/detail.do');
			}
			
			//编辑[%if (ownedComment){%][%ownedComment.body%][%}%]
			function edit[%name.capitalize()%](){
				if($.core.grids[0].checkSelect() == true){
					winOpen('[%name.firstLower()%]/detail.do?id=' + $.core.grids[0].curId);
				}
			}
			
			//删除[%if (ownedComment){%][%ownedComment.body%][%}%]
			function del[%name.capitalize()%](){
				if($.core.grids[0].checkSelect() == true){
					confirm("确认删除?",function(){
						ajaxGet("[%name.firstLower()%]/delete.do?id="+$.core.grids[0].curId,function(data){
							if(data.sign == true){
								alert("删除成功");
								refreshPage();
							}else{
								alert("删除失败");
							}
						});
					});
				}
			}
			
			//初始化
			$(function(){
				commonCapability({tableTarget : $("#grid"),pageTarget : $("#pageNav")});
			});	
		</script>
	</head>
	<body>
		<div id="container" class="container" >
			<form action="[%name.firstLower()%]/query.do" class="ym-form" method="post">
				[%for (ownedOperation){%]
					[%if (name.lowerCase().contains("search")){%]
						[%if (ownedParameter.size() > 0){%]
	        	<div class="ym-fbox">
					<div class="ym-fbox-wrap ym-grid">
							[%for (ownedParameter){%]
								[%push()%]
									[%for (get("attrs")){%]
										[%if (peek().name == name){%]
						<div class="ym-g33 ym-gl">
						    <div class="ym-gbox-left">
								<label for="[%name.firstLower()%]" >[%if (ownedComment != null){%][%ownedComment.body%]:[%}%]</label>
											[%if (type.filter("PrimitiveType") != null){%]
												[%if (type.name.lowerCase().contains("string")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("int")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("boolean")){%]
								<input type="checkbox" id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("date")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("datetime")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("byte")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("char")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("short")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("long")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("float")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("double")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("blob")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}else if(type.name.lowerCase().contains("clob")){%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
												[%}%]
											[%}else if(type.filter("Enumeration") != null){%]
												[%name.lowerCase().push()%]
								<select id="[%name.firstLower()%]" name="[%name.firstLower()%]" size="1">
									[%for (type.ownedElement){%]
									<option value="[%ownedElement.filter("LiteralInteger").value%]" >[%if (ownedComment != null){%][%ownedComment.body%][%}%]</option>
									[%}%]
								</select>
												[%pop()%]
											[%}else{%]
					    		<input id="[%name.firstLower()%]" name="[%name.firstLower()%]" />
											[%}%]
						    </div>
						</div>
										[%}%]
									[%}%]
								[%pop()%]	
							[%}%]
	            	</div>
				</div>
						[%}%]
					[%}%]		
				[%}%]
	          	<div class="ym-fbox-footer">
					<input type="button" class="save ym-primary" onclick="new[%name.capitalize()%]()" style="width:70px" value="新增" />
					<input type="button" class="ym-button ym-success" onclick="edit[%name.capitalize()%]()" style="width:70px" value="编辑" />
					<input type="button" class="ym-button ym-add" onclick="del[%name.capitalize()%]()" style="width:70px" value="删除" />
					<input type="submit" class="ym-button" style="width:70px" value="查询" />
				</div>
			</form>	
			<table id="grid"></table>
			<div id='pageNav' class='page' ></div>
			<div id="dlg" ></div>
		</div>
	</body>
</html>