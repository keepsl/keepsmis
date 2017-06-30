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
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"createtime","resizable":true,"label":"操作",formatter:function(cellValue, options, rowObject){
			var authorize = "<a class='gbn-min gbn-deploy gbn-blue' href='javascript:;' title='授权' onclick='showMenuGrantWin("+rowObject.id+")' ><i></i></a>";
        	return authorize;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"name","resizable":true,"label":"角色名称"},
		{"hidden":false,"align":"left","sortable":true,"width":250,"name":"status","resizable":true,"label":"状态",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '启用';
			}else if(cellvalue==2){
				return '禁用';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"updatetime","resizable":true,"label":"更新时间",formatter:function(cellValue, options, rowObject){
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
function showMenuGrantWin(roleid){
	var onData = function (iframe,callback) {
		iframe.save(function (success) {
	    	callback(success);
	    });
	    /*iframe.getData(function (data) {
	    	$.post(basePath+'/menu/saveOrgMenuGrant', {'roleid':data.roleid,'idarr':(data.idarr).join(","),'typearr':(data.typearr).join(",")}, function(json){
				if(json&&json.success==true){
					callback(true);
				}else{
					Log.e(json.message);
					callback(false);
				}
			},'json');
	    	callback(false);
	    });*/
	}
	openWin(basePath+'/menu/showMenuGrantWin?roleid='+roleid,'授权',350,500,'',onData);
}
function query(){
	var url = '../role/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../role/delete',function (success) {
		if(success){
		}
	});
}
function add_obj(){
	openWin('../role/add','新增角色',700,500,'',onSaveUpdateSuccess);
}
function edit_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../role/edit?id='+id,'编辑角色',700,500,'',onSaveUpdateSuccess);
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