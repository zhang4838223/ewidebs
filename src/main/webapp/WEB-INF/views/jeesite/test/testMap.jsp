<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>百度地图API</title>
	<meta name="decorator" content="defaultx"/>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" /><!-- 添加一个meta标签，以便使您的页面更好的在移动平台上展示 -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=19679a1617e89c39b75eafe8db2b999f">
	</script>
	<style type="text/css">  
  body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";font-size:14px;}
		#r-result{width:100%;}
    #container{height:100%}    
	</style>
</head>
<body>
	<div id="r-result">请输入:<input type="text" id="suggestId" size="20" value="百度" style="width:150px;" /></div>
	<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
	<div id="container"></div> 
	<script type="text/javascript"> 
	var map = new BMap.Map("container");          // 创建地图实例  
	//var point = new BMap.Point(114.302345,30.588605);  // 创建点坐标  
	add_control();
	map.centerAndZoom("武汉", 14);                 // 初始化地图，设置中心点坐标和地图级别  
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	
	//var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));//自定义图标
	//var marker = new BMap.Marker(point,{icon:myIcon});  // 创建标注
	//var marker = new BMap.Marker(point);  // 创建标注
	//map.addOverlay(marker);               // 将标注添加到地图中
	
	//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	//marker.enableDragging();//标记点可以拖拽
	//marker.disableDragging();// 不可拖拽
	
	var points=[[114.302345,30.588605,"工单数","6"],[114.29329,30.5748,"工单数","5"],[114.317005,30.573681,"工单数","11"],[114.313699,30.600169,"工单数","2"],[114.335115,30.594698,"工单数","0"]];
	for(var i = 0;i<points.length;i++){
		var point=points[i];
		addMarker(point);
	}
	
	//添加控件和比例尺
	function add_control(){
		var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
		var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
		var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
		/*缩放控件type有四种类型:
		BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/
		map.addControl(top_left_control);        
		map.addControl(top_left_navigation);     
		map.addControl(top_right_navigation);    
	}
	
	// 编写自定义函数,创建标注
	function addMarker(point){
		var sContent =
		"<div><img style='float:left;margin:4px' id='imgDemo' src='http://app.baidu.com/map/images/tiananmen.jpg' width='139' height='104' title='天安门'/>" + 
		"<h4 >"+point[2]+":</h4><b>" + point[3]+
		//"<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>天安门坐落在中国北京市中心,故宫的南侧,与天安门广场隔长安街相望,是清朝皇城的大门...</p>" + 
		"</b>个<a href='javascript:void(-1);' onclick='showDialog()'>查看</a></div>";
		var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
		
		var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));//自定义图标
		var marker = new BMap.Marker(new BMap.Point(point[0],point[1]),{icon:myIcon});  // 创建标注
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		
		marker.addEventListener("click", function(){          
			   this.openInfoWindow(infoWindow);
			   //图片加载完毕重绘infowindow
			   document.getElementById('imgDemo').onload = function (){
				   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
			   }
		});
		map.addOverlay(marker); // 将标注添加到地图中
	}
	
	function showDialog(){
		$.modal.showModalDialog({
			title:'工单列表',
			//width:'500',
			//height:'1500',
			showBtn:false,
			btn:['确定', '取消', '添加' ],
			url:system.getContextPath()+'/test/map/modal',
			success: function(){
				//alert('弹出回调');
			},error: function(){
				//alert('错误回调');
			},yes: function(index,childData){
				alert('确定回调Index:'+index);
				$.modal.close(index);
			},cancel: function(index,childData){
				alert('取消回调Index:'+index+' childData:'+childData);
			}
		});
	}
	
	
		var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
			{"input" : "suggestId"
			,"location" : map
		});

		ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
		var str = "";
			var _value = e.fromitem.value;
			var value = "";
			if (e.fromitem.index > -1) {
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
			
			value = "";
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
			G("searchResultPanel").innerHTML = str;
		});

		var myValue;
		ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
		var _value = e.item.value;
			myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
			
			setPlace();
		});

		function setPlace(){
			//map.clearOverlays();    //清除地图上所有覆盖物
			function myFun(){
				var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
				map.centerAndZoom(pp, 18);
				map.addOverlay(new BMap.Marker(pp));    //添加标注
			}
			var local = new BMap.LocalSearch(map, { //智能搜索
			  onSearchComplete: myFun
			});
			local.search(myValue);
		}
		
		function G(id) {		
			return document.getElementById(id);
		}
	
	</script>  
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
</body>
</html>