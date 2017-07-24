(function($){
	
	
	
	
	$.fn.extend({
		
		selectx : function(option){
			
			$(this).onchange(function(){
				
				//拿value
				var val = $(this).val();
				//取数据
				var data;
				//操作从select的option
				$("#"+ options.cid);
				
			});
			
			//select2 控件的操作，主要是select效果。
			
			$(this).select2();
			
		}
		
	});
	
	var _options = $.extend(true,{},option);
	
	
	
	
})(jQuery);

