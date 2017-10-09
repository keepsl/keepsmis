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
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"title","resizable":true,"label":"试卷名称"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"shijuantypename","resizable":true,"label":"分类"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"kaoshitype","resizable":true,"label":"参与方式",formatter:function(cellValue, options, rowObject){
			if(cellValue==1){
				return '按部门';
			}else if(cellValue==2){
				return '按人员';
			}else if(cellValue==3){
				return '公开';
			}else{
				return cellValue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"degofdifftypename","resizable":true,"label":"难易度"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"empname","resizable":true,"label":"创建人"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"updatetime","resizable":true,"label":"更新时间",formatter:function(cellValue, options, rowObject){
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
        		$("input[name='shijuantype']").val(treeNode.id);
        		query();
        	}
    	}
    };
	//获取菜单列表树
	$.ajax({
        type:"POST",
        url:"../dict/getDicttypeTreeByCode?code=shijuan_type",
        success:function(data){
        	$.fn.zTree.init($("#treeid"), setting,  $.parseJSON(data).message);
        	$("#treediv").removeClass("g-area-loading");
        }
    });
}

function query(){
	var url = '../shijuan/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../shijuan/delete',function (success) {
		if(success){
			query();
		}
	});
}
function add_obj(){
	openWin('../shijuan/add?pid='+$("input[name='pid']").val(),'新增试卷',800,600,'',onSaveUpdateSuccess);
}
function edit_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../shijuan/edit?id='+id,'编辑试卷',800,600,'',onSaveUpdateSuccess);
	}
}
function info_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openViewWin('../questions/info?id='+id,'试卷详细',600,380,'');
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