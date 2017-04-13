var grid=null,myGrid=null,pid = '';
$(function(){
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"tagname","resizable":true,"label":"类别"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"tagname","resizable":true,"label":"类别"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"clicknum","resizable":true,"label":"展示方式"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"goodsnum","resizable":true,"label":"宽度/字数"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"goodsnum","resizable":true,"label":"高度"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"goodsnum","resizable":true,"label":"广告数"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"ishot","resizable":true,"label":"是否启用",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIsshowById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIsshowById\');">【是】</a>';
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
function ajax_set_fieid(value,id,url){
	Ajax.post("../advPosition/"+url,{id:id,value:value},function(p){
		layer.msg('操作成功!');
		query();
	});
}
function query(){
	var url = '../advPosition/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId", jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../advPosition/delete',function (success) {
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
	openWin('../advPosition/add?typeid='+$("input[name='typeid']").val(),'新增广告位',550,350,'',onSuccess);
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
		openWin('../advPosition/edit?id='+id,'编辑广告位',550,350,'',onSuccess);
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