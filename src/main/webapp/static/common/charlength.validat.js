(function($){
	/**
	 * 用于校验页面输入的字符字符长度，主要针对中文和字符
	 * @param $
	 */
	$.fn.bootstrapValidator.validators.charLength = {
		validate: function(validator,$field,options){
			var input_value = $field.val();
			if(getLength(input_value)==0){
				return true;
			}
			var minLength = options.minLength;
			var maxLength = options.maxLength;
			if(minLength==null || minLength==undefined){ 
				minLength=0;
			}
			var max_length = options.maxLength;
			if(maxLength==null || maxLength==undefined){ 
				maxLength=0;
			}
			
			if(parseInt(minLength) < getLength(input_value) && getLength(input_value) <= parseInt(maxLength)){
				return true;
			}else{
				return false;
			}
		}	
	};
}(window.jQuery));

/**
 * 计算字符的长度，中文+=2，单字节++
 * @param str
 * @returns {Number}
 */
function getLength(str){
	var len = 0;
	if (str == null || str == "")	return len;
	str = $.trim(str);
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		//单字节加1 
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			len++;
		} else {
			len += 2;
		}
	}
	return len;
}