<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/16
  Time: 下午5:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="<%=path%>/permissionAction/LoginAuth">
    姓名：<input type="text" name="username"/><br/>
    密码：<input type="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>
</body>
</html>
