<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mgunes
  Date: 06.01.2017
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Hesap Analizi</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-3">
                    <form method="post" action="accountanalyze">
                        <div class="form-group">
                            <label for="username">Kullanıcı adı:</label>
                            <input type="text" name="username" id="username"/>
                        </div>
                        <button type="submit" class="btn btn-default">Analiz</button>
                    </form>
                </div>
                <div class="col-md-3">
                    <h4><b>Hesap Analizi</b></h4>
                    <table>
                        <tr>
                            <th>Tweet Sayısı</th>
                            <td></td>
                        </tr>
                        <tr>
                            <c:forEach var="item" items="${}">

                            </c:forEach>
                        </tr>
                    </table>
                </div>
                <div class="col-md-3">
                    <h4><b>En benzer kullanıcılar</b></h4>
                </div>
            </div>
        </div>
    </body>
</html>
