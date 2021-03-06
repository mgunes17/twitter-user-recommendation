package training;

import db.hibernate.ParsedTweetDAO;
import db.model.Category;
import db.model.ParsedTweet;
import db.model.Sentiment;

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
@WebServlet(name = "LabelAllSentimentServlet", urlPatterns = {"/labelallsentiment"})
public class LabelAllSentimentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();
        List<ParsedTweet> notCategorized = parsedTweetDAO.getNotLabeled();

        TfIdf tfIdf = new TfIdf();
        Sentiment sentiment = null;

        for(ParsedTweet tweet: notCategorized) {
            String s = tweet.getOrderedWords().replaceAll("-", " ");
            tfIdf.setTweet(s);

            sentiment = new Sentiment(tfIdf.findSentimentID());
            tweet.setSentiment(sentiment);
        }

        parsedTweetDAO.updateParsedList(notCategorized);

        HttpSession session = request.getSession();
        session.setAttribute("countLabeledTweet", notCategorized.size());

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
