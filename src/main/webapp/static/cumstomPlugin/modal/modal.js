/*
 * 模态框组件公用方法
 * @author TianChong
 * @version 2016-4-27
 */


var modal = function(options){
	var that = {};
	var title=options.title;//模态框标题
	var url=options.url;//jsp路径/文件名
	var width=options.width;//模态框宽度
	var height=options.height;//模块框高度
	var successCallBack=options.success;//弹出成功成功回调函数
	var errorCallBack=options.error;//弹出失败回调函数
	var data=options.data;//可传值给子页面
	var showBtn=options.showBtn;//是否显示按钮 true,false
	var btn=options.btn;//多按钮
	var oneCol=options.oneCol;//是否是一列true，false
	
	var content=options.content;//提示内容
	var icon=options.icon;//图标类型 可传入1-3
	var time=options.time;//关闭时间（毫秒）,如果为空则不关闭
	var iconClass;//存储icon css
	var childData='';
	
	if(icon==1){
		iconClass='glyphicon glyphicon-ok';//OK
	}else if(icon==2){
		iconClass='glyphicon glyphicon-remove';//REMOVE
	}else if(icon==3){
		iconClass='glyphicon glyphicon-warning-sign';//WARNING
	}else{
		iconClass='glyphicon glyphicon-info-sign';
	}
	
	//给默认值
	if(title==null)title='标题';
	if(width==null)width="auto";
	if(height==null)height='auto';
	if(showBtn==null)showBtn=true;
	if(oneCol==null)oneCol=true;
	
//	if(data!=null)this.setParentData(data);
	
	var index=options.index;
	
	//显示模态框
	that.show = function(){
		//cancelCallBack();
		$('#modalTable'+index).modal("show");
	};
	
	
	that.setIndex = function(_index){
		index = _index;
	};
	that.getIndex = function(){
		return index;
	};
	
	that.getParentData = function(){
		return options.data;
	}
	
	that.getChildData = function(){
		return childData;
	}
	
	that.setChildData = function(_data){
		childData=_data;
	}
	
	if(typeof(options.yes) != 'undefined'){
		that.yesCallBack = function(_index,childData){
			return options.yes(_index,childData);
		};
	}
	
	if(typeof(options.cancel) != 'undefined'){
		that.cancelCallBack = function(_index,childData){
			return options.cancel(_index,childData);
		};
	}
	
	var initShowModalDialog = function(){
		try{
			$.ajax({
				type: "POST",
				async: "false",
				url: url,
				success: function(val){
					var modalStart='<div class="modal fade" style="overflow:scroll" tabindex="-1" id="modalTable'+index+'" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">';
					var modalHeader='<div class="modal-dialog"> <div class="modal-content" style="width:'+width+(width=='auto'?'':'px')+';"> <div class="modal-header" style="width:'+width+(width=='auto'?'':'px')+';"><button type="button" class="close input-sm" data-dismiss="modal" aria-label="Close" onclick="$.modal.cancel('+index+')"><span aria-hidden="true">&times;</span></button><h4 class="modal-title"><span >'+title+'</span></h4></div>';
					var modalBody='<div class="modal-body"  style="height:'+height+(height=='auto'?'':'px')+';"><div class="container-fluid">'+val+'</div></div>';
					var modalFooter='<div class="modal-footer" style="width:'+width+(width=='auto'?'':'px')+';"><div class="container-fluid"><div class="row">';
					if(showBtn){
						if(btn!=null && btn!=''){
							if(btn!=null && btn!='' && btn.length>1){
								//this.clickCallBack(3,parm);
								for(var i=0;i<btn.length;i++){
									modalFooter+='<button type="button" class="btn btn-'+((i==0)?'primary':'default')+' input-sm" id="btn'+index+'_'+i+'">'+btn[i]+'</button>';
								}
							}else{
								modalFooter+='<button type="button" class="btn btn-primary input-sm" onclick="$.modal.yes('+index+')" id="btn'+index+'_0">'+btn[0]+'</button>';
							}
						}else{
							modalFooter+='<button type="button" class="btn btn-primary input-sm" onclick="$.modal.yes('+index+')" id="btn'+index+'_0">确定</button><button type="button" class="btn btn-default input-sm" onclick="$.modal.cancel('+index+')" id="btn'+index+'_1">取消</button>';
						}
						//modalFooter+='</div>';
					} 
					var modalEnd='</div></div></div>';
					$(document.body).append(modalStart+modalHeader+modalBody+modalFooter+modalEnd);
					$('#modalTable'+index).modal("show");
					$('#modalTable'+index).modal({
						  keyboard: true //键盘上的 esc 键被按下时关闭模态框
					});
					$('#myModal').modal('handleUpdate');
					//$('body').scrollspy({ target: '#navbar-example' })
					//$('.modal-body').css('height',$('#modalTable'+index).height()-190);
//					$('#modalTable').data('bs.modal').handleUpdate();
					
					if(successCallBack!=null && typeof successCallBack =='function')successCallBack();
				},
				error: function(e){
					$.modal.showPrompt({content:'服务器异常!',time:3000});
					if(errorCallBack!=null && typeof errorCallBack =='function'){
						errorCallBack(e);
					}
				}
			});
		}catch(e){
			$.modal.showPrompt({content:'JavaScript异常!'+e,time:3000});
			if(errorCallBack!=null && typeof errorCallBack =='function'){
				errorCallBack(e);
			}
		}
		
	};
	
	var initShowConfirm = function(){
		try{
			var h='<div id="modalTable'+index+'" class="modal fade prompt-box" role="dialog" aria-labelledby="mySmallModalLabel" data-backdrop="static"><div class="modal-dialog" style="width:300px;margin:100px auto"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$.modal.cancel('+index+')"><span aria-hidden="true">&times;</span></button><h6 class="modal-title">'+title+'</h6></div><div class="modal-body"><i class="'+iconClass+'"></i><span class="warning-text" style="font-size:18px">'+content+'</span></div>';
			if(showBtn){
				h+='<div class="modal-footer"><button type="button" class="btn btn-primary input-sm" onclick="$.modal.yes('+index+')">确定</button><button type="button" class="btn btn-default input-sm" onclick="$.modal.cancel('+index+')">取消</button></div>';
			}
			$(document.body).append(h);
			$('#modalTable'+index).modal("show");
			if(time!=null && time!=0){
				setTimeout(function(){$.modal.close('#modalTable'+index)},time); 
			}
		}catch(e){
			$.modal.showPrompt({content:'JavaScript异常!'+e,time:3000});
		}
	}
	if(options.flag=='1'){
		initShowModalDialog();
	}else if(options.flag=='2'){
		initShowConfirm();
	}
	return that;
};



(function($){
	//模态框存放栈
	var modals = [];
	$.modal = { 
			showModalDialog:function(parm){//模态框组件
				parm.index=modals.length;
				parm.flag='1';
				var modalDialog = modal(parm);
				var _in = modals.push(modalDialog);
				modalDialog.setIndex(_in-1);
			},showConfirm:function(parm){//确认框
				parm.index=modals.length;
				parm.flag='2';
				var confirm=modal(parm);
				var _in = modals.push(confirm);
				confirm.setIndex(_in-1);
			},showPrompt:function(parm){//消息框
				$.modal.showConfirm({title:parm.title,content:parm.content,time:parm.time,showBtn:false});
			},cancel:function(_index){//取消回调
				var curDialog = modals[_index];
				if(curDialog.cancelCallBack!=null && typeof curDialog.cancelCallBack =='function'){
					curDialog.cancelCallBack('#modalTable'+_index,curDialog.getChildData());
				}
				$.modal.close('#modalTable'+_index);
			},yes:function(_index){//确定回调
				var curDialog = modals[_index];
				if(curDialog.yesCallBack!=null && typeof curDialog.yesCallBack =='function'){
					curDialog.yesCallBack('#modalTable'+_index,curDialog.getChildData());
				}
			},getParentData : function(){
				return modals[modals.length-1].getParentData();
			},setChildData : function(_data){
				modals[modals.length-1].setChildData(_data);
			},setBtnFun : function(opt){
				$(opt).each(function(i,e){
					if(e && typeof e === "function"){
						var index=modals.length-1;
						$("#btn"+index+"_" + i).removeAttr("onclick");
						if(i == 0){
							$("#btn"+index+"_" + i).click(function(){
								e();
								var id=this.getAttribute("id");
								$.modal.yes(id.substring(3,id.indexOf('_')));
							});
						}else if(i==1){
							$("#btn"+index+"_" + i).click(function(){
								e();
								var id=this.getAttribute("id");
								$.modal.cancel(id.substring(3,id.indexOf('_')));
							});
						}else{
							$("#btn"+index+"_" + i).click(function(){
								e();
							});
						}
					}
				});
			},close:function(index){//关闭模态框并清除
				$(index).modal("hide");
				$('.modal-backdrop.fade.in:last').remove();
				$(index).remove();
				modals.pop();//删除最后一个元素
			}
			
		};
})(jQuery);

