$(document).ready( function(){
	  //form表单的一些样式优化
	  $("body").on("click", ".J_checkBox input", function(event) {
	    event.stopPropagation();
	    var _this = $(this).parents(".J_checkBox");
	    if (_this.hasClass("disabled") || _this.hasClass("check-disabled")) {
	      return;
	    }
	    if (_this.hasClass("on")) {
	      _this.removeClass("on");
	    } else {
	      _this.addClass("on");
	    }
	    //console.log($(this).find("input"));
	  });
	//页面加载完了之后移除loadding动画
	$(".J_mainContent", window.parent.document).removeClass("page-loading");
	// 全选和反选
	$('.layui-table thead').on( 'click', 'tr input[type="checkbox"]', function () {
	    var obj = $(".layui-table tbody input[type='checkbox']:checkbox");
	    if(this.checked){
	        obj.prop("checked", true);
	    }else{
	        obj.prop("checked", false);
	    }
	});
	//点击iframe页面内容关闭左侧菜单
	$("body").on("click", function() {
		$(".g-nav", window.parent.document).find(".nav-second-level").hide();
		$(".g-nav", window.parent.document).find("li").removeClass("active");
		//点击iframe页面内容关闭tab选项卡的右侧菜单
		$(".J_tabMenu", window.parent.document).hide();
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
	  closeBtn:2,//关闭按钮：1和2
	  content: url,
	  skin: 'layui-layer-rim',//加上边框
	  success:function(){//初始化
		  if ($.isFunction(onInit)) {
			  onInit(this);
          }
	  },
	  btn: ['保存', '取消'],
	  cancel:function(){
	  },
	  close: function(index){
	  },
	  yes: function(index, layero) {
		  if ($.isFunction(onSuccess)) {
			  var index = parent.layer.load(2);
              var iframeWindow = layero.find('iframe')[0].contentWindow;
              var bo = onSuccess(iframeWindow,function (success) {
            	  if(success){
            		  parent.layer.closeAll();
            	  }else{
            		  parent.layer.close(index);
            	  }
              });
          }
		  //onSuccess(layero);
		  //var iframeWin = window[layero.find('iframe')[0]['name']];
		  //layero.find('iframe')[0].contentWindow.save();
		  //parent.layer.close(index);
	  },btn2:function(index){
		  
	  }
	});
}
