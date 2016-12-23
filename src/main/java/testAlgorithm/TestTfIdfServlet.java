package testAlgorithm;

import db.hibernate.ParsedTweetDAO;
import db.model.ParsedTweet;
import training.TfIdf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by mgunes on 24.12.2016.
 */
@WebServlet(name = "TestTfIdfServlet", urlPatterns = {"/testtfidf"})
public class TestTfIdfServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ParsedTweetDAO tweetDAO = new ParsedTweetDAO();
        List<ParsedTweet> parsedTweetList = tweetDAO.getLabeledTweet();

        int trueCount = 0;

        TfIdf tfIdf = new TfIdf();

        for(ParsedTweet tweet: parsedTweetList) {
            String s = tweet.getOrderedWords().replaceAll("-", " ");
            tfIdf.setTweet(s);
            if(tweet.getCategory().getId() == tfIdf.findCategoryID())
                trueCount++;
        }

        HttpSession session = request.getSession();
        session.setAttribute("tfidfsuccessrate", (double) trueCount / parsedTweetList.size());

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
