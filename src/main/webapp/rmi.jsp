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
    <title>index</title>
</head>
<body>

<h3>RMI模拟快递公司发货</h3>
<p>说明：以IDEA为例，先运行service包中的RmiServer，作为快递公司的模拟后台。<br>
    然后部署运行网站，在框中输入快递号，点击发货，会将快递单号通过RMI客户端传到服务端，服务端返回On processing</p>
<br>
<div id="sid_input">
    <p>{{message}}</p>
    <input type="text" name="sid" id="sid" v-model="sid" placeholder="请输入快递单号">
    <button v-on:click="submitSid">发货</button>
</div>

<script>
    var app1 = new Vue({
        el: '#sid_input',
        data:{
            message:"当前状况：未发货",
            sid:""
        },
        methods:{
            submitSid: function(){
                var _self = this;
                var actionUrl = '<%=path%>/testRmi/testrmi';
                var param = _self.sid.valueOf();
                $.ajax({
                    type:'POST',
                    url:actionUrl,
                    data:{'sid':param},
                    success:function(data){
                        if(data.success){
                            _self.message = data.response;
                        }
                    },
                    error:function(xhr,status,error){
                        alert('status='+status+',error='+error);
                    }
                });
            }
        }
    })
</script>
</body>
</html>
