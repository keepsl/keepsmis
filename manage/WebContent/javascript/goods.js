var grid=null,myGrid=null,myGrid2=null,pid = '';
$(init);
function init(){
  	$("#goodspanelli").addClass("active");
  	$("#goodspanel").addClass("active");
}
$(function(){
	$("#myTab li").click(function(){
		if($(this).index()==1){
			query2();
			resize();
		}else{
			query();
		}
	});
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"classname","resizable":true,"label":"所属分类"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"goodsname","resizable":true,"label":"商品名称",formatter:function(cellvalue, options, rowObject){
			return '<a style="color:#5cb85c" href="javascript:;" onclick="window.open(\'/shengzhelai/goods/info/'+rowObject.id+'\')" target="main">'+cellvalue+'</a>';
		}},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"goodssource","resizable":true,"label":"商品来源",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '淘宝';
			}else if(cellvalue==2){
				return '天猫';
			}else if(cellvalue==3){
				return '京东';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"ishot","resizable":true,"label":"热门",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIshotById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIshotById\');">【是】</a>';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"isrecommend","resizable":true,"label":"推荐",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIsrecommendById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIsrecommendById\');">【是】</a>';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":160,"name":"couponafterprice","resizable":true,"label":"原价-折扣=现价",formatter:function(cellvalue, options, rowObject){
			return rowObject.currentprice +"-"+rowObject.couponprice+"="+cellvalue+"（元）";
		}},
		{"hidden":false,"align":"left","sortable":true,"width":130,"name":"starttime","resizable":true,"label":"折扣开始时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd");
		}},
		{"hidden":false,"align":"left","sortable":true,"width":130,"name":"endtime","resizable":true,"label":"折扣结束时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd");
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
        		$("input[name='classid']").val(treeNode.id);
        		if($("#myTab li").eq(0).hasClass('active')){
        			query();
        		}else{
        			query2();
        		}
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
	var url = '../goods/query?searchtype=1&'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:false, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}

function query2(){
	var url = '../goods/query?searchtype=2&'+$("#form2").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId2",multiselect:true, shrinkToFit:false, jqGridPagerId:"jqGridPagerId2"};
	if(myGrid2 == null){
		myGrid2 = new MyJqGrid(param);
		myGrid2.loadMyGrid();
	}else{
		myGrid2.query(param);
	}
}
function imp_obj(){
	var onSuccess = function (iframe,callback) {
        iframe.saveFile(function (success) {
        	if(success){
        		query();
            	initTree();
        	}
        	callback(success);
        });
    }
	openWin('../goods/imp','导入商品',600,350,'',onSuccess);
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../goods/delete',function (success) {
		if(success){
			initTree();
		}
	});
}
function delete_obj2(){
	var ids = myGrid2.bindGridDelEvent('../goods/delete',function (success) {
		if(success){
			initTree();
		}
	});
}

function add_obj(){
	var onSuccess = function (iframe,callback) {
        iframe.saveOrUpdate(function (success) {
        	if(success){
        		query();
        	}
        	callback(success);
        });
    }
	openWin('../goods/add?classid='+$("input[name='classid']").val(),'新增商品',980,600,'',onSuccess);
}

function edit_obj(){
	var ids = myGrid.selectedId(1);
	if(ids!=null){
		var onSuccess = function (iframe,callback) {
	        iframe.saveOrUpdate(function (success) {
	        	if(success){
	        		query();
	        	}
	        	callback(success);
	        });
	    }
		openWin('../goods/edit?id='+ids,'编辑商品',980,600,'',onSuccess);
	}
}
function edit_obj2(){
	var ids = myGrid2.selectedId(1);
	if(ids!=null){
		var onSuccess = function (iframe,callback) {
	        iframe.saveOrUpdate(function (success) {
	        	if(success){
	        		query();
	        	}
	        	callback(success);
	        });
	    }
		openWin('../goods/edit?id='+ids,'编辑商品',980,600,'',onSuccess);
	}
}
function ajax_set_fieid(value,id,url){
	Ajax.post("../goods/"+url,{id:id,value:value},function(p){
		layer.msg('操作成功!');
		query();
	});
}
//调整表格宽高
function resize(){
    $(".J_conWarp").height($(window).height()-20);
    $(".J_conWarp").niceScroll({});
    var width = $(window).width();
	$("#jqGridId").setGridWidth(width-250);
	$("#jqGridId").setGridHeight($(window).height()-200);
	$("#jqGridId2").setGridWidth(width-250);
	$("#jqGridId2").setGridHeight($(window).height()-200);
}

$(window).resize(resize); 