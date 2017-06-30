var grid=null,myGrid=null,pid = '';
$(function(){
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"name","resizable":true,"label":"栏目名称"},
		//{"hidden":false,"align":"left","sortable":true,"width":120,"name":"pid","resizable":true,"label":"上级栏目"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"pid","resizable":true,"label":"父子节点",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==0){
				return '父';
			}else{
				return '子';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"templatetype","resizable":true,"label":"栏目类型",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '列表页';
			}else{
				return '内容页';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"listtemplateurl","resizable":true,"label":"列表页模版"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"articletemplateurl","resizable":true,"label":"内容页模版"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"isshow","resizable":true,"label":"是否显示",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是';
			}else{
				return '否';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"sort","resizable":true,"label":"顺序"},
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
	//获取文章栏目树
	$.ajax({
        type:"POST",
        url:"../articletype/getArticleTypeTree",
        success:function(data){
        	$.fn.zTree.init($("#treeid"), setting,  $.parseJSON(data).message);
        	$("#treediv").removeClass("g-area-loading");
        }
    });
}

function query(){
	var url = '../articletype/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId", jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../articletype/delete',function (success) {
		if(success){
			initTree();
		}
	});
}

function add_obj(){
	var onSuccess = function (iframe,callback) {
        iframe.save(function (success) {
        	if(success){
        		query();
            	initTree();
        	}
        	callback(success);
        });
    }
	openWin('../articletype/add','新增文章栏目',700,500,'',onSuccess);
}

function edit_obj(){
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
		openWin('../articletype/edit?id='+ids,'编辑文章栏目',700,500,'',onSuccess);
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