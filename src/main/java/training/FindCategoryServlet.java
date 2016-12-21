package training;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mgunes on 21.12.2016.
 */
@WebServlet(name = "FindCategoryServlet", urlPatterns = {"/findcategory"})
public class FindCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String tweet = request.getParameter("tweet");
        LearningModel learningModel = new LearningModel(tweet);
        String category = learningModel.trainingNaiveBayes();
        HttpSession session = request.getSession();
        session.setAttribute("category", category);
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}