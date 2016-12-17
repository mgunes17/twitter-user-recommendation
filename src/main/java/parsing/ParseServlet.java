package parsing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ercan on 17.12.2016.
 */
@WebServlet(name = "ParseServlet", urlPatterns = {"/parse"})
public class ParseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession httpSession = request.getSession();

        ParseAlgorithm parseAlgorithm = new ParseAlgorithm();
        int parsedTweetCount = parseAlgorithm.parseNewTweets();

        httpSession.setAttribute("isParsed", 1);
        httpSession.setAttribute("isSaved", 0);
        httpSession.setAttribute("parsedTweetCount", parsedTweetCount);
        System.out.println("asd");
        response.sendRedirect("index.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
