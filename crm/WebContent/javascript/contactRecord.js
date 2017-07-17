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
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"empname","resizable":true,"label":"联系人"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"contacttime","resizable":true,"label":"联系时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm");
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"visittime","resizable":true,"label":"来访时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			var cdate = new Date(cellValue);
			var str = cdate.format("yyyy-MM-dd hh:mm");
			return str;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":180,"name":"remark","resizable":true,"label":"联系内容",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			return "<a href='javascript:;' onclick='viewOpen(\""+encodeURI(cellValue)+"\",\"查看联系详细内容\",\"500\",\"300\")' ><span style='color:#3399ff'>详细</span></a>&nbsp&nbsp"+cellValue;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"nexttime","resizable":true,"label":"下次联系时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm");
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

function query(){
	var url = basePath+'/contactrecord/query/'+$('#operType').val()+'?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid, jqGridId:"jqGridId",multiselect:false, shrinkToFit:true, jqGridPagerId:"jqGridPagerId"};
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}
function resize(){
	$(".J_conWarp").height($(window).height()-20);
    $(".J_conWarp").niceScroll({});
	$("#jqGridId").setGridWidth($(window).width()-50);
	$("#jqGridId").setGridHeight($(window).height()-180);
}

function addContactRecord(id){
	openWin(basePath+'/contactrecord/add/'+$('#operType').val()+'?clientid='+$("#id").val(),'新增沟通记录',730,500,'',onSaveUpdateSuccess);
}

$(window).resize(resize); 