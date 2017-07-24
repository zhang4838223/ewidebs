<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${fns:getConfig('productName')}</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/basecss/sysindex/sysIndex.css" />
		<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
		<link href="${ctxStatic}/common/jeesite.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
		
	<div class="index">
		<!--头部-->
		<div class="intop">
			<div class="intop_one font12">
				<div class="intop_one_left">
					<label>当前部门：<i>${fns:getUser().office.name}</i></label>
					<label>当前人员：<i>${fns:getUser().loginName}</i></label>
					<label><i id="divT"></i></label>
					<label>当前在线人数：<i class="font16">12345</i></label>	
				</div>
				<div class="intop_one_right">
					<div class="intop_search"><input type="text"><a href="#"></a></div>
					<div class="intop_user" >
						<label><span style="background: url(${ctxStatic}/images/sysindex/intop_user01.png) no-repeat left center;">个人中心</span><a class="intop_user_down"></a></label>
						<div class="intop_user_disble" style="display:none ;">
							<label><span style="background: url(${ctxStatic}/images/sysindex/intop_user02.png) no-repeat left center;"><a aa="29" id="29" title="个人信息" src="${ctx}/sys/user/info" onclick="menuClick(this)" style="color:#000">个人信息</a></span></label>
							<label><span style="background: url(${ctxStatic}/images/sysindex/intop_user03.png) no-repeat left center;"><a aa="30" id="30" title="修改密码" src="${ctx}/sys/user/modifyPwd" onclick="menuClick(this)" style="color:#000">修改密码</a></span></label>
						</div>
					</div>
					<div class="intop_function">
						<label class="if_label">
							<span class="if_label_float" style="background:#f8ac59;">13</span>
							<a class="intop_notice" style="background: url(${ctxStatic}/images/sysindex/intop_function01.png)no-repeat left center;">公告</a>
							<div class="intop_notice_div">
								<ul>
									<li><a href="#"><span>第一条公告</span><i>4分钟前</i></a></li>
									<li><a href="#"><span>第一条公告</span><i>4分钟前</i></a></li>
									<li><a href="#"><span>第一条公告</span><i>4分钟前</i></a></li>		
									<li><a href="#"><span>第一条公告</span><i>4分钟前</i></a></li>											
								</ul>
								<a class="notice_btn">查看更多&gt;&gt;</a>
							</div>
						</label>	
						
						<label class="if_label">
							<span class="if_label_float" style="background:#1ab394;">13</span>
							<a class="intop_infomation" style="background: url(${ctxStatic}/images/sysindex/intop_function02.png)no-repeat left center;">信息</a>
							<div class="intop_infomation_div">
								<ul>
									<li><a href="#"><span>这个在日本投降书上签字的军官，建国后一定是个不小的干部吧？ </span><i>4分钟前</i></a></li>
									<li><a href="#"><span>第一条公告</span><i>4分钟前 2016-24-23 12:12:20</i></a></li>
									<li><a href="#"><span>第一条公告</span><i>4分钟前</i></a></li>		
									<li><a href="#"><span>第一条公告</span><i>4分钟前</i></a></li>											
								</ul>
								<a class="infomation_btn">查看更多&gt;&gt;</a>
							</div>
						</label>		
						
						<a href="${ctx}/logout" style="background: url(${ctxStatic}/images/sysindex/intop_function03.png)no-repeat left center;">退出</a>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			
			<div class="intop_two">
				<div class="index_logo"><img src="${ctxStatic}/images/sysindex/logo.png"></div>
				<%-- <div class="intop_username"><img src="${ctxStatic}/images/sysindex/user_image.png"> <span>${fns:getUser().loginName}</span></div> --%>
			</div>
			
			<div class="intop_shadow"></div>
		</div>
		<!-- 左边导航-->
		<c:set var="menulist" value="${fns:getMenuList()}"/>
		<div class="leftsider">
		<!-- 一级菜单 -->
		<c:forEach items="${menulist}" var="menu" varStatus="idxStatus">
		<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
		<div class="leftsider_dd">
		<p><img src="${ctxStatic}/images/sysindex/leftsider_dd01.png"><a>${menu.name}<img style="" src="${ctxStatic}/images/sysindex/leftsider_dd_down.png"></a></p>
				<ol class="firstOl" style="display: none;">
					<!-- 二级菜单 --> 
					<c:forEach items="${menulist}" var="menu2">
						<c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
						<c:if test="${empty menu2.href}">
						<li>
						<a class="">${menu2.name}</a>
						<ul style="display: none;">
						<!-- 三级菜单 --> 
						<c:forEach items="${menulist}" var="menu3">
							<c:if test="${menu3.parent.id eq menu2.id && menu3.isShow eq '1'}">
								<c:if test="${empty menu3.href}">
								<li>
								<a class="menu3">${menu3.name}</a>
								<div class="seccendOl" style="display: none;">
								<!-- 四级菜单 -->
								<c:forEach items="${menulist}" var="menu4">
									<c:if test="${menu4.parent.id eq menu3.id && menu4.isShow eq '1'}">
										<c:if test="${not empty menu4.href}">
										<label><a class="menu4 link" aa="${menu4.id }" id="${menu4.id }" title="${menu4.name}" src="${fn:indexOf(menu4.href, '://') eq -1 ? ctx : ''}${not empty menu4.href ? menu4.href : '/404'}" onclick="menuClick(this)">${menu4.name}</a></label>
										</c:if>
									</c:if>
								</c:forEach>
								</div>
								</li>
								</c:if>
								<c:if test="${not empty menu3.href}">
								<li><a class="menu3 link" aa="${menu3.id }" id="${menu3.id }" title="${menu3.name}" src="${fn:indexOf(menu3.href, '://') eq -1 ? ctx : ''}${not empty menu3.href ? menu3.href : '/404'}" onclick="menuClick(this)">${menu3.name}</a></li>
								</c:if>
							</c:if>
						</c:forEach>
						</ul>
						</li>
						</c:if>
						<c:if test="${not empty menu2.href}">
						<li><a class="menu2 link" aa="${menu2.id }" id="${menu2.id }" title="${menu2.name}" src="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}" onclick="menuClick(this)">${menu2.name}</a></li>
						</c:if>
						</c:if>
					</c:forEach>
				</ol>
				<div class="leftsider_hover font12" style="display: none;">
					<div class="leftsider_hover_div" id="menu_${menu.name}">
						<a onclick="openMenu(this)" class="first_hover_menu">${menu.name}<img style="" src="${ctxStatic}/images/sysindex/leftsider_dd_down.png"></a>
						<ul style="display: none;">
						<c:forEach items="${menulist}" var="menu2">
						<c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
						<c:if test="${empty menu2.href}">
						<a onclick="openMenu(this)" class="second_hover_menu">${menu2.name}<img style="" src="${ctxStatic}/images/sysindex/leftsider_dd_down.png"></a>
						<ul style="display: none;">
						<c:forEach items="${menulist}" var="menu3">
						<c:if test="${menu3.parent.id eq menu2.id && menu3.isShow eq '1'}">
						<c:if test="${empty menu3.href}">
						<a onclick="openMenu(this)" class="three_hover_menu">${menu3.name}<img style="" src="${ctxStatic}/images/sysindex/leftsider_dd_down.png"></a>
						<ul style="display: none;">
						<c:forEach items="${menulist}" var="menu4">
						<c:if test="${menu4.parent.id eq menu3.id && menu4.isShow eq '1'}">
						<c:if test="${not empty menu4.href}">
						<a class="four_hover_menu" aa="${menu4.id }" id="${menu4.id }" title="${menu4.name}" src="${fn:indexOf(menu4.href, '://') eq -1 ? ctx : ''}${not empty menu4.href ? menu4.href : '/404'}" onclick="menuClick(this)">${menu4.name}</a>
						</c:if>
						</c:if>
						</c:forEach>
						</ul>
						</c:if>
						<c:if test="${not empty menu3.href}">
						<a class="three_hover_menu_link" aa="${menu3.id }" id="${menu3.id }" title="${menu3.name}" src="${fn:indexOf(menu3.href, '://') eq -1 ? ctx : ''}${not empty menu3.href ? menu3.href : '/404'}" onclick="menuClick(this)">${menu3.name}</a>
						</c:if>
						</c:if>
						</c:forEach>
						</ul>
						</c:if>
						<c:if test="${not empty menu2.href}">
						<li><a class="menu2 link" aa="${menu2.id }" id="${menu2.id }" title="${menu2.name}" src="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}" onclick="menuClick(this)">${menu2.name}</a></li>
						</c:if>
						</c:if>
						</c:forEach>
						</ul>
					</div>
				</div>
		</div>
		</c:if>
		</c:forEach>
		</div> 
		<!--右边内容-->
		<div class="rightcont">
			<div class="righttop font12">
				<div class="righttop_left">
					<a class="menu_left"><img src="${ctxStatic}/images/sysindex/righttop_left_menu.png"></a>
					<a class="left_mobile"><img src="${ctxStatic}/images/sysindex/righttop_left_left.png"></a>
				</div>
			<div class="top-tabs" style="position: absolute; left:60px;">
				<div class="righttop_label">
					<p id="indexpage">首页</p>
					<ul>
					</ul>
				</div>
			</div>
				<div class="righttop_right">
					<a class="right_mobile"><img src="${ctxStatic}/images/sysindex/righttop_left_right.png"></a>
					<div class="righttop_right_div">
						<span>关闭操作</span><a class="righttop_right_div_a"></a>
						<div class="righttop_right_div_hidden" style="display:none">
						<a class="close_all">关闭所有</a>
						<a class="close_on">关闭当前</a>
						<a class="close_other">关闭其它</a>
					</div>	
					</div>	
						
				</div>
				<div class="clear"></div>
			</div>
			
			
			<div class="rightcont_iframe">
			</div>
		</div>
		
		<div class="clear"></div>
	</div>	
	<input type="hidden" id="ctxStatic" value="${ctxStatic}" />
	<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"/></script>
		<script src="${ctxStatic}/modules/sysindex/sysIndex.js"></script>
		<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/common/jeesite.js"></script>
	</body>
</html>
