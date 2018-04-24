(function(){
    $('#processData').click(function(){
        ajaxGet("dwBtsdHourLine/processData.do", function(data){
            if(data.sign == true){
                alert("执行成功");
                // refreshPage();
            }else{
                alert("执行失败");
            }
        });
    });
    $('#incrementScaleKMean').click(function () {
        ajaxGet("dwBtsdHourLine/incrementScaleKMean.do", function(data){
            if(data.sign == true){
                alert("执行成功");
                // refreshPage();
            }else{
                alert("执行失败");
            }
        });
    });
    $('#createHmm').click(function(){
        ajaxGet("dwBtsdHourLine/createHmm.do", function(data){
            if(data.sign == true){
                alert("执行成功");
            }else{
                alert("执行失败");
            }
        });
    });
})();