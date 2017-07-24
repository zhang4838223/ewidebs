<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
    <meta name="decorator" content="defaultx" />
</head>

<!-- <div class="modal fade one-col-modal" tabindex="-1" aria-labelledby="mySmallModalLabel">
        <div class="modal-dialog modal-md"> -->
<!--             <div class="modal-content"> -->
                <!-- <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Modal title</h4>
                </div> -->
                <div class="modal-body form-horizontal bv-form">
                        <div class="  form-group">
                            <label class="col-xs-3 col-xs-offset-1">用户名</label>

                            <div class="col-xs-5">
                                <input type="text" class="form-control" name="用户名"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 col-xs-offset-1">密码</label>

                            <div class="col-xs-5">
                                <input type="password" class="form-control" name="密码"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 col-xs-offset-1">邮箱</label>

                            <div class="col-xs-5">
                                <input type="password" class="form-control" name="邮箱"/>
                            </div>
                        </div>
                    </div>

                <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary">关闭</button>
                </div>
<!--             </div> -->
            <!-- /.modal-content -->
<!--         </div>
    </div> -->
<%@include file="/WEB-INF/views/include/js-footer.jsp" %>
<script src="${ctxStatic}/basecss/js/dialogs/dialog.js"></script>
<script type="text/javascript">
	var userName = parent.$("#userName").val();
// 	alert(userName);
// 	alert(parent.test);
	
// 	console.info(parent._dia_params);
	
	var params = $.myDialogs.getParentParams();
	console.info(params);
// 	$.myDialogs.setReturnData({dataName:"1234",dataId:"5678"});
	
	$.myDialogs.clickYes(function(){
		$.myDialogs.setReturnData({name:"subpage",id:"subpageid"});
	});
	
</script>
