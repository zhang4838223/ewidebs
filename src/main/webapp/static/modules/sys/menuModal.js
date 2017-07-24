$(document).ready(function() {
	//初始化父页面传的数据
	initData();
	//初始化下拉框
	initSelect();
	
	//初始化表单验证
	bootstrapValidator();
	$.modal.setBtnFun([ function() {
		//点击第一个按钮事件
		save();
	}, function() {

	}]);
});
/**
 * 初始化下拉框
 */
function initSelect(){
//初始化目标下拉框
$("#target").bindDict({
	select2Option : {
		placeholder : '请选择目标'
	},
	dictTypeName:"SYS_MNEU_TARGET"
});

}
/**
 * 初始化父页面传的数据
 */
function initData(){
	var data=$.modal.getParentData();
	if(data!=null && data.length==4){
		$("#parentId").val(data[0]);
		$("#menuId").val(data[0]);
		$("#parentName").val(data[1]);		
		$("#subsystemCode").val(data[2]);
		$("#parentIds").val(data[3]);
	}else if(data!=null && data.length==3){
		$("#menuId").val(data[0]);	
		$("#subsystemCode").val(data[1]);	
		$("#parentIds").val(data[2]);
	}
}
/**
 * 提交按钮事件
 */
function save(){
	//表单验证
	$('#menuForm').data('bootstrapValidator').validate();
	if(!$('#menuForm').data('bootstrapValidator').isValid())
	{
	$.modal.setChildData(false);
	return;// 直接return 不做后续任何操作
	}
	//判断是否选择上级区域
	var parentId=$("#parentId").val();
		var subsystemCode=$("#subsystemCode").val();
		var id=$("#menuId").val();
		var pIds=$("#parentIds").val();
	$.ajax({
		type : "POST",
		url : system.getContextPath() + "/sys/menu/save",
		async: true,
		processData : true,
		data : $("#menuForm").serialize(),
		success : function() {
			$.modal.showPrompt({
				title : '提示',
				content : "保存成功!",
				time : 1500
			});
			var treeNode=null;
			if(id!=null && id!="" && pIds!=null && pIds!=""){
				treeNode={'id':id,'pIds':pIds};
			}
			//刷新树
			refreshTree(subsystemCode);
			//刷新table
			refreshTable(subsystemCode,treeNode);
		}
	});
	$.modal.setChildData(true);
}
/**
 * 初始化表单验证
 */
function bootstrapValidator(){
	$('#menuForm').bootstrapValidator(
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
								message : '菜单名称不能为空！'// 这是提示信息
							},
							stringLength: {
					             min: 1,
								 max: 10,
					             message: '菜单名称长度必须在1到10位之间'
								           },
								callback : {
									message : '菜单名称已存在！',
									callback : function(value, validator) {
										return true;
									}
								}
						}
					},
					//上级菜单可以为空
					'parent.name' : {// 这里为输入框的name
						validators : {
							callback : {
								message : '请选择上级菜单！',
								callback : function(value, validator) {
									return true;
								}
							}
						}
					},
					href : {// 这里为输入框的name
						validators : {
							stringLength : {
								min : 1,
								max : 50,
								message : '链接长度必须在1到50位之间'
							}
						}
					},
					permission : {// 这里为输入框的name
						validators : {
							stringLength : {
								min : 1,
								max : 50,
								message : '权限标识必须在1到50位之间'
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
					},target : {// 这里为输入框的name
						validators : {
							callback : {
								message : '请选择目标！',
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
		//子系统编号
		var subsystemCode=$("#subsystemCode").val();
		if(name!=noName){
		$.ajax({
			url: system.getContextPath() + "/sys/menu/findName",
			type: 'GET',
			async: false,
			data:{'name':name,'subsystemCode':subsystemCode,'noName':noName},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#menuForm').data('bootstrapValidator').updateStatus('name', 'VALID','callback');
				}else if('false'==data){
					$('#menuForm').data('bootstrapValidator').updateStatus('name', 'INVALID','callback');
				}
			}
		});
		}
	  });

// 表单验证提示位置
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
 * 回调函数
 * @param v
 * @param nodes
 */
function parentTreeselectCallBack(v,nodes){
	//根据是否有根节点、按钮来判断上级菜单的提示 
	var count=$("#count").val();
	if (v=="ok"){
		var noId=$("#noId").val();
		if(nodes[0].id!=null && nodes[0].id==noId){
			top.$.jBox.tip("不能选择（"+nodes[0].name+"）请重新选择。");
			$("#parentId").val('');
			$("#parentName").val('');
			return false;
		}
	}else if (v=="clear"){
    }
}