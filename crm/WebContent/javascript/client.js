var grid=null,myGrid=null,pid = '',drowid=0;
var onSaveUpdateSuccess = function (iframe,callback) {
    iframe.saveOrUpdate(function (success) {
    	if(success){
    		query();
    	}
    	callback(success);
    });
}
$(function(){
	
	$(".tracktime li a").click(function(){
		$("#tracktime").val($(this).attr('attr-time'));
		$('.hodgepodgetypetext').html('按分类查询');
		$("#hodgepodgetype").val('');
		$('.visitttimetypetext').html('按来访时间查询');
		$("#visitttimetype").val('');
	    if($(this).attr('attr-time')==0){
		    $('.tracktimetext').html('按跟踪时间');
	    }else{
		    $('.tracktimetext').html($(this).html());
	    }
	    query(1);
	});
	$(".hodgepodgetype li a").click(function(){
		$("#hodgepodgetype").val($(this).attr('attr-time'));
		$('.tracktimetext').html('按跟踪时间');
		$("#tracktime").val('');
		$('.visitttimetypetext').html('按来访时间查询');
		$("#visitttimetype").val('');
	    if($(this).attr('attr-time')==0){
		    $('.hodgepodgetypetext').html('按分类查询');
	    }else{
		    $('.hodgepodgetypetext').html($(this).html());
	    }
	    query(1);
	});
	$(".visitttimetype li a").click(function(){
		$("#visitttimetype").val($(this).attr('attr-time'));
		$('.tracktimetext').html('按跟踪时间');
		$("#tracktime").val('');
		$('.hodgepodgetypetext').html('按分类查询');
		$("#hodgepodgetype").val('');
	    if($(this).attr('attr-time')==0){
		    $('.visitttimetypetext').html('按来访时间查询');
	    }else{
		    $('.visitttimetypetext').html($(this).html());
	    }
	    query(1);
	});
	grid = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"attentionvalue","resizable":false,"label":"attentionvalue"},
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"fzempid","resizable":false,"label":"fzempid"},
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"typename","resizable":false,"label":"typename"},
		{"hidden":false,"align":"left","sortable":false,"width":60,"name":"operattr","resizable":true,"label":"操作",formatter:function(cellValue, options, rowObject){
			var str = '';
			if($('#operType').val()==1){
				str +="<a class='gbn-min gbn-examinations gbn-yellow' href='javascript:;' title='更改负责人' onclick='clientGrantEmp("+rowObject.id+",1)' ><i></i></a>";	
			}
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"name","resizable":true,"label":"姓名"},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"phone","resizable":true,"label":"联系电话",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			if($('#operType').val()!=1){
				var reg = /^(\d{3})\d{4}(\d{4})$/;
				return "<span title='"+rowObject.phone+"' >"+cellValue.replace(reg, "$1****$2")+"</span>";
			}
			return cellValue;
		}},
		/*{"hidden":false,"align":"left","sortable":true,"width":110,"name":"linkempname","resizable":true,"label":"最近联系人",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无'
			}
			return cellValue +"&nbsp&nbsp<a href='javascript:;' onclick='showContactRecord("+rowObject.id+",\""+rowObject.name+"\")' ><span style='color:#3399ff'>更多》</span></a>";
		}},*/
		{"hidden":false,"align":"left","sortable":true,"width":90,"name":"linkempname","resizable":true,"label":"最近联系人"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"contacttime","resizable":true,"label":"最近联系时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			var cdate = new Date(cellValue);
			var str = cdate.format("yyyy-MM-dd hh:mm");
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"nexttime","resizable":true,"label":"下次联系时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			var cdate = new Date(cellValue);
			var str = cdate.format("yyyy-MM-dd hh:mm");
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"visittime","resizable":true,"label":"来访时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			var cdate = new Date(cellValue);
			var str = cdate.format("yyyy-MM-dd hh:mm");
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"attentionname","resizable":true,"label":"关注度",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			return cellValue;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"typename","resizable":true,"label":"客户类型",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			return cellValue;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":100,"name":"fzempname","resizable":true,"label":"负责人",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			return cellValue;
		}},{"hidden":false,"align":"left","sortable":true,"width":100,"name":"receivename","resizable":true,"label":"邀约人",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			return cellValue;
		}},{"hidden":false,"align":"left","sortable":true,"width":100,"name":"receivetypename","resizable":true,"label":"邀约方式"}
		,{"hidden":true,"align":"left","sortable":true,"width":100,"name":"isopen","resizable":true,"label":"是否开放",formatter:function(cellValue, options, rowObject){
			if(cellValue==1){
				return "否&nbsp&nbsp"+"<a href='javascript:;' onclick='openOrCloseClient(2)' ><span style='color:#3399ff'>开放</span></a>";
			}else if(cellValue==2){
				return "是&nbsp&nbsp"+"<a href='javascript:;' onclick='openOrCloseClient(1)' ><span style='color:#3399ff'>关闭</span></a>";
			} 
			return cellValue;
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
	//初始化enter事件
	$('#form1').bind('keyup', function(event){
	   if (event.keyCode=="13"){
		   query();
	   }
	});
});

//选择客户负责人窗口
function clientGrantEmp(id,type){
	var bo = true;
	var name = '';
	var empids = '';
	var receiveid = '';
	if(id=='' || id==null || id==undefined){
		bo = false;
		if(type==1){
			empids=$('#fzempid').val();
		}else if(type==2){
			receiveid=$('#receiveid').val();
		}
	}else{
	    var rowData = $("#jqGridId").getRowData(id);
	    name = rowData.name;
	    empids = rowData.fzempid;
	}
	var onData = function (iframe,callback) {
	    iframe.getData(function (data) {
	    	if(bo){
		    	$.post('../saveEmpClient', {empids:(data.idarr).toString(),clientid:id}, function(json){
					if(json&&json.success==true){
						$("#jqGridId").jqGrid('setCell',id, 'fzempname', (data.namearr).toString());
						$("#jqGridId").jqGrid('setCell',id, 'fzempid', (data.idarr).toString());
						callback(true);
					}else{
						Log.e(json.message);
						callback(false);
					}
				},'json');
	    	}else{
	    		if(type==1){
		    		$('#fzempid').val((data.idarr).toString());
	    			$('#fzempname').val((data.namearr).toString());
	    		}else if(type==2){
	    			$('#receiveid').val((data.idarr).toString());
	    			$('#receivename').val((data.namearr).toString());
	    		}
	    		callback(true);
	    	}
	    });
	}
	var str = '';
	if(bo){
		str = '选择客户《'+name+'》的负责人';
	}else{
		str = '选择客户负责人';
	}
	openWin('../emp/empSelectWin?empids='+empids+'&type='+type,str,350,500,'',onData);
}
function openOrCloseClient(isopen){
	layer.confirm('确认要进行此操作吗?', {
	  btn: ['确认','取消'] //按钮
	}, function(){
    	$.getJSON('../isopen?id='+drowid+"&isopen="+isopen,function(json){
    		if(json&&json.success==true){
    			$("#jqGridId").jqGrid('setCell',drowid, 'isopen', isopen);
    			layer.closeAll();
    		}else{
    			layer.closeAll();
    			Log.e(json.message);
    		}
    	});
	}, function(){
	});
}
function showContactRecord(id,name){
	openViewWin('../contactrecord/index/'+$('#operType').val()+'?clientid='+id,'查看与《'+name+'》的沟通记录',850,550,'',onSaveUpdateSuccess);
}


var onRowsData = function (callback) {
	if($('#operType').val()==1){
		$("#jqGridId").setGridParam().showCol("isopen").trigger("reloadGrid");
	}
	if($('#operType').val()!=1){
		$("#jqGridId").setGridParam().hideCol("operattr");
    }
	var re_records = $("#jqGridId").getGridParam('records');
	if(re_records == null || re_records == 0){
		if($('#operType').val()!=1 && $('#phone').val()!=null  && $('#phone').val()!='' && $('#phone').val().length==11){
			$.post(basePath+'/client/getListInfoByPhone', {'phone':$('#phone').val()}, function(json){
				if(json&&json.code==1){
					var str = '客户姓名：'+json.recored.name+"<br/>所属人："+json.recored.fzempname;
					viewOpen(encodeURI(str),'客户已存在',500,300);
				}else{
					//Log.e(json.message);
				}
			},'json');
		}
	}else{
		var ids = $("#jqGridId").getDataIDs();
	    for(var i=0;i<ids.length;i++){
	        var rowData = $("#jqGridId").getRowData(ids[i]);
	        if(rowData.attentionvalue!='' && rowData.attentionvalue!=undefined && rowData.attentionvalue!=null){
	            $('#'+ids[i]).find("td[aria-describedby='jqGridId_attentionname']").css("background-color",rowData.attentionvalue);
	        }
	    }
	}
}
function query(reset){
	if(reset==1){
		$('#form1')[0].reset();
	}
	var url = '../query/'+$('#operType').val()+'?'+$("#form1").serialize();
	
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:true, shrinkToFit:false, jqGridPagerId:"jqGridPagerId",callback:onRowsData,
		onSelectRow: function (rowid, status) {
			drowid = rowid;
		},ondblClickRow: function (rowid, status) {
			//双击
			edit_obj(rowid);
		}
	};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function delete_obj(){
	var ids = myGrid.bindGridDelEvent('../delete/'+$('#operType').val(),function (success) {
		if(success){
		}
	});
}
function imp_obj(){
	var onSuccess = function (iframe,callback) {
        iframe.saveFile(function (success) {
        	if(success){
        		query();
        	}
        	callback(success);
        });
    }
	openWin('../client/imp/'+$('#operType').val(),'导入客户',600,350,'',onSuccess);
}
function add_obj(){
	openWin('../client/add/'+$('#operType').val(),'新增客户',750,550,'',onSaveUpdateSuccess);
}
//批量编辑
function batch_edit_obj(){
	var onEditUpdateSuccess = function (iframe,callback) {
	    iframe.batchEditUpdate(function (success) {
	    	if(success){
	    		query();
	    	}
	    	callback(success);
	    });
	}
	ids = myGrid.selectedId();
	if(ids!=null){
		openWin('../client/batchEdit/'+$('#operType').val()+'?ids='+ids,'批量编辑客户',800,550,'',onEditUpdateSuccess);
	}
}
function edit_obj(id){
	if(id==null || id == '' || id==undefined){
		id = myGrid.selectedId(1);
	}
	if(id!=null){
		openWin('../client/edit/'+$('#operType').val()+'?id='+id,'编辑客户',850,550,'',onSaveUpdateSuccess);
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