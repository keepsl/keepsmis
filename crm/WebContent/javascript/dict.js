var grid=null,myGrid=null,isinsertdict=1;
var onSaveUpdateSuccess = function (iframe,callback) {
    iframe.saveOrUpdate(function (success) {
    	if(success){
    		query();
    	}
    	callback(success);
    });
}
$(function(){
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"name","resizable":true,"label":"字典名称"},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"value","resizable":true,"label":"字典值"},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"typename","resizable":true,"label":"所属分类"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"sort","resizable":true,"label":"顺序"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"status","resizable":true,"label":"状态",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '启用';
			}else if(cellvalue==2){
				return '禁用';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"fixed","resizable":true,"label":"系统内置",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '<span style="color: #FF0000;">[系统固化数据]</span>';
			}else{
				return '否';
			}
		}}/*,
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"createtime","resizable":true,"label":"操作",formatter:function(cellValue, options, rowObject){
			var authorize = "<a class='gbn-min gbn-deploy gbn-blue' href='javascript:;' title='授权' onclick='authorize("+rowObject.id+")' ><i></i></a>";
        	return authorize;
		}}*/
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

function initTree(){
	var setting = {
        view: {
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
        	onClick: function(e,treeId, treeNode){
        		isinsertdict=treeNode.type;
        		$("input[name='typeid']").val(treeNode.id);
        		query();
        	}
    	}
    };
	//获取菜单列表树
	$.ajax({
        type:"POST",
        url:"../dict/getDicttypeTree",
        success:function(data){
        	$.fn.zTree.init($("#dicttypetreeid"), setting,  $.parseJSON(data).message);
        	$("#treediv").removeClass("g-area-loading");
        }
    });
}

function query(){
	var url = '../dict/queryDict?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_type_obj(){
	var typeid = $("input[name='typeid']").val();
	if(typeid==null || typeid=='' || typeid == 0){
		Log.e('请选择要删除的字典分类.');
		return false;
	}
	layer.confirm('确认要删除这些数据吗?', {
	  btn: ['确认','取消'] //按钮
	}, function(){
		$.post('../dict/deleteDictTypeById', {id:typeid}, function(json){
			if(json&&json.success==true){
				initTree();
				layer.closeAll();
			}else{
				layer.closeAll();
				Log.e(json.message);
			}
		},'json');
	}, function(){
	});
}
var onDictTypeSuccess = function (iframe,callback) {
    iframe.saveOrUpdate(function (success) {
    	if(success){
    		query();
    		initTree();
    	}
    	callback(success);
    });
}
function add_type_obj(){
	var typeid = $("input[name='typeid']").val();
	openWin('../dict/addDictType?id='+$("input[name='typeid']").val(),'新增字典分类',750,550,'',onDictTypeSuccess);
}
function edit_type_obj(id){
	var typeid = $("input[name='typeid']").val();
	if(typeid==null || typeid=='' || typeid == 0){
		Log.e('请选择要编辑的字典分类.');
		return false;
	}
	openWin('../dict/editDictType?id='+$("input[name='typeid']").val(),'编辑字典分类',750,550,'',onDictTypeSuccess);
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../dict/deleteDictById',function (success) {
		if(success){
			initTree();
		}
	});
}
function add_obj(){
	var typeid = $("input[name='typeid']").val();
	if(typeid==null || typeid=='' || typeid == 0){
		Log.e('请在左侧选择分类后在进行新增字典.');
		return false;
	}
	if(isinsertdict==2){
		Log.e('该字典分类下不允许添加字典.');
		return false;
	}
	openWin('../dict/addDict?typeid='+$("input[name='typeid']").val(),'新增字典',750,550,'',onSaveUpdateSuccess);
}
function edit_obj(id){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../dict/editDict?id='+id,'编辑字典',750,550,'',onSaveUpdateSuccess);
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