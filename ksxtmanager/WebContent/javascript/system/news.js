var grid=null,myGrid=null;
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
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"title","resizable":true,"label":"操作",formatter:function(cellValue, options, rowObject){
			var str = '';
				str +="<a class='gbn-min gbn-check gbn-blue' href='javascript:;' title='预览' onclick='showNews("+rowObject.id+")' ><i></i></a>";	
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"title","resizable":true,"label":"公告标题"},
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"dicttypename","resizable":true,"label":"公告类型"},
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"empname","resizable":true,"label":"发布人"},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"publishtime","resizable":true,"label":"发布时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm:ss");
		}},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"createtime","resizable":true,"label":"创建时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm:ss");
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
	var url = '../news/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId",ondblClickRow: function (rowid, status) {
		//双击
		edit_obj(rowid);
	}};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function showNews(id){
	openViewWin('../news/show?id='+id,'公告详情',850,550,'');
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../news/delete',function (success) {
		if(success){
		}
	});
}
function add_obj(){
	openWin('../news/add','新增公告',850,550,'',onSaveUpdateSuccess);
}
function edit_obj(id){
	if(id==null || id == '' || id==undefined){
		id = myGrid.selectedId(1);
	}
	if(id!=null){
		openWin('../news/edit?id='+id,'编辑公告',850,550,'',onSaveUpdateSuccess);
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