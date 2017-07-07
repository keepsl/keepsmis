var grid2=null,myGrid2=null;
var onSaveUpdateSuccess2 = function (iframe,callback) {
    iframe.saveOrUpdate(function (success) {
    	if(success){
    		buyquery();
    	}
    	callback(success);
    });
}
$(function(){
	grid2 = [
		{"hidden":true,"align":"left","sortable":false,"width":10,"name":"id","resizable":false,"label":"id"},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"empname","resizable":true,"label":"负责人"},
		{"hidden":false,"align":"left","sortable":true,"width":70,"name":"productname","resizable":true,"label":"负责人"},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"price","resizable":true,"label":"购买金额",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return 0;
			}
			return cellValue;
		}},
		{"hidden":false,"align":"left","sortable":true,"width":150,"name":"updatetime","resizable":true,"label":"购买时间",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '';
			}
			var cdate = new Date(cellValue);
			return cdate.format("yyyy-MM-dd hh:mm");
		}},
		{"hidden":false,"align":"left","sortable":true,"width":180,"name":"remark","resizable":true,"label":"备注",formatter:function(cellValue, options, rowObject){
			if(cellValue==null || cellValue == ''){
				return '无';
			}
			return "<a href='javascript:;' onclick='viewOpen(\""+encodeURI(cellValue)+"\",\"查看详细\",\"500\",\"300\")' ><span style='color:#3399ff'>详细</span></a>&nbsp&nbsp"+cellValue;
		}}
	];
	buyquery();
	resize2();
	//初始化enter事件
	$('#form2').bind('keyup', function(event){
	   if (event.keyCode=="13"){
		   buyquery();
	   }
	});
});

function buyquery(){
	var url = basePath+'/buyrecord/query/'+$('#operType').val()+'?'+$("#form1").serialize();
	var	param = {url:url, colModel:grid2, jqGridId:"jqGridId2",multiselect:true, shrinkToFit:true, jqGridPagerId:"jqGridPagerId2"};
	if(myGrid2 == null){
		myGrid2 = new MyJqGrid(param);
		myGrid2.loadMyGrid();
	}else{
		myGrid2.query(param);
	}
}
function resize2(){
	$(".J_conWarp").height($(window).height()-20);
    $(".J_conWarp").niceScroll({});
	$("#jqGridId2").setGridWidth($(window).width()-50);
	$("#jqGridId2").setGridHeight($(window).height()-180);
}

function addBuyRecord(id){
	openWin(basePath+'/buyrecord/add/'+$('#operType').val()+'?clientid='+$("#id").val(),'新增购买记录',700,500,'',onSaveUpdateSuccess2);
}

$(window).resize(resize); 