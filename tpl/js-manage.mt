[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
import VariableServices
import com.se7en.service.sloth.md.operateForm
%]

[%script type="Class" name="detail-se7en" file="WebContent/jsp/[%package.namespace.name%]/[%name.lowerCase()%]/manage.js" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%getAllAttributes().put("attrs")%][%name.firstLower().put("varName")%]
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		
	})();
</script>