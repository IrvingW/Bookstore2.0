<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/18
  Time: 下午7:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>myOrder</title>
</head>
<body>

</body>
<!-- header -->
<s:action name="header" executeResult="true" namespace="/"/>

<div class="products">
    <div class="container">
        <div class="col-md-3 rsiderbar span_1_of_left">
            <section class="sky-form">
                <div class="product_right">
                    <h3 class="m_2"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>操作选单</h3>

                    <div class="tab1">
                        <ul class="place">
                            <li class="sort"><a href="<%=path%>/userAction/showUserProfile">个人信息</a></li>
                        </ul>
                        <div class="clearfix"> </div>
                    </div>

                    <div class="tab1">
                        <ul class="place">
                            <li class="sort"><a href="#">我的购买</a></li>
                            <li class="by"><span class="glyphicon glyphicon-triangle-bottom" aria-hidden="true"></span></li>
                        </ul>
                        <div class="clearfix"> </div>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </section>
        </div>

        <h3 align="center">我的订单</h3>
        <div id="tip"></div>
        <div id="cartinfo" class="cart-item">
            <div class="container">

                <!-- 迭代器显示订单信息 -->
                <s:iterator value="#orderList" var="st">
                    <div id="<s:property value="orderID"/>" class="cart-header">
                        <div class="cart-sec simpleCart_shelfItem">
                            <div class="cart-item cyc">
                            </div>
                            <div class="cart-item-info">
                                <h4>
                                    <s:if test="orderStatus=='NOTPAID'">
                                        <p id="status<s:property value="orderID"/>">订单号：<s:property value="orderID"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前状态：未支付</p>
                                    </s:if>
                                    <s:elseif test="orderStatus=='CANCELED'">
                                        <p id="status<s:property value="orderID"/>">订单号：<s:property value="orderID"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前状态：已取消</p>
                                    </s:elseif>
                                    <s:elseif test="orderStatus=='NOTSHIPPED'">
                                        <p id="status<s:property value="orderID"/>">订单号：<s:property value="orderID"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前状态：未发货</p>
                                    </s:elseif>
                                    <s:elseif test="orderStatus=='SHIPPED'">
                                        <p id="status<s:property value="orderID"/>">订单号：<s:property value="orderID"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前状态：已发货</p>
                                    </s:elseif>
                                    <s:elseif test="orderStatus=='COMPLETED'">
                                        <p id="status<s:property value="orderID"/>">订单号：<s:property value="orderID"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前状态：已完成</p>
                                    </s:elseif>
                                </h4><br>
                                <ul class="qty">
                                    <li><p>下单时间：<s:property value="orderDate"/></p></li>
                                    <li><p>总金额：<s:property value="totalPrice"/>元</p></li>
                                </ul>
                                <div class="delivery">
                                    <p>收货地址：<s:property value="address"/></p><br>
                                    <s:if test="orderStatus=='NOTSHIPPED'">
                                        <p id="payDate<s:property value="orderID"/>">付款时间：<s:property value="payDate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                                        <p id="fhDate<s:property value="orderID"/>" style="display: none">发货时间：<s:property value="fhDate"/></p><br>
                                        <p id="shDate<s:property value="orderID"/>" style="display: none">收货时间：<s:property value="shDate"/></p><br>
                                    </s:if>
                                    <s:elseif test="orderStatus=='SHIPPED'">
                                        <p id="payDate<s:property value="orderID"/>">付款时间：<s:property value="payDate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                                        <p id="fhDate<s:property value="orderID"/>">发货时间：<s:property value="fhDate"/></p><br>
                                        <p id="shDate<s:property value="orderID"/>" style="display: none">收货时间：<s:property value="shDate"/></p><br>
                                        <a id="confirmOrderBtn<s:property value="orderID"/>" href="#" class="add-cart item_add" onclick="confirmOrderReceipt(<s:property value="orderID"/>)">确认收货</a>
                                    </s:elseif>
                                    <s:elseif test="orderStatus=='COMPLETED'">
                                        <p id="payDate<s:property value="orderID"/>">付款时间：<s:property value="payDate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                                        <p id="fhDate<s:property value="orderID"/>">发货时间：<s:property value="fhDate"/></p><br>
                                        <p id="shDate<s:property value="orderID"/>">收货时间：<s:property value="shDate"/></p><br>
                                    </s:elseif>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <s:iterator value="#st.orderItemProfileList" status="id">
                            <div class="cart-sec simpleCart_shelfItem">
                                <div class="cart-item cyc">
                                    <img src="<%=path%>/imageAction/showImage?imageID=<s:property value="imageID"/>" class="img-responsive" alt="">
                                </div>
                                <div class="cart-item-info">
                                    <ul class="qty">
                                        <li><p><a href="<%=path%>/bookAction/showBookProfile?bookID=<s:property value="bookID"/>">
                                            书名：<s:property value="bookName"/></a></p></li>
                                        <li><p>数量：<s:property value="bookAmount"/></p></li>
                                        <li><p>价格：<s:property value="bookPrice"/></p></li>
                                    </ul>
                                    <div class="delivery">
                                        <p>ISBN：<s:property value="isbn"/></p>
                                        <p>出版社：<s:property value="press"/></p><br>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </s:iterator>
                    </div><hr>
                </s:iterator>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>

</html>
