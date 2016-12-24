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
@WebServlet(name = "LabelCountServlet", urlPatterns = {"/labelcount"})
public class LabelCountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = Integer.parseInt(request.getParameter("count"));
        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();
        List<ParsedTweet> notLabeled = parsedTweetDAO.getRandomParsedTweet(count);

        TfIdf tfIdf = new TfIdf();
        Category category = null;
        Sentiment sentiment = null;

        for(ParsedTweet tweet: notLabeled) {
            String s = tweet.getOrderedWords().replaceAll("-", " ");
            tfIdf.setTweet(s);

            category = new Category(tfIdf.findCategoryID());
            sentiment = new Sentiment(tfIdf.findSentimentID());
            tweet.setCategory(category);
            tweet.setSentiment(sentiment);
        }

        parsedTweetDAO.updateParsedList(notLabeled);
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
