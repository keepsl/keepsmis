var grid=null,myGrid=null;
$(function(){
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":true,"label":"id"},
		{"hidden":false,"align":"left","sortable":false,"width":120,"name":"method","resizable":true,"label":"方法名"},
		{"hidden":false,"align":"left","sortable":false,"width":120,"name":"message","resizable":true,"label":"参数"},
		{"hidden":false,"align":"left","sortable":false,"width":120,"name":"ip","resizable":true,"label":"IP"},
		{"hidden":false,"align":"left","sortable":false,"width":100,"name":"nickname","resizable":true,"label":"操作人"},
		{"hidden":false,"align":"left","sortable":false,"width":150,"name":"createtime","resizable":true,"label":"创建时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm:ss");
		}}
	];
	query();
	resize();
});

function query(){
	var url = '../log/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId", jqGridPagerId:"jqGridPagerId",multiselect:false};
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
	//setComputeGridHigth();
	$("#jqGridId").setGridHeight($(window).height()-190);
}

$(window).resize(resize); 