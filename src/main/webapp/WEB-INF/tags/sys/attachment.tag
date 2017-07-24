<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="文件名 隐藏域id属性"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="files、images、可以自定义类型如：.jpg,.doc,.docx 中间用逗号分隔"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="文件名 隐藏域name属性"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" description="最大宽度"%>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" description="最大高度"%>
<%@ attribute name="autoUpload" type="java.lang.Boolean" required="false" description="是否自动上传"%>
<%@ attribute name="value" type="java.lang.String" required="false" description="文件默认ID，可多个逗号分割，文件默认加载"%>
<%@ attribute name="fileSizeLimit" type="java.lang.Integer" required="false" description="总文件大小"%>
<%@ attribute name="fileSingleSizeLimit" type="java.lang.Integer" required="false" description="单个文件大小"%>
<%@ attribute name="fileNumLimit" type="java.lang.Integer" required="false" description="文件个数"%>
<div id="${id}Preview"></div>
<div id="uploader" class="wu-example">
    <!--用来存放文件信息-->
    <div id="thelist" class="uploader-list"></div>
    <c:if test="${readonly==false}">
    <div class="btns">
        <div id="picker" style="float:left;">选择文件</div>
        <c:if test="${autoUpload == false}"><button id="ctlBtn" class="btn btn-default" style="background:#00b7ee;width:86px;height:40px;color:white;">开始上传</button></c:if>
    </div>
    </c:if>
    <input type="hidden" id="${id}" name="${name}">
</div>
<script>
var uploader,fileList={},state = 'pending';
jQuery(function() {
	//var $ = jQuery, 
	//var $list = $('#thelist'),
	//$btn = $('#ctlBtn'),
    
	//初始化upload配置
	uploader = WebUploader.create({
	    // 不压缩image
	    resize: false,
	 	  // 选完文件后，是否自动上传。
	    auto: ${autoUpload==true?true:false},
	    // swf文件路径
	    //swf: BASE_URL + '/js/Uploader.swf',
	    // 文件接收服务端。
	    server: '${ctx}/sys/fileUpload/upload',
	    fileSizeLimit:${fileSizeLimit},//文件总大小
	    fileSingleSizeLimit :${fileSingleSizeLimit},//单个文件大小
	    fileNumLimit :${fileNumLimit},//文件数量
	    <c:choose>
	    <c:when test="${type == 'images'}">
	 	// 只允许选择图片文件。
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/*'
		},
	    </c:when>
	    <c:when test="${type == 'files'}">
		accept: {
			title: 'Files',
			extensions: 'doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,gif,jpg,jpeg,bmp,png',
			mimeTypes: 'file/.doc,.docx,.jpg'
			extensions: 'doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,gif,jpg,jpeg,bmp,png',
			mimeTypes: 'file/*'
		},
	    </c:when>
	    <c:otherwise>
	    //自定义类型
		accept: {
			title: 'CustomFiles',
			extensions: '${fn:replace(type,".","")}',
			mimeTypes: '${type}'
		},
	    </c:otherwise>
	    </c:choose>
	    
		<c:if test="${selectMultiple==false}">
		//multiple:false,//是否开起同时选择多个文件能力。
		</c:if>
		<c:if test="${selectMultiple==true}">
		//multiple:true,//是否开起同时选择多个文件能力。
		</c:if>
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#picker'
	});
  
	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
		//if($list.children().length>0){
			//file.id='WU_FILE_'+$list.children().length;
		//}
	    <c:choose>
	    <c:when test="${type == 'images'}">
	 //图片预览      
	    var $li = $(
			'<div id="' + file.id + '">' +
			'<img style=\"max-width:${empty maxWidth ? 200 : maxWidth}px;max-height:${empty maxHeight ? 200 : maxHeight}px;height:${empty maxHeight ? 200 : maxHeight}px;border:0;padding:3px;\">' +
			<c:if test="${!readonly}">'<a id="cleanBtn" href="javascript:" onclick="removeFile(\''+file.id+'\')">X</a>'+</c:if>
			    //'<div class="info">' + file.name + '</div>' +
			'</div>'
	    ),
	    $img = $li.find('img');
	    // $list为容器jQuery实例
	    <c:if test="${selectMultiple==false}">
	    $('#thelist').html($li);
	    </c:if>
	    <c:if test="${selectMultiple==true}">
	    $('#thelist').append( $li );
	    </c:if>
	    //$("#${id}Preview").html($list);
	    
	    
	     // 优化retina, 在retina下这个值是2
		var ratio = window.devicePixelRatio || 2,
		// 缩略图大小
		thumbnailWidth = 100 * ratio,
		thumbnailHeight = 100 * ratio;
	
	    // 创建缩略图
	    // 如果为非图片文件，可以不用调用此方法。
	    // thumbnailWidth x thumbnailHeight 为 100 x 100
	    uploader.makeThumb( file, function( error, src ) {
			if ( error ) {
				$img.replaceWith('<div id="' + file.id + '" class="item">' +
				'<h4 class="info">' + file.name + '</h4>' +
				'<p class="state">等待上传...</p>' +
				'</div>');
				//$img.replaceWith('<span>不能预览</span>');
				return;
			}
	        $img.attr( 'src', src );
	    }, thumbnailWidth, thumbnailHeight ); 
	    </c:when>
	    <c:otherwise>
	    //文件列表
	    var html='<div id="' + file.id + '" class="item">' +
		'<h4 class="info">' + file.name + '   <a href="javascript:" onclick="removeFile(\''+file.id+'\')">×</a>' +'</h4>' +
		'<p class="state">等待上传...</p>' +
		'</div>';
	    <c:if test="${selectMultiple==true}">
	    $('#thelist').append(html);
	    </c:if>
	 	<c:if test="${selectMultiple==false}">
	 	$('#thelist').html(html);
	 	</c:if>
	    </c:otherwise>
	    </c:choose>
	    $("#${id}Preview").children().remove();
	});

	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),$percent = $li.find('.progress .progress-bar');
	    // 避免重复创建
	    if ( !$percent.length ) {
			$percent = $('<div class="progress progress-striped active">' +
			'<div class="progress-bar" role="progressbar" style="width: 0%">' +
			'</div>' +
			'</div>').appendTo( $li ).find('.progress-bar');
	    }
	    $li.find('p.state').text('上传中');
	    $percent.css( 'width', percentage * 100 + '%' );
	});
	
	//文件上传成功
	uploader.on( 'uploadSuccess', function( file,response ) {
		fileList[file.id]=response._raw;
		$( '#'+file.id ).find('p.state').text('已上传');
		var filePath=$('#${id}').val();
		<c:if test="${selectMultiple==true}">
		if(filePath!=null && filePath!=''){
		 filePath+=','+response._raw;
		}else{
		 filePath=response._raw;
		}
		 //zip,rar,gz,bz2 不支持预览
	    var previewHtml='';
	    if(file.name.lastIndexOf('.')>-1){
	    	var ext=file.name.substring(file.name.lastIndexOf('.')+1);
	    	if(ext!='zip' && ext!='rar' && ext!='gz' && ext!='bz2'){
	    		previewHtml='<a href="javascript:" onclick="previewFile(\''+response._raw+'\')">预览</a>';
	    	}
	    }
		$('#'+file.id).find('h4.info').append('  <a href="javascript:" onclick="downLoadFile(\''+response._raw+'\')">下载</a>  '+previewHtml);
		</c:if>
		<c:if test="${selectMultiple==false}">
		 if(filePath!=''){
		  ajaxDeleteFile(filePath); //删除数据中file记录
		 }
			  filePath=response._raw;
		</c:if>
		$('#${id}').val(filePath);
	});
	
	//上传失败
	uploader.on( 'uploadError', function( file ) {
		$( '#'+file.id ).find('p.state').text('上传出错');
	});

	uploader.on( 'uploadComplete', function( file ) {
		$( '#'+file.id ).find('.progress').fadeOut();
	});

	uploader.on( 'all', function( type ) {
		if ( type === 'startUpload' ) {
		    state = 'uploading';
		} else if ( type === 'stopUpload' ) {
		    state = 'paused';
		} else if ( type === 'uploadFinished' ) {
		    state = 'done';
		} else if ( type === 'uploadError'){
			state = 'error';
		}else if ( type === 'error') {//文件类型错误，文件总数超出，单个文件大小超出，总文件大小超出
			alert('文件类型错误或文件大小超出');
		}
		if ( state === 'uploading' ) {
		    $('#ctlBtn').text('暂停上传');
		}  else if( state === 'error'){
			$('#ctlBtn').text('重新上传');
		}else {
		    $('#ctlBtn').text('开始上传');
		    $('#ctlBtn').text('暂停上传');
		} else {
		    $('#ctlBtn').text('开始上传');
		}
	});

	$('#ctlBtn').on( 'click', function() {
		if ( state === 'uploading' ) {
		    uploader.stop(true);
		} else if ( state === 'error' || $('#ctlBtn').text()=='重新上传'){
			uploader.retry();
		} else {
		    uploader.upload();
		}
		return false;
	});
	
	${id}Preview();
});
	
	//ajax删除数据库中文件记录
	function ajaxDeleteFile(id){
		if(typeof(id) == "undefined")return;
		$.ajax({
			url : '${ctx}/sys/fileUpload/deleteFile?id='+id,
			async : false,
			success : function(data) {
				if(data!=null && data=='true'){
				}
			},
			error : function(){
			}
		});
	}

  
	// 负责view的销毁
	function removeFile(id,flag) {
		if(typeof(flag) == "undefined")flag=true;
		ajaxDeleteFile(fileList[id]);
		var $li = $('#'+id);
	    $li.off().find('.file-panel').off().end().remove(); 
	    if(flag)uploader.removeFile(id,true);
	    <c:if test="${selectMultiple==false}">uploader.reset();//重置队列</c:if>
		if($('#thelist:has(div)').length==0){
	    	 $("#${id}Preview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
	    	 $('#${id}').val('');
	    }else{
	    	var fileNames=$('#${id}').val();
	    	if(fileNames.indexOf(','+fileList[id])>-1){
	    		fileNames=fileNames.replace(','+fileList[id],'');
	    	}else{
	    		fileNames=fileNames.replace(fileList[id]+',','');
	    	}
	    	$('#${id}').val(fileNames);
	    }
		
	}
	
	//根据类型展示对应上传控件
	function ${id}Preview(){
		<c:choose>
	    <c:when test="${value!=null && value!=''}">
		    $.ajax({
				url : '${ctx}/sys/fileUpload/getFileInfo?id=${value}',
				async : false,
				success : function(data) {
					var file=jQuery.parseJSON(data);
					if(data!=null && data!='false'){
						<c:if test="${type=='images'}">
					      $.each(file, function(i, field){
					    	  fileList['WU_FILE_'+(0-(i+1))]=field.id;
					    	  //图片预览      
						      var $li = $(
						              '<div id="WU_FILE_-1">' +
						                  '<img src="'+field.path+'" style=\"max-width:${empty maxWidth ? 200 : maxWidth}px;max-height:${empty maxHeight ? 200 : maxHeight}px;height:${empty maxHeight ? 200 : maxHeight}px;border:0;padding:3px;\">' +
						                  <c:if test="${!readonly}">'<a id="cleanBtn" href="javascript:" onclick="removeFile(\'WU_FILE_-1\',false)">X</a>'+</c:if>
						              '</div>'
						              ),
						      $img = $li.find('img');
					    	
						      <c:if test="${selectMultiple==false}">
						      $('#thelist').html($li);
						      $('#${id}').val(field.id);
						      </c:if>
						      <c:if test="${selectMultiple==true}">
					    	  $('#thelist').append($li);
						      var filePath=$('#${id}').val();
							  if(filePath!=null && filePath!=''){
								 filePath+=','+field.id;
							  }else{
								 filePath=field.id;
							  }
							  
						      $('#${id}').val(filePath);
						      </c:if>
						      
					      });
						</c:if>
						<c:if test="${type=='files'}">
						  $.each(file, function(i, field){
							    fileList['WU_FILE_'+(0-(i+1))]=field.id;
							    //zip,rar,gz,bz2 不支持预览
							    var previewHtml='';
							    if(field.name.lastIndexOf('.')>-1){
							    	var ext=field.name.substring(field.name.lastIndexOf('.')+1);
							    	if(ext!='zip' && ext!='rar' && ext!='gz' && ext!='bz2'){
							    		previewHtml='<a href="javascript:" onclick="previewFile(\''+field.id+'\')">预览</a>';
							    	}
							    }
								var html='<div id="WU_FILE_' + (0-(i+1)) + '" class="item">' +
							      '<h4 class="info">' + field.name + '   <a href="javascript:" onclick="removeFile(\'WU_FILE_'+(0-(i+1))+'\',false)">×</a>' +'  <a href="javascript:" onclick="downLoadFile(\''+field.id+'\')">下载</a>  '+previewHtml+'</h4>' +
							      '<p class="state">已上传</p>' +
							  	  '</div>';
								<c:if test="${selectMultiple==true}">
						      	$('#thelist').append(html);
						      	var filePath=$('#${id}').val();
						        if(filePath!=null && filePath!=''){
						      	  filePath+=','+field.id;
						        }else{
						      	  filePath=field.id;
						        }
						        $('#${id}').val(filePath);
						      	</c:if>
							  	<c:if test="${selectMultiple==false}">
							  	$('#thelist').html(html);
							  	$('#${id}').val(file.id);
							  	</c:if>
						  });
						</c:if>
					}else{
						$('#${id}').val('${value}');
						$("#${id}Preview").html("<li id='wu' style='list-style:none;padding-top:5px;'>该文件不存在</li>");
					}
				},
				error : function(){
				}
			});
	    </c:when>
	    <c:otherwise>
	    	$("#${id}Preview").html("<li id='wu' style='list-style:none;padding-top:5px;'>无</li>");
	    </c:otherwise>
	    </c:choose>
	}	
	
	//文件下载	
	function downLoadFile(id){
		window.open("${ctx}/sys/fileUpload/downLoadFile?id="+id);
	}
	
	function previewFile(id){
		window.open("${ctx}/sys/fileUpload/previewFile?id="+id);
	}
</script>