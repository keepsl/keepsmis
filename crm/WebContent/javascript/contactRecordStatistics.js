var grid=null,myGrid=null,pid = '',drowid=0;
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
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"clientname","resizable":true,"label":"客户姓名"},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"clientphone","resizable":true,"label":"联系电话",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			var reg = /^(\d{3})\d{4}(\d{4})$/;
			return "<span title='"+rowObject.clientphone+"' >"+cellValue.replace(reg, "$1****$2")+"</span>";
			return cellValue;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"contactnum","resizable":true,"label":"联系次数"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"contacttime","resizable":true,"label":"最近联系时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			var cdate = new Date(cellValue);
			var str = cdate.format("yyyy-MM-dd hh:mm");
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"visittime","resizable":true,"label":"最近来访时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			var cdate = new Date(cellValue);
			var str = cdate.format("yyyy-MM-dd hh:mm");
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"fzempname","resizable":true,"label":"负责人",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			return cellValue;
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

function query(reset){
	if(reset==1){
		$('#form1')[0].reset();
	}
	var url = basePath+'/contactrecord/queryStatistics/?'+$("#form1").serialize();
	
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:false, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
//调整表格宽高
function resize(){
    $(".J_conWarp").height($(window).height()-20);
    $(".J_conWarp").niceScroll({});
    var width = $("#jqGridWarp").width();
	$("#jqGridId").setGridWidth(width-2);
	$("#jqGridId").setGridHeight($(window).height()-190);
}

$(window).resize(resize); 