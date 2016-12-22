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
        String path = request.getParameter("path");

        LearningModel learningModel = new LearningModel(tweet, path);
        String categoryBayes = learningModel.trainingNaiveBayes();
        //String categoryKNN = learningModel.trainingKNN();
        //String categoryTree = learningModel.trainingJ48();

        HttpSession session = request.getSession();
        session.setAttribute("bayes", categoryBayes);
        //session.setAttribute("knn", categoryKNN);
        //session.setAttribute("tree", categoryTree);
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
