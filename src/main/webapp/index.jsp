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
        <jsp:include page="menu.html"/>
        <div class="jumbotron container-fluid">
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <p>Veri Seti Oluştur</p>
                        </div>
                        <div class="panel-body">
                            <c:choose>
                                <c:when test="${isSaved eq 1}">
                                    <div class="alert alert-success">
                                        tweetler başarıyla çekilip kaydedildi.
                                        getirilen toplam tweet sayısı : ${tweetCount}
                                    </div>
                                </c:when>
                                <c:when test="${isSaved eq 2}">
                                    <div class="alert alert-danger">
                                        bir hata oluştu.
                                    </div>
                                </c:when>
                            </c:choose>
                            <form method="post">
                                <div class="form-group">
                                    <label for="keyword">Hashtag/Username</label>
                                    <input type="text" id="keyword" name="keyword" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="count">Maximum Tweet Sayısı</label>
                                    <input type="text" id="count" name="count" value="500" class="form-control">
                                </div>
                                <div class="col-md-6">
                                    <button class="form-control btn btn-success" type="submit" formaction="fetchbyhashtag">Hashtag</button>
                                </div>
                                <div class="col-md-6">
                                    <button class="form-control btn btn-primary" type="submit" formaction="fetchbyusername">Kullanıcı Adı</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <p>Tweetleri Parse Et</p>
                        </div>
                        <div class="panel-body">
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
                            <form method="post" action="parse">
                                <div class="form-group">
                                    <button class="btn btn-success" type="submit" >Parse et</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <p>Tweet Etiketleyin</p>
                        </div>
                        <div class="panel-body">
                            <form method="post" action="createtrainingdata">
                                <div class="form-group">
                                    <label for="randomCount">Etiketlemek istediğiniz tweet sayısı:</label>
                                    <input type="text" name="randomTweetCount" id="randomCount" class="form-control">
                                </div>
                                <button type="submit" class="btn btn-success">Getir</button>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <p>Kategori ve Duygu Etiketle</p>
                        </div>
                        <div class="panel-body">
                            <form method="post" action="labelparsedtweets">
                                <div class="form-group">
                                    <label for="in">Etiketlenecek tweet sayısı:</label>
                                    <input type="text" class="form-control" name="count" id="in">
                                </div>
                                <button type="submit" class="btn btn-success">Etiketle</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <p>Tweet Test Et</p>
                        </div>
                        <div class="panel-body">
                            <c:out value="Kategori: ${tfidf}"/> <br/>
                            <c:out value="Duygu: ${sentiment}"/>
                            <form method="post" action="findcategory">
                                <div class="form-group">
                                    <label for="sentimentTweet">Tweet Gir</label>
                                    <input type="text" id="sentimentTweet" class="form-control" name="tweet"/>
                                </div>
                                <button type="submit" class="btn btn-success">Duygu Bul</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <p>Arff Oluştur</p>
                        </div>
                        <div class="panel-body">
                            <c:choose>
                                <c:when test="${arff eq 1}">
                                    <div class="alert alert-success">
                                        Arff dosyası oluşturuldu.
                                    </div>
                                </c:when>
                                <c:when test="${arff eq 2}">
                                    <div class="alert alert-danger">
                                        Bir hata meydana geldi.
                                    </div>
                                </c:when>
                            </c:choose>
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
            </div>
        </div>
    </body>
</html>
