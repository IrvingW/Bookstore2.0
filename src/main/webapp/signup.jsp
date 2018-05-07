<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/7
  Time: 下午5:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>Sign up</title>
</head>
<body>

<!-- header -->
<s:action name="header" executeResult="true" namespace="/"/>

<style>
    @media ( min-width :768px) {

        .form-control-noNewline {
            width: 100px;
            display: inline;
        }

        .form-horizontal .form-group-auto {
            margin-right: 0px;
            margin-left: 0px;
        }
    }
</style>

<div class="account">
    <div class="container">
        <div id="tip"> </div>
        <div class="register">
            <form id="registerForm" class="form-horizontal">
                <div class="register-top-grid" id="RegisterBox">
                    <h3>用户注册</h3>
                    <div class="form-group form-group-auto" id="ParaEmail">
                        <label>邮箱地址</label><font color="#FF0000">*</font>&nbsp;
                        <input type="text" name="registerInfo.email" class="form-control" v-model="register_email" @blur="checkEmail" v-focus><span v-bind:style="styleObject">{{message}}</span>
                    </div>
                    <div class="form-group form-group-auto" id="ParaPwd">
                        <label>密码</label><font color="#FF0000">*</font>&nbsp;
                        <input type="password" name="registerInfo.plainPassword" class="form-control" v-model="register_pwd" @blur="checkPwd"><span v-bind:style="styleObject">{{message}}</span>
                    </div>
                    <div class="form-group form-group-auto" id="ParaPwd2">
                        <label>确认密码</label><font color="#FF0000">*</font>&nbsp;
                        <input type="password" name="confirmpassword" class="form-control" v-model="register_pwd2" @blur="confirmPwd"><span v-bind:style="styleObject">{{message}}</span>
                    </div>
                    <div class="form-group form-group-auto" id="nickName">
                        <label>昵称</label><font color="#FF0000">*</font>&nbsp;
                        <input type="text" name="registerInfo.nickName" class="form-control" v-model="register_nickName" @blur="checkNickName"><span v-bind:style="styleObject">{{message}}</span>
                    </div>
                    <div class="form-group form-group-auto" id="gender">
                        <label>年龄</label><font color="#FF0000">*</font>&nbsp;
                        <select name="userProfile.gender" class="form-control form-control-noNewline" >
                            <option>男</option>
                            <option>女</option>
                        </select>
                    </div>
                    <div class="form-group form-group-auto" id="age">
                        <label>年龄</label><font color="#FF0000">*</font>&nbsp;
                        <input type="text" name="registerInfo.age" class="form-control" v-model="register_age" @blur="checkage">
                    </div>
                    <div class="form-group form-group-auto" id="mobile">
                        <label>手机</label><font color="#FF0000">*</font>&nbsp;
                        <input type="text" name="registerInfo.mobile" class="form-control" v-model="register_mobile" @blur="checkMobile"><span v-bind:style="styleObject">{{message}}</span>
                    </div>
                    <div class="clearfix"> </div>
                    <div class="register-but" id="register">
                        <input type="button" value="注册" @click="submitRegisterForm">
                        <div class="clearfix"> </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- footer -->
<jsp:include page="footer.jsp"/>

<script>
    Vue.directive('focus', {

        inserted: function (el) {
            // 聚焦元素
            el.focus()
        },
    });

    var app1 = new Vue({
        el: '#ParaEmail',
        data: {
            message:"",
            register_email:"",
            styleObject:{
                color:"green",
                fontSize:"15px"
            }
        },
        methods:{
            checkEmail: function(){
                var _self = this;
                var email = this.register_email.valueOf();
                var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                if(!reg.test(email)){
                    _self.message = "请输入正确的邮箱地址";
                    _self.styleObject.color = "red";
                }else{
                    _self.message = "";
                    _self.styleObject.color = "green";

                    $.ajax({
                        url:'<%=path%>/authAction/checkEmailAvailable',
                        type:'GET',
                        data:{'email':email},
                        success:function(data){
                            if(data.success){
                                _self.message = "";
                            }else{
                                _self.message = "该邮箱地址已存在";
                                _self.styleObject.color = "red";
                            }
                        },
                        error:function(xhr,status,error){
                            alert('status='+status+',error='+error);
                        }
                    });
                }
            }
        }
    });

    var app2 = new Vue({
        el: '#ParaPwd',
        data: {
            message:"",
            register_pwd:"",
            styleObject:{
                color:"green",
                fontSize:"15px"
            }
        },
        methods:{
            checkPwd: function(){
                var _self = this;
                var pwd = this.register_pwd.valueOf();
                var reg = /^[a-zA-z]\w{5,11}$/;
                if(!reg.test(pwd)){
                    _self.message = "密码由字母、数字、下划线组成，字母开头，6-12位";
                    _self.styleObject.color = "red";
                }else{
                    _self.message = "";
                    _self.styleObject.color = "green";
                }
            }
        }
    });

    var app3 = new Vue({
        el: '#ParaPwd2',
        data: {
            message:"",
            register_pwd2:"",
            styleObject:{
                color:"green",
                fontSize:"15px"
            }
        },
        methods:{
            confirmPwd: function(){
                var _self = this;
                var pwd1 = app2.register_pwd.valueOf();
                var pwd2 = this.register_pwd2.valueOf();
                if(pwd2 != pwd1){
                    _self.message = "两次密码不一致";
                    _self.styleObject.color = "red";
                }else{
                    _self.message = "";
                    _self.styleObject.color = "green";
                }
            }
        }
    });

    var app4 = new Vue({
        el: '#nickName',
        data: {
            message:"",
            register_nickName:"",
            styleObject:{
                color:"green",
                fontSize:"15px"
            }
        },
        methods: {
            checkNickName: function(){
                var _self = this;
                var nickName = _self.register_nickName.valueOf();
                var reg = /^[a-zA-z]\w{3,15}$/;
                if(!reg.test(nickName)){
                    _self.message = "昵称由字母、数字、下划线组成，字母开头，4-16位";
                    _self.styleObject.color = "red";
                }else{
                    _self.message = "";
                    _self.styleObject.color = "green";
                }
            }
        }
    });

    var app5 = new Vue({
        el: '#mobile',
        data: {
            message:"",
            register_mobile:"",
            styleObject:{
                color:"green",
                fontSize:"15px"
            }
        },
        methods: {
            checkMobile: function(){
                var _self = this;
                var mobile = _self.register_mobile.valueOf();
                var reg = /^1[3|4|5|8][0-9]\d{4,8}$/;
                if(!reg.test(mobile)){
                    _self.message = "请输入正确的手机号码";
                    _self.styleObject.color = "red";
                }else{
                    _self.message = "";
                    _self.styleObject.color = "green";
                }
            }
        }
    });

    var app6 = new Vue({
        el: '#register',
        methods: {
            submitRegisterForm: function(){
                if(app1.message == "" && app2.message == "" && app3.message == ""
                   && app4.message == "" && app5.message == ""){
                    var params = $("#registerForm").serialize();
                    $.ajax({
                        url: "<%=path%>/authAction/register",
                        type: "post",
                        data: params,
                        success: function (msg) {
                            if(msg.success){
                                showTip('注册成功!', 'success');
                                window.setTimeout("window.location='<%=path%>/index.jsp'",1500);
                            }
                            else{
                                //var msg = response.message;
                                showTip('注册失败!', 'danger');
                            }
                        }
                    });
                }else{
                    showTip('请正确填写注册信息！', 'danger');
                }

            }
        }
    })

</script>
</body>
</html>
