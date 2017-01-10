package training;

import db.hibernate.ParsedTweetDAO;
import db.model.ParsedTweet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by mgunes on 20.12.2016.
 */
@WebServlet(name = "CreateArffServlet", urlPatterns = {"/createarff"})
public class CreateArffServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();
        List<ParsedTweet> parsedTweetList = parsedTweetDAO.getLabeledTweet();

        String fileName = request.getParameter("name");
        String path = request.getParameter("path");
        String relation = request.getParameter("relation");
        ArffOperation arffOperation = new ArffOperation(fileName, path, relation);
        boolean operation = arffOperation.createArff(parsedTweetList);

        HttpSession session = request.getSession();
        session.setAttribute("isSaved", 0);

        if(operation == true) {
            session.setAttribute("arff", 1);
        } else {
            session.setAttribute("arff", 2);
        }

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
