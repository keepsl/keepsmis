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
	initTree();
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"roleids","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":false,"width":50,"name":"createtime","resizable":true,"label":"操作",formatter:function(cellValue, options, rowObject){
			var str= "<a class='gbn-min gbn-revoke gbn-red' href='javascript:;' title='重置密码' onclick='resetPwd("+rowObject.id+",\""+rowObject.name+"\")' ><i></i></a>";		
        	return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"name","resizable":true,"label":"姓名"},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"jobnumber","resizable":true,"label":"工号"},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"phone","resizable":true,"label":"联系电话"},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"orgname","resizable":true,"label":"所属组织"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"rolenames","resizable":true,"label":"所属角色"},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"status","resizable":true,"label":"状态",formatter:function(cellvalue, options, rowObject){
			if(cellvalue==1){
				return '正常';
			}else if(cellvalue==2){
				return '冻结';
			}else if(cellvalue==2){
				return '离职';
			}else{
				return cellvalue;
			}
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"updatetime","resizable":true,"label":"创建/更新时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm:ss");
		}}
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
        		$("input[name='orgid']").val(treeNode.id);
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
function resetPwd(id,name){
	layer.confirm('确认要重置【'+name+'】的密码吗?', {
	  btn: ['确认','取消'] //按钮
	}, function(){
    	$.getJSON('../emp/resetPwd?id='+id,function(json){
    		if(json&&json.success==true){
    			layer.msg('密码重置成功!');
    		}else{
    			layer.closeAll();
    			Log.e(json.message);
    		}
    	});
	}, function(){
	});
}

function query(){
	var url = '../emp/query?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../emp/delete',function (success) {
		if(success){
			initTree();
		}
	});
}
function add_obj(){
	openWin('../emp/add?orgid='+$("input[name='orgid']").val(),'新增员工',700,500,'',onSaveUpdateSuccess);
}
function edit_obj(id){
	var id = myGrid.selectedId(1);
	if(id!=null){
		openWin('../emp/edit?id='+id,'编辑员工',700,500,'',onSaveUpdateSuccess);
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