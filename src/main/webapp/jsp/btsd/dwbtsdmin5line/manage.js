(function(){
    $('#processData').click(function(){
        ajaxGet("dwBtsdMin5Line/processData.do", function(data){
            if(data.sign == true){
                alert("执行成功");
                //refreshPage();
            }else{
                alert("执行失败");
            }
        });
    });
    $('#createHmm').click(function(){
        ajaxGet("dwBtsdMin5Line/createHmm.do", function(data){
            if(data.sign == true){
                alert("执行成功");
            }else{
                alert("执行失败");
            }
        });
    });
})();