var grid=null,myGrid=null,pid = '';
$(function(){
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"classname","resizable":true,"label":"分类名称"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"pid","resizable":true,"label":"父子节点",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==0){
				return '父';
			}else{
				return '子';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"ishot","resizable":true,"label":"是否热门",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIshotById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIshotById\');">【是】</a>';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"isrecommend","resizable":true,"label":"是否推荐",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIsrecommendById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIsrecommendById\');">【是】</a>';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"classicon","resizable":true,"label":"图标"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"classsort","resizable":true,"label":"顺序"},
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
	//获取商品分类树
	$.ajax({
        type:"POST",
        url:"../goodsClass/getGoodsClassTree",
        success:function(data){
        	$.fn.zTree.init($("#treeid"), setting,  $.parseJSON(data).message);
        	$("#treediv").removeClass("g-area-loading");
        }
    });
}

function query(){
	var url = '../goodsClass/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId", jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../goodsClass/delete',function (success) {
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
	openWin('../goodsClass/add?pid='+$("input[name='pid']").val(),'新增商品分类',700,500,'',onSuccess);
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
		openWin('../goodsClass/edit?id='+ids,'编辑商品分类',700,500,'',onSuccess);
	}
}
function ajax_set_fieid(value,id,url){
	Ajax.post("../goodsClass/"+url,{id:id,value:value},function(p){
		layer.msg('操作成功!');
		query();
	});
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