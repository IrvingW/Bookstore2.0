<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/1
  Time: 上午11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>

<!-- header -->
<div class="header">
    <div class="container">
        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <h1 class="navbar-brand"><a href="<%=path%>/index">BookStore</a></h1>
            </div>
            <!--navbar-header-->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="<%=path%>/index"><s:text name="global.header1"/></a></li>
                    <li class="dropdown grid">
                        <a href="#" class="dropdown-toggle list1" data-toggle="dropdown"><s:text name="global.header2"/><b class="caret"></b></a>
                        <ul class="dropdown-menu multi-column columns-4">
                            <div class="row">
                                <s:iterator value="#category1List">
                                    <div class="col-sm-3">
                                        <h4><s:property value="category1Name"/></h4>
                                        <ul class="multi-column-dropdown">
                                            <li><a class="list" href="<%=path%>/permissionAction/showAllBooks?category1Name=<s:property value="category1Name"/>"><s:property value="category1Name"/></a></li>
                                            <s:iterator value="category2List">
                                                <li><a class="list" href="<%=path%>/permissionAction/showAllBooks?category2Name=<s:property value="category2Name"/>"><s:property value="category2Name"/></a></li>
                                            </s:iterator>
                                        </ul>
                                    </div>
                                </s:iterator>
                            </div>
                        </ul>
                    </li>
                    <li class="dropdown grid"><a href=" <%=path%>/bookAction/showAllBooks" class="" ><s:text name="global.header3"/></a>
                    </li>
                    <li><a href="<%=basePath%>helpPage.jsp" class="" ><s:text name="global.header4"/></a>
                    </li>
                </ul>
                <!--/.navbar-collapse-->
            </div>
            <!--//navbar-header-->
        </nav>
        <div class="header-info">
            <div class="header-right search-box">
                <a href="#"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
                <div class="search">
                    <form class="navbar-form" action="<%=path%>/permissionAction/showAllBooks">
                        <input type="text" class="form-control" name="searchText">
                        <button type="submit" class="btn btn-default" aria-label="Left Align">
                            Search
                        </button>
                    </form>
                </div>
            </div>
            <div class="header-right login">
                <a href="<%=path%>/userAction/showUserProfile"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></a>
                <div id="loginBox">
                    <form id="loginForm">
                        <s:if test="#session.userInfo==null">
                            <fieldset id="body">
                                <fieldset>
                                    <label><s:text name="global.email"/></label>
                                    <input type="email" name="email" id="email" class="required email" ><div id="status1"></div>
                                </fieldset>
                                <fieldset>
                                    <label><s:text name="global.password"/></label>
                                    <input type="password" name="password" id="password" class="required" ><div id="status2"></div>
                                </fieldset>
                                <input type="button" id="login" value="<s:text name="global.login"/>">
                            </fieldset>
                            <p><s:text name="global.newUser"/> ? <a class="sign" href="<%=basePath%>signup.jsp"><s:text name="global.register"/></a><!-- <span><a href="#">忘记密码?</a></span> --></p>
                        </s:if>
                        <s:elseif test="#session.userInfo.role==@common.constants.UserRole@ADMIN">
                            <label><s:text name="global.welcomeAdmin"/></label><br>
                            <label><a href="<%=path%>/adminAction/showAllUserList"><s:text name="global.management"/></a></label><br>
                            <label><a href="<%=path%>/authAction/logout"><s:text name="global.logout"/></a></label><br>

                        </s:elseif>
                        <s:else>
                        <label><s:text name="global.welcomeUser"/> <s:property value="#session.userInfo.email"/></label><br>
                        <label><a href="<%=path%>/userAction/showUserProfile"><s:text name="global.userCenter"/></a></label><br>
                        <label><a href="<%=path%>/chatroom.jsp"><s:text name="global.chatroom"/></a></label><br>
                        <label><a href="<%=path%>/orderAction/showMyOrder"><s:text name="global.myOrder"/></a></label><br>
                        <label><a href="<%=path%>/authAction/logout"><s:text name="global.logout"/></a></label><br>
                    </form>
                </div>
                </s:else>
                </form>
            </div>

        </div>
        <div class="header-right cart">
            <a href="<%=path%>/cartAction/showBuyCart"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span></a>
            <div class="cart-box">
                <s:if test="#session.buyCart==null||#session.buyCart.size()==0">
                    <h4><span><s:text name="global.cart1"/></span></h4>
                    <h4><a href="<%=path%>/bookAction/showAllBooks"><s:text name="global.cart2"/></a></h4>
                </s:if>
                <s:else>
                    <table class="table table-bordered">
                        <tr>
                            <th field="bookName" width="16%"><s:text name="global.cart3"/></th>
                            <th field="amount" width="16%"><s:text name="global.cart4"/></th>
                            <th field="price" width="16%"><s:text name="global.cart5"/></th>
                        </tr>
                        <s:iterator value="#session.buyCart" var="cartItem" status="st">
                            <tr>
                                <td><s:property value="#cartItem.bookName"/></td>
                                <td><s:property value="#cartItem.amount"/></td>
                                <td><s:property value="#cartItem.price"/></td>
                            </tr>
                        </s:iterator>
                    </table>
                    <p><a href="<%=path%>/cartAction/emptyBuyCart" class="simpleCart_empty"><s:text name="global.cart6"/></a></p>
                </s:else>
                <div class="clearfix"> </div>
            </div>
        </div>

    </div>

    <div class="clearfix"> </div>
</div>
<div class="clearfix"> </div>
</div>


<script>
    $(document).ready(function() {
        $("#email").focus();
        $("#email").keyup(function(){
            var email = $("#email").val();
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
            if(!reg.test(email)){
                $("#status1").html("<span style='color:red'><s:text name="global.error1"/></span>");
            }else{
                $("#status1").html("<span></span>");
            }
        });
        $("#email").blur(function(){
            var email = $("#email").val();
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
            if(!reg.test(email)){
                $("#status1").html("<span style='color:red'><s:text name="global.error1"/></span>");
            }else{
                $("#status1").html("<span></span>");
            }
        });
        $("#password").focus();
        $("#password").keyup(function(){
            var password = $("#password").val();
            if(password.length < 1){
                $("#status2").html("<span style='color:red'><s:text name="global.error2"/></span>");
            }else{
                $("#status2").html("<span></span>");
            }
        });
        $("#password").blur(function(){
            var password = $("#password").val();
            if(password.length < 1){
                $("#status2").html("<span style='color:red'><s:text name="global.error2"/></span>");
            }else{
                $("#status2").html("<span></span>");
            }
        });
        $("#login").click(function () {
            var params = $("#loginForm").serialize();
            $.ajax({
                url: "<%=path%>/authAction/login",
                type: "post",
                data: params,
                success: function (data) {
                    if(data.success){
                        if(data.role == 1) {
                            //先替换原页面元素，等到用户刷新再真正判断session来载入真正的元素
                            var replace = "<label>" + "<s:text name="global.welcomeUser"/>" + data.email + "</label><br>" +
                                "<label><a href='<%=path%>/userAction/showUserProfile'><s:text name="global.userCenter"/></a></label><br>" +
                                "<label><a href='<%=path%>/reserveAction/showMyOrder'><s:text name="global.myOrder"/></a></label><br>" +
                                "<label><a href='<%=path%>/authAction/logout'>" + "<s:text name="global.logout"/>" + "</a><label><br>";
                            $('#loginForm').html(replace);
                            showTip("<s:text name="global.error3"/>", "success");
                            //alert(response.message);
                        }
                        if(data.role == 0){
                            showTip('<s:text name="global.welcomeAdmin"/>','success');
                            window.setTimeout("window.location='<%=path%>/adminAction/showAllUserList'",1000);
                        }
                    }
                    else{
                        showTip("<s:text name="global.error4"/>","danger");
                    }
                }
            });
        });
    });


</script>
