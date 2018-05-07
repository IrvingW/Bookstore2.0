<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/11
  Time: 下午7:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>AdminUser</title>
</head>
<body>
<s:action name="header" executeResult="true" namespace="/"/>

<script>
    $(document).ready(function() {
        $("#adminBook").hide();
        $("#adminOrder").hide();
        $("#adminUser").show();


        $("#current-tab ul").click(function () {
            $("#current-tab .single-bottom").slideToggle(300);
        });


        $("#admin-user").click(function () {
            $("#adminBook").hide();
            $("#adminOrder").hide();
            $("#adminUser").show();

        });

        $("#admin-book").click(function () {
            $("#adminUser").hide();
            $("#adminOrder").hide();
            $("#adminBook").show();
        });


        $("#admin-order").click(function () {
            $("#adminUser").hide();
            $("#adminBook").hide();
            $("#adminOrder").show();

        });

    });

    function showAddUserForm(){
        $("#addUserForm").show();
    }

    function addUser(){
        var email = $("#addEmail").val();
        var password = $("#addPassword").val();
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if(!reg.test(email)){
            showTip('Email地址格式错误','danger');
        }else{
            if(password.length < 1){
                $("#addPassword").val("000000");
            }
            $("#addUserForm").submit();
        }
    }

    function updateUser(){
        var email = $("#updateEmail").val();
        var password = $("#updatePassword").val();
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if(!reg.test(email)){
            showTip('Email地址格式错误','danger');
        }else{
            if(password.length < 1){
                $("#updatePassword").val("000000");
            }
            $("#updateUserForm").submit();
        }
    }

    function showUpdateUserForm(userID){
        $.ajax({
            url:'<%=path%>/adminAction/getOldUserInfo',
            type:'GET',
            data:{'userID':userID},
            success:function(msg){
                $("#updateUserID").val(msg.userID);
                $("#updateEmail").val(msg.email);
                $("#updatePassword").val(msg.password);
            }
        });
        $("#updateUserForm").show();
    }

    function modifyBook(bookID){

    }

    function showShipForm(orderID){
        $("#orderID").val(orderID);
        $("#shipForm").show();
    }

    function shipOrder(){
        var params = $("#shipForm").serialize();
        $.ajax({
            url:'<%=path%>/testRmi/shipOrder',
            type:'POST',
            data:params,
            success:function(msg){
                if(msg.success){
                    showTip(msg.response,'success');
                    window.setTimeout("window.location='<%=path%>/adminAction/showAllUserList'",1500);
                }
            }
        })
    }
</script>
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

<div class="products">
    <div class="container">
        <h2>管理员界面</h2>
        <div class="col-md-3 rsiderbar span_1_of_left">
            <section class="sky-form">
                <div class="product_right">
                    <h3 class="m_2"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>操作选单</h3>

                    <div id="current-tab" class="tab1">
                        <ul class="place">
                            <li class="sort"><a href="#">信息管理</a></li>
                            <li class="by"><span class="glyphicon glyphicon-triangle-bottom" aria-hidden="true"></span></li>
                        </ul>
                        <div class="clearfix"> </div>
                        <div class="single-bottom">
                            <a id="admin-user" href="#"><p>用户管理</p></a>
                            <a id="admin-book" href="#"><p>图书管理</p></a>
                            <a id="admin-order" href="#"><p>订单管理</p></a>
                        </div>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </section>
        </div>
        <div class="col-md-9 product-model-sec">
        <div id="tip"></div>
        <div id="cartinfo" class="cart-item">
            <div class="container">
                <div id="adminUser">
                    <h4>用户列表</h4>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>用户ID</th>
                            <th>Email</th>
                            <th>密码</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="#userList" status="st">
                            <tr>
                                <td><s:property value="userID"/></td>
                                <td><s:property value="email"/></td>
                                <td><s:property value="password"/></td>
                                <td><a href="#" onclick="showUpdateUserForm(<s:property value="userID"/>)">修改用户</a></td>
                                <td><a href="<%=path%>/adminAction/deleteUser?userID=<s:property value="userID"/>">删除账户</a></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                    <a href="#" class="add-cart item_add" onclick="showAddUserForm()">添加账户</a>
                    <form id="addUserForm" style="display: none" action="<%=path%>/adminAction/addUser" method="post">
                        <input type="text" id="addEmail" name="email" placeholder="Email"/>
                        <input type="password" id="addPassword" name="password" placeholder="密码"/>
                        <a href="#" class="add-cart item_add" onclick="addUser()">添加</a>
                    </form>
                    <form id="updateUserForm" style="display: none" action="<%=path%>/adminAction/updateUser" method="post">
                        <input type="hidden" id="updateUserID" name="userID"/>
                        <input type="text" id="updateEmail" name="email" placeholder="Email"/>
                        <input type="password" id="updatePassword" name="password" placeholder="密码"/>
                        <a href="#" class="add-cart item_add" onclick="updateUser()">修改</a>
                    </form>
                </div>
                <div id="adminBook">
                    <h4>图书列表</h4>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>书名</th>
                            <th>作者</th>
                            <th>ISBN</th>
                            <th>库存</th>
                            <th>售价</th>
                            <td></td>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="#bookList" status="st">
                            <tr>
                                <td><s:property value="bookName"/></td>
                                <td><s:property value="author"/></td>
                                <td><s:property value="isbn"/></td>
                                <td><s:property value="amount"/></td>
                                <td><s:property value="price"/></td>
                                <td><a href="#" onclick="modifyBook(<s:property value="bookID"/>)">修改</a></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                    <a href="<%=path%>/adminAction/showBookRelease" class="add-cart item_add">添加新书</a>
                </div>
                <div id="adminOrder">
                    <h4>订单列表</h4>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>购买人</th>
                            <th>总价</th>
                            <th>收货地址</th>
                            <th>订单状态</th>
                            <td></td>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="#orderList" status="st">
                            <tr>
                                <td><s:property value="orderID"/></td>
                                <td><s:property value="buyerID"/></td>
                                <td><s:property value="totalPrice"/></td>
                                <td><s:property value="address"/></td>
                                <td><s:property value="status"/></td>
                                <s:if test="status.toString()=='NOTSHIPPED'.toString()">
                                    <td><a href="#" onclick="showShipForm(<s:property value="orderID"/>)">发货</a></td>
                                </s:if>
                                <s:else></s:else>
                        </s:iterator>
                        </tbody>
                    </table>
                    <form id="shipForm" style="display: none" action="<%=path%>/testRmi/shipOrder" method="post">
                        <input type="hidden" id="orderID" name="orderID"/>
                        <input type="text" id="trackingNo" name="trackingNo" placeholder="快递单号"/>
                        <a href="#" class="add-cart item_add" onclick="shipOrder()">确认发货</a>
                    </form>
                </div>
            </div>
        </div>
        </div>
    </div>
</div>


<jsp:include page="footer.jsp"/>

</body>
</html>
