<%--
  Created by IntelliJ IDEA.
  User: Augie
  Date: 11/23/13
  Time: 1:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<s:actionerror/>
<s:form action="login">
    <s:textfield label="User ID" key="userId"/>
    <s:password label="Password" key="password"/>
    <s:submit/>
</s:form>
</body>
</html>