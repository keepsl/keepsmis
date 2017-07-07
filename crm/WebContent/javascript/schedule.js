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
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"title","resizable":true,"label":"日程标题"},
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"status","resizable":true,"label":"日程状态",formatter:function(cellValue, options, rowObject){
			if(cellValue==1){
				return '未执行';
			}else if(cellValue==2){
				return '执行中';
			}else if(cellValue==3){
				return '结束';
			}else{
				return '';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"remark","resizable":true,"label":"日程内容",formatter:function(cellValue, options, rowObject){
			return "<a href='javascript:;' onclick='viewOpen(\""+encodeURI(cellValue)+"\",\"日程详细内容\",\"550\",\"380\")' ><span style='color:#3399ff'>详细</span></a>&nbsp&nbsp"+cellValue;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"scheduletime","resizable":true,"label":"执行时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm");
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
	var url = '../schedule/query?'+$("#form1").serialize();
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
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../schedule/delete',function (success) {
		if(success){
		}
	});
}
function add_obj(){
	openWin('../schedule/add','新增日程',750,550,'',onSaveUpdateSuccess);
}
function edit_obj(id){
	if(id==null || id == '' || id==undefined){
		id = myGrid.selectedId(1);
	}
	if(id!=null){
		openWin('../schedule/edit?id='+id,'编辑日程',750,550,'',onSaveUpdateSuccess);
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