<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>BootstrapValidator demo</title>
    <%-- <link rel="stylesheet" href="${ctxStatic }/basecss/validator/vendor/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${ctxStatic }/basecss/validator/dist/css/bootstrapValidator.css"/>

    <script type="text/javascript" src="${ctxStatic }/basecss/validator/vendor/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/basecss/validator/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/basecss/validator/dist/js/bootstrapValidator.js"></script> --%>
    <meta name="decorator" content="defaultx" />
</head>
<body>
<div class="container">
    <div class="row">
        <!-- form: -->
        <section>
            <div class="col-lg-8 col-lg-offset-2">
                <div class="page-header">
                    <h2>Sign up</h2>
                </div>

                <form id="defaultForm" method="post" class="form-horizontal" action="target.php"
                      data-bv-message="This value is not valid"
                      data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
                      data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
                      data-bv-feedbackicons-validating="glyphicon glyphicon-refresh">
                    <div class="form-group">
                    	<label class="col-lg-3 control-label">Full name</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" name="id" placeholder="id" data-bv-idNumber="true" data-bv-idNumber-message="id的长度必须为5位！"/>
                        </div>
                        <div class="col-lg-4">
<!--                             <input type="text" class="form-control" name="lastName" placeholder="Last name" required data-bv-notempty-message="The last name is required and cannot be empty" /> -->
                        </div>
                    
                    </div>
                      
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Full name</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" name="firstName" placeholder="First name" required data-bv-notempty-message="The first name is required and cannot be empty" />
                        </div>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" name="lastName" placeholder="Last name" required data-bv-notempty-message="The last name is required and cannot be empty" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Username</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control" name="username"
                                   data-bv-message="The username is not valid"
                                   required data-bv-notempty-message="The username is required and cannot be empty"
                                   pattern="[a-zA-Z0-9_\.]+" data-bv-regexp-message="The username can only consist of alphabetical, number, dot and underscore"
                                   data-bv-stringlength="true" data-bv-stringlength-min="6" data-bv-stringlength-max="30" data-bv-stringlength-message="The username must be more than 6 and less than 30 characters long"
                                   data-bv-different="true" data-bv-different-field="password" data-bv-different-message="The username and password cannot be the same as each other"
                                   data-bv-remote="true" data-bv-remote-url="remote.php" data-bv-remote-message="The username is not available"
                                    />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Email address</label>
                        <div class="col-lg-5">
                            <input class="form-control" name="email" type="email" data-bv-emailaddress-message="The input is not a valid email address" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Password</label>
                        <div class="col-lg-5">
                            <input type="password" class="form-control" name="password"
                                   required data-bv-notempty-message="The password is required and cannot be empty"
                                   data-bv-identical="true" data-bv-identical-field="confirmPassword" data-bv-identical-message="The password and its confirm are not the same"
                                   data-bv-different="true" data-bv-different-field="username" data-bv-different-message="The password cannot be the same as username"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Retype password</label>
                        <div class="col-lg-5">
                            <input type="password" class="form-control" name="confirmPassword"
                                   required data-bv-notempty-message="The confirm password is required and cannot be empty"
                                   data-bv-identical="true" data-bv-identical-field="password" data-bv-identical-message="The password and its confirm are not the same"
                                   data-bv-different="true" data-bv-different-field="username" data-bv-different-message="The password cannot be the same as username"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Gender</label>
                        <div class="col-lg-5">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="male" required data-bv-notempty-message="The gender is required" /> Male
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="female" /> Female
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="other" /> Other
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Birthday</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control" name="birthday" data-bv-date="false" data-bv-date-format="YYYY/MM/DD" data-bv-date-message="The birthday is not valid" /> (YYYY/MM/DD)
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Languages</label>
                        <div class="col-lg-5">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages[]" value="english" data-bv-message="Please specify at least one language you can speak" /> English
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages[]" value="french" /> French
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages[]" value="german" required /> German
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages[]" value="russian" /> Russian
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages[]" value="other" /> Other
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Programming Languages</label>
                        <div class="col-lg-5">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="net" data-bv-choice="true" data-bv-choice-min="2" data-bv-choice-max="4" data-bv-choice-message="Please choose 2 - 4 programming languages you are good at" /> .Net
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="java" /> Java
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="c" /> C/C++
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="php" /> PHP
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="perl" /> Perl
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="ruby" /> Ruby
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="python" /> Python
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs[]" value="javascript" /> Javascript
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <button type="submit" class="btn btn-primary">Sign up</button>
                            <button type="button" class="btn btn-primary" id="check">校验</button>
                        </div>
                    </div>
                </form>
            </div>
        </section>
        <!-- :form -->
    </div>
</div>
<%@include file="/WEB-INF/views/include/js-footer.jsp" %>
<script type="text/javascript" src="${ctxStatic}/basecss/validator/idNumber.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#defaultForm').bootstrapValidator();
    });
    
    
    $("#check").click(function(){
    	console.info("111111");
    	$('#defaultForm').data('bootstrapValidator').validate();
    	
    	
    });
</script>
</body>
</html>