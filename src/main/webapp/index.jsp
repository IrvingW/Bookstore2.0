<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/18
  Time: 下午8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title><s:text name="global.title"/></title>
</head>
<body>
<s:action name="header" executeResult="true" namespace="/"/>
<div class="banner">
    <div class="container">
        <!-- <h2 class="hdng">在线<span>图书</span>商店</h2>  -->
        <h2 class="hdng"><s:text name="global.title1"/><span><s:text name="global.title2"/></span><s:text name="global.title3"/></h2>
        <!-- <p>开始阅读吧！</p> -->
        <p><s:text name="global.title4"/></p>
        <a href="<%=path%>/bookAction/showAllBooks"><s:text name="global.title5"/></a>
        <div class="banner-text">
            <img src="<%=path%>/images/index.jpeg" alt=""/>
        </div>
    </div>
</div>
<div class="gallery">
    <div class="container">
        <div class="gallery-grids">
            <h3 align="center"><s:text name="global.title6"/></h3><br>
            <!--struts 迭代器 迭代8次 -->
            <s:iterator value="#recommendBookList" status="#st">
                <div class="col-md-3 gallery-grid ">
                    <a href="<%=path%>/bookAction/showBookProfile?bookID=<s:property value="bookID"/>"><img src="<%=path%>/imageAction/showImage?imageID=<s:property value="imageID"/>" class="img-thumbnail" alt=""/>
                        <div class="gallery-info">
                            <p><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>浏览</p>
                            <a class="shop" href="<%=path%>/bookAction/showBookProfile?bookID=<s:property value="bookID"/>">查看详情</a>
                            <div class="clearfix"> </div>
                        </div>
                    </a>
                    <div class="galy-info">
                        <p><s:property value="bookName"/></p>
                    </div>
                </div>
            </s:iterator>

        </div>
        <div id="tip"> </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
