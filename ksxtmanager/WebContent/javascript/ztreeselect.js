/**
 * 下拉树
 */
(function($) {
	var defaults = {
			idLabel : "id",
			textLabel : "name",
			pidLabel : "pId",
			attrLabel : "obj",
			transition : "ztree",
			isCheckParent:false,
			isopen:false,
			items : []
	};
	/**
	 * target:input element;
	 * 
	 */
	function DropTree(target, options) {
		this.target = target;
		this.value = target.value;
		this.$target = $(target);
		this.opts = $.extend({}, defaults, options, this.$target.data());
		this.id = this.target.id || this.target.name;
		if (this.$target.length > 0) {
			this._init();
		}
		return this;
	}

	DropTree.prototype._init = function() {
		var self = this;
		this.$target.hide();
		this.$selected = $('<input type="text" class="form-control">').insertBefore(this.$target);
		// this.$selected.css("height",15);
		//this.$selected.html(this.value + " ");
		//this.$down = $("<span> </span>").prependTo(this.$selected);
		this.transition = Transitions[this.opts.transition].call(this);

	};

	var Transitions = {
		ztree : function() {
			var treeId = this.id + "_tree";
			// <ul id="treeDemo" class="ztree"></ul>
			this.$options = $(
					'<div class="g-total-pd g-searchtree-box g-hide" style="max-height:280px;z-index: 999999;position: absolute;overflow:auto;"><ul id="' + treeId + '" class="mfs-options ztree">')
					.insertBefore(this.$target);
			var selected = this.$selected;
			var menudivlist = this.$options;
			var srcElem = this.target;
			var idLabel = this.opts.idLabel;
			var textLabel = this.opts.textLabel;
			var attrLabel = this.opts.attrLabel;
			var isCheckParent = this.opts.isCheckParent;
			var isopen = this.opts.isopen;
			var zTreeOnClick = function(event, treeId, treeNode) {
				$(selected).val(treeNode[textLabel]);
				// 为下拉箭头占位符
				$(srcElem).val(treeNode[idLabel]);
				$(srcElem).attr("attr-name",treeNode[attrLabel])
				menudivlist.hide();
				$(srcElem).trigger("change");
			};
			var zTreeBeforeClick = function(treeId, treeNode, clickFlag) {
				if(isCheckParent){
					return true;
				}
			    return !treeNode.isParent;//当是父节点 返回false 不让选取
			};
			var setting = {
				data : {
					simpleData : {
						enable : true,
						idKey : this.opts.idLabel,
						pIdKey : this.opts.pidLabel
					}
				},
				callback : {
					onClick : zTreeOnClick,
					beforeClick: zTreeBeforeClick
				}
			};

			this.oper = $.fn.zTree.init($("#" + treeId), setting,
					this.opts.items);
			if(isopen){
				this.oper.expandAll(true);
			}
			// 设置默认值
			var nodes = this.oper.getNodesByParam(idLabel, this.value, null);
			if (nodes.length > 0) {
				var nodeName = (nodes[0])[this.opts.textLabel];
				$(selected).val(nodeName);
				// 为下拉箭头占位符
				this.oper.selectNode(nodes[0], true);
			}
			var enableMagic = function() {
				menudivlist.hide();
				selected.click(function() {
					if (menudivlist.is(':visible')) {
						menudivlist.hide();
					} else {
						menudivlist.show();
					}
					$(this).blur();
					return false;
				});
			}// end enableMagic
			enableMagic();
			
			$(document).click(function (e) {
		    	var target_id = $(e.target).attr('id');
		    	if(target_id==null||target_id==''||target_id==undefined){
		    		menudivlist.hide();
		    	}else if(target_id.indexOf(treeId)==-1){
		    		menudivlist.hide();
		    	}
		    	
			});
		}

	};

	$.fn.droptree = function(options) {
		return this.each(function() {
			if (!$.data(this, 'droptree')) {
				$.data(this, 'droptree', new DropTree(this, options));
			}
		});
	};

})(jQuery)