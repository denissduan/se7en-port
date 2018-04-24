/**
 * 加载页面
 * @param url
 */
function loadPage(dom,url,param){
	dom.load(url,param, function( response, status, xhr ) {
        if ( status == "error" ) {
            dom.html(response);
        }
    });
}

function loadMain(url,param){
	$('#container').load(url,param);
}

function openMenu(url,aTag){
	$.core.clear();
	loadPage($('#container'),url);
	$('#breadcrumb').text($(aTag).attr('breadcrumb'));
}

function alert(msg){
	$.fancybox.open({
		'modal' : true,
		'content' : "<div style=\"margin:1px;width:240px;\">" + msg + "<div style=\"text-align:right;margin-top:10px;\"><button class=\"btn btn-white btn-default btn-round\" onclick=\"jQuery.fancybox.close();\" ><i class=\"ace-icon fa fa-check bigger-110\"></i>确定</button></div></div>"
	});
}

function confirm(msg,trueCall,falseCall){
	$.fancybox.open({
		modal : true,
		content : "<div style=\"margin:1px;width:240px;\">" + msg + "<div style=\"text-align:right;margin-top:10px;\"><button id=\"fancyConfirm_ok\" class=\"btn btn-white btn-default btn-round\" ><i class=\"ace-icon fa fa-check bigger-110\"></i>确定</button><button id=\"fancyConfirm_cancel\" class=\"btn btn-white btn-default btn-round\" style=\"margin-left:5px;\" ><i class=\"ace-icon fa fa-times bigger-110\"></i>取消</button></div></div>",
		afterShow : function() {
		    $("#fancyConfirm_cancel").click(function() {
			    ret = false; 
			    $.fancybox.close();
			    if(falseCall)
			    	falseCall();
			})
			$("#fancyConfirm_ok").click(function() {
			    ret = true; 
			    $.fancybox.close();
			    if(trueCall)
			    	trueCall();
			})
		}
	});
}

function prompt(msg){
	return bootbox.prompt(msg,function(result){
		return result;
	})
}