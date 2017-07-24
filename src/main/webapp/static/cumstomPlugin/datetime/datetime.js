(function($){
	var data = {
			format:'YYYY/MM/DD hh:mm:ss',//时间格式
			locale:'zh-CN',//中文提示
			useCurrent:true,//On show, will set the picker to the current date/time
			showClose:true,//Show the "Close" button in the icon toolbar.Clicking the "Close" button will call hide()
			showTodayButton:true,//显示今天按钮
			showClear:true,//显示清空按钮，点击会清空时间
			focusOnShow:true//If false, the textbox will not be given focus when the picker is shown
	};
	$.extend({
		//开始时间，结束时间 时间控件
		towDatetime:function(startDateOption,endDateOption){
			var startId = startDateOption.id;
			var endId = endDateOption.id;
			var startOptions = $.extend(true,data,startDateOption.startOption);
			var endOptions = $.extend(true,data,endDateOption.endOption);
			$("#"+startId).datetimepicker(startOptions);
			$("#"+endId).datetimepicker(endOptions);
			
		    $("#"+startId).on("dp.change", function (e) {
		    	$("#"+endId).data("DateTimePicker").minDate(e.date);
	        });
		    $("#"+endId).on("dp.change", function (e) {
		    	 $("#"+startId).data("DateTimePicker").maxDate(e.date);
		    });
		    
		}
	});
	$.fn.extend({
		//单个时间控件
		oneDateTime:function(options){
			var _options = $.extend(true,data,options);
			$(this).datetimepicker(_options);
		}
	});
})(jQuery);
