(function($) {
    $.fn.bootstrapValidator.validators.idNumber = {
        /**
         * 用于测试，校验规则为：长度必须为5
         * @param {BootstrapValidator} validator The validator plugin instance
         * @param {jQuery} $field The jQuery object represents the field element
         * @param {Object} options The validator options
         * @returns {boolean}
         */
        validate: function(validator, $field, options) {
            // You can get the field value
             var value = $field.val();
            //
            // Perform validating
             if(value.length === 5){
            	 return true;
             }else{
            	 return false;
             }
            //
            // return true if the field value is valid
            // otherwise return false
        }
    };
}(window.jQuery));