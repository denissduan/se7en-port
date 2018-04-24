<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="<%=basePath%>css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css" />
		
		<!-- page specific plugin styles -->
		
		<!-- text fonts -->
		<link rel="stylesheet" href="<%=basePath%>css/ace-fonts.css" />
		
		<!-- ace styles -->
		<link rel="stylesheet" href="<%=basePath%>css/ace.min.css" id="main-ace-style" />
		
		<!--[if lte IE 9]>
			<link rel="stylesheet" href="<%=basePath%>css/ace-part2.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="<%=basePath%>css/ace-skins.min.css" />
		<link rel="stylesheet" href="<%=basePath%>css/ace-rtl.min.css" />
		
		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="<%=basePath%>css/ace-ie.min.css" />
		<![endif]-->
		
		<!-- inline styles related to this page -->
		
		<!-- ace settings handler -->
		<script src="<%=basePath%>js/ace-extra.min.js"></script>
		
		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
		
		<!--[if lte IE 8]>
		<script src="<%=basePath%>js/html5shiv.min.js"></script>
		<script src="<%=basePath%>js/respond.min.js"></script>
		<![endif]-->
		
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath%>js/jquery.min.js'>"+"<"+"/script>");
		</script>
		
		<!-- <![endif]-->
		
		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='<%=basePath%>js/jquery1x.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="<%=basePath%>js/bootstrap.min.js"></script>
		
		<!-- page specific plugin scripts -->
		<!--[if lte IE 8]>
		  <script src="<%=basePath%>js/excanvas.min.js"></script>
		<![endif]-->
		<script src="<%=basePath%>js/jquery-ui.custom.min.js"></script>
		<script src="<%=basePath%>js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=basePath%>js/chosen.jquery.min.js"></script>
		<script src="<%=basePath%>js/fuelux/fuelux.spinner.min.js"></script>
		<script src="<%=basePath%>js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="<%=basePath%>js/date-time/bootstrap-timepicker.min.js"></script>
		<script src="<%=basePath%>js/date-time/moment.min.js"></script>
		<script src="<%=basePath%>js/date-time/daterangepicker.min.js"></script>
		<script src="<%=basePath%>js/date-time/bootstrap-datetimepicker.min.js"></script>
		<script src="<%=basePath%>js/bootstrap-colorpicker.min.js"></script>
		<script src="<%=basePath%>js/jquery.knob.min.js"></script>
		<script src="<%=basePath%>js/jquery.autosize.min.js"></script>
		<script src="<%=basePath%>js/jquery.inputlimiter.1.3.1.min.js"></script>
		<script src="<%=basePath%>js/jquery.maskedinput.min.js"></script>
		<script src="<%=basePath%>js/bootstrap-tag.min.js"></script>
		<script src="<%=basePath%>js/typeahead.jquery.min.js"></script>
		
		<!-- ace scripts -->
		<script src="<%=basePath%>js/ace-elements.min.js"></script>
		<script src="<%=basePath%>js/ace.min.js"></script>
		
		<!-- inline scripts related to this page -->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/ztree/zTreeStyle.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/fancybox/fancybox.css">
		<script type="text/javascript" src="<%=basePath%>js/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ztree/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/fancybox/fancybox.pack.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/se7en/core.js"></script>