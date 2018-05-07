<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/8
  Time: 下午7:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
<script>
    $(document).ready(function(){
        $("#login_email").focus();
        $("#login_email").keyup(function(){
            var email = $("#login_email").val();
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
            if(!reg.test(email)){
                $("#available_status").html("<span style='color:red'>请输入正确的邮件地址</span>");
            }else{
                $("#available_status").html("<span></span>");
            }
        });
    });



    function signin() {
        var params = $("#registerForm").serialize();
        $.ajax({
            url: "<%=path%>/authAction/login",
            type: "post",
            data: params,
            success: function (data) {
                if(data.success){
                    if(data.role == 1) {
                        showTip("登陆成功！", "success");
                        window.setTimeout("window.location='<%=path%>/index'",1000);
                        //alert(response.message);
                    }
                    if(data.role == 0){
                        showTip('欢迎管理员','success');
                        window.setTimeout("window.location='<%=path%>/adminAction/showAllUserList'",1000);
                    }
                }
                else{
                    showTip("登陆失败！","danger");
                }
            }
        });
    }
</script>
<s:action name="header" executeResult="true" namespace="/"/>

<div class="account">
    <div class="container">
        <div id="tip"> </div>
        <div class="register" id="registerBox">
            <form id="registerForm"  class="form-horizontal">
                <div class="register-top-grid">
                    <h3>请重新登录</h3>
                    <div class="form-group form-group-auto">
                        <label>邮箱地址</label><font color="#FF0000">*</font>&nbsp;
                        <input type="text" name="email" class="form-control" id="login_email"><div id="available_status"></div>
                    </div>
                    <div class="form-group form-group-auto">
                        <label>密码</label><font color="#FF0000">*</font>&nbsp;
                        <input type="password" name="password" class="form-control" id="login_password"><div id="available_status2"></div>
                    </div>


                    <div class="clearfix"> </div>
                    <a href="#" class="add-cart item_add" id="loginButton" onclick="signin()">登录</a>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- footer -->
<jsp:include page="footer.jsp"/>
</body>
</html>
