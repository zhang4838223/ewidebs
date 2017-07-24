(function($){
	/*左右布局下，用户table初始化时，获取table的高度*/
	$.rlLayout = {
		getBsHeight : function(){
			var aaheight = $('.navbar-form').parent().outerHeight();
			var bbheight = $('.right-layout').height();
			var ccheight = parseInt(bbheight) - parseInt(aaheight);
			return ccheight;
		}
	};
})(jQuery);