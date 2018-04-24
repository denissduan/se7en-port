<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="org.eclipse.uml2.uml.EnumerationLiteral" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.se7en.service.md.StringServices" %>
<%@ page import="com.se7en.service.md.ListServices" %>
<%@ page import="com.se7en.service.md.VariableServices" %>
<%@ page import="com.se7en.service.md.TemplateStringUtil" %>
<%@ page import="com.se7en.service.md.ViewUtil" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.eclipse.emf.common.util.EList" %>
<%@ page import="org.eclipse.uml2.uml.Enumeration" %>
<%@ page import="org.eclipse.uml2.uml.Operation" %>
<%@ page import="org.eclipse.uml2.uml.Parameter" %>
<%@ page import="org.eclipse.uml2.uml.PrimitiveType" %>
<%@ page import="org.eclipse.uml2.uml.Property" %>
<%@ page import="org.eclipse.uml2.uml.Type" %>
<%@ page import="org.eclipse.uml2.uml.Element" %>
<%@ page import="org.eclipse.uml2.uml.Package" %>
<%@ page import="org.eclipse.emf.ecore.InternalEObject" %>
<%@ page import="org.eclipse.uml2.uml.internal.impl.ClassImpl" %>
<%@ page import="com.se7en.service.md.ModelEngine" %>
<%--初始化信息 --%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    //获取页面信息
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
    ModelEngine engine = (ModelEngine) context.getBean("modelEngine");
    String className = (String) request.getAttribute("className");
    ClassImpl cls = engine.getClass(className);
    Package pkg = cls.getPackage();
    //
    ListServices listSrv = (ListServices) context.getBean("listServices");
//	StringServices TemplateStringUtil = (StringServices)context.getBean("stringServices");
    String voName = TemplateStringUtil.firstLower(cls.getName());
    EList<Property> props = cls.getAllAttributes();
%>
<%--查询界面 --%>
<%
    EList<Operation> operations = cls.getOwnedOperations();
    for (int opIndex = 0; opIndex < operations.size(); opIndex++) {
        Operation operation = operations.get(opIndex);
        if (operation.getName().toLowerCase().contains("search")) {
            int parameterSize = operation.getOwnedParameters().size();
            EList<Parameter> parameters = operation.getOwnedParameters();
            if (parameterSize > 0) {
                Parameter param = null;
%>
<div class="page-header">
    <form id="searchForm" action="<%=voName%>/query.do" method="post" class="form-inline" role="form">
        <%
            params_loop:
            for (int paramIndex = 0; paramIndex < parameterSize; paramIndex++) {
                param = parameters.get(paramIndex);
                String fldName = param.getName();
                String fldTagId = ViewUtil.transPropertyName2TagId(param.getName());
                Type type = param.getType();

                boolean isRender = false;
                props_loop:
                for (int propIndex = 0; propIndex < props.size(); propIndex++) {
                    Property prop = props.get(propIndex);
                    //1.自有属性,2.关联属性
                    if (param.getName().equals(prop.getName()) || param.getName().indexOf(prop.getName()) == 0) {
                        if (isRender == true) {
                            continue params_loop;
                        }
                        isRender = true;
                        if (type == null) {
                            type = prop.getType();
                        }
                        if (PrimitiveType.class.isInstance(type)) {
                            String typeName = null;
                            if (((InternalEObject) type).eProxyURI() == null) {
                                typeName = type.getName().toLowerCase();
                            } else {
                                typeName = ((InternalEObject) type).eProxyURI().fragment().toLowerCase();
                            }
                            if (typeName.contains("string")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="form-control input-sm" placeholder="">
        </div>
        <%} else if (typeName.contains("int")) {%>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="input-mini" placeholder=""/>
            <script type="text/javascript">
                $('#<%=ViewUtil.getJqueryIdByFieldName(fldTagId)%>').ace_spinner({
                    value: null,
                    min:<%=ViewUtil.adaptLowerValue(prop)%>,
                    max:<%=ViewUtil.adaptUpperRange(prop,4294967295L)%>,
                    step: 1,
                    btn_up_class: 'btn-info',
                    btn_down_class: 'btn-info'
                });
            </script>
        </div>
        <%
        } else if (typeName.contains("boolean")) {
        %>
        <div class="checkbox">
            <label>
                <input type="checkbox" id="<%=fldTagId%>" name="<%=fldTagId%>" value="0" onclick="$.core.checkClick(this)"> <%=ViewUtil.getPropertyComment(prop)%>
            </label>
        </div>
        <%
        } else if (typeName.contains("date")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop)%></label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" size="16" class="form_datetime"/>
            <span class="add-on"><i class="icon-remove"></i></span>
            <span class="add-on"><i class="icon-th"></i></span>
            <script type="text/javascript">
                $('#<%=fldTagId%>').datepicker({language: 'zh-CN', format: 'yyyy-mm-dd'});
            </script>
        </div>
        <%
        } else if (typeName.contains("datetime")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop)%></label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" size="16" class="form_datetime"/>
            <span class="add-on"><i class="icon-remove"></i></span>
            <span class="add-on"><i class="icon-th"></i></span>
            <script type="text/javascript">
                $('#<%=fldTagId%>').datetimepicker({language: 'zh-CN', format: 'YYYY-MM-DD hh:mm:ss'});
            </script>
        </div>
        <%
        } else if (typeName.contains("byte")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop)%></label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="input-mini" placeholder=""/>
            <script type="text/javascript">
                $('#<%=ViewUtil.getJqueryIdByFieldName(fldTagId)%>').ace_spinner({
                    value: null,
                    min:<%=ViewUtil.adaptLowerValue(prop)%>,
                    max:<%=ViewUtil.adaptUpperRange(prop, 255)%>,
                    step: 1,
                    btn_up_class: 'btn-info',
                    btn_down_class: 'btn-info'
                });
            </script>
        </div>
        <%
        } else if (typeName.contains("char")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop)%></label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="form-control input-sm" placeholder="">
        </div>
        <%
        } else if (typeName.contains("short")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop)%>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="input-mini" placeholder=""/>
            <script type="text/javascript">
                $('#<%=ViewUtil.getJqueryIdByFieldName(fldTagId)%>').ace_spinner({
                    value: null,
                    min:<%=ViewUtil.adaptLowerValue(prop)%>,
                    max:<%=ViewUtil.adaptUpperRange(prop, 65535)%>,
                    step: 1,
                    btn_up_class: 'btn-info',
                    btn_down_class: 'btn-info'
                });
            </script>
        </div>
        <%
        } else if (typeName.contains("long")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="form-control input-sm" placeholder="">
        </div>
        <%
        } else if (typeName.contains("float")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop)%>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="input-mini" placeholder=""/>
            <script type="text/javascript">
                $('#<%=ViewUtil.getJqueryIdByFieldName(fldTagId)%>').ace_spinner({
                    value: null,
                    min:<%=ViewUtil.adaptLowerValue(prop)%>,
                    max:<%=ViewUtil.adaptUpperRange(prop,65535)%>,
                    step: 0.1,
                    btn_up_class: 'btn-info',
                    btn_down_class: 'btn-info'
                });
            </script>
        </div>
        <%
        } else if (typeName.contains("double")) {
        %>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop)%>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="input-mini" placeholder=""/>
            <script type="text/javascript">
                $('#<%=ViewUtil.getJqueryIdByFieldName(fldTagId)%>').ace_spinner({
                    value: null,
                    min:<%=ViewUtil.adaptLowerValue(prop)%>,
                    max:<%=ViewUtil.adaptUpperRange(prop,4294967295L)%>,
                    step: 0.1,
                    btn_up_class: 'btn-info',
                    btn_down_class: 'btn-info'
                });
            </script>
        </div>
        <%} else if (typeName.contains("blob")) {%>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="form-control input-sm" placeholder="">
        </div>
        <%} else if (typeName.contains("clob")) {%>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="form-control input-sm" placeholder="">
        </div>
        <%}%>
        <%} else if (EnumerationLiteral.class.isInstance(type)) {%>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <select id="<%=fldTagId%>" name="<%=fldTagId%>" size="1" class="form-control input-sm">
                <%
                    EList<Element> eles = prop.getType().getOwnedElements();
                    int size = eles.size();
                    for (int i = 0; i < size; i++) {
                        Element ele = eles.get(i);
                %>
                <option value="<%=ViewUtil.getEnumerationValue(ele)%>"><%=ViewUtil.getEnumerationText(ele) %></option>
                <%}%>
            </select>
        </div>
        <%--关联字段--%>
        <%} else if (org.eclipse.uml2.uml.Class.class.isInstance(type)) {%>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <input type="hidden" id="<%=fldTagId%>" name="<%=fldTagId%>" class="form-control input-sm"
                   value="${vo[fldName].id }" placeholder="">
            <input type="text" id="<%=fldTagId%>_el" class="form-control input-sm" value="${vo[fldName].name }"
                   placeholder="" readonly="readonly">
            <!-- <span class="form-control input-group-btn">
            </span> -->
            <button class="form-control input-group-btn btn btn-sm btn-light" type="button"
                    onclick='relOpen("<%=TemplateStringUtil.firstLower(type.getName()) %>/query.do","<%=fldTagId%>")'>
                <i class="ace-icon fa fa-hand-o-right bigger-110"></i>
            </button>
        </div>
        <%} else {%>
        <div class="form-group">
            <label class="control-label" for="<%=fldTagId%>"><%=ViewUtil.getPropertyComment(prop) %>
            </label>
            <input type="text" id="<%=fldTagId%>" name="<%=fldTagId%>" class="form-control input-sm" placeholder="">
        </div>
        <%}%>
        <%}%>
        <%}%>
        <%}%>
    </form>
</div>
<%}%>
<%}%>
<%}%>
<div class="page-header">
    <button id="newBtn" class="btn btn-white">
        <i class="ace-icon fa fa-plus bigger-120"></i>新增
    </button>
    <button id="editBtn" class="btn btn-white">
        <i class="ace-icon fa fa-edit bigger-120"></i>修改
    </button>
    <button id="delBtn" class="btn btn-white">
        <i class="ace-icon fa fa-times bigger-120"></i>删除
    </button>
    <%-- 自定义按钮处理 --%>
    <%
        EList<Operation> btnOperations = cls.getOwnedOperations();
        for (int opIndex = 0; opIndex < btnOperations.size(); opIndex++) {
            Operation operation = btnOperations.get(opIndex);
            if (operation.getName().toLowerCase().contains("button_manage")) {
                int parameterSize = operation.getOwnedParameters().size();
                EList<Parameter> parameters = operation.getOwnedParameters();
                if (parameterSize > 0) {
                    Parameter param = null;
                    for (int paramIndex = 0; paramIndex < parameterSize; paramIndex++) {
                        param = parameters.get(paramIndex);
                        String id = param.getName();
                        String comment = param.getOwnedComments().get(0).getBody();
    %>
    <button id="<%=id%>" class="btn btn-white" >
        <i class="ace-icon fa fa-edit bigger-120"></i><%=comment%>
    </button>
    <%
                    }
                }
            }
        }
    %>
    <button id="searchBtn" class="btn btn-sm btn-primary">
        <i class="ace-icon fa fa-search bigger-120"></i>查询
    </button>
</div>
<table id="grid" class="table table-hover"></table>
<script type="text/javascript">
    (function () {
        //新建<%=ViewUtil.getClassComment(cls)%>
        $('#newBtn').click(function () {
            loadMain('<%=voName%>/detail.do');
        })

        //编辑<%=ViewUtil.getClassComment(cls)%>
        $('#editBtn').click(function () {
            if ($.core.grids[0].checkSelect() == true) {
                loadMain('<%=voName%>/detail.do?id=' + $.core.grids[0].curId);
            }
        })

        //删除<%=ViewUtil.getClassComment(cls)%>
        $('#delBtn').click(function () {
            if ($.core.grids[0].checkSelect() == true) {
                confirm("确认删除?", function () {
                    ajaxGet("<%=voName%>/delete.do?id=" + $.core.grids[0].curId, function (data) {
                        if (data.sign == true) {
                            alert("删除成功");
                            refreshPage();
                        } else {
                            alert("删除失败");
                        }
                    });
                });
            }
        })

        //初始化
        $(function () {
            $.core.commonCapability({tableTarget: $("#grid"), pageTarget: $("#pageNav")});
        });
    })();
</script>
<script type="text/javascript" src="<%=basePath%>jsp/<%=pkg.getNamespace().getName()%>/<%=className.toLowerCase()%>/manage.js"></script>