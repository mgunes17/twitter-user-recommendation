<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Anasayfa</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <h2>Veri Seti Oluştur</h2>

                    <form method="post" action="fetchbyhashtag">
                        <div class="form-group">
                            <label for="hashtag">Hashtag:</label>
                            <input type="text" id="hashtag" name="hashtag" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="count">Tweet Sayısı</label>
                            <input type="text" id="count" name="count" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Getir</button>
                    </form>
                    <c:choose>
                        <c:when test="${isSaved == 1}">
                            <div class="alert alert-success">
                                tweetler başarıyla çekilip kaydedildi.
                            </div>
                        </c:when>
                        <c:when test="${isSaved eq 2}">
                            <div class="alert alert-danger">
                                bir hata oluştu.
                            </div>
                        </c:when>

                    </c:choose>
                </div>
            </div>
        </div>
    </body>
</html>
