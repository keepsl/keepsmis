var grid=null,myGrid=null,pid = '';
$(function(){
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"name","resizable":true,"label":"标签名"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"typename","resizable":true,"label":"所属栏目"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"clicknum","resizable":true,"label":"点击数"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"ishot","resizable":true,"label":"是否热门",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIshotById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIshotById\');">【是】</a>';
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
function initTree(){
	$("#treeid").trees({url:'../articletype/getArticleTypeTree',
		callback:function(id,name,treeId){
			$("input[name='typeid']").val(id);
    		query();
		} 
	});
	$("#treediv").removeClass("g-area-loading");
}

function ajax_set_fieid(value,id,url){
	Ajax.post("../tag/"+url,{id:id,value:value},function(p){
		layer.msg('操作成功!');
		query();
	});
}
function query(){
	var url = '../tag/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId", jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../tag/delete',function (success) {
		if(success){
		}
	});
}

function add_obj(){
	var onSuccess = function (iframe,callback) {
        iframe.save(function (success) {
        	if(success){
        		query();
        	}
        	callback(success);
        });
    }
	openWin('../tag/add?typeid='+$("input[name='typeid']").val(),'新增标签',550,350,'',onSuccess);
}

function edit_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		var onSuccess = function (iframe,callback) {
	        iframe.update(function (success) {
	        	if(success){
	        		query();
	        	}
	        	callback(success);
	        });
	    }
		openWin('../tag/edit?id='+id,'编辑标签',550,350,'',onSuccess);
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