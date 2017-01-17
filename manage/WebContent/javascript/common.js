$(document).ready( function(){ 
	// 全选和反选
	$('.layui-table thead').on( 'click', 'tr input[type="checkbox"]', function () {
	    var obj = $(".layui-table tbody input[type='checkbox']:checkbox");
	    if(this.checked){
	        obj.prop("checked", true);
	    }else{
	        obj.prop("checked", false);
	    }
	});
});
function selectedId(type){
    /* 获取选中的项 */
    var arr = new Array;
    $(".layui-table tbody input[type='checkbox']:checked").each(function(){
    	arr.push(this.value);
    });
	if(type!=undefined){
		if(type != arr.length){
			Log.e("请选择"+type+"条要操作的记录，当前选了"+arr.length+"条记录!");
			return '';
		}
	}else{
		if(arr.length==0){
			Log.e("至少选择1条要操作的记录，当前选了"+arr.length+"条记录!");
			return '';
		}
	}
	return arr.join(",");
}
/**
 * 
 * @param url	打开页面url
 * @param title	页面标题
 * @param width	页面宽度
 * @param height 页面高度
 * @param onInit 初始化页面函数
 * @param onSuccess 
 * @returns
 */
function openWin(url, title, width, height, onInit, onSuccess) {
	parent.layer.open({
	  type: 2,
	  title :title,
	  area: [width+"px", height+"px"],
	  fixed: false, //不固定
	  maxmin: false,
	  closeBtn:1,//关闭按钮：1和2
	  content: url,
	  skin: 'layui-layer-rim',//加上边框
	  success:function(){//初始化
		  if ($.isFunction(onInit)) {
			  onInit(this);
          }
	  },
	  btn: ['保存', '取消'],
	  cancel:function(){
		  alert(123);
	  },
	  close: function(index){
		  alert(222);
	  },
	  yes: function(index, layero) {
		  if ($.isFunction(onSuccess)) {
              var iframeWindow = layero.find('iframe')[0].contentWindow;
              onSuccess(iframeWindow);
          }
		  //onSuccess(layero);
		  //var iframeWin = window[layero.find('iframe')[0]['name']];
		  //layero.find('iframe')[0].contentWindow.save();
		  //parent.layer.close(index);
	  },btn2:function(index){
		  
	  }
	});
}
