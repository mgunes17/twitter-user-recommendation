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
                <div class="col-md-3">
                    <c:forEach var="entry" items="${istatistik.categoryList}">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>${entry.key.title}</p>
                            </div>
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <c:forEach var="item" items="${entry.value}">
                                        <tr>
                                            <td class="text-left">
                                                <c:out value="${item.wordCategoryFrequencyPK.word}"/>
                                            </td>
                                            <td class="text-right">
                                                <c:out value="${item.count}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-md-3">
                    <c:forEach var="entry" items="${istatistik.sentimentList}">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>${entry.key.title}</p>
                            </div>
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <c:forEach var="item" items="${entry.value}">
                                        <tr>
                                            <td class="text-left">
                                                <c:out value="${item.wordSentimentFrequencyPK.word}"/>
                                            </td>
                                            <td class="text-right">
                                                <c:out value="${item.count}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-md-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <p>En fazla geçen kelimeler</p>
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover">
                                <c:forEach var="item" items="${istatistik.topList}">
                                    <tr>
                                        <td class="text-left">
                                            <c:out value="${item.word}"/>
                                        </td>
                                        <td class="text-right">
                                            <c:out value="${item.count}"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

