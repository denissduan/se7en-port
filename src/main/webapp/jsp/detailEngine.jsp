<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ page import="org.eclipse.uml2.uml.EnumerationLiteral"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="com.se7en.service.md.TemplateStringUtil"%>
<%@ page import="com.se7en.service.md.ListServices"%>
<%@ page import="com.se7en.service.md.VariableServices"%>
<%@ page import="com.se7en.service.md.ViewUtil"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.eclipse.emf.common.util.EList" %>
<%@ page import="org.eclipse.uml2.uml.Enumeration" %>
<%@ page import="org.eclipse.uml2.uml.Operation" %>
<%@ page import="org.eclipse.uml2.uml.Parameter" %>
<%@ page import="org.eclipse.uml2.uml.PrimitiveType" %>
<%@ page import="org.eclipse.uml2.uml.Property" %>
<%@ page import="org.eclipse.uml2.uml.Type" %>
<%@ page import="org.eclipse.uml2.uml.Element" %>
<%@ page import="org.eclipse.uml2.uml.Package" %>
<%@ page import="org.eclipse.uml2.uml.Class" %>
<%@ page import="org.eclipse.uml2.uml.internal.impl.DataTypeImpl" %>
<%@ page import="org.eclipse.emf.ecore.InternalEObject" %>
<%@ page import="org.eclipse.uml2.uml.internal.impl.ClassImpl" %>
<%@ page import="org.eclipse.uml2.uml.internal.impl.LiteralStringImpl" %>
<%@ page import="com.se7en.service.md.ModelEngine" %>
<%--初始化信息 --%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			  	+ request.getServerPort() + path + "/";
	//获取页面信息
	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
	ModelEngine engine = (ModelEngine)context.getBean("modelEngine");
	String className = (String)request.getAttribute("className");
	ClassImpl cls = engine.getClass(className);
	Package pkg = cls.getPackage();
	//
	ListServices listSrv = (ListServices)context.getBean("listServices");
//	StringServices TemplateStringUtil = (StringServices)context.getBean("stringServices");
//	ViewServices ViewUtil = (ViewServices)context.getBean("viewServices");
	String voName = TemplateStringUtil.firstLower(cls.getName());
	EList<Property> props = cls.getAllAttributes();
%>
<div class="page-header"><%=ViewUtil.getClassComment(cls) %></div>
<form id="<%=voName%>" action="<%=voName%>/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${vo.id }" >
<%
	EList<Operation> operations = cls.getOwnedOperations();
for(int opIndex = 0;opIndex < operations.size();opIndex++){
	Operation operation = operations.get(opIndex);
	if (operation.getName().toLowerCase().contains("form")){
		int parameterSize = operation.getOwnedParameters().size();
		EList<Parameter> parameters = operation.getOwnedParameters();
		if (parameterSize > 0){
	Parameter param = null;
	for (int paramIndex = 0;paramIndex < parameterSize;paramIndex++){
		param = parameters.get(paramIndex);
		String fldName = param.getName();
		Property prop = engine.getProperty(className, fldName);
		Type type = param.getType();
		if(type == null){
			type = prop.getType();
		}
		//缓存字段名称
		pageContext.setAttribute("fldName", fldName);
		//int propSize = props.size();
		//for (int propIndex = 0;propIndex < propSize;propIndex++){
			//1.自有属性,2.关联属性
			//if (param.getName().equals(prop.getName()) || param.getName().indexOf(prop.getName()) == 0){
				//自定义脚本
				if (DataTypeImpl.class.isInstance(param.getType()) && param.getType().getName().equals("custom")){
%>
	<div class="form-group">
		<%
			EList<Element> customs = param.getOwnedElements();
			for(int cusIndex = 0,size = customs.size();cusIndex < size;cusIndex++){
				if(customs.get(cusIndex) instanceof LiteralStringImpl){
					LiteralStringImpl lsi = (LiteralStringImpl)customs.get(cusIndex);
		%>
		<label class="col-sm-3 control-label no-padding-right" for=""><%=lsi.getName() %></label>
		<div class="col-sm-9">
		<%=lsi.getValue() %>
		</div>
		<%
				}
			}
		%>
	</div>
			<%
				}else if (engine.isPrimartyProperty(className, fldName)){
					String typeName = null;
					if(((InternalEObject)type).eProxyURI() == null){
						typeName = type.getName().toLowerCase();
					}else{
						typeName = ((InternalEObject)type).eProxyURI().fragment().toLowerCase();
					}
					if (typeName.contains("string")){
						long upper = ViewUtil.getUpperValue(prop);
						if(upper > 80){
			%>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop) %></label>
		<div class="col-sm-9">
			<textarea id="<%=fldName%>" name="<%=fldName%>" class="form-control" placeholder="" maxlength="<%=ViewUtil.adaptUpperRange(prop, 4294967295l)%>">${vo[fldName]}</textarea>
		</div>
	</div>
								<%
									}else{
								%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" id="<%=fldName%>" name="<%=fldName%>" class="col-xs-10 col-sm-5" value="${vo[fldName] }" placeholder="">
		</div>
	</div>
								<%
									}
								%>
							<%
								}else if (typeName.contains("int")){
							%>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" class="input-mini" id="<%=fldName%>" name="<%=fldName%>" />
		</div>
    	<script type="text/javascript">
    		$('#<%=ViewUtil.getJqueryIdByFieldName(fldName)%>').ace_spinner({value:'${vo[fldName]}',min:<%=ViewUtil.adaptLowerValue(prop)%>,max:<%=ViewUtil.adaptUpperRange(prop,4294967295L)%>,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
    	</script>
	</div>
							<%
								}else if (typeName.contains("boolean")){
							%>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="checkbox">
			<label>
				<input type="checkbox" id="<%=fldName%>" name="<%=fldName%>" ${vo[fldName] eq "1" ? "checked" : ""} value="${vo[fldName] }" onclick="$.core.checkClick(this)" class="ace" />
				<span class="lbl"> <%=ViewUtil.getPropertyComment(prop)%></span>
			</label>
		</div>
	</div>
							<%
								}else if (typeName.equals("date")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" id="<%=fldName%>" name="<%=fldName%>" value="${vo[fldName] }" class="col-xs-10 col-sm-5 date-picker" data-date-format="yyyy-mm-dd" />
			<span class="input-group-addon">
				<i class="fa fa-calendar bigger-110"></i>
			</span>
		</div>
		<script type="text/javascript">
			$('#<%=fldName%>').datepicker({language: 'zh-CN',format: 'YYYY-MM-DD hh:mm:ss'});
		</script>
	</div>
							<%
								}else if (typeName.equals("datetime")){
							%>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" id="<%=fldName%>" name="<%=fldName%>" value="${vo[fldName] }" class="col-xs-10 col-sm-5 datetime-picker" data-date-format="yyyy-mm-dd" />
			<span class="input-group-addon">
				<i class="fa fa-calendar bigger-110"></i>
			</span>
		</div>
		<script type="text/javascript">
			$('#<%=fldName%>').datetimepicker({language: 'zh-CN',format: 'YYYY-MM-DD hh:mm:ss'});
		</script>
	</div>
							<%
								}else if (typeName.contains("byte")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" class="input-mini" id="<%=fldName%>" name="<%=fldName%>" />
		</div>
    	<script type="text/javascript">
    		$('#<%=ViewUtil.getJqueryIdByFieldName(fldName)%>').ace_spinner({value:'${vo[fldName] }',min:<%=ViewUtil.adaptLowerValue(prop)%>,max:<%=ViewUtil.adaptUpperRange(prop,256)%>,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
    	</script>
	</div>
							<%
								}else if (typeName.contains("char")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" id="<%=fldName%>" name="<%=fldName%>" class="col-xs-10 col-sm-5" value="${vo[fldName] }" placeholder="">
		</div>
	</div>
							<%
								}else if (typeName.contains("short")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" class="input-mini" id="<%=fldName%>" name="<%=fldName%>" />
		</div>
    	<script type="text/javascript">
    		$('#<%=ViewUtil.getJqueryIdByFieldName(fldName)%>').ace_spinner({value:'${vo[fldName] }',min:<%=ViewUtil.adaptLowerValue(prop)%>,max:<%=ViewUtil.adaptUpperRange(prop,65535)%>,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
    	</script>
	</div>
							<%
								}else if (typeName.contains("long")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" class="input-mini" id="<%=fldName%>" name="<%=fldName%>" />
		</div>
    	<script type="text/javascript">
    		$('#<%=ViewUtil.getJqueryIdByFieldName(fldName)%>').ace_spinner({value:'${vo[fldName] }',min:<%=ViewUtil.adaptLowerValue(prop)%>,max:<%=ViewUtil.adaptUpperRange(prop,4294967295L)%>,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
    	</script>
	</div>
							<%
								}else if (typeName.contains("float")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" class="col-xs-10 col-sm-5" id="<%=fldName%>" name="<%=fldName%>" />
		</div>
    	<%--<script type="text/javascript">
    		$('#<%=ViewUtil.getJqueryIdByFieldName(fldName)%>').ace_spinner({value:'${vo[fldName] }',min:<%=ViewUtil.adaptLowerValue(prop)%>,max:<%=ViewUtil.adaptUpperRange(prop,65535)%>,step:0.1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
    	</script>--%>
	</div>
							<%
								}else if (typeName.contains("double")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<input type="text" class="col-xs-10 col-sm-5" id="<%=fldName%>" name="<%=fldName%>" />
		</div>
    	<%--<script type="text/javascript">
    		$('#<%=ViewUtil.getJqueryIdByFieldName(fldName)%>').ace_spinner({value:'${vo[fldName] }',min:<%=ViewUtil.adaptLowerValue(prop)%>,max:<%=ViewUtil.adaptUpperRange(prop,65535)%>,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
    	</script>--%>
	</div>
							<%
								}else if (typeName.contains("blob")){
							%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<textarea id="<%=fldName%>" name="<%=fldName%>" class="form-control" placeholder="" maxlength="<%=ViewUtil.adaptUpperRange(prop,4294967295L)%>">${vo[fldName]}</textarea>
		</div>
	</div>
							<%
								}else if (typeName.contains("clob")){
							%>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop)%></label>
		<div class="col-sm-9">
			<textarea id="<%=fldName%>" name="<%=fldName%>" class="form-control" placeholder="" maxlength="<%=ViewUtil.adaptUpperRange(prop,4294967295L)%>">${vo[fldName]}</textarea>
		</div>
	</div>
							<%}%>
						<%--枚举字段--%>
						<%
							}else if(engine.isEnumerationProperty(className, fldName)){
						%>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop) %></label>
		<div class="col-sm-9">
			<select id="<%=fldName%>" name="<%=fldName%>" size="1">
				<%
				EList<Element> eles = prop.getType().getOwnedElements();
				int size = eles.size();
				for (int i = 0;i < size;i++){
					Element ele = eles.get(i);
				%>
				<option value="<%=ViewUtil.getEnumerationValue(ele)%>" ><%=ViewUtil.getEnumerationText(ele) %></option>
				<%}%>
			</select>
		</div>
	</div>
						<%--关联字段--%>
						<%
							}else if(engine.isClassProperty(className, fldName)){
						%>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop) %></label>
		<div class="col-sm-9">
			<input type="hidden" id="<%=fldName%>" name="<%=fldName%>.id" class="col-xs-10 col-sm-5" value="${vo[fldName].id }" placeholder="">
			<input type="text" id="<%=fldName%>_el" class="col-xs-10 col-sm-5" value="${vo[fldName].name }" placeholder="" readonly="readonly">
			<span class="input-group-btn">
				<button class="btn btn-sm btn-light" type="button" onclick='relOpen("<%=TemplateStringUtil.firstLower(type.getName()) %>/query.do","<%=fldName%>")'>
					<i class="ace-icon fa fa-hand-o-right bigger-110"></i>
				</button>
			</span>
		</div>
	</div>
						<%
							}else{
						%>
    <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="<%=fldName%>"><%=ViewUtil.getPropertyComment(prop) %></label>
		<div class="col-sm-9">
			<input type="text" id="<%=fldName%>" name="<%=fldName%>" class="col-xs-10 col-sm-5" value="${vo[fldName] }" placeholder="">
		</div>
	</div>
						<%}%>
					<%//}%>
				<%
				//}
				pageContext.removeAttribute("fldName");
				%>
			<%}%>
		<%}%>
	<%}%>
<%}%>
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
			<button class="btn btn-grey" type="button" onclick="loadMain('<%=voName%>/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#<%=voName%>").submit(function() {
				return formSubmit($('#<%=voName%>'));
			});
		});
	})();
</script>
<script type="text/javascript" src="<%=basePath%>jsp/<%=pkg.getNamespace().getName()%>/<%=className.toLowerCase()%>/detail.js"></script>