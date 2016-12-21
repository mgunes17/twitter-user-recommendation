package training;

import db.hibernate.CategoryDAO;
import db.hibernate.ParsedTweetDAO;
import db.hibernate.SentimentDAO;
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
 * Created by mgunes on 18.12.2016.
 */
@WebServlet(name = "CreateTrainingDataServlet", urlPatterns = {"/createtrainingdata"})
public class CreateTrainingDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.getCategoryList();

        int randomCount = Integer.parseInt(request.getParameter("randomTweetCount"));
        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();
        List<ParsedTweet> parsedTweetList = parsedTweetDAO.getRandomParsedTweet(randomCount);

        SentimentDAO sentimentDAO = new SentimentDAO();
        List<Sentiment> sentimentList = sentimentDAO.getSentimentList();

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("categoryList", categoryList);
        httpSession.setAttribute("tweetList", parsedTweetList);
        httpSession.setAttribute("sentimentList", sentimentList);

        response.sendRedirect("tweet-siniflandir.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
