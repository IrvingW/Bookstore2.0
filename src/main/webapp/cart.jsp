<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/17
  Time: 下午4:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<!-- header -->
<s:action name="header" executeResult="true" namespace="/"/>

<!--cart-items-->
<div class="cart-items">
    <div class="container">
        <div id="tip"> </div>
        <script>
            function deleteBook(bookID) {
                console.log("delete current book, bookid: "+bookID);
                var method = '<s:property value="#buyOrBorrow"/>';
                var cmp = method;
                var url = "";
                if(cmp=="borrow"){
                    url = '/cartAction/removeFromBorrowCart';
                }
                if(cmp=="buy"){
                    url = '/cartAction/removeFromBuyCart';
                }
                $.ajax({
                    url: '<%=path%>'+ url,
                    type:'POST',
                    data: {
                        'bookID' : bookID
                    },
                    success: function(msg){
                        if (msg.success) {
                            $("div").remove("#"+bookID);
                            showTip('从购物车删除!', 'success');
                        }
                        else {
                            showTip('无法删除，请刷新页面！', 'danger');
                        }
                    },
                    error:function(xhr,status,error){
                        alert('status='+status+',error='+error);
                    }

                });

            }
        </script>



        <s:if test="#session.buyCart==null">
            <h3>购物车为空</h3>
            <h3><a href="<%=path%>/bookAction/showAllBooks">前去浏览图书</a></h3>
        </s:if>
        <s:else>
                <h3>购物车</h3><br>

                <div id="cart" class="cart-header">
                    <div class="cart-sec simpleCart_shelfItem">
                        <div class="cart-item-info">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>书名</th>
                                    <th>数量</th>
                                    <th>价格</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
            <s:iterator value="#cartList" var="st">
                                <tr>
                                    <td><a href="<%=path%>/bookAction/showBookProfile?bookID=<s:property value="#st.bookID"/>"><s:property value="#st.bookName"/></a></td>
                                    <td><s:property value="#st.amount"/></td>
                                    <td><s:property value="#st.price"/></td>
                                    <td><a href="<%=path%>/cartAction/addOneToCart?bookID=<s:property value="#st.bookID"/>&amount=1">+1</a></td>
                                    <td><a href="<%=path%>/cartAction/removeOneFromCart?bookID=<s:property value="#st.bookID"/>&amount=1">-1</a></td>
                                </tr>
            </s:iterator>
                                </tbody>
                            </table>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>

            <span>总金额：<s:property value="#totalPrice"/></span>

            <button class="btn-default" onclick="window.location.href='<%=path%>/orderAction/buyCheckout'">提交订单</button>
        </s:else>
    </div>
</div>
<!--//checkout-->

<jsp:include page="footer.jsp"/>
</body>
</html>
