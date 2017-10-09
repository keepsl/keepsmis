/**
 * 通过JSON字符串向界面对象放值的方法 例子
 * $("#form1").setFormValues({id:111,name:'ccc',dyh:'aaaa' ,khfs:'-1'});
 * @param {Object} $
 */
(function($) {
	$.fn.setFormValues = function(json) {
		var form1 = $(this);
		$.each(json,
				function(id, value) {
					/** 该功能是为jquery版的 combox提供* */
					var this_obj = form1.find("[name='" + id + "']");
					var this_obj_type = this_obj.attr("type");

					if (undefined == this_obj_type || this_obj_type == ""
							|| "text" == this_obj_type) {
						this_obj.val(value);
					} else if (this_obj_type == "radio"
							|| "checkbox" == this_obj_type) {
						this_obj.each(function() {
							if ($(this).val() == value) {
								$(this).prop({
									'checked' : true
								});
							} else {
								$(this).prop({
									'checked' : false
								});
							}
						});
					} else {
						this_obj.val(value);
					}
				});
	};
	$.fn.clearFormValues = function() {
		var form1 = $(this);
		form1.find("input").each(
				function() {
					var this_obj = $(this);
					var this_obj_type = $(this).attr("type");
					if (undefined == this_obj_type || this_obj_type == ""
							|| "text" == this_obj_type) {
						this_obj.val("");
					} else if (this_obj_type == "radio"
							|| "checkbox" == this_obj_type) {
						this_obj.each(function() {
							this_obj.prop({
								'checked' : false
							});
						});
					}
				});
		form1.find("select").each(function() {
			$(this).val("");
		});
		form1.find("textarea").each(function() {
			$(this).val("");
		});
	};
	$.fn.validate = function() {
		var form1 = $(this);
		b = true;
		$.each(form1.find(".must"), function(i, v) {
			var cv = $(this).val();
			if (cv.replace(/\s/g, "") == "") {
				$(this).focus();
				b = false;
				return b;
			}
		});

		return b;
	};
	/***************************************************************************
	 * 判断对象的值是否为空.
	 * 
	 * @memberOf {TypeName}
	 * @return {TypeName}
	 */
	$.fn.isEmpty = function() {
		var cv = $(this).val();
		return cv.replace(/\s/g, "") == ""
	};

})(jQuery);

var Log = {
	m : function(message) {
		Log.common(message, "#05e400", "top: 2px");
	},
	e : function(message) {
		Log.common(message, "#d30000", "top: 2px");
	},
	w : function(message) {
		Log.common(message, "#FF7F00", "top: 2px");
	},
	common : function(message, color, style) {
		if ($("#tt_tip") != null)
			$("#tt_tip").remove();
		$(
				"<div id='tt_tip' class='topTip' style='background: "
						+ color
						+ ";padding: 3px;border-radius: 5px 5px 5px 5px !important;font-size: 12px;color: #fff;width: 250px;position: fixed;"
						+ style
						+ ";left: 50%;margin-left: -125px; text-indent: .8em;display: none;z-index: 99999;'>"
						+ message + "</div>").appendTo($("body"));
		$(".topTip").css("display", "block").delay(3000).fadeOut();
	},

	getLoadDivId : function() {
		return "message_div_default_m";
	},
	v : function() {// loading
		var message_div_default_m = Log.getLoadDivId();
		var message = "正在操作，请稍候……";

		$(
				"<div id='"
						+ message_div_default_m
						+ "' style='background:#FFFF66;padding: 3px;border-radius: 5px 5px 5px 5px !important;font-size: 12px;color: #000000;width: 250px;position: fixed;top: 2px;left: 50%;margin-left: -125px; text-indent: .8em;display: none;z-index: 99999;'>"
						+ message + "</div>").appendTo($("body"));
		$("#" + message_div_default_m).css("display", "block");
	},
	h : function() {// loaded
		var message_div_default_m = Log.getLoadDivId();
		$("#" + message_div_default_m).css("display", "none").fadeOut();
	}
}
var Utils = {
	isEmpty : function(v) {
		if (v == null || 'null' == v || v == '') {
			return true;
		}
		return (v + "").replace(/\s/g, "") == "";
	},
	isNumber : function(v) {
		return (v != null) && (v.match(/^([+]?)\d*\.?\d+$/) != null);
	}
}

var Ajax = {
	post : function(url, param, func) {
		$.post(url, param, function(p) {
			eval("info=" + p);
			if (info.success) {
				func(info);
			} else {
				Log.e(info.message);
			}
		});
	}
}

function Pager() {
}
Pager.prototype.post = function(url, param, fun, successMsg, autoEval) {
	this.ajax({
		url : url,
		param : param,
		success : fun,
		successMsg : (successMsg == null ? "操作成功!" : successMsg),
		autoEval : autoEval == null ? true : autoEval
	});
}
Pager.prototype.ajax = function(json) {
	$.ajax({
		type : json.type == null ? 'POST' : json.type,
		url : json.url,
		data : json.param,
		success : function(o, textStatus) {
			var definedMessage = json.successMsg;
			if (!Utils.isEmpty(definedMessage)) {
				if ("noMessage" != definedMessage) {// 如果未定义不提示，才打印成功提示
					Log.e(json.successMsg);
				}
			}
			if (json.autoEval) {
				if (info.success) {
					if (typeof (json.success) == 'function') {
						json.success(info);
					}
				} else {
					Log.e(info.message);
				}
			} else {
				if (typeof (json.success) == 'function') {
					json.success(o);
				}
			}
		},
		timeout : json.timeout == null ? 60 * 1000 : json.timeout,
		async : json.async == null ? true : json.async,
		beforeSend : function(XMLHttpRequest) {
			Log.v();
		},
		complete : function(XMLHttpRequest, textStatus) {
			Log.h();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			Log.e("操作失败!");
		}
	});
}

var pager = new Pager();

/*******************************************************************************
 * 
 * 
 * $("#atreeid").trees({url:'http://',function(){
 * 
 * },check:true});
 *  { url:'${path}/aa/aa/aa' callback:function(id,name,treeId){
 *  } }
 * 
 * 
 * 
 * 
 * @param $
 */
(function($) {
	$.fn.trees = function(tjson) {
		var _t = $(this);
		_t.addClass("ztree");
		if (tjson == null) {
			return;
		}
		tjson.check = tjson.check == null ? false : tjson.check;
		tjson.selectedMulti = tjson.selectedMulti == null ? false : tjson.selectedMulti;
		if (tjson.setting == null) {
			tjson.setting = {
				async : {
					enable : true,
					url : tjson.url,
					autoParam : [ "id", "name" ],
					otherParam : [],
					dataFilter : function(treeId, parentNode, responseData) {
						if (responseData.success) {
							return eval(responseData.message);
						} else {
							Log.e(responseData.message);
							return null;
						}
					}
				},
				callback : {
					onClick : function(event, treeId, treeNode, clickFlag) {
						tjson.callback(treeNode.id, treeNode.name, treeId);
					}
				},
				check : {
					enable : tjson.check
				},
				view: {
		            selectedMulti: tjson.selectedMulti
		        },
		        data: {
		            simpleData: {
		                enable: true
		            }
		        }
			};
		}
		$.fn.zTree.init($("#" + _t.attr("id")), tjson.setting);

		return $.fn.zTree.getZTreeObj(_t.attr("id"));
	};
})(jQuery);

String.prototype.startsWith = function(substring) {
	var reg = new RegExp("^" + substring);
	return reg.test(this);
};

String.prototype.endsWith = function(substring) {
	var reg = new RegExp(substring + "$");
	return reg.test(this);
};
/*******************************************
查找制定元素的的位置
*******************************************/
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
/*******************************************
删除数组中的制定元素
*******************************************/
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
String.prototype.Trim = function()  
{  
	if(this==null || this==undefined){
		return '';
	}else if(this&&typeof(this)=="function"){ 
		return this.replace(/(^\s*)|(\s*$)/g, "");  
	}
}
/**
 * 格式化日期 例子
 * new Date().format("yyyy-MM-dd hh:mm:ss");
 * @param
 */
Date.prototype.format = function(format)
{ 
  var o = { 
    "M+" : this.getMonth()+1, //month 
    "d+" : this.getDate(),    //day 
    "h+" : this.getHours(),   //hour 
    "m+" : this.getMinutes(), //minute 
    "s+" : this.getSeconds(), //second 
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter 
    "S" : this.getMilliseconds() //millisecond 
  } 
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 
    (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o)if(new RegExp("("+ k +")").test(format)) 
    format = format.replace(RegExp.$1, 
      RegExp.$1.length==1 ? o[k] : 
        ("00"+ o[k]).substr((""+ o[k]).length)); 
  return format; 
} 
