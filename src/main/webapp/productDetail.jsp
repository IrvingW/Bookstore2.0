<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/17
  Time: 下午3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>ProductDetail</title>
</head>
<body>
<script src="<%=path%>/js/imagezoom.js"></script>
<script defer src="<%=path%>/js/jquery.flexslider.js"></script>
<link rel="stylesheet" href="<%=path%>/css/flexslider.css" type="text/css" media="screen" />
<script>
    $(window).load(function() {
        $('.flexslider').flexslider({
            animation: "slide",
            controlNav: "thumbnails"
        });
    });

    function addToBuyCart(bookID){
        var buyAmountID = "buyAmount"+bookID;
        var buyAmount = $('#'+buyAmountID).val();
        $.ajax({
            url:'<%=path%>/cartAction/addToBuyCart',
            type:'POST',
            data:{
                'bookID': bookID,
                'amount': buyAmount
            },
            success: function (msg) {
                if (msg.success) {
                    showTip('添加成功!', 'success');
                }
                else {
                    showTip('添加失败', 'danger');
                }
            },
            error:function(xhr,status,error){
                alert('status='+status+',error='+error);
            }
        });
    }
</script>

<s:action name="header" executeResult="true" namespace="/"/>

<div class="single">
    <div class="container">
        <div class="single-grids">
            <div class="col-md-4 single-grid">
                <div class="flexslider">
                    <ul class="slides">
                        <li data-thumb="<%=path%>/imageAction/showImage?imageID=<s:property value="#bookProfile.imageID"/>">
                            <div class="thumb-image"> <img src="<%=path%>/imageAction/showImage?imageID=<s:property value="#bookProfile.imageID"/>" data-imagezoom="true" class="img-responsive"> </div>
                        </li>
                        <s:iterator value="#bookProfile.otherPictureIDList" var="otherPictureID">
                            <li data-thumb="<%=path%>/imageAction/showImage?imageID=<s:property value="otherPictureID"/>">
                                <div class="thumb-image"> <img src="<%=path%>/imageAction/showImage?imageID=<s:property value="otherPictureID"/>" data-imagezoom="true" class="img-responsive"> </div>
                            </li>
                        </s:iterator>
                    </ul>
                </div>
            </div>
            <div class="col-md-4 single-grid simpleCart_shelfItem">
                <ul class="size">
                    <h3>书名</h3>
                    <li><span></span><s:property value="#bookProfile.bookName"/></li></li>
                </ul>

                <ul class="size">
                    <h3>作者</h3>
                    <li><span><s:property value="#bookProfile.author"/></span></li>
                </ul>
                <ul class="size">
                    <h3>ISBN</h3>
                    <li><span><s:property value="#bookProfile.isbn"/></span></li>
                </ul>
                <ul class="size">
                    <h3>出版社</h3>
                    <li><span><s:property value="#bookProfile.press"/></span></li>
                </ul>
                <ul class="size">
                    <h3>价格</h3>
                    <li><span>库存：<s:property value="#bookProfile.amount"/></span></li>
                    <li><span>价格：<s:property value="#bookProfile.price"/></span></li>
                    <div class="clearfix"></div><br>
                </ul>
                <s:if test="#bookProfile.amount==0">
                    <p>此书已售罄</p>
                </s:if>
                <div class="btn_form">
                    <s:if test="#bookProfile.amount!=0">
                        <input type="number" id="buyAmount<s:property value="#bookProfile.bookID"/>">
                        <a href="#" class="add-cart item_add" onclick="addToBuyCart(<s:property value="#bookProfile.bookID"/>)">购买</a>
                    </s:if>
                    <s:else>

                    </s:else>
                </div>
                <div class="tag">
                    <p>分类 : <a href="#"><s:property value="#bookProfile.category1"/></a></p>
                    <p>标签 : <a href="#"><s:property value="#bookProfile.category2"/></a></p>
                </div>
            </div>
            <div class="clearfix"> </div>
        </div>
    </div>
</div>
<!-- collapse -->
<div class="collpse tabs">
    <div class="container">
        <div class="panel-group collpse" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingOne">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            内容简介
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <s:property value="#bookProfile.intro"/>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingTwo">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            其他信息
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                    <div class="panel-body">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="tip"></div>
<jsp:include page="footer.jsp"/>


</body>
</html>
