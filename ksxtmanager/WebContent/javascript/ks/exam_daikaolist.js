var grid=null,myGrid=null,pid = '';
var onSaveUpdateSuccess = function (iframe,callback) {
    iframe.saveOrUpdate(function (success) {
    	if(success){
    		query();
    	}
    	callback(success);
    });
}
$(function(){
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":180,"name":"title","resizable":true,"label":"标题"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"shijuantypename","resizable":true,"label":"分类"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"degofdifftypename","resizable":true,"label":"难度"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"timelength","resizable":true,"label":"答题时长（分钟）"},
		{"hidden":false,"align":"left","sortable":true,"width":180,"name":"startendtime","resizable":true,"label":"考试时间",formatter:function(cellValue, options, rowObject){
			return rowObject.starttimestr+'至'+rowObject.endtimestr;
		}}
	];
	query();
	resize();
	//初始化enter事件
	$('#form1').bind('keyup', function(event){
	   if (event.keyCode=="13"){
		   query();
	   }
	});
});

function query(){
	var url = basePath+'/shijuan/queryWksj?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:false, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function resize(){
	$(".J_conWarp").height($(window).height()-20);
    $(".J_conWarp").niceScroll({});
	$("#jqGridId").setGridWidth($(window).width()-50);
	$("#jqGridId").setGridHeight($(window).height()-180);
}

$(window).resize(resize); 