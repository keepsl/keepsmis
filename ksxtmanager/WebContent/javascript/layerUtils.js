

function openViewWin(url, title, width, height, onInit) {
	parent.layer.open({
	  type: 2,
	  title :title,
	  area: [width+"px", height+"px"],
	  fixed: false, //不固定
	  maxmin: false,
	  closeBtn:2,//关闭按钮：1和2
	  content: url,
	  success:function(){//初始化
		  if ($.isFunction(onInit)) {
			  onInit(this);
          }
	  },
	  btn: ['关闭'],
	  cancel:function(){
	  },
	  close: function(index){
	  }
	});
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
			  var loadindex = parent.layer.load(2);
              var iframeWindow = layero.find('iframe')[0].contentWindow;
              var bo = onSuccess(iframeWindow,function (success) {
            	  if(success){
            		  parent.layer.close(index);
            		  parent.layer.close(loadindex);
            	  }else{
            		  parent.layer.close(loadindex);
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

function viewOpen(content, title, width, height) {
	parent.layer.open({
	  type: 1,
	  title :title,
	  area: [width+"px", height+"px"],
	  fixed: false, //不固定
	  maxmin: false,
	  closeBtn:2,//关闭按钮：1和2
	  content: '<div class="container-fluid">'+decodeURI(content)+'</div>',
	  skin: 'layui-layer-rim'
	});
}
