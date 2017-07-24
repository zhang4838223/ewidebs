(function($){
	var data={
			allowClear: true,
			placeholder : '请选择',
			closeOnSelect: true, // Only applies to multiple selects. Closes the select upon selection.
		    minimumResultsForSearch: 8
			
	};
	$.fn.extend({
		bindSelect:function(options){
			$.fn.modal.Constructor.prototype.enforceFocus = function () {}; 
			//获取dom元素上的默认值
			var defaultValue = $(this).attr("value");
			var selectName = options.selectName;
			var select2Option = options.select2Option;
			var _options = $.extend(true,data,select2Option);
			var url = options.url;
			$(this).empty();//清空下拉框
			$(this).append("<option></option>");
			var id = $(this).attr("id");
			$(this).select2(_options);
			url = encodeURI(url);
		    $.getJSON(url, function (data) {
		        //control.selectpicker('val',0);
		        $.each(data, function (i, item) {
		        	$("#"+id).append("<option value='" + item.value + "'>&nbsp;" + item.text + "</option>");
		        });
		        if(defaultValue && defaultValue!=""){
		        	$("#"+id).select2("val",defaultValue);
		        }
		    });
		},
		//字典下拉框
		bindDict:function(options){
			var dictTypeName = options.dictTypeName;
			var select2Option = options.select2Option;
			var url = system.getContextPath()+"/sys/dict/get?type="+dictTypeName;
			$(this).bindSelect({select2Option:select2Option,url:url});
		}
		
	});
	$.extend({
		//级联字典下拉框
		cascadeDictSelect:function(options){
			var firstOptions = options.firstOptions;
			var secondOptions = options.secondOptions;
			var firstId = options.firstId;
			var secondId = options.secondId;
			var firstValue = options.firstValue;
			var secondValue = options.secondValue;
			$("#"+firstId).bindDict({select2Option:firstOptions,dictTypeName:firstValue});
			$("#"+secondId).attr("disabled",true);
			$("#"+firstId).on("change",function(){
				var firstOptionValue = $("#"+firstId).val();
				var firstOptionsLabel = $("#"+firstId).find("option[value="+firstOptionValue+"]").text().trim();
				var url = system.getContextPath()+"/sys/dict/getCascadeDictJson?firstValue="+firstOptionValue+"&secondValue="+secondValue+"&firstLabel="+firstOptionsLabel;
				$("#"+secondId).attr("disabled",false);
				$("#"+secondId).bindSelect({select2Option:secondOptions,url:url});
			});
		},
		cascadeSelect : function(options){
			var firstOptions = options.firstOptions;
			var secondOptions = options.secondOptions;
		    var firstUrl = options.firstUrl;
		    var secondUrl = options.secondUrl;
		    var firstId = options.firstId;
			var secondId = options.secondId;
			$("#"+firstId).bindSelect({select2Option:firstOptions,url:firstUrl});
			$("#"+firstId).on("change",function(){
	    	 var selectVal = $(this).val();
	    	$("#"+secondId).bindSelect({select2Option:secondOptions,url:(secondUrl+selectVal)});
	    });
	}
	});
})(jQuery);