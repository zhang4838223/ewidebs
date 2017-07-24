$(document).ready(function() {
	//获取父页面传值
	initData();	
	//初始化下拉框
	initSelect();
	//初始化表单验证
	bootstrapValidator();
	
	//模态框按钮事件
	$.modal.setBtnFun([ function() {
	//提交表单
	save();
	}, function() {

	}]);
});
/**
 * 获取父页面传值
 */
function initData(){
var data=$.modal.getParentData();
if(data!=null && data.length==3){
	$("#parentId").val(data[0]);
	$("#areaId").val(data[0]);
	$("#parentName").val(data[1]);
	$("#parentIds").val(data[2]);	
}else if(data!=null && data.length==2){
	$("#areaId").val(data[0]);	
	$("#parentIds").val(data[1]);	
}
}
/**
 * 初始化下拉框
 */
function initSelect(){
$("#type").bindDict({
	dictTypeName:"sys_area_type"
});
}
/**
 * 提交表单
 */
function save(){
	//判断是否存在根节点
	var count=$("#count").val();
	//表单验证
	$('#areaForm').data('bootstrapValidator').validate();
	if(!$('#areaForm').data('bootstrapValidator').isValid())
	{
	$.modal.setChildData(false);
	return;// 直接return 不做后续任何操作
	}
	//判断是否选择上级区域
	var parentId=$("#parentId").val();
	if(parentId == '' && count>0){
		$('#areaForm').data('bootstrapValidator').updateStatus('parent.name', 'INVALID',null);
		$.modal.setChildData(false);
	}else{
		var id=$("#areaId").val();
		var pIds=$("#parentIds").val();
		//新增、修改
		$.ajax({
			type : "POST",
			url : system.getContextPath() + "/sys/area/save",
			processData : true,
			async: true,
			data : $("#areaForm").serialize(),//获取表单所有值
			success : function() {
				//操作完后的提示
				$.modal.showPrompt({
					title : '提示',
					content : "保存成功!",
					time : 1500
				});
				//刷新树
				refreshTree();
			}
		});
		$.modal.setChildData(true);
	}
}
/**
 * 初始化表单验证
 */
function bootstrapValidator(){
	$('#areaForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '区域名称不能为空！'// 这是提示信息
							},
							stringLength: {
					             min: 1,
								 max: 10,
					             message: '区域名称长度必须在1到10位之间'
								           },
								callback : {
									message : '区域名称已存在！',
									callback : function(value, validator) {
										return true;
									}
								}
						}
					},
					//上级区域可以为空
					'parent.name' : {// 这里为输入框的name
						validators : {
							callback : {
								message : '请选择上级区域！',
								callback : function(value, validator) {
									var count=$("#count").val();
									var noParentId=$("#noParentId").val();
									var flag = true;
									if(count==0  && value==''){
										flag = true;
									}else if(count!=0  && value!=''){
										flag = true;
									}else if(count!=0 && noParentId=='0'){
										flag = true;
									}else if(count!=0 && typeof(noParentId) == "undefined"&&value!=''){
										flag = true;
									}else{
										flag = false;
									}
									return flag;
								}
							}
						}
					},
					code : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '区域编码不能为空！'// 这是提示信息
							},regexp: {
					             regexp: /^[0-9]*$/,
					             message: '区域编码只能输入数字！'
					           },stringLength: {
					        	   min: 1,
					        	   max: 10,
					               message: '区域编码长度必须在1到10位之间！'
					             },callback : {
										message : '区域编码已存在！',
										callback : function(value, validator) {
											return true;
										}
									}
						}
					},
					remarks : {// 这里为输入框的name
						validators : {
							stringLength : {
								min : 0,
								max : 1000,
								message : '备注必须在1000位之内'
							}
						}
					},
					type : {// 这里为输入框的name
						validators : {
							callback : {
								message : '请选择区域类型！',
								callback : function(value, validator) {
									return (value != null && value != '');
								}
							}
						}
					},
					sort : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '排序不能为空！'// 这是提示信息
							},
							regexp: {
					             regexp: /^[0-9]*$/,
					             message: '排序只能输入数字！'
					           },stringLength: {
					               min: 1,
					               max: 9,
					               message: '排序长度必须在1到9位之间！'
					             }
						}
					}
				}
		    });
	
	//查询区域名是否已存在
	$("#name").blur(function(){
		var name=$("#name").val();
		//判断name是否存在
		var noName=$("#noName").val();
		if(name!=noName){
		$.ajax({
			url: system.getContextPath() + "/sys/area/findNameOrCode",
			type: 'GET',
			async: false,
			data:{'name':name,'noName':noName},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#areaForm').data('bootstrapValidator').updateStatus('name', 'VALID','callback');
				}else if('false'==data){
					$('#areaForm').data('bootstrapValidator').updateStatus('name', 'INVALID','callback');
				}
			}
		});
		}
	  });
	//查询区域名是否已存在
	$("#code").blur(function(){
		var code=$("#code").val();
		//判断name是否存在
		var noCode=$("#noCode").val();
		if(code!=noCode){
		$.ajax({
			url: system.getContextPath() + "/sys/area/findNameOrCode",
			type: 'GET',
			async: false,
			data:{'code':code,'noCode':noCode},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#areaForm').data('bootstrapValidator').updateStatus('code', 'VALID','callback');
				}else if('false'==data){
					$('#areaForm').data('bootstrapValidator').updateStatus('code', 'INVALID','callback');
				}
			}
		});
		}
	  });

	
//表单验证提示位置
	var prompt = $(".form-group .help-block")
	$("<div>").addClass("tooltip-arrow").appendTo(prompt.parent());
	prompt.css({
		"right" : -15,
		"top" : -27
	});
	$(".tooltip-arrow").show().css({
		"right" : 13,
		"top" : 0
	});
}
/**
 * 上级区域回调函数
 * @param v
 * @param nodes
 */
function parentTreeselectCallBack(v,nodes){
	//根据是否有根节点、按钮来判断上级区域的提示 
	var count=$("#count").val();
	if (v=="ok"){
		var noId=$("#noId").val();
		if(nodes[0].id!=null && nodes[0].id==noId){
			top.$.jBox.tip("不能选择（"+nodes[0].name+"）请重新选择。");
			$("#parentId").val('');
			$("#parentName").val('');
			return false;
		}
		if(nodes.length!=null && nodes.length == 0 && count>0){
			$('#areaForm').data('bootstrapValidator').updateStatus('parent.name', 'INVALID',null);
		}else{
			$('#areaForm').data('bootstrapValidator').updateStatus('parent.name', 'VALID',null);
		}
		
	}else if (v=="clear"){
		if(count>0){
			$('#areaForm').data('bootstrapValidator').updateStatus('parent.name', 'INVALID',null);
		}else{
			$('#areaForm').data('bootstrapValidator').updateStatus('parent.name', 'VALID',null);
		}
    }
}