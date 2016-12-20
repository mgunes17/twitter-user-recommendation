<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Anasayfa</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                            <label for="keyword">Hashtag/Username</label>
                            <input type="text" id="keyword" name="keyword" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="count">Maximum Tweet Sayısı</label>
                            <input type="text" id="count" name="count" value="500" class="form-control">
                        </div>
                        <div class="form-group row">
                            <button class="btn btn-success" type="submit" formaction="fetchbyhashtag">Hashtag'e göre getir</button>
                            <button class="btn btn-primary" type="submit" formaction="fetchbyusername">Username'e göre getir</button>
                        </div>
                    </form>

                    <c:choose>
                        <c:when test="${isSaved eq 1}">
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
                <div class="col-md-3">
                    <h2>Tweetleri Parse Et</h2>
                    <form method="post" action="parse">
                        <div class="form-group row">
                            <button class="btn btn-success" type="submit" >Parse et</button>
                        </div>
                    </form>
                    <c:choose>
                        <c:when test="${isParsed eq 1}">
                            <div class="alert alert-success">
                                parse edilen tweet sayısı : ${parsedTweetCount}"
                            </div>
                        </c:when>
                        <c:when test="${isParsed eq 2}">
                            <div class="alert alert-danger">
                                bir hata oluştu.
                            </div>
                        </c:when>
                    </c:choose>
                </div>
                <div class="col-md-2">
                    <form method="post" action="createtrainingdata">
                        <div class="form-group">
                            <label for="randomCount">Etiketlemek istediğiniz tweet sayısı:</label>
                            <input type="text" name="randomTweetCount" id="randomCount" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Getir</button>
                    </form>
                </div>
                <div class="col-md-2">
                    <h3>Arff Oluştur</h3>
                    <form method="post" action="createarff">
                        <div class="form-group">
                            <label for="name">Dosya Adı:</label>
                            <input type="text" name="name" id="name" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="path">Dosya yolu:</label>
                            <input type="text" name="path" id="path" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="relation">İlişki Adı:</label>
                            <input type="text" name="relation" id="relation" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Oluştur</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
