;(function($){
    $.fn.extend({
        /* 弹出角色选择层 */
        showBox : function(op){
            var o = { clickDom:'', showDom:'',close:'',type:'2'};
            var option = jQuery.extend(o,op);
            $(option['clickDom']).bind('click',function(){
                $(option['showDom']).fadeToggle();
            })
            if(option['close']!=''){
                $(option['close']).bind('click',function(){
                    $(option['showDom']).hide();
                })
            }
        }
    });
})(jQuery);