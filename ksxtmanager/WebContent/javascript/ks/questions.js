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
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"title","resizable":true,"label":"试题名称"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"questionstypename","resizable":true,"label":"分类"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"problemtypename","resizable":true,"label":"类型"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"degofdifftypename","resizable":true,"label":"难易度"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"correctnum","resizable":true,"label":"正确"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"errornum","resizable":true,"label":"错误"},
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
        		$("input[name='questionstype']").val(treeNode.id);
        		query();
        	}
    	}
    };
	//获取菜单列表树
	$.ajax({
        type:"POST",
        url:"../dict/getDicttypeTreeByCode?code=questions_type",
        success:function(data){
        	$.fn.zTree.init($("#questionstreeid"), setting,  $.parseJSON(data).message);
        	$("#treediv").removeClass("g-area-loading");
        }
    });
}

function query(){
	var url = '../questions/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../questions/delete',function (success) {
		if(success){
			query();
		}
	});
}
function add_obj(){
	openWin('../questions/add?pid='+$("input[name='pid']").val(),'新增试题',800,600,'',onSaveUpdateSuccess);
}
function edit_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../questions/edit?id='+id,'编辑试题',800,420,'',onSaveUpdateSuccess);
	}
}
function info_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openViewWin('../questions/info?id='+id,'试题详细',600,380,'');
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