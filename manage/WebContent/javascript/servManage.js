function bindServManagePageEvent(){
	$("#jqGridActionbarId").children().each(function(){
		var this_obj = $(this);
		var myGrid = new MyJqGrid();
		if(this_obj.attr("btncode")=="ADD"){
			this_obj.click(function(){
				$("#cjr").attr("disabled", false);
				myGrid.bindGridAddEvent();
			});
		}else if(this_obj.attr("btncode")=="EDIT"){
			this_obj.click(function(){
				url = path+"/service/get/";
				myGrid.bindGridEditEvent(url);
			});
		}else if(this_obj.attr("btncode")=="DELETE"){
			this_obj.click(function(){
				var url = path+"/service/delete";
				myGrid.bindGridDelEvent(url);
			});
		}else if(this_obj.attr("btncode")=="START"){
			this_obj.click(function(){
				bindServStatusEvent('start');
			});
		}else if(this_obj.attr("btncode")=="STOP"){
			this_obj.click(function(){
				bindServStatusEvent('stop');
			});
		}else if(this_obj.attr("btncode")=="CHECK"){
			this_obj.click(function(){
				bindServUsableEvent();
			});
		}
	});
	$("#add_service_back_btn").click(function(){
		window.location.reload();
	});
	
	$("#add_service_ok_btn").unbind('click');
	$("#add_service_ok_btn").click(function(){
		var url = "";
		var id = $("#rrt-manager-editing-id").val();
		if(id==""){
			url = path+"/service/save";
		}else{
			url = path+"/service/update";
		}
		if($("#fwdtb").val()=="" || $("#fwxtb").val()==""){
			artDialog.alert("请选择一个图标.",function(){});
			return;
		}
		var param = {
				id : id,
				fwmc : $("#fwmc").val(),
				fwms : $("#fwms").val(),
				url : $("#url").val(),
				sfky : "1",
				fwdtb : $("#fwdtb").val(),
				fwxtb : $("#fwxtb").val(),
				fwztb : "",
				jcfs : $("#jcfs").val(),
				jcmb : $("#jcmb").val(),
				iframeHeight : $("#iframeHeight").val(),
				fwlx : $("#fwlx").val()
		};
		$("#add_service_ok_btn").attr("disabled", true);
		$.post(url, param, function(json){
			$("#add_service_ok_btn").attr("disabled", false);
			if(json&&json.success==true){	
				$("#jqGridId").trigger("reloadGrid");
				$("#rrt-manager-editing-id").val("");
				$("#rrt-manager-grid").show();
		    	$("#rrt-manager-form").hide();
			}else{
				if(id=="") {
					artDialog.error("创建失败!",function(){});
				}else {
					artDialog.error("修改失败!",function(){});	
				}
			}
		} ,'json');
	});
}

function formatSfky(cellValue, options, rowObject){
	return cellValue=="1"?"启动":"停止";
}
var myGrid = null;
function loadZoneServList(param){
	if(myGrid == null){
		myGrid = new MyJqGrid(param);
		myGrid.loadMyGrid();
	}else{
		myGrid.query(param);
	}
}