/*
 * 模态框组件公用方法 layer版
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
	
	var content=options.content;//提示内容
	var icon=options.icon;//图标类型 可传入1-3
	var time=options.time;//关闭时间（毫秒）,如果为空则不关闭
	var iconClass;//存储icon css
	var childData='';
	var shade=options.shade;//遮罩透明度
	var fix=options.fix;//即鼠标滚动时，层是否固定在可视区域。如果不想，设置fix: false即可
	var offset=options.top;//弹出层top距离
	var shift=options.shift;//出场动画0-6
	if(icon==3 || icon>6)icon=0;
	
	
	//给默认值
	if(title==null)title='标题';
	if(width==null)width="500";
	if(height==null)height='500';
	if(showBtn==null)showBtn=true;
	if(fix==null)fix=false;
	if(offset==null)offset='0';
	if(shift==null)shift=0;
	if(showBtn==false){
		btn='';
	}else{
		if(btn==null){
			btn=['确定','取消'];
		}
	}
	if(shade==null)shade=0.4;
//	if(data!=null)this.setParentData(data);
	
	var index=options.index;
	layer.index=index;//同步index
	
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
					var html='<div class="container-fluid">'+val+'</div>';
					layer.open({
						  type: 1, //page层
						  area: [width+'px', height+'px'],
						  title: title,
						  shade: shade, //遮罩透明度
						  moveType: 1, //拖拽风格，0是默认，1是传统拖动
						  shift: shift, //0-6的动画形式，-1不开启
						  content: html,
						  showBtn:showBtn,
						  fix: fix,
						  btn:btn,
						  zIndex:index,
						  offset:offset+'px',
						  success: function(layero, index){
							  if(successCallBack!=null && typeof successCallBack =='function')successCallBack();
					      },
					      yes: function(_index, layero){
					    	  $.modal.yes(_index);
					      },
					      cancel: function(_index){
					    	  $.modal.cancel(_index);
					      },
					      btn3: function(_index){
					    	  return false;
					      },
					      btn4: function(_index){
					    	  return false;
					      },
					      btn5: function(_index){
					    	  return false;
					      }
					      
					}); 
					
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
			layer.confirm(content, {icon: icon,shade:shade,title:title}, 
				function(_index){//确定按钮回调
					$.modal.yes(_index);
				},
				function(_index){//取消按钮回调
					$.modal.cancel(_index);
				}
			);
		}catch(e){
			$.modal.showPrompt({content:'JavaScript异常!'+e,time:3000});
		}
	};
	
	var initShowPrompt = function(){
		layer.msg(content, {
			  icon: 0,
			  time: time, //2秒关闭（如果不配置，默认是3秒）
			  shade: shade//遮罩透明度
			});   
			     
	};
	
	if(options.flag=='1'){
		initShowModalDialog();
	}else if(options.flag=='2'){
		initShowConfirm();
	}else if(options.flag=='3'){
		initShowPrompt();
	}
	
	layer.style(index, {
		  top: '0px'
		});
	
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
				parm.flag='3';
				var confirm=modal(parm);
				//var _in = modals.push(confirm);
				//confirm.setIndex(_in-1);
			},cancel:function(_index){//取消回调
				var curDialog = modals[_index-1];
				if(curDialog.cancelCallBack!=null && typeof curDialog.cancelCallBack =='function'){
					curDialog.cancelCallBack(_index,curDialog.getChildData());
				}
				$.modal.close(_index);
			},yes:function(_index){//确定回调
				debugger;
				var curDialog = modals[_index-1];
				if(curDialog.yesCallBack!=null && typeof curDialog.yesCallBack =='function'){
					curDialog.yesCallBack(_index,curDialog.getChildData());
				}
			},getParentData : function(){
				return modals[modals.length-1].getParentData();
			},setChildData : function(_data){
				modals[modals.length-1].setChildData(_data);
			},setBtnFun : function(opt){
				$(opt).each(function(i,e){
					if(e && typeof e === "function"){
						var index=modals.length-1;
						if(i == 0){
							$("#layui-layer"+layer.index+" a.layui-layer-btn" + i).click(function(){
								e();
								//$.modal.yes(layer.index);
							});
						}else if(i==1){
							$("#layui-layer"+layer.index+" a.layui-layer-btn" + i).click(function(){
								e();
								//$.modal.cancel(layer.index);
							});
						}else{
							$("#layui-layer"+layer.index+" a.layui-layer-btn" + i).click(function(){
								e();
							});
						}
					}
				});
			},close:function(index){//关闭模态框并清除
				layer.close(index);
				modals.pop();//删除最后一个元素
			}
			
		};
})(jQuery);

