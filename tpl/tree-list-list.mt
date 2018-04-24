[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
import com.se7en.service.sloth.md.searchForm
%]

[%script type="Class" name="tree-list-list" file="WebContent/jsp/[%package.namespace.name%]/[%name.lowerCase()%]/list.jsp" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%attribute.put("attrs")%][%name.firstLower().put("varName")%]
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
	})();	
</script>
[%for (ownedOperation){%]
[%searchFormInit()%]
[%}%]
<table id="grid" class="table table-hover"></table>