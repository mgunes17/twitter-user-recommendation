<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mgunes
  Date: 10.01.2017
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>İstatistik</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="menu.html"/>
        <div class="jumbotron container-fluid">
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-4">
                    <c:forEach var="item" items="${categoryList}">
                        <h3>${item.title}</h3>
                        <table>
                            <thead>
                            <tr>
                                <th>Kelime</th>
                                <th>Sayısı</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="word" items="${item.wordFrequencies}">
                                <tr>
                                    <td>${word.wordCategoryFrequencyPK.word}</td>
                                    <td>${word.count}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
