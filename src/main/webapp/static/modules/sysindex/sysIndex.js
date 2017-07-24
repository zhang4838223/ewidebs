/**
 * 窗体加载
 */
$(document).ready(function() {
	setInterval("getCurDate()",100);  
	
	//头部点击下拉
	$('.intop_user_down').bind('click',function(){
		$('.intop_user_disble').slideToggle();
		$('.intop_notice_div').css('display','none');
		$('.intop_infomation_div').css('display','none');
	});
	
	//左边导航(系统设置、客服系统、UI演示)点击效果
	$('.leftsider_dd p a').bind('click',function(){
		$('.leftsider_dd p a').removeClass();
		$(this).parent().parent().siblings().find('ol').css('display','none');//关闭其他下拉
		$(this).parent().parent().siblings().find('p a img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png');//改变其他下拉的图标
		$(this).parent().parent().find('a').removeClass('leftside_select');//移除所有二级菜单的选中颜色
		$(this).parent().parent().find('ol li ul').css('display','none');//移除所有三级菜单的展开效果
		$(this).parent().parent().find('ol li ul li a').removeClass('leftsider_dd_select2');//移除所有三级菜单的文字选中效果
		if($(this).find('img').attr('src')==system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png'){//判断点击下拉的图标，并改变
			$(this).find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_up.png');
			$(this).addClass('leftsider_dd_select2');
		}else{
			$(this).find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png')
			$(this).removeClass('leftsider_dd_select2');
		}
		$(this).parent().parent().find('.firstOl').slideToggle();
	});
	
	//导航以外的菜单点击效果
	$('.leftsider_dd ol li a').bind('click',function(){
		$(this).parent().siblings().find('ul').css('display','none');
		$(this).parent().find('ol').css('display','none');
		$(this).parent().find('ul').slideToggle();
		$(this).addClass('leftside_select');
		$(this).parent().siblings().find('a').removeClass('leftside_select');
		$('.seccendOl a').removeClass('leftsider_dd_select2');
		$(this).parent().siblings().find('.seccendOl').css('display','none');	
	});
	
	//三级菜单的点击效果
	$('.menu3').bind('click',function(){
		if($(this).hasClass("link")){
			$(this).removeClass('leftside_select');
			$(this).addClass('leftsider_dd_select2');
			$(this).parent().siblings().find('a').removeClass('leftsider_dd_select2');
		}else{
			$(this).parent().siblings().find('ol').css('display','none');
			$(this).parent().find('ol').slideToggle();
			$(this).parent().parent().parent().find('a').removeClass('leftside_select');
			$(this).addClass('leftside_select');
			
			if($(this).parent().find('.seccendOl').css('display')=='none'){
				$('.seccendOl a').removeClass('leftsider_dd_select2');
				$(this).parent().find('.seccendOl').css('display','block');
				$(this).parent().siblings().find('.seccendOl').css('display','none');				
			}else{
				$(this).parent().find('.seccendOl').css('display','none');
			}
		}
	});
	
	//四级菜单的点击效果
	$('.menu4').bind('click',function(){
		$(this).removeClass('leftside_select');
		$(this).addClass('leftsider_dd_select2');
		$(this).parent().siblings().find('a').removeClass('leftsider_dd_select2');
	});
	
	$('.leftsider_dd ol li ul li div label a').bind('click',function(){//四级菜单的点击效果
		$(this).removeClass('leftside_select');
		$(this).addClass('leftsider_dd_select2');
	});
	
	//用于控制左边菜单的折叠图标点击效果
	$('.menu_left').bind('click',function(){
		if($('.leftsider_dd p a').css('display')=='none'){
			$('.leftsider').css('width','166px');
			$('.leftsider_dd p a').css('display','block');
			$('.rightcont').css('margin-left','169px');
			$('.leftsider').css('overflow-y','auto');
			$('.leftsider').css('overflow-x','hidden');
		}else{
			$('.leftsider').css('width','66px');
			$('.leftsider_dd p a').css('display','none');
			$('.leftsider_dd ol').css('display','none');
			$('.leftsider_dd p a').css('color','#666');
			$('.leftsider_dd p a img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png');	
			$('.rightcont').css('margin-left','69px');
			$('.leftsider').css('overflow','visible');
		}
	});
	
	//点击悬浮中第一个a标签触发事件，即悬浮中的一级菜单
	$('.leftsider_hover_div>a').bind('click',function(){
		if($('.leftsider_hover_div ul').css('display')=='block'){
			$(this).parent().parent().css('height','300px');
			$(this).parent().parent().css('overflow-y','auto');
		}else{
			$(this).parent().parent().css('height','56px');
			$(this).parent().parent().css('overflow','hidden');
		}		
	});
	
	//点击悬浮一级菜单
	$('.first_hover_menu').bind('click',function(){
		$('.second_hover_menu').css('color','#666');
		$('.three_hover_menu').css('color','#666');
		$('.four_hover_menu').css('color','#666');
		$(this).css('color','#2d6cd3');
	});
	
	//点击悬浮二级菜单
	$('.second_hover_menu').bind('click',function(){
		$(this).siblings().find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png');
		$(this).siblings().css('color','#666');		
		if($(this).next().css('display')=='block'){
			$(this).parent().find('ul').css('display','none');
			$(this).find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_up.png');			
			$(this).next().css('display','block');	
		}else{
			$(this).parent().find('ul').css('display','none');
			$(this).find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png');
		}		
		$('.first_hover_menu').css('color','#666');
		$(this).css('color','#2d6cd3');
	});
	
	//点击悬浮三级菜单
	$('.three_hover_menu').bind('click',function(){
		$(this).siblings().find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png');
		$(this).siblings().css('color','#666');
		if($(this).next().css('display')=='block'){
			$(this).parent().find('ul').css('display','none');
			$(this).find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_up.png');			
			$(this).next().css('display','block');	
		}else{
			$(this).parent().find('ul').css('display','none');
			$(this).find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png');
		}
		$('.first_hover_menu').css('color','#666');
		$('.second_hover_menu').css('color','#666');
		$(this).css('color','#2d6cd3');
	});
	
	//点击悬浮四级菜单
	$('.four_hover_menu').bind('click',function(){
		$(this).siblings().css('color','#666');
		$('.first_hover_menu').css('color','#666');
		$('.second_hover_menu').css('color','#666');
		$('.three_hover_menu').css('color','#666');
		$(this).css('color','#2d6cd3');
	});
	
	$('.leftsider_hover_div ul li').bind('hover',function(){
		$(this).css('background','#fff')
	});
	
	//触发悬浮导航hover效果		
	$('.leftsider_dd').mouseover(function(){		
//		hoverHeight($(this));
		if($('.leftsider_dd p a').css('display')=='none'){
			$(this).find('.leftsider_hover').css('display','block');
			$(this).css('background','#c6c6c6');
		}else{
		}			
	});
	
	//清除悬浮导航的样式，leftsider_dd样式下的子元素不会触发该事件
	$('.leftsider_dd').mouseenter(function(){
		$('.leftsider_hover').find('ul').css('display','none');
		$('.leftsider_hover').find('a').css('color','#666');
		$('.leftsider_hover').css('height','56px');
		$('.leftsider_hover').css('overflow','hidden');		
		$('.first_hover_menu img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_up.png');
	});
	
	//鼠标离开时移除悬浮菜单hover效果
	$('.leftsider_dd').mouseout(function(){		
		$(this).find('.leftsider_hover').css('display','none');
		$(this).css('background','none');	
	});	
	
	//关闭下拉选择
	$('.righttop_right_div a').bind('click',function(){
		$('.righttop_right_div_hidden').slideToggle();
	});
	
	//点击标签页样式
	$('.righttop_label ul').on('click','li',function(e){
		if($(e.target).is('a')){
			$('.leftsider_dd ol li ul li a').removeClass('leftsider_dd_select2');
			$('.rightcont_iframe iframe[aa="'+$(this).attr('aa')+'"]').remove();
			
			var prev = $(this).prev();
			$(this).remove();
			showPrevTab(prev);
		}else{
			$('.righttop_label p').removeClass('righttop_label_select');
			$('.righttop_label .righttop_label_select').removeClass('righttop_label_select');
			$(this).addClass('righttop_label_select');
			$(this).siblings().removeClass('righttop_label_select');	
			$('.leftsider_dd ol li ul li a').removeClass('leftsider_dd_select2');
			$('.leftsider_dd ol li ul li a[aa="'+$(this).attr('aa')+'"]').addClass('leftsider_dd_select2');
			$('.rightcont_iframe iframe').siblings().css('display','none');
			$('.rightcont_iframe iframe[aa="'+$(this).attr('aa')+'"]').css('display','block');
//			$('.rightcont_iframe iframe[aa="'+$(this).attr('aa')+'"]').siblings().css('display','none');
		}
	});
	
	//点击首页之后
	$('.righttop_label p').bind('click',function(){
		$('.righttop_label ul li').removeClass('righttop_label_select');
		$('.leftsider_dd ol li ul li a').removeClass('leftsider_dd_select2');
		$('.righttop_label p').addClass('righttop_label_select');
		$('.rightcont_iframe iframe').css('display','none');
	});
	
	//关闭当前
	$('.close_on').bind('click',function(){
		var marginLeft = parseInt($('.righttop_label').css('margin-left'));	
	    var selectLength = $('.righttop_label ul .righttop_label_select').length;
		if(selectLength == 0){
//			$('.righttop_label ul li').remove();
//			$('.leftsider_dd ol li ul li a').removeClass('leftsider_dd_select2');
//			$('.rightcont_iframe iframe').remove();
//			//首页不关闭
//			var marginLeft = parseInt($('.righttop_label').css('margin-left'));	
//			if(marginLeft<0){
//				var offset = Math.abs(marginLeft);
//				$(".righttop_label").animate({marginLeft: "+="+offset+"px"}, 222);
//			}
			return;
		}else{
			var title = $('.righttop_label_select').text();
			var obj = $('a[title="tab_'+title+'"]').parent().prev();			
				
			$('.rightcont_iframe iframe[aa="'+$('.righttop_label ul li.righttop_label_select').attr('aa')+'"]').remove();
			$('.righttop_label ul .righttop_label_select').remove();
			$('.leftsider_dd ol li ul li a').removeClass('leftsider_dd_select2');
			
			//关闭当前后默认显示上一页签
			if(obj!=null &&  obj != undefined){
				showPrevTab(obj);				
			}
		}
	});
	
	//关闭所有
	$('.close_all').bind('click',function(){
		$('.righttop_label ul li').remove();
		$('.leftsider_dd ol li ul li a').removeClass('leftsider_dd_select2');
		$('.rightcont_iframe iframe').remove();
		//首页不关闭
		var marginLeft = parseInt($('.righttop_label').css('margin-left'));	
		if(marginLeft<0){
			var offset = Math.abs(marginLeft);
			$(".righttop_label").animate({marginLeft: "+="+offset+"px"}, 222);
		}
	});
	
	//关闭其它
	$('.close_other').on('click', function() {
		$('.righttop_label li:not(.righttop_label_select)').remove();
		var selectedLi = $('.righttop_label li.righttop_label_select');
		$('.rightcont_iframe iframe[aa!="' + selectedLi.attr('aa') + '"]').remove();
		$('.leftsider_dd ol li ul li a').removeClass('leftsider_dd_select2');
		if(selectedLi.length == 0) {
			$('.righttop_label p').trigger('click');
		}
		//首页不关闭
		var marginLeft = parseInt($('.righttop_label').css('margin-left'));	
		if(marginLeft<0){
			var offset = Math.abs(marginLeft);
			$(".righttop_label").animate({marginLeft: "+="+offset+"px"}, 222);
		}
	});
	
	//页签向左滚动
	$('.left_mobile').bind('click',function(){
		var marginLeft = parseInt($('.righttop_label').css('margin-left'));	
		if(marginLeft >= 0) return;
		//判断元素是否正处于动画状态,如果当前没有进行动画，则添加新动画
		if(!($(".righttop_label").is(":animated"))){
			$(".righttop_label").animate({
				marginLeft: "+=92px"
			}, 222);
		}
	});
	
	//页签向右移动
	$('.right_mobile').on('click', function(){
		var menuWidth = 92;
		var marginRight = 0,marginLeft = 0;
		//var menuWidth = $('.righttop_label p').width();
		$('.righttop_label li').each(function(index, element) {
			 if(!isNaN($(element).outerWidth())){
				 menuWidth += $(element).outerWidth();				 
			 }
		});
		if(!isNaN(parseInt($('.righttop_label li').css('margin-right')))){
			marginRight = parseInt($('.righttop_label li').css('margin-right'));						
		}
		menuWidth += marginRight * ($('.righttop_label li').length - 1);
		marginLeft = parseInt($('.righttop_label').css('margin-left'));			
//		alert("menuWidth:====>>"+menuWidth+"marginRight:==>>"+marginRight+"marginLeft:====>>"+marginLeft);
		if (menuWidth + marginLeft < getTabCanScrollWidth()) return;
		//判断元素是否正处于动画状态,如果当前没有进行动画，则添加新动画
		if(!($(".righttop_label").is(":animated"))){
			$(".righttop_label").animate({
				marginLeft: "-=92px"
			}, 222);
		}
	});
	
	//左边导航hover效果	
	$('.righttop_right_div').mouseover(function(){
		if($('.righttop_right_div_hidden').css('display')=='none'){	
			$('.righttop_right_div_hidden').css('display','block');
		}else{		
		}	
	});
	
	$('.righttop_right_div').mouseout(function(){
		$('.righttop_right_div_hidden').css('display','none');
	});	
	
	//公告
	$('.intop_notice').bind('click',function(){
		$('.intop_notice_div').slideToggle();
		$('.intop_infomation_div').css('display','none');
		$('.intop_user_disble').css('display','none');
	});
	
	//信息
	$('.intop_infomation').bind('click',function(){
		$('.intop_infomation_div').slideToggle();
		$('.intop_notice_div').css('display','none');
		$('.intop_user_disble').css('display','none');
	});
});

/**
 * 获取当前页签可滚动区域宽度，先默认为1000px;
 * TODO 后期考虑是否根据窗口宽度及相关元素进行计算
 * @returns
 */
function getTabCanScrollWidth(){
	var width = parseInt($('.top-tabs').width());
	if(width<1000 || width>1000){
		width = 1000;
	}
	return width;
}

/**
 * 关闭当前Tab页签，展示当前页签前面的页签内容
 * @param dom
 */
function showPrevTab(dom){
	var aa= $(dom).attr('aa');
	var text= $(dom).text();
	var src = $("#"+aa).attr("src");
//	alert("aa：====>>"+aa+" text:====>>"+text+" src:====>>"+src);
	if(aa==null || aa == undefined){
		$("#indexpage").trigger('click');
	}else{
		$(dom).trigger('click');		
	}
}

/**
 * 菜单点击事件
 * @param dom
 */
function menuClick(dom){
	//准备操作
	//取当前的菜单
	//1. 添加tab页签
	var text= $(dom).text();
	var aa= $(dom).attr('aa');
	var src = $(dom).attr('src');
	addTab(text,aa);
	//2. 根据url请求后台页面，并将页面显示到iframe		
	addContentFrame(aa,text,src);
}

/**
 * 添加tab页签
 * @param tabName
 * @param id
 */
function addTab(tabName,id){	
	$('.righttop_label p').removeClass('righttop_label_select');
	var dom = "<li aa='"+id+"'><span>"+tabName+"</span><a aa='"+id+"' title='tab_"+tabName+"'></a></li>";	
	$($('.righttop_label ul li')).removeClass('righttop_label_select');//清除其他页标签的样式
	if(isExists(id)){			
		$($('.righttop_label ul li[aa="'+id+'"]')).addClass('righttop_label_select');
	}
	else{
		$(".righttop_label ul").append(dom);
		$($('.righttop_label ul li[aa="'+id+'"]')).addClass('righttop_label_select');
	}
	
	//计算点击左边菜单，右边页签自动填充
	var menuWidth = 92;
	var px = parseInt($('.righttop_label').css('margin-left'));
	var leftNum = Math.abs((px/menuWidth)); 
	var rightNum = parseInt($('.righttop_label ul li').length)-leftNum;
	var topNum = parseInt(getTabCanScrollWidth()/92);
	var offset = 0;
	if(rightNum>=topNum){
		offset = menuWidth*(rightNum-topNum+1);
		$(".righttop_label").animate({marginLeft: "-="+offset+"px"}, 222);
	}
}

/**
 * 根据页签的title获取页签对象
 * @param tabName 页签名称
 */
function getTabObject(tabName){
	if(tabName==null || tabName == "") return ;
	var obj = $('a[title="'+tabName+'"]');
	if(obj == null || obj == undefined) return;
	var aa = obj.attr("aa"); 
	var src = obj.attr("src");
//	alert("aa：====>>"+aa+" src:====>>"+src);
	return $('a[title="'+tabName+'"]').parent();
}

/**
 * 根据页签名字获取对应的IFrame
 * @param tabName 页签名称
 */
function getFrmIdByTabName(tabName){
	if(tabName==null || tabName == "") return ;
	var obj = $('a[title="'+tabName+'"]');
	
	if(obj == null || obj == undefined) return;
	var aa = obj.attr("aa"); 
	var src = obj.attr("src");
	return aa + "frm";
}

/**
 * 根据页签Title关闭页签
 * @param title
 */
function closeTabByTitle(title){
	$($('.rightcont_iframe iframe')).css('display','none');//清除其他页标签的样式		
	if(title==null || title=="" || title==undefined) return;
	var aa = $('a[title="tab_'+title+'"]').attr("aa");
	if(aa==null || aa=="" || aa==undefined) return;
	$("#"+aa).trigger('click');
		
	var obj = $('a[title="tab_'+title+'"]').parent().prev();
	
	$('.righttop_label p').removeClass('righttop_label_select');
	$('.rightcont_iframe iframe[aa="'+$('.righttop_label ul li.righttop_label_select').attr('aa')+'"]').remove();
	$('.righttop_label ul .righttop_label_select').remove();
	
	$(this).remove();
	
	showPrevTab(obj);
}

/**
 * 判断菜单是否已被打开
 * @param id
 * @returns {Boolean}
 */
function isExists(id){
	if(!id){
		//理论上为异常
		return false;
	}
	if($('.righttop_label ul li[aa="'+id+'"]').size() > 0){//判断页标签是否纯在
		return true;
	}
	return false;
}	

/**
 * 判断iframe是否存在
 * @param id
 * @returns {Boolean}
 */
function isExistsIframe(id){
	if(!id){
		//理论上为异常
		return false;
	}
	if($('.rightcont_iframe iframe[aa="'+id+'"]').size()>0){
		return true;
	}
	return false;
}

/**
 * 添加菜单对应页面的frame
 * @param id
 * @param text
 * @param src
 */
function addContentFrame(id,text,src){
	$($('.rightcont_iframe iframe')).css('display','none');//清除其他页标签的样式		
	var frm = "<iframe src='"+src+"' aa='"+id+"' title='"+text+"' id='"+id+"frm' name='"+id+"frm' ></iframe>";
	//将其他frame隐藏
	//添加frame，并显示
	if(isExistsIframe(id)){
		$('.rightcont_iframe iframe[aa="'+id+'"]').css('display','block');
	}else{
		$(".rightcont_iframe").append(frm);
	}
}

/**
 * 获取当前选中的页签
 */
function closeCurrentCheckedTab(){
	var obj = $('.righttop_label ul .righttop_label_select');
	if(obj == null || obj == undefined) return;
	closeTabByTitle(obj.text());
}

/**
 * 判断页签的长度
 * @returns {Boolean}
 */
function isLength(){
	if($('.righttop_label ul li').length>6){
		return true;
	}else{
		return false;
	}
}

/**
 * 打开菜单
 * @param dom
 */
function openMenu(dom){
	$(dom).css('color','#666');
	if($(dom).find('img').attr('src')==system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png'){//判断点击下拉的图标，并改变
		$(dom).find('img').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_up.png');
		$(dom).css('color','#2d6cd3');
	}else{
		$(dom).find('img:first').attr('src',system.getStaticPath()+'/images/sysindex/leftsider_dd_down.png')
		$(dom).css('color','#666');
	}
//	$(dom).parent().find('ul:first').css('display','none');
//	$(dom).parent().find('ul:first').slideToggle();
	if($(dom).next().css('display')=='none'){
		$(dom).next().css('display','block');
	}else{
		$(dom).next().css('display','none');
	}
	
//	var menu = $(dom).text();
//	alert($('.leftsider_hover_div > ul  >a').size());
//	alert($('#menu_'+menu+' > ul > a').size());
}

/**
 * 获取悬浮窗口的最大高度
 * @param 当前触发悬浮的对象
 */
function hoverHeight(dom){
	alert(dom.index());
}

/**
 * 获取当前日期
 */
function getCurDate() {
	var d = new Date();
	var week;
	switch (d.getDay()) {
	case 1:
		week = "星期一";
		break;
	case 2:
		week = "星期二";
		break;
	case 3:
		week = "星期三";
		break;
	case 4:
		week = "星期四";
		break;
	case 5:
		week = "星期五";
		break;
	case 6:
		week = "星期六";
		break;
	default:
		week = "星期天";
	}
	var years = d.getFullYear();
	var month = add_zero(d.getMonth() + 1);
	var days = add_zero(d.getDate());
	var hours = add_zero(d.getHours());
	var minutes = add_zero(d.getMinutes());
	var seconds = add_zero(d.getSeconds());
	var ndate = years + "年" + month + "月" + days + "日 " + hours + ":" + minutes+ ":" + seconds + "\t\t\t\t" + week;
	divT.innerHTML = ndate;
}  
  
/**
 * 日期处理
 * @param temp
 * @returns
 */
function add_zero(temp) {
	if (temp < 10){
		return "0" + temp;		
	}else{
		return temp;		
	}
} 
	
	