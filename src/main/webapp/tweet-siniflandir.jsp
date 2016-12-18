<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mgunes
  Date: 18.12.2016
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Twit Sınıflandır</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <form method="post" action="#">
                        <table class="table table-stripped">
                            <thead>
                                <tr>
                                    <th>Kelimeler</th>
                                    <th>Kategori</th>
                                    <th>Duygu</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${tweetList}">
                                    <tr>
                                        <td>${item.orderedWords}</td>
                                        <td>
                                            <select name="category" class="form-control">
                                                <c:forEach var="item" items="${categoryList}">
                                                    <option value="${item.id}">
                                                        <c:out value="${item.title}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select name="sentiment" class="form-control">
                                                <c:forEach var="item" items="${sentimentList}">
                                                    <option value="${item.id}">
                                                        <c:out value="${item.title}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <button type="submit" class="btn btn-default">Gönder</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
