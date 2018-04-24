<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					  	+ request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	(function(){
		$(function(){
			$("#btsdHmmState").submit(function() {
				return formSubmit($('#btsdHmmState'));
			});
		});
	})();
</script>
<div class="page-header">最大边界</div>
<form id="btsdHmmState" action="btsdHmmState/save.do" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${btsdHmmState.id }" >
	    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="type">模型类型1.日线2.时线3.五分线4.历史交易</label>
        <div class="col-sm-9">
            <input type="text" class="input-mini" id="type" name="type" />
        </div>
        <script type="text/javascript">
            $('#type').ace_spinner({value:'${btsdhmmstate.type}',min:1 ,max: 256,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
        </script>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="coin">虚拟币代码</label>
        <div class="col-sm-9">
            <input type="hidden" id="coin" name="$StringUtil.firstLower($prop.Name).id" class="col-xs-10 col-sm-5" value="${btsdhmmstate.$StringUtil.firstLower($prop.Name).id }" placeholder="">
            <input type="text" id="coin_el" class="col-xs-10 col-sm-5" value="${btsdhmmstate.$StringUtil.firstLower($prop.Name).name }" placeholder="" readonly="readonly">
            <span class="input-group-btn">
                <button class="btn btn-sm btn-light" type="button" onclick='relOpen("btsdCoin/query.do","coin")'>
                    <i class="ace-icon fa fa-hand-o-right bigger-110"></i>
                </button>
            </span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="name">状态名称</label>
        <div class="col-sm-9">
            <input type="text" id="name" name="name" class="col-xs-10 col-sm-5" value="${btsdhmmstate.name }" placeholder="">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="no">编号</label>
        <div class="col-sm-9">
            <input type="text" class="input-mini" id="no" name="no" />
        </div>
        <script type="text/javascript">
            $('#no').ace_spinner({value:'${btsdhmmstate.no}',min:1 ,max: 256,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
        </script>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="minBound">最小边界</label>
        <div class="col-sm-9">
            <input type="text" class="input-mini" id="minBound" name="minBound" />
        </div>
        <script type="text/javascript">
            $('#minBound').ace_spinner({value:'${btsdhmmstate.minBound}',min:1 ,max: 256,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
        </script>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="maxBound">最大边界</label>
        <div class="col-sm-9">
            <input type="text" class="input-mini" id="maxBound" name="maxBound" />
        </div>
        <script type="text/javascript">
            $('#maxBound').ace_spinner({value:'${btsdhmmstate.maxBound}',min:1 ,max: 256,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
        </script>
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
			
			&nbsp; &nbsp; &nbsp;
			<button class="btn btn-grey" type="button" onclick="loadMain('btsdHmmState/init.do')">
				<i class="ace-icon fa fa-reply bigger-110"></i>
				返回
			</button>
		</div>
	</div>
</form>