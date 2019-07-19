<%--
  Created by IntelliJ IDEA.
  User: I am
  Date: 15.07.2019
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>List with Meal</title>
</head>
<body>

<c:forEach items="${listMealTo}" var="listMealTo">
    <c:out value="${component.name}" />
</c:forEach>

<c:if test="${not empty listMealTo}">
<table style="border: 1px solid black; width: 500px; text-align:center;">
    <thead style="background:#65b9ff">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Excess</th>
        <th colspan="3"></th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="listMealTo" scope="request" type="java.util.List"/>
    <c:forEach items="${listMealTo}" var="listMealTo">
        <tr style="background-color:${listMealTo.isExcess() ? 'green' : 'red'}">
            <td>
                <fmt:parseDate value="${ listMealTo.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td><c:out value="${listMealTo.getDescription()}" /></td>
            <td><c:out value="${listMealTo.getCalories()}" /></td>
            <td><c:out value="${listMealTo.isExcess()}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</c:if>
</body>
</html>
