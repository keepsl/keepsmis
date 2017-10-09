var grid=null,myGrid=null,pid = '';
var onSaveUpdateSuccess = function (iframe,callback) {
    iframe.saveOrUpdate(function (success) {
    	if(success){
    		query();
    		initTree();
    	}
    	callback(success);
    });
}
$(function(){
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"name","resizable":true,"label":"组织名称"},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"pname","resizable":true,"label":"上级组织",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '启用';
			}else if(cellvalue==2){
				return '禁用';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"updatetime","resizable":true,"label":"更新时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm:ss");
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"createtime","resizable":true,"label":"创建时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm:ss");
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
        		$("input[name='pid']").val(treeNode.id);
        		query();
        	}
    	}
    };
	//获取菜单列表树
	$.ajax({
        type:"POST",
        url:"../org/getOrgTree",
        success:function(data){
        	$.fn.zTree.init($("#orgtreeid"), setting,  $.parseJSON(data).message);
        	$("#treediv").removeClass("g-area-loading");
        }
    });
}

function query(){
	var url = '../org/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../org/delete',function (success) {
		if(success){
			initTree();
		}
	});
}
function add_obj(){
	openWin('../org/add?pid='+$("input[name='pid']").val(),'新增组织',700,500,'',onSaveUpdateSuccess);
}
function edit_obj(id){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../org/edit?id='+id,'编辑组织',700,500,'',onSaveUpdateSuccess);
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