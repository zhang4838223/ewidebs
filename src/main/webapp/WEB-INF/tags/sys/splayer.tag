<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="src" type="java.lang.String" required="true" description="文件路径"%>
<%@ attribute name="hidden" type="java.lang.Boolean" required="true" description="是否显示"%>
<%@ attribute name="autostart" type="java.lang.Boolean" required="true" description="初始化是否播放"%>
<%@ attribute name="width" type="java.lang.String" required="false" description="宽度"%>
<%-- <%@ attribute name="defaultMuted" type="java.lang.String" required="false" description="是否静音"%> --%>
<div id="${id}player"></div>
<div id="audio" style="display: none">
	<a href="javascript:void(0);" onclick="getCurrentTime();">获取播放时间</a>
	<a href="javascript:void(0);" onclick="playOrPaused(this);">播放</a>
	<a href="javascript:void(0);" onclick="getDuration();">获取总时间</a>
	<a href="javascript:void(0);" onclick="getFast();">快进五秒</a>
	<a href="javascript:void(0);" onclick="getRewind();">快退五秒</a>
	<a href="javascript:void(0);" onclick="hiddenY(this);">隐藏控制框</a>
	<a href="javascript:void(0);" onclick="muted(this);">开启静音</a>
	<input type="button" value="+" id="upVol" onclick="vol('up' , this )"/>音量<input type="button" value="-" onclick="vol('down' ,this )"/>
</div>
<div id="embed" style="display: none">
	<input type="button" value="获取播放时间" onclick="getTime(this);"/>
	<input type="button" value="播放" id="b5" onclick="play(this);"/>
	<input type="button" value="快进5秒" id="b7" onclick="getFast1(this);"/>
	<input type="button" value="快退5秒" id="b8" onclick="getRewind1(this);"/>
	<input type="button" value="获取文件时间" id="b9" onclick="getDuration(this);"/>
	<a href="javascript:void(0);" onclick="hideOrShowControls1(this);">隐藏控制框</a>
	<input type="button" value="开启静音" id="b10" onclick="muted1(this);"/>
	<input type="button" value="+" id="upVol" onclick="vol1(this )"/>音量<input type="button" value="-" onclick="vol2(this )"/>
</div>

<script src="${ctxStatic}/basecss/js/jquery.min.js"></script>	
<script type="text/javascript">
//判断浏览器类型
if ((!!window.ActiveXObject || "ActiveXObject" in window) && (navigator.userAgent.indexOf('Opera') < 0)){
	$("#${id}player").html("<embed id='${id}Id' src='${src}' <c:if test='${hidden==false}'>hidden='no'</c:if>  autostart='${autostart==null?false:autostart}'  height='40' width='${width==null?'200':width}' loop=false />");
	$("#embed").show();
}else{
	$("#${id}player").html("<audio id='${id}Id' src='${src}' onplay='' defaultMuted height='40' <c:if test='${autostart==true}'>autoplay</c:if> width='${width==null?'200':width}'  <c:if test='${hidden==true}'>controls</c:if>></audio>");
	$("#audio").show();
}			
			//获取播放器元素
			var audio ;
			window.onload = function(){
				initAudio();
			}
			var initAudio = function(){
				audio = document.getElementById('${id}Id');
			}
/*-----------------------------audio-----------------------------------  */
			//获取播放时间			
			function getCurrentTime(){			
				alert(parseInt(audio.currentTime) + '：秒');
				
			}
			//audio获取总时间		
			function getDuration(){			
				alert(parseInt(audio.duration) + '：秒');
			}
			//快进
			function getFast(){			
				audio.currentTime+=5;
			}
			//快退
			function getRewind(){			
				audio.currentTime-=5;
			}
			//播放暂停
			function playOrPaused(obj){
				if(audio.paused){
					audio.play();
					obj.innerHTML='暂停';
					return;
				}
				audio.pause();
				obj.innerHTML='播放';
			}
			//隐藏控制器
			function hiddenY(obj){
				if(audio.controls){
					audio.removeAttribute('controls');
					obj.innerHTML = '显示控制框'
					return;
				}
				audio.controls = 'controls';
				obj.innerHTML = '隐藏控制框'
				return;
			}
			//静音
			function muted(obj){
				if(audio.muted){
					audio.muted = false;
					obj.innerHTML = '开启静音';
				}else{
					audio.muted = true; 
					obj.innerHTML = '关闭静音';
				}
			}
			//设置音量
			function vol(type , obj){
				if(type == 'up'){
					var volume = audio.volume  + 0.1;
					if(volume >=1 ){
						volume = 1 ;
					
					}
					audio.volume =  volume;
				}else if(type == 'down'){
					var volume = audio.volume  - 0.1;
					if(volume <=0 ){
						volume = 0 ;
					}
					audio.volume =  volume;
				}
			}
			
/*-----------------------------embed-----------------------------------  */
			//获取播放时间
			function getTime(){
				alert(Math.ceil(audio.currentPosition));
			}
			//播放暂停
			function play(obj){
			if(obj.value=='播放'){
				audio.play();
				obj.value='暂停';
				return;
			}
			audio.pause();
			obj.value='播放';
			}
			//快进
			function getFast1(){
			audio.currentPosition+=5;
				
			}
			//快退
			function getRewind1(){
			audio.currentPosition-=5;
			}
			//获取总时间
			function getDuration(){
			alert(parseInt(audio.duration) );
			
			}
			//音量+
			function vol1(obj){
			if(audio.volume>=-10000 && audio.volume<-3000){
			audio.volume=-3000;
			}else if(audio.volume>-100){
			audio.volume=0;
			}else{
			audio.volume-=audio.volume*0.2;
			}
			}
			//音量-
			function vol2(obj){
			if(audio.volume>=-10000 && audio.volume<-3000){
			audio.volume=-10000;
			}else if(audio.volume>-100){
			audio.volume=-100;
			}else{
			audio.volume+=audio.volume*0.2;
			
			}
			}
			//静音
			function muted1(obj){
				if(obj.value == '开启静音'){
					audio.mute = true;
					obj.value = '关闭静音';
				}else{
					audio.mute = false;
					obj.value = '开启静音';
				}
					
												
			}
			//隐藏控制器
			function hideOrShowControls1(obj){
					audio.hidden = true;
			}
</script>