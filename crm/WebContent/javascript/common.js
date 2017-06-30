var sysMsgSwitch = 0;//消息开关,0关闭，1开启
$(document).ready( function(){
	setingSysMsg(sysMsgSwitch);
	  // form表单的一些样式优化
	$("body").on(
	"click",
	".J_checkBox input",
	function(event) {
		event.stopPropagation();
		var _this = $(this).parents(".J_checkBox");
		if (_this.hasClass("disabled")
				|| _this.hasClass("check-disabled")) {
			return;
		}
		if (_this.hasClass("on")) {
			_this.removeClass("on");
		} else {
			_this.addClass("on");
		}
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
	$("body").on("click", ".J_radioBox label input", function() {
		var _this = $(this).parents("label");
	    if (_this.hasClass("disabled") || _this.hasClass("check-disabled")) {
	      return;
	    }
	    if (_this.hasClass("on")) return;
	    _this.parents(".J_radioBox").find("label").removeClass("on");
	    _this.addClass("on");
	});
	  //列表的提示信息的展开与关闭
	$("body").on("click", ".J_arrowAction", function() {
		if ($(this).find('i').length > 0) {
			var _h4 = $(this).parents("H4");
			var _JtipBox = _h4.next('.J_tipBox');
			if (_JtipBox.length > 0) {
				if (_JtipBox.is(":hidden")) {
					$(this).find('i').attr("class", 'gi-down');
					_JtipBox.slideDown("slow", function() {// 消息展开后需要重新设置grid高度，
						_h4.css("border-color", "#faebcc");
						sysMsgSwitch = 1;
						setComputeGridHigth();
					});
				} else {
					$(this).find('i').attr("class", 'gi-up');
					_JtipBox.slideUp("slow", function() {// 消息折叠后需要重新设置grid高度，
						_h4.css("border-color", "#ececec");
						sysMsgSwitch = 0;
						setComputeGridHigth();
					});
				}
			}
		}
	});
	
	  //更多筛选条件的展开与关闭
	  $("body").on("click", ".J_downBtn", function() {
	    $(this).hide();
	    $(this).parents("form").find(".J_upBtn").show();
	    $(this).parents("form").find(".J_upBtn").removeClass("hide");
	    $(this).parents("form").find(".J_moreFilter").slideDown("slow",function(){
	        setComputeGridHigth();
	    });
	  });
	  $("body").on("click", ".J_upBtn", function() {
	    $(this).hide();
	    $(this).parents("form").find(".J_downBtn").show();
	    $(this).parents("form").find(".J_moreFilter").slideUp("slow",function(){
	    	setComputeGridHigth();
	    });
	  });
	  
	  
	//竖直列表点击选中事件
	$("body").on("click", ".J_navStacked li", function() {
        if($(this).hasClass("active")) return;
        $(this).addClass("active");
        $(this).siblings().removeClass('active');
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
 * 计算grid高度
 * @returns
 */
function setComputeGridHigth(moreFilterHright){
	var warningBoxHeight=0;
	if($(".g-warning-box").length>0){
		warningBoxHeight = $(".g-warning-box").height();
	}
	var titleHeight=0;
	if($(".g-title").length>0){
		titleHeight=$(".g-title").height();
	}
	var formHeight = 0;
	if($(".col-container").length>0){
		formHeight=$(".col-container").height();
	}
	var gridHeights = warningBoxHeight - titleHeight - formHeight - 30 - 20 - 20 - 35;
	if(sysMsgSwitch == 1){//消息显示
		var msgHeight = $(".g-warning-box").find(".J_tipBox").height()+30;//消息高度
		gridHeights = gridHeights - msgHeight;
	}
	if(gridHeights<0){
		gridHeights=80;
	}
	if($("#jqGridId").length>0){
	    $("#jqGridId").setGridHeight(gridHeights);
	}
}
//初始化设置是否显示页面简介
function setingSysMsg(sysMsgSwitch){
	if(sysMsgSwitch == 0){
		var _J_tipBox = $(".J_tipBox");
		for(var i =0;i<_J_tipBox.length;i++){
			_J_tipBox.eq(i).hide();
			if(_J_tipBox.eq(i).prev('H4').length>0){
				_J_tipBox.eq(i).prev('H4').css("border-color","#ececec");
				_J_tipBox.eq(i).prev('H4').find(".J_arrowAction").find("i").attr("class",'gi-up');
			}else{
				parent.$(".aui_titleBar").find(".aui_title").css("border-bottom","1px solid");
				parent.$(".aui_titleBar").find(".aui_title").css("border-color","#ececec");
				parent.$(".aui_titleBar").find(".J_arrowAction").find('i').attr("class",'gi-up');
			}
		}
	}else{//打开窗口需要设置线条
		parent.$(".aui_titleBar").find(".aui_title").css("border-bottom","0px solid");
	}
}