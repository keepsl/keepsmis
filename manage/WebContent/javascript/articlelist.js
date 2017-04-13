var grid=null,myGrid=null,pid = '';
$(function(){
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"ispublish","resizable":false},
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"coverimage","resizable":false},
		{"hidden":false,"align":"left","sortable":true,"width":200,"name":"title","resizable":true,"label":"文章标题",formatter:function(cellvalue, options, rowObject){
			var str = '';
			if(rowObject.ispublish==1){
				str = "<span style='color:red'>[草稿]</span> "+cellvalue;
			}else{
				str = cellvalue;
			}
			if(rowObject.coverimage=='' || rowObject.coverimage == null){
				str = "<span style='color:red'>[无封面]</span> "+str;
			}
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":120,"name":"typename","resizable":true,"label":"所属栏目"},
		{"hidden":false,"align":"left","sortable":true,"width":60,"name":"clicknum","resizable":true,"label":"点击量"},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"publishnickname","resizable":true,"label":"发布人"},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"isshow","resizable":true,"label":"是否显示",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIsshowById\');">【否】</a>';
			}else{
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIsshowById\');">【是】</a>';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"ishot","resizable":true,"label":"是否热门",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIshotById\');">【否】</a>';
			}else{
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIshotById\');">【是】</a>';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":80,"name":"isrecommend","resizable":true,"label":"是否推荐",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '是 <a style="color:#5cb85c" href="javascript:ajax_set_fieid(2,'+rowObject.id+',\'updateIsrecommendById\');">【否】</a>';
			}else{
				return '否  <a style="color:#5cb85c" href="javascript:ajax_set_fieid(1,'+rowObject.id+',\'updateIsrecommendById\');">【是】</a>';
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"keyword","resizable":true,"label":"关键字"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"taglist","resizable":true,"label":"标签"},
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

function query(){
	var url = '../article/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",shrinkToFit:false, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}

function ajax_set_fieid(value,id,url){
	Ajax.post("../article/"+url,{id:id,value:value},function(p){
		layer.msg('操作成功!');
		query();
	});
}
function deleteMenu(){
	var ids = myGrid.bindGridDelEvent('../article/delete',function (success) {
		if(success){
		}
	});
}

function addMenu(){
	var saveSuccess = function (iframe,callback) {
        iframe.save(function (success) {
        	if(success){
        		query();
        	}
        	callback(success);
        });
    }
	var tempSaveSuccess = function (iframe,callback) {
        iframe.tempsave(function (success) {
        	callback(success);
        });
    }
	openArticleWin('../article/add?typeid='+$("input[name='typeid']").val(),'发布文章',980,600,saveSuccess,tempSaveSuccess);
}
function editMenu(){
	var id = myGrid.selectedId(1);
	if(id!=null){
		var saveSuccess = function (iframe,callback) {
	        iframe.save(function (success) {
	        	if(success){
	        		query();
	        	}
	        	callback(success);
	        });
	    }
		var tempSaveSuccess = function (iframe,callback) {
	        iframe.tempsave(function (success) {
	        	callback(success);
	        });
	    }
		openArticleWin('../article/edit?id='+id,'编辑文章',980,600,saveSuccess,tempSaveSuccess);
	}
}
function openArticleWin(url, title, width, height, saveSuccess,tempSaveSuccess) {
	parent.layer.open({
	  type: 2,
	  title :title,
	  area: [width+"px", height+"px"],
	  fixed: false, //不固定
	  maxmin: false,
	  closeBtn:2,//关闭按钮：1和2
	  content: url,
	  skin: 'layui-layer-rim',//加上边框
	  btn: ['立即发布', '保存草稿', '取消'],
	  cancel:function(){
		  query();
	  },
	  yes: function(index, layero) {
		  if ($.isFunction(saveSuccess)) {
			  var index = parent.layer.load(2);
              var iframeWindow = layero.find('iframe')[0].contentWindow;
              var bo = saveSuccess(iframeWindow,function (success) {
            	  if(success){
            		  parent.layer.closeAll();
            	  }else{
            		  parent.layer.close(index);
            	  }
              });
          }
	  },btn2:function(index, layero){
		  if ($.isFunction(tempSaveSuccess)) {
			  var index = parent.layer.load(2);
              var iframeWindow = layero.find('iframe')[0].contentWindow;
              var bo = tempSaveSuccess(iframeWindow,function (success) {
            	  parent.layer.close(index);
            	  if(success){
            		  parent.layer.msg('已保存到草稿!');
            	  }
              });
          }
		  return false;
	  },btn3:function(index){
		  query();
		  return true;
	  }
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