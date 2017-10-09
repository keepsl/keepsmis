function MyJqGrid(obj){
	//this.jqGridId = "jqGridId";
	//this.jqGridPagerId = "jqGridPagerId";
	this.jqGridId = obj.jqGridId?obj.jqGridId:'jqGridId';
	this.jqGridPagerId = obj.jqGridPagerId?obj.jqGridPagerId:'jqGridPagerId';
	this.multiselect=true;
	this.jqGridActionbarId = "jqGridActionbarId";
	this.isinit= false;
	callback = null;
	onSelectRow = function(){};//选中行时，默认执行一个空方法
	loadComplete = function(){};//选中行时，默认执行一个空方法
	if(obj){
		this.jqGridId=obj.jqGridId?obj.jqGridId:'jqGridId';
		this.jqGridPagerId =obj.jqGridPagerId?obj.jqGridPagerId:'jqGridPagerId';
		this.rownumbers=(obj.rownumbers==false)?false:true;
		this.url = obj.url;
		this.datatype = obj.datatype?obj.datatype:'json';
		this.colModel = obj.colModel?obj.colModel:[];
		this.width = obj.width?obj.width:'auto';
	    this.height = obj.height?obj.height:'auto';
	    this.viewrecords = obj.viewrecords?obj.viewrecords:true;
	    this.shrinkToFit = (obj.shrinkToFit==false)?false:true;
	    this.sortname = obj.sortname?obj.sortname:null;
	    this.sortorder = obj.sortorder?obj.sortorder:'asc';
	    this.multiselect = (obj.multiselect==false)?false:true;
	    callback = (obj.callback == null?null:obj.callback);
	    this.rownumWidth = obj.rownumWidth?obj.rownumWidth:40;
	    this.multiboxonly = (obj.multiboxonly==true)?true:false;
	    this.jsonReader = obj.jsonReader?obj.jsonReader:null;
	    this.localReader = obj.localReader?obj.localReader:null;
	    this.rowNum = obj.rowNum?obj.rowNum:10;
	    this.onSelectRow=obj.onSelectRow?obj.onSelectRow:onSelectRow;
	    this.ondblClickRow=obj.ondblClickRow?obj.ondblClickRow:null;
	    this.loadComplete=obj.loadComplete?obj.loadComplete:loadComplete;
	}
    MyJqGrid.prototype.bindGridAddEvent = function(){
    	$("#rrt-manager-form").find("input").each(function(){
    		var this_obj = $(this);
    		var this_obj_type = $(this).attr("type");
    		if(undefined ==this_obj_type || this_obj_type=="" || "text"==this_obj_type){
    			this_obj.val("");
			}else if(this_obj_type=="radio" || "checkbox"==this_obj_type){
				this_obj.each(function(){
					this_obj.prop({'checked':false});
				}); 
			}
    	});
    	$("#rrt-manager-form").find("select").each(function(){
    		$(this).val("");
    	});
    	$("#rrt-manager-form").find("textarea").each(function(){
    		$(this).val("");
    	});
    	$("#rrt-manager-editing-id").val("");
    	$("#rrt-manager-grid").hide();
    	$("#rrt-manager-form").show();
    };
    MyJqGrid.prototype.bindGridEditEvent = function(url){
    	var ids=$("#"+this.jqGridId).jqGrid("getGridParam","selarrrow");
		if(ids.length>1){
			Log.e("编辑时一次只能选择一行.");
		}else if(ids.length==0){
			Log.e("请选择一行.");
		}else{
			var rowId = $("#"+this.jqGridId).jqGrid("getGridParam","selrow");
			var id = $("#"+this.jqGridId).jqGrid("getRowData", rowId).ID;
			$("#rrt-manager-grid").hide();
	    	$("#rrt-manager-form").show();
	    	$("#rrt-manager-editing-id").val(id);
	    	$.getJSON(url+id,function(json){
	    		if(json&&json.message&&json.success==true){
	    			var data = json.message;
	    			for(var key in data){
	    				var item = $("#rrt-manager-form").find("[name='"+key+"']");//$("#"+key);
						if(data[key] && item){
							var item_type = item.attr("type");
							if(undefined ==item_type || item_type=="" || "text"==item_type){
		        				item.val(data[key]);
		        			}else if(item_type=="radio" || "checkbox"==item_type){
		        				item.each(function(){
		        					if($(this).val()==String(data[key])){
		        						$(this).prop({'checked':true});
		        					}else{
		        						$(this).prop({'checked':false});
		        					}
		        				}); 
		        			}else{
		        				item.val(data[key]);
		        				if(key=="fwxtb"){
		        					$(".rcmdServImg").each(function(){
		        						if($(this).attr("src").indexOf($("#"+key).val())>-1){
		        							$(this).addClass("selected");
		        						}
		        					});
		        				}
		        			}
						}
					}
	    		}else{
	    			Log.e(json.message);
	    		}
	    	});
		}
    };
    MyJqGrid.prototype.bindGridDelEvent = function(url,callback){
    	var rowIds=$("#"+this.jqGridId).jqGrid("getGridParam","selarrrow");
		if(rowIds.length==0){
			Log.e("请选要删除的数据.");
		}else{
			var jqGridId = this.jqGridId;
			layer.confirm('确认要删除这些数据吗?', {
			  btn: ['确认','取消'] //按钮
			}, function(){
				var ids = [];
				for(var i=0;i<rowIds.length;i++){
					var id = $("#"+jqGridId).jqGrid('getRowData',rowIds[i]).id;
					if(id==undefined){
						id = $("#"+jqGridId).jqGrid('getRowData',rowIds[i]).ID;
					}
					ids.push(id);
				}
		    	$.getJSON(url+"?ids="+ids.toString(),function(json){
		    		callback(json.success);
		    		if(json&&json.success==true){
		    			layer.closeAll();
		    			$("#"+jqGridId).trigger("reloadGrid");
		    		}else{
		    			layer.closeAll();
		    			Log.e(json.message);
		    		}
		    	});
			}, function(){
			});
		}
    };
	
	MyJqGrid.prototype.loadMyGrid = function(){
		$("#"+this.jqGridId).jqGrid({
			url: this.url,
			datatype: this.datatype,
			shrinkToFit:this.shrinkToFit,//为true时列宽百分比自适应，为false时定宽出水平滚动条
	        colModel: this.colModel,
	        width: this.width,
	        height: this.height,
	        pager: this.jqGridPagerId,
	        viewrecords: this.viewrecords,
	        sortname: this.sortname,
	        sortorder: this.sortorder,
	        multiselect: this.multiselect,
	        multiboxonly: this.multiboxonly,
	        gridview: false,
	        rowNum : this.rowNum,
	        rowList : [ 10, 20, 50 ],
	        rownumbers: this.rownumbers,
	        rownumWidth:this.rownumWidth,
	        localReader:this.localReader?this.localReader:{
	        	root: function (obj) {
	            },  
	            page: function (obj) { 
	            },  
	            total: function (obj) { 
	            },  
	            records: function (obj) {  
	            }
	        },
	        jsonReader: this.jsonReader?this.jsonReader:{
	        	root: function (obj) { 
	                return obj.message.record;  
	            },  
	            page: function (obj) { 
	                return obj.message.page;  
	            },  
	            total: function (obj) { 
	                return obj.message.pageTotal;  
	            },  
	            records: function (obj) {  
	                return obj.message.total;  
	            },  
	            repeatitems: false
	        },
	        onSelectRow : this.onSelectRow,
	        ondblClickRow : this.ondblClickRow,
	        gridComplete : function(){
	        	if(callback!=null){
	        		
	        		callback();
	        	}
	        },
	        loadComplete:this.loadComplete
		});
		//$("#"+this.jqGridId).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	};
	
	MyJqGrid.prototype.complete = function(params){
		
	};
	
	MyJqGrid.prototype.query = function(params){
		if(params.datatype=="" || params.datatype==null || params.datatype==undefined){
			params.datatype = 'json';
		}
		if(params=="" || params==null){
			return ;
		}
		var urls = {url:params.url,datatype:params.datatype};
		$("#"+this.jqGridId).jqGrid('setGridParam',urls).trigger("reloadGrid");
		
	};
	
	MyJqGrid.prototype.selectedId=function(type){
		var ids = $("#"+this.jqGridId).jqGrid('getGridParam','selarrrow');
		if(type!=undefined){
			if(type != ids.length){
				Log.e("请选择"+type+"条要操作的记录，当前选了"+ids.length+"条记录!");
				return null;
			}
		}else{
			if(ids.length==0){
				Log.e("至少选择1条要操作的记录!");
				return null;
			}
		}
		var arr = new Array;
		for(i=0;i<ids.length; i++){ 
			var id = $("#"+this.jqGridId).jqGrid('getRowData',ids[i]).id;
			if(id==undefined){
				id = $("#"+this.jqGridId).jqGrid('getRowData',ids[i]).ID;
			}
			arr.push(id);
		}

		return arr.join(",");
	};
}