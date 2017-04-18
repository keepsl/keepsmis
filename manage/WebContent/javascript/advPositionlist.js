var grid=null,myGrid=null,pid = '';
var onSaveUpdateSuccess = function (iframe,callback) {
    iframe.saveOrUpdate(function (success) {
    	if(success){
    		query();
    	}
    	callback(success);
    });
}
$(function(){
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"apName","resizable":true,"label":"名称"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"apClass","resizable":true,"label":"类别",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '图片';
			}else if(cellvalue==2){
				return '文字';
			}else if(cellvalue==3){
				return '幻灯';
			}else if(cellvalue==4){
				return 'Flash';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"apDisplay","resizable":true,"label":"展示方式",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '幻灯片';
			}else if(cellvalue==2){
				return '多广告展示';
			}else if(cellvalue==3){
				return '单广告展示';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"apWidth","resizable":true,"label":"宽度/字数"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"apHeight","resizable":true,"label":"高度",formatter:function(cellvalue, options, rowObject){
			if(rowObject.apClass==2){
				return "--";
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"advNum","resizable":true,"label":"广告数"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"clickNum","resizable":true,"label":"点击数"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"isShow","resizable":true,"label":"是否展示",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIsshowById\');">【否】</a>';
			}else if(cellvalue==2){
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIsshowById\');">【是】</a>';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"isCache","resizable":true,"label":"缓存文件",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==0){
				return '未缓存';
			}else if(cellvalue==1){
				return '已缓存 <a style="color:#5cb85c" href="javascript:viewCodeWin('+rowObject.id+');">【调用代码】</a>';
			}else if(cellvalue==2){
				return '有更新  <a style="color:#5cb85c" href="javascript:viewCodeWin('+rowObject.id+');">【调用代码】</a> || <a style="color:#5cb85c" href="javascript:cacheAdvFile('+rowObject.id+');">【缓存】</a>';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":180,"name":"apIntro","resizable":true,"label":"简介"},
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
function viewCodeWin(id){
	openViewWin('../advPosition/viewCode?id='+id,'查看调用代码',450,200,'',null);
}
function ajax_set_fieid(value,id,url){
	Ajax.post("../advPosition/"+url,{id:id,value:value},function(p){
		layer.msg('操作成功!');
		query();
	});
}
function query(){
	var url = '../advPosition/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:false, jqGridPagerId:"jqGridPagerId"};
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
	openWin('../advPosition/add','新增广告位',750,600,'',onSaveUpdateSuccess);
}
function edit_obj(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../advPosition/edit?id='+id,'编辑广告位',750,600,'',onSaveUpdateSuccess);
	}
}
//根据广告位缓存广告
function cacheAdvFile(ids){
	if(ids==undefined){
		ids = myGrid.selectedId();
	}
	if(ids!=null){
		layer.confirm('确认要继续该操作吗?确认后将覆盖原有的广告位.', {
		  btn: ['确认','取消'] //按钮
		}, function(){
			if(ids!=null){
				Ajax.post("../advPosition/cacheAdvFile",{ids:ids},function(p){
					layer.msg('操作成功!');
					query();
				});
			}
		}, function(){
		});
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