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
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"advTitle","resizable":true,"label":"广告名称",formatter:function(cellvalue, options, rowObject){
			return '<a style="color:#5cb85c" href="javascript:;" onclick="window.open(\''+rowObject.advLink+'\')" target="main">'+cellvalue+'</a>';
		}},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"apName","resizable":true,"label":"所属广告位"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"apClass","resizable":true,"label":"类别",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '图片';
			}else if(cellvalue==2){
				return '文字';
			}else if(cellvalue==3){
				return '幻灯';
			}else if(cellvalue==4){
				return 'Flash';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"advLink","resizable":true,"label":"连接"},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"clickNum","resizable":true,"label":"点击量"},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"slideSort","resizable":true,"label":"幻灯片排序"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"starttime","resizable":true,"label":"开始时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd");
		}},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"endtime","resizable":true,"label":"结束时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd");
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"isShow","resizable":true,"label":"是否展示",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIsshowById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIsshowById\');">【是】</a>';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"createtime","resizable":true,"label":"创建时间",formatter:function(cellValue, options, rowObject){
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
function ajax_set_fieid(value,id,url){
	Ajax.post("../adv/"+url,{id:id,value:value},function(p){
		layer.msg('操作成功!');
		query();
	});
}
function query(){
	var url = '../adv/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:false, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../adv/delete',function (success) {
		if(success){
		}
	});
}
function add_obj(){
	openWin('../adv/add','新增广告',750,600,'',onSaveUpdateSuccess);
}
function edit_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../adv/edit?id='+id,'编辑广告',750,600,'',onSaveUpdateSuccess);
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