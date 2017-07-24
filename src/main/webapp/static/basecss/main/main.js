$('.help-block').bind('click',function(){
	$(this).css('display','none');
	$(this).parent().find('.tooltip-arrow').css('display','none');
});
$('.input-group i.form-control').bind('click',function(){
	$(this).parent().parent().find('.help-block').css('display','block');
	$(this).parent().parent().find('.tooltip-arrow').css('display','block');
});