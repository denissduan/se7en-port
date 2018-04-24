[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
import VariableServices
import com.se7en.service.sloth.md.operateForm
%]

[%script type="Class" name="detail-se7en" file="WebContent/jsp/[%package.namespace.name%]/[%name.lowerCase()%]/detail.jsp" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%getAllAttributes().put("attrs")%][%name.firstLower().put("varName")%]
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#[%name.firstLower()%]").submit(function() {
				return formSubmit($('#[%name.firstLower()%]'));
			});
		});
	})();
</script>
<div class="page-header">[%if (ownedComment != null){%][%ownedComment.body%][%}%]</div>
<form id="[%name.firstLower()%]" action="[%name.firstLower()%]/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${[%name.firstLower()%].id }" >
	[%for (ownedOperation){%]
	[%operateForm()%]
	[%}%]
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
			<button class="btn btn-grey" type="button" onclick="loadMain('[%name.firstLower()%]/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>