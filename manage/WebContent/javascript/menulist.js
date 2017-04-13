var grid=null,myGrid=null,pid = '';
$(function(){
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"menuname","resizable":true,"label":"菜单名称"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"pname","resizable":true,"label":"上级菜单"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"url","resizable":true,"label":"菜单连接"},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"icon","resizable":true,"label":"样式名"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"status","resizable":true,"label":"状态",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '启用';
			}else{
				return '禁用';
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
        		$("input[name='pid']").val(treeNode.id);
        		query();
        	}
    	}
    };
	//获取菜单列表树
	$.ajax({
        type:"POST",
        url:"../menu/getMenuTree",
        success:function(data){
        	$.fn.zTree.init($("#menutreeid"), setting,  $.parseJSON(data).message);
        	$("#treediv").removeClass("g-area-loading");
        }
    });
}

function query(){
	var url = '../menu/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId", jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function deleteMenu(){
	myGrid.bindGridDelEvent('../menu/delete',function (success) {
		if(success){
			initTree();
		}
	});
}

function addMenu(){
	var onSuccess = function (iframe,callback) {
        iframe.save(function (success) {
        	if(success){
        		query();
            	initTree();
        	}
        	callback(success);
        });
    }
	openWin('../menu/add','新增菜单',700,500,'',onSuccess);
}

function editMenu(){
	var ids = myGrid.selectedId(1);
	if(ids!=null){
		var onSuccess = function (iframe,callback) {
	        iframe.update(function (success) {
	        	if(success){
	        		query();
	            	initTree();
	        	}
	        	callback(success);
	        });
	    }
		openWin('../menu/edit?id='+ids,'编辑菜单',700,500,'',onSuccess);
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