<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<%--<html>--%>
<%--<head>--%>
    <%--<title>My first chart using FusionCharts Suite XT</title>--%>
    <script type="text/javascript" src="<%=basePath%>js/fusionchart/fusioncharts.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/fusionchart/themes/fusioncharts.theme.fint.js?cacheBust=56"></script>
<%--</head>--%>
<%--<body>--%>
<div class="page-header">
    <form id="searchForm" action="btsd/getCandleStickData.do" method="post" class="form-inline" role="form">
        <select id="type" name="type" size="1" class="form-control input-sm">
            <option value="0">-请选择-</option>
            <option value="1">日线</option>
            <option value="2">时线</option>
            <option value="3">5分线</option>
        </select>
        <div class="form-group">
            <label class="control-label" for="coin">币种</label>
            <input type="hidden" id="coin" name="coin" class="form-control input-sm" value="" placeholder="">
            <input type="text" id="coin_el" class="form-control input-sm" value="" placeholder="" readonly="readonly">
            <!-- <span class="form-control input-group-btn"></span> -->
            <button class="form-control input-group-btn btn btn-sm btn-light" type="button" onclick='relOpen("btsdCoin/query.do","coin")'>
                <i class="ace-icon fa fa-hand-o-right bigger-110"></i>
            </button>
        </div>
        <div class="form-group">
            <label class="control-label" for="startDate">开始时间</label>
            <input type="text" id="startDate" name="startDate" size="16" class="form_datetime"/>
            <span class="add-on"><i class="icon-remove"></i></span>
            <span class="add-on"><i class="icon-th"></i></span>
            <script type="text/javascript">
                $('#startDate').datetimepicker({language: 'zh-CN', format: 'YYYY-MM-DD hh:mm:ss'});
            </script>
        </div>
        <div class="form-group">
            <label class="control-label" for="endDate">结束时间</label>
            <input type="text" id="endDate" name="endDate" size="16" class="form_datetime"/>
            <span class="add-on"><i class="icon-remove"></i></span>
            <span class="add-on"><i class="icon-th"></i></span>
            <script type="text/javascript">
                $('#endDate').datetimepicker({language: 'zh-CN', format: 'YYYY-MM-DD hh:mm:ss'});
            </script>
        </div>
    </form>
</div>
<div id="chart-container"></div>
<script type="text/javascript">

    $(function(){
        $('#type').change(function(){
            refreshChart();
        });
        $('#coin_el').change(function(){
            refreshChart();
        });
        $('#startDate').change(function(){
            refreshChart();
        });
        $('#endDate').change(function(){
            refreshChart();
        });
    });

    function refreshChart(){
        var type = $('#type').val();
        var coin = $('#coin').val();
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();

        if(type == 0){
            return;
        }
        if(isNull(coin) == true){
            return;
        }
        //5分线数据过大，需要设置区间
        if(type == 3){
            if(isNull(startDate) == true && isNull(endDate)){
                return;
            }
        }

        formSubmit($('#searchForm'), function(result){
            var fusioncharts = new FusionCharts({
                        type: 'candlestick',
                        renderAt: 'chart-container',
                        id: 'myChart',
                        width: '1200',
                        height: '600',
                        dataFormat: 'json',
                        dataSource: {
                            "chart": {
                                "caption": "Daily Stock Price HRYS",
                                "subCaption": "Last 2 months",
                                "numberPrefix": "",
                                "vNumberPrefix": " ",
                                "pYAxisName": "Price",
                                "vYAxisName": "Volume (In Millions)",
                                "bgColor": "#ffffff",
                                "showBorder": "0",
                                "canvasBgColor": "#ffffff",
                                "showCanvasBorder": "0",
                                "showAlternateHGridColor": "0",
                                "baseFontColor": "#333333",
                                "baseFont": "Helvetica Neue,Arial",
                                "captionFontSize": "14",
                                "subcaptionFontSize": "14",
                                "subcaptionFontBold": "0",
                                "toolTipColor": "#ffffff",
                                "toolTipBorderThickness": "0",
                                "toolTipBgColor": "#000000",
                                "toolTipBgAlpha": "80",
                                "toolTipBorderRadius": "2",
                                "toolTipPadding": "5",
                                "divlineAlpha": "100",
                                "divlineColor": "#999999",
                                "divlineThickness": "1",
                                "divLineDashed": "1",
                                "divLineDashLen": "1",
                                "divLineGapLen": "1"
                            },
                            "categories": [{
                                "category": result.xAxis
                            }],
                            "dataset": [{
                                "data": result.data
                            }]
                        },
                        events: {
                            dataPlotClick: function (eventObj, argsObj) {
                                var tooltip = argsObj.toolText;
                                var tips = tooltip.split('<br />');
                                var open = tips[0].split(':')[1];
                                var high = tips[1].split(':')[1];
                                var low = tips[2].split(':')[1];
                                var close = tips[3].split(':')[1];
                                var volume = tips[4].split(':')[1];
                                var date = tips[5].split(':')[1];
                                var point = tips[6].split(':')[1];
                                if(point == 'buy'){

                                }else if(point == 'seil'){

                                }else if(point == ''){

                                }

                                argsObj.color = '008ee4';
                            }
                        }
                    }
            );
            fusioncharts.render();
        });
    }
    debugger;
</script>
<%--</body>--%>
<%--</html>--%>
