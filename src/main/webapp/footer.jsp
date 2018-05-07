<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/8
  Time: 上午10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<div class="footer">
    <div class="container">
        <div class="footer-grids">
            <div class="col-md-2 footer-grid">
                <h4><s:text name="global.aboutMe1"/></h4>
                <ul>
                    <li><a href="#"><s:text name="global.aboutMe2"/></a></li>
                    <li><a href="#"><s:text name="global.aboutMe3"/></a></li>
                    <li><a href="#"><s:text name="global.aboutMe4"/></a></li>
                </ul>
            </div>
            <div class="col-md-2 footer-grid">
                <h4><s:text name="global.service1"/></h4>
                <ul>
                    <li><a href="#"><s:text name="global.service2"/></a></li>
                    <li><a href="#"><s:text name="global.service3"/></a></li>
                    <li><a href="#"><s:text name="global.service4"/></a></li>
                </ul>
            </div>
            <div class="col-md-2 footer-grid">
                <h4><s:text name="global.helpCenter"/></h4>
                <ul>
                    <li><a href="#"><s:text name="global.helpCenter2"/></a></li>
                </ul>
            </div>
            <div class="col-md-2 footer-grid">
                <h4><s:text name="global.langSelect"/></h4>
                <ul>
                    <li><a href="<%=path%>/locale.action?request_locale=en_US">English</a></li>
                    <li><a href="<%=path%>/locale.action?request_locale=zh_CN">中文</a></li>
                </ul>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!--//footer-->
<div class="footer-bottom">
    <div class="container">
        <p>Copyright &copy; 2018.Bookstore2.0 All rights reserved.</p>
    </div>
</div>

<script>

</script>