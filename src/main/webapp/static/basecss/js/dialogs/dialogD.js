(function($){
	
	
	/*$.myDialogs = {
		showDialogs : function(options) {
			var __options = $.extend(true, {}, options);
			_options = $.extend(true, {}, options);
			_options.success = function(index,layero) {
				if(typeof __options.success === "function"){
					__opitons.success(layero,index);
				}
			};
			_options.yes = function(index,layero) {
				if(_options._yes){
					_options._yes();
				}
				if(typeof __options.yes === "function"){
					options.yes(_dia_retData,index,layero);
				}
				layer.close(index);
			};
			_options.cancel = function(index) {
				
				if(typeof __options.cancel === "function"){
					__opitons.cancel(index);
				}
			};
			_options.end = function() {
				if(typeof __options.end === "function"){
					__opitons.end();
				}
			};
			
			layer.open(_options);
			_dia_params = options.params;
		},
		
		
		_setParams : function(data){
			_dia_retData = data;
		},
		_getParams : function(){
			return _dia_retData;
		},
		_getParentParams : function(){
			return _dia_params;
		},
		_setYes : function(fun){
			if(typeof fun === "function"){
				_options._yes = fun;
			}
		},
		getParentParams : function() {
			return parent.$.myDialogs._getParentParams();
		},
		clickYes : function(fun){
			parent.$.myDialogs._setYes(fun);
		},
		setReturnData : function(data){
			parent.$.myDialogs._setParams(data);
		},
		setBtnClick : function(index,data){
			
		}
	};
	
	
	
	var init = function(){
		_options["btn1Click"] = 
		$("#btn1").click(function(){
			
		});
		
	};*/
	
	$.myDialog = dialog();
	
	//定义dialog
	var dialog = function(options){
		var _options = {};
		var _dia_retData = null;
		var _dia_params = null;
		
		var _dialog;
		
		var showDialogs = function(options) {
			var __options = $.extend(true, {}, options);
			_options = $.extend(true, {}, options);
			_options.success = function(index,layero) {
				if(typeof __options.success === "function"){
					__opitons.success(layero,index);
				}
			};
			_options.yes = function(index,layero) {
				if(_options._yes){
					_options._yes();
				}
				if(typeof __options.yes === "function"){
					options.yes(_dia_retData,index,layero);
				}
				layer.close(index);
			};
			_options.cancel = function(index) {
				
				if(typeof __options.cancel === "function"){
					__opitons.cancel(index);
				}
			};
			_options.end = function() {
				if(typeof __options.end === "function"){
					__opitons.end();
				}
			};
			
			layer.open(_options);
			_dia_params = options.params;
		}
		
//		_dialog.showDialogs = showDialogs;
		
		_dialog = {
				showDialogs : showDialogs,
				
				_setParams : function(data){
					_dia_retData = data;
				},
				_getParams : function(){
					return _dia_retData;
				},
				_getParentParams : function(){
					return _dia_params;
				},
				_setYes : function(fun){
					if(typeof fun === "function"){
						_options._yes = fun;
					}
				},
				getParentParams : function() {
					return parent.$.myDialogs._getParentParams();
				},
				clickYes : function(fun){
					parent.$.myDialogs._setYes(fun);
				},
				setReturnData : function(data){
					parent.$.myDialogs._setParams(data);
				},
				setBtnClick : function(index,data){
					
				}
		};
		return _dialog;
	}
	
	
})(jQuery);