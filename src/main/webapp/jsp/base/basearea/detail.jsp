<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		function setMap(){
			/* try{
				var map = new BMap.Map("map");
				var point = new BMap.Point(116.331398,39.897445);
				var areaName = $('#name').val();
				if(!isNull(areaName)){
					point = areaName; 
				}
				map.centerAndZoom(point,11);
				map.enableScrollWheelZoom(true);
				
				var overlays = [];
				var overlaycomplete = function(e){
					e.overlay.enableEditing();
			        overlays.push(e.overlay);
			    };
			    var styleOptions = {
			        strokeColor:"red",    //边线颜色。
			        //fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
			        strokeWeight: 2,       //边线的宽度，以像素为单位。
			        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
			        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
			        strokeStyle: 'solid' //边线的样式，solid或dashed。
			    }
			    //实例化鼠标绘制工具
			    var drawingManager = new BMapLib.DrawingManager(map, {
			        isOpen: false, //是否开启绘制模式
			        enableDrawingTool: true, //是否显示工具栏
			        drawingToolOptions: {
			            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
			            offset: new BMap.Size(5, 5), //偏离值
			            scale: 0.8 //工具栏缩放比例
			        },
			        circleOptions: styleOptions, //圆的样式
			        polylineOptions: styleOptions, //线的样式
			        polygonOptions: styleOptions, //多边形的样式
			        rectangleOptions: styleOptions //矩形的样式
			    });  
				 //添加鼠标绘制工具监听事件，用于获取绘制结果
			    drawingManager.addEventListener('overlaycomplete', overlaycomplete);
			}catch(e){
				alert('error');
			} */
		}
		
		function clearAll() {
			for(var i = 0; i < overlays.length; i++){
	            map.removeOverlay(overlays[i]);
	        }
	        overlays.length = 0   
	    }
		
		$(function(){
			$("#baseArea").submit(function() {
				return formSubmit($('#baseArea'),function(data){
					if(data.sign == true){
						alert("保存成功");
						$.fn.zTree.getZTreeObj('baseAreaTree').reAsyncChildNodes(null, "refresh");
					}else{
						alert("保存失败");
					}
				});
			});
			
			setMap();
		});
	})();
</script>
<div class="page-header">表单</div>
<form id="baseArea" action="baseArea/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${baseArea.id }" />
	<input type="hidden" name="pid" value="${empty baseArea.pid ? -1 : baseArea.pid}" />
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="pid">父节点</label>
		<div class="col-sm-9">
			<input type="text" id="parentName" class="col-xs-10 col-sm-5" value="${parentName }"  readonly="readonly" />
		</div>
	</div>
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="name">名称</label>
			<div class="col-sm-9">
				<input type="text" id="name" name="name" class="col-xs-10 col-sm-5" value="${baseArea.name }" placeholder="">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="describ">描述</label>
			<div class="col-sm-9">
				<div id="map" class="col-xs-12" style="height:400px;"></div>
			</div>
		</div>
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
		</div>
	</div>
</form>