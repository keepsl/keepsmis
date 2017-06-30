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