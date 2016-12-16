<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

                    <form method="post">
                        <div class="form-group">
                            <label for="keyword">Hashtag/username:</label>
                            <input type="text" id="keyword" name="keyword" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="count">Tweet Sayısı</label>
                            <input type="text" id="count" name="count" class="form-control">
                        </div>
                        <div class="form-group row">
                            <button class="btn btn-success" type="submit" formaction="fetchbyhashtag">Hashtag'e göre getir</button>
                            <button class="btn btn-primary" type="submit" formaction="fetchbyusername">Username'e göre getir</button>
                        </div>
                    </form>

                    <c:choose>
                        <c:when test="${isSaved == 1}">
                            <div class="alert alert-success">
                                tweetler başarıyla çekilip kaydedildi.
                                getirilen toplam tweet sayısı : ${tweetCount}"
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
