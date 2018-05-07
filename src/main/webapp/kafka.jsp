<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/26
  Time: 下午7:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>kafka</title>
</head>
<body>
    <form action="<%=path%>/testKafka/send" method="post">
        <input type="text" name="address" placeholder="address"/>
        <input type="password" name="password" />
        <input type="submit" value="submit" />
    </form>
</body>
</html>
