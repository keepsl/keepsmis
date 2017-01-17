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

	/**
	 * 基于jquery-ui的combobox实现autocomplete
	 */
	$
			.widget(
					"custom.combobox",
					{
						_create : function() {
							this.wrapper = $("<span>").addClass(
									"custom-combobox")
									.insertAfter(this.element);

							this.element.hide();
							this._createAutocomplete();
							this._createShowAllButton();
						},

						_createAutocomplete : function() {
							var selected = this.element.children(":selected"), value = selected
									.val() ? selected.text() : "";

							this.input = $("<input>")
									.appendTo(this.wrapper)
									.val(value)
									.attr("title", "")
									.addClass(
											"custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left")
									.autocomplete({
										delay : 0,
										minLength : 0,
										source : $.proxy(this, "_source")
									}).tooltip({
										tooltipClass : "ui-state-highlight"
									});

							this._on(this.input, {
								autocompleteselect : function(event, ui) {
									ui.item.option.selected = true;
									this._trigger("select", event, {
										item : ui.item.option
									});
									$("#" + this.options.id).val(ui.item.id);
								},

								autocompletechange : "_removeIfInvalid"
							});
						},

						_createShowAllButton : function() {
							var input = this.input, wasOpen = false;

							$("<a>").attr("tabIndex", -1).attr("title",
									"Show All Items").tooltip().appendTo(
									this.wrapper).button({
								icons : {
									primary : "ui-icon-triangle-1-s"
								},
								text : false
							}).removeClass("ui-corner-all").addClass(
									"custom-combobox-toggle ui-corner-right")
									.mousedown(
											function() {
												wasOpen = input.autocomplete(
														"widget")
														.is(":visible");
											}).click(function() {
										input.focus();

										// Close if already visible
										if (wasOpen) {
											return;
										}

										// Pass empty string as value to search
										// for, displaying all results
										input.autocomplete("search", "");
									});
						},

						_source : function(request, response) {
							this.options.source.call(null, request.term,
									response);
						},

						_removeIfInvalid : function(event, ui) {

							// Selected an item, nothing to do
							if (ui.item) {
								return;
							}

							// Search for a match (case-insensitive)
							var value = this.input.val(), valueLowerCase = value
									.toLowerCase(), valid = false;
							this.element
									.children("option")
									.each(
											function() {
												if ($(this).text()
														.toLowerCase() === valueLowerCase) {
													this.selected = valid = true;
													return false;
												}
											});

							// Found a match, nothing to do
							if (valid) {
								return;
							}

							// Remove invalid value
							this.input.val("").attr("title",
									value + " didn't match any item").tooltip(
									"open");
							this.element.val("");
							this._delay(function() {
								this.input.tooltip("close").attr("title", "");
							}, 2500);
							this.input.autocomplete("instance").term = "";
						},

						_destroy : function() {
							this.wrapper.remove();
							this.element.show();
						}
					});
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
					artDialog.success(json.successMsg, function() {
					});
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
