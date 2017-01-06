package training;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mgunes on 24.12.2016.
 */
@WebServlet(name = "FindSentimentServlet", urlPatterns = {"/findsentiment"})
public class FindSentimentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tweet = request.getParameter("tweet");

        TfIdf tfIdf = new TfIdf(tweet);

        String category = tfIdf.findCategory();
        String sentiment = tfIdf.findSentiment();

        HttpSession session = request.getSession();
        session.setAttribute("category", category);
        session.setAttribute("sentiment", sentiment);

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}