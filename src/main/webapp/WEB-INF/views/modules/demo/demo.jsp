<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="defaultx" />
    <title>UI规范</title>
</head>
<body>

<!--内容-->
<div class="container-fluid" style="min-width: 1000px;">
    <!--搜索框-->
    <div class="col-xs-12">
        <form class="navbar-form search-wrap" role="search">
            <span class="form-group ">
                <span class=" input-sm">用户名：</span>
                <input id="userName" value="许国辉" type="text" class="form-control input-sm " placeholder="用户名">
            </span>
             <span class="form-group ">
                 <span class=" input-sm">地址：</span>
                <input type="text" class="form-control input-sm " placeholder="地址">
            </span>
             <span class="form-group ">
                 <span class=" input-sm">归属用户：</span>
                <input type="text" class="form-control input-sm " placeholder="归属用户">
            </span>
            <span class="form-group ">
                 <span class=" input-sm">归属用户：</span>
                <input type="text" class="form-control input-sm " placeholder="归属用户">
            </span>
            <span class="form-group ">
                 <span class=" input-sm">归属用户：</span>
                <input type="text" class="form-control input-sm " placeholder="归属用户">
            </span>
            <span class="form-group ">
                 <span class=" input-sm">归属用户：</span>
                <input type="text" class="form-control input-sm " placeholder="归属用户">
            </span>
            <span class="form-group ">
                 <span class=" input-sm">归属用户：</span>
                <input type="text" class="form-control input-sm " placeholder="归属用户">
            </span>
            <span class="form-group ">
                 <span class=" input-sm">归属用户：</span>
                <input type="text" class="form-control input-sm " placeholder="归属用户">
            </span>
             <span class="form-group ">
                 <span class=" input-sm">性别：</span>

                  <div class="radio  input-sm">
                      <label>
                          <input type="radio" name="user"> 管理员
                      </label>
                      <label>
                          <input type="radio" name="user"> 普通用户
                      </label>
                  </div>
            </span>
            <span class="form-group " style="margin-bottom: 8px">
                <button type="submit" class="btn btn-default btn-primary input-sm">查询</button>
            </span>
        </form>
    </div>
    <div class="col-xs-12">
        <!--表格头部组件-->
        <div id="toolbar">
            <button id="append" class="btn btn-success input-sm" data-toggle="modal" data-target="#modalTable"
                    data-name="append">
                <i class="icon-plus"></i> 新增
            </button>

            <button id="modify" class="btn btn-primary input-sm"
                    data-name="modify">
                <i class="icon-pencil"></i> 修改
            </button>
            <button id="remove" class="btn btn-danger input-sm" disabled>
                <i class="icon-trash"></i> 删除
            </button>
            <button class="btn btn-warning input-sm" data-toggle="modal" data-target=".one-col-modal">
                <i class="icon-trash"></i> 一列
            </button>
            <button id="alerts" class="btn btn-warning input-sm" data-toggle="modal">
                <i class="icon-trash"></i> 提示框
            </button>
            <button id="confirms" class="btn btn-warning input-sm" data-toggle="modal">
                <i class="icon-trash"></i> 确认框
            </button>
            <button id="iframes" class="btn btn-warning input-sm" data-toggle="modal">
                <i class="icon-trash"></i> iframe
            </button>
            <button id="iframes2" class="btn btn-warning input-sm" data-toggle="modal">
                <i class="icon-trash"></i> iframe2
            </button>
        </div>
        <!--表格-->
        <table id="table"></table>
    </div>
</div>
<!--1列模态框-->
<form id="loginFormd" class="login-form" method="post">
    <%@ include file="one-column.jsp" %>
</form>
<!--两列模态框-->
<form id="loginForm" class="login-form" method="post" data-bv-message="This value is not valid"
    data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
    data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
    data-bv-feedbackicons-validating="glyphicon glyphicon-refresh">
    <div class="modal fade modalTable two-col-modal" id="modalTable" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="true"
         data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-header">
                <button type="button" class="close input-sm" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改</h4>
            </div>
            <div class="modal-content">
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-5  form-group">
                                <div class="input-group">
                                    <label class="input-group-addon" id="basic-addon1">工号</label> 
                                    	<input type="text"
											class="form-control input-sm" placeholder="工号" name="number"
											aria-describedby="basic-addon1" data-bv-stringlength="true"
											data-bv-stringlength-min="6" data-bv-stringlength-max="30"
											data-bv-stringlength-message="The username must be more than 6 and less than 30 characters long">
										<span class="required">*</span>
                                </div>
                            </div>
                            <div class="col-xs-5 form-group col-xs-offset-1">
                                <div class="input-group">
                                    <label class="input-group-addon" id="basic-addon2">姓名</label>
                                    <input type="text" class="form-control input-sm" placeholder="姓名" name="name"
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-xs-5 form-group">
                                <div class="input-group">
                                    <label class="input-group-addon">登录名</label>
                                    <input type="text" class="form-control input-sm" placeholder="登录名" name="loginname">
                                    <span class="required">*</span>
                                </div>
                            </div>
                            <div class="col-xs-5 form-group col-xs-offset-1">
                                <div class="input-group">
                                    <label class="input-group-addon">密码</label>
                                    <input type="password" class="form-control input-sm" placeholder="密码"
                                           name="password">
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-xs-5 form-group">
                                <div class="input-group">
                                    <label class="input-group-addon">确认密码</label>
                                    <input type="password" class="form-control input-sm" placeholder="确认密码"
                                           name="confirmPassword">
                                </div>
                            </div>
                            <div class="col-xs-5 form-group col-xs-offset-1">
                                <div class="input-group">
                                    <label class="input-group-addon ">邮箱</label>
                                    <input type="text" class="form-control input-sm" placeholder="邮箱"
                                           name="email">
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-xs-5 form-group">
                                <div class="input-group">
                                    <label class="input-group-addon">电话</label>
                                    <input type="text" class="form-control input-sm" placeholder="电话"
                                           name="telephone">
                                </div>
                            </div>
                            <div class="col-xs-5 form-group col-xs-offset-1">
                                <div class="input-group">
                                    <label class="input-group-addon">所在部门</label>
                                    <input type="text" class="form-control input-sm">
                                        <span class="input-group-btn">
                                            <button class="btn btn-default input-sm" type="button" id="two"
                                                    data-toggle="modal"
                                                    data-target="#twoModal"><i class="icon-search"></i>
                                            </button>
                                        </span>

                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-xs-5 form-group">
                                <div class="input-group ">
                                    <label class="input-group-addon">开始时间</label>

                                    <div class="form-control" style="padding: 0;border: none">
                                        <div class=" date start_datetime input-group">
                                            <input size="16" type="text" class="form-control input-sm" name="starttime"
                                                   style="padding: 0 5px;"
                                                   readonly>
                                            <span class="add-on input-group-addon"><i class="icon-th"></i></span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="col-xs-5 form-group col-xs-offset-1">
                                <div class="input-group ">
                                    <label class="input-group-addon">结束时间</label>

                                    <div class="form-control" style="padding: 0;border: none">
                                        <div class=" date end_datetime input-group">
                                            <input size="16" type="text" class="form-control input-sm" name="endtime"
                                                   style="padding: 0 5px;"
                                                   readonly>
                                            <span class="add-on input-group-addon"><i class="icon-th"></i></span>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-xs-5 form-group">
                                <div class="input-group">
                                    <label class="input-group-addon">选择省份</label>
                                    <select id="first-disabled" class="selectpicker form-control input-sm show-tick"
                                            data-hide-disabled="true" data-live-search="true" countSelectedText="5">
                                        <optgroup>
                                            <option value="">选择省份</option>

                                        </optgroup>

                                    </select>
                                </div>
                            </div>
                            <div class="col-xs-5 form-group col-xs-offset-1">
                                <div class="input-group">
                                    <label class="input-group-addon">选择市</label>
                                    <select id="last-disabled" class="selectpicker form-control input-sm">
                                        <optgroup>
                                            <option value="">选择市</option>
                                        </optgroup>

                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row ">

                            <div class="col-xs-10 form-group">
                                <div class="input-group">
                                    <label class="input-group-addon">当前用户</label>

                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="user"> 管理员
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员1
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户1
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员2
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户2
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员3
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户3
                                        </label>
                                    </div>

                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-xs-10 form-group">
                                <div class="input-group">
                                    <label class="input-group-addon">兴趣</label>

                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="languages[]">音乐
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">上网
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">电视剧
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">电影
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">篮球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">足球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">排球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">兵乓求
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">游泳
                                        </label>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-xs-10 form-group">
                                <div class="input-group">
                                    <label class="input-group-addon">备注</label>
                                    <textarea class="form-control" rows="3" cols="30" style="resize: none"></textarea>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-xs-10">
                            <button type="submit" class="btn btn-primary input-sm" id="determine">确定</button>
                            <button type="button" class="btn btn-default input-sm" id="cancelBtn" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</form>
<!--三列模态框-->
<form id="loginForm1" class="login-form" method="post">
    <div class="modal fade modalTable  three-col-modal" id="modalTable1" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="true"
         data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-header">
                <button type="button" class="close input-sm" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改</h4>
            </div>
            <div class="modal-content">
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row ">
                            <div class="form-group col-xs-4">
                                <div class="input-group">
                                    <label class="input-group-addon">工号</label>
                                    <input type="text" class="form-control input-sm" placeholder="工号" name="number"
                                           aria-describedby="basic-addon1">
                                    <span class="required">*</span>
                                </div>
                            </div>
                            <div class="form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">姓名</label>
                                    <input type="text" class="form-control input-sm" placeholder="姓名" name="name"
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                            <div class="form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">昵称</label>
                                    <input type="text" class="form-control input-sm" placeholder="昵称" name="name"
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" form-group col-xs-4  ">
                                <div class="input-group">
                                    <label class="input-group-addon">邮箱</label>
                                    <input type="text" class="form-control input-sm" placeholder="邮箱" name="number"
                                           aria-describedby="basic-addon1">
                                    <span class="required">*</span>
                                </div>
                            </div>
                            <div class=" form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">密码</label>
                                    <input type="text" class="form-control input-sm" placeholder="密码" name="name"
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                            <div class=" form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">确认密码</label>
                                    <input type="text" class="form-control input-sm" placeholder="确认密码" name="name"
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class=" form-group col-xs-4  ">
                                <div class="input-group">
                                    <label class="input-group-addon">电话</label>
                                    <input type="text" class="form-control input-sm" placeholder="电话" name="number"
                                           aria-describedby="basic-addon1">
                                    <span class="required">*</span>
                                </div>
                            </div>
                            <div class=" form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">手机</label>
                                    <input type="text" class="form-control input-sm" placeholder="手机" name="name"
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                            <div class=" form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">所在部门</label>
                                    <input type="text" class="form-control input-sm" placeholder="所在部门" name="name"
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" form-group col-xs-4  ">
                                <div class="input-group ">
                                    <label class="input-group-addon">开始时间</label>

                                    <div class="form-control" style="padding: 0;border: none">
                                        <div class=" date start_datetime input-group">
                                            <input size="16" type="text" class="form-control input-sm" name="starttime"
                                                   style="padding: 0 5px;"
                                                   readonly>
                                            <span class="add-on input-group-addon"><i class="icon-th"></i></span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="form-group col-xs-4 ">
                                <div class="input-group ">
                                    <label class="input-group-addon">中间时间</label>

                                    <div class="form-control" style="padding: 0;border: none">
                                        <div class=" date start_datetime input-group">
                                            <input size="16" type="text" class="form-control input-sm" name="starttime"
                                                   style="padding: 0 5px;"
                                                   readonly>
                                            <span class="add-on input-group-addon"><i class="icon-th"></i></span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="form-group col-xs-4 ">
                                <div class="input-group ">
                                    <label class="input-group-addon">结束时间</label>

                                    <div class="form-control" style="padding: 0;border: none">
                                        <div class=" date start_datetime input-group">
                                            <input size="16" type="text" class="form-control input-sm" name="starttime"
                                                   style="padding: 0 5px;"
                                                   readonly>
                                            <span class="add-on input-group-addon"><i class="icon-th"></i></span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-4  ">
                                <div class="input-group">
                                    <label class="input-group-addon">选择省份</label>
                                    <select id="firss" class="selectpicker form-control input-sm"
                                            data-hide-disabled="true" data-live-search="true" value="添加">
                                        <optgroup>
                                            <option value="">选择省份</option>
                                        </optgroup>

                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">选择市</label>
                                    <select id="first-" class="selectpicker form-control input-sm"
                                            data-hide-disabled="true" data-live-search="true">
                                        <optgroup>
                                            <option value="">选择市</option>
                                        </optgroup>

                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-xs-4 ">
                                <div class="input-group">
                                    <label class="input-group-addon">选择区域</label>
                                    <select id="first- " class="selectpicker form-control input-sm"
                                            data-hide-disabled="true" data-live-search="true">
                                        <optgroup>
                                            <option value="">选择区域</option>
                                        </optgroup>

                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">

                            <div class="form-group col-xs-12 ">
                                <div class="input-group">
                                    <label class="input-group-addon">当前用户</label>

                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="user"> 管理员
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员1
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户1
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员2
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户2
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员3
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户3
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员1
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户1
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员2
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户2
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 管理员3
                                        </label>
                                        <label>
                                            <input type="radio" name="user"> 普通用户3
                                        </label>
                                    </div>

                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="form-group col-xs-12 ">
                                <div class="input-group">
                                    <label class="input-group-addon">兴趣</label>

                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="languages[]">音乐
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">上网
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">电视剧
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">电影
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">篮球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">足球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">排球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">兵乓求
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">游泳
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">篮球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">足球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">排球
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">兵乓求
                                        </label>
                                        <label>
                                            <input type="checkbox" name="languages[]">游泳
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-12 ">
                                <div class="input-group">
                                    <label class="input-group-addon">备注</label>
                                    <textarea class="form-control" rows="3" cols="30" style="resize: none"></textarea>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-xs-12">
                            <button type="button" class="btn btn-default input-sm" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary input-sm" id="determine1">确定</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<!--模态框中的模态框-->
<div class="modal fade" id="twoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     data-backdrop="static">
    <div class="modal-dialog" style="height: auto">
        <div class="modal-content" style="width: 60%;margin:100px auto 0">
            <div class="modal-header">
                <button type="button" class="close input-sm" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Modal Ztree</h4>
            </div>
            <div class="modal-body">

                <ul id="treeDemo" class="ztree"></ul>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default input-sm" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!--确认模态框-->
<div class="modal fade prompt-box" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
     data-backdrop="static">
    <div class="modal-dialog" style="width:300px;margin:100px auto">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <i class="icon-warning-sign"></i>
                <span class="warning-text">确定是否删除？</span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary input-sm" id="deleteBtn">确定</button>
                <button type="button" class="btn btn-default input-sm" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/js-footer.jsp" %>
<script>
    var setting = {};

    var zNodes = [
        {
            name: "pNode 01", open: true,
            children: [
                {
                    name: "pNode 11",
                    children: [
                        {name: "leaf node 111"},
                        {name: "leaf node 112"},
                        {name: "leaf node 113"},
                        {name: "leaf node 114"}
                    ]
                },
                {
                    name: "pNode 12",
                    children: [
                        {name: "leaf node 121"},
                        {name: "leaf node 122"},
                        {name: "leaf node 123"},
                        {name: "leaf node 124"}
                    ]
                },
                {name: "pNode 13 - no child", isParent: true}
            ]
        },
        {
            name: "pNode 02",
            children: [
                {
                    name: "pNode 21", open: true,
                    children: [
                        {name: "leaf node 211"},
                        {name: "leaf node 212"},
                        {name: "leaf node 213"},
                        {name: "leaf node 214"}
                    ]
                },
                {
                    name: "pNode 22",
                    children: [
                        {name: "leaf node 221"},
                        {name: "leaf node 222"},
                        {name: "leaf node 223"},
                        {name: "leaf node 224"}
                    ]
                },
                {
                    name: "pNode 23",
                    children: [
                        {name: "leaf node 231"},
                        {name: "leaf node 232"},
                        {name: "leaf node 233"},
                        {name: "leaf node 234"}
                    ]
                }
            ]
        },
        {name: "pNode 3 - no child", isParent: true}

    ];

    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });

</script>

<script type="text/javascript" src="${ctxStatic}/basecss/js/index.js"></script>
<script type="text/javascript">
	
	$("#alerts").click(function(){
// 		layer.alert("test");
		layer.msg("提示框",{
			time:1000
		});
	});
	
	$("#confirms").click(function(){
		layer.confirm("测试确认框",{icon:3,title:"确认"},
		function(){
			layer.alert("点击了确认");
		},
		function(){
			layer.alert("点击了取消");
		}
		);
	});
	
	var test = "test";
	
	$("#iframes").click(function(){
	    /* layer.open({
	     type: 2,
	     title: '单列',
	     shadeClose: false,
	     shade: 0.3,
	     maxmin: true, //开启最大化最小化按钮
	     area: ['600px', '400px'],
	     content: '${ctx}/demo/oneColumn.do',
	     btn:["确认","取消"],
	     yes:function(index,layero){
	    	 console.info($(layero).html());
	    	 layer.close(index);
	     }
	    }); */
	  
	 
		$.myDialogs.showDialogs({
		     type: 2,
		     title: '单列',
		     shadeClose: false,
		     shade: 0.3,
		     params : {
		     	name:"",
		     	id:""
		     },
		     maxmin: true, //开启最大化最小化按钮
		     area: ['600px', '400px'],
		     content: '${ctx}/demo/oneColumn.do',
		     btn:["确认","取消"],
		     yes:function(index,layero){
		    	 console.info($(layero).html());
		     }
		    });
	});
	
	$("#iframes2").click(function(){
		 
		$.myDialogs.showDialogs({
		     type: 2,
		     title: '单列',
		     shadeClose: false,
		     shade: 0.3,
		     params : {
		     	name:"111",
		     	id:"222"
		     },
		     maxmin: true, //开启最大化最小化按钮
		     area: ['600px', '400px'],
		     content: '${ctx}/demo/oneColumn.do',
		     btn:["确认","取消"],
		     yes:function(data,index){
		    	 console.info(data);
		    	 console.info($(layero).html());
		     }
		    });
		 
	 });
	
	
</script>

</body>
</html>