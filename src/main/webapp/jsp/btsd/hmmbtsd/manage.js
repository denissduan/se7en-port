(function(){
    $('#testHmm').click(function(){
        ajaxGet("hmmBtsd/testHmm.do", function(data){
            if(data.sign == true){
                alert("执行成功");
            }else{
                alert("执行失败");
            }
        });
    });
})();