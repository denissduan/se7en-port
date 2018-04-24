[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
%]

[%script type="Class" name="detail-se7en" file="WebContent/jsp/[%package.namespace.name%]/[%name.lowerCase()%]/detail.jsp" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%attribute.put("attrs")%][%name.firstLower().put("varName")%]
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" >
	<head>
		<base href="<%=basePath%>">
		<title>se7en</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script type="text/javascript" src="js/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="js/se7en/core.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#[%name.firstLower()%]").submit(function() {
					return windowSubmit($('#[%name.firstLower()%]'));
				});
			});
		</script>
	</head>
	<body>
		<form id="[%name.firstLower()%]" action="[%name.firstLower()%]/save.do" class="ym-form linearize-form" method="post">
			<input type="hidden" name="id" value="${[%name.firstLower()%].id }" >
			[%for (ownedOperation){%]
				[%if (name.lowerCase().contains("form")){%]
					[%for (ownedParameter){%]
						[%push()%]
							[%for (get("attrs")){%]
								[%if (peek().name == name){%]
			<div class="ym-fbox ym-fbox-text">
			    <label for="[%name.firstLower()%]">[%if (ownedComment != null){%][%ownedComment.body%]:[%}%]</label>
			    					[%if (type.filter("PrimitiveType") != null){%]
										[%if (type.name.lowerCase().contains("string")){%]
											[%for (ownedElement.filter("LiteralInteger")){%]
												[%if (self().name == "length"){%]
													[%if (self().value != null && self().value > 80){%]
				<textarea id="[%name.firstLower()%]" name="[%name.firstLower()%]" rows="5">[%get("varName")%].[%name.firstLower()%]</textarea>
													[%}else{%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
													[%}%]
												[%}else{%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
												[%}%]
											[%}%]
										[%}else if(type.name.lowerCase().contains("int")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("boolean")){%]
				<input type="checkbox" id="[%name.firstLower()%]" name="[%name.firstLower()%]" ${[%get("varName")%].[%name%] eq "1" ? "checked" : ""} />
										[%}else if(type.name.lowerCase().contains("date")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("datetime")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("byte")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("char")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("short")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("long")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("float")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("double")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("blob")){%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
										[%}else if(type.name.lowerCase().contains("clob")){%]
				<textarea id="[%name.firstLower()%]" name="[%name.firstLower()%]" rows="5">[%get("varName")%].[%name.firstLower()%]</textarea>
										[%}%]
									[%}else if(type.filter("Enumeration") != null){%]
										[%name.lowerCase().push()%]
				<select id="[%name.firstLower()%]" name="[%name.firstLower()%]" size="1">
					[%for (type.ownedElement){%]
					<option value="[%ownedElement.filter("LiteralInteger").value%]" ${[%get("varName")%].[%peek()%] eq "[%ownedElement.filter("LiteralInteger").value%]" ? "selected" : ""}>[%if (ownedComment != null){%][%ownedComment.body%][%}%]</option>
					[%}%]
				</select>
										[%pop()%]
									[%}else{%]
			    <input id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="${[%get("varName")%].[%name.firstLower()%] }" />
									[%}%]	
			</div>
								[%}%]
							[%}%]
						[%pop()%]	
					[%}%]
				[%}%]
			[%}%]
			<div class="ym-fbox-footer ym-fbox-button">
			    <input type="submit" value="提交" class="save ym-save ym-success" >
			</div>
		</form>
	</body>
</html>