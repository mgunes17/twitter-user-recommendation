package statistics;

import db.hibernate.WordCategoryFrequencyDAO;
import db.hibernate.WordSentimentFrequencyDAO;
import db.model.Category;
import db.model.Sentiment;
import db.model.WordCategoryFrequency;
import db.model.WordSentimentFrequency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by mgunes on 10.01.2017.
 */
@WebServlet(name = "StatisticInitServlet", urlPatterns = {"/statisticinit"})
public class StatisticInitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WordCategoryFrequencyDAO categoryFrequencyDAO = new WordCategoryFrequencyDAO();
        WordSentimentFrequencyDAO sentimentFrequencyDAO = new WordSentimentFrequencyDAO();

        WordStatistic wordStatistic = new WordStatistic();

        //kategori istatistik
        for(int i = 1; i < 7; i++) {
            List<WordCategoryFrequency> categoryList = categoryFrequencyDAO.getMaxWords(i);
            Category category = categoryList.get(0).getWordCategoryFrequencyPK().getCategory();
            wordStatistic.getCategoryList().put(category, categoryList);
        }

        //duygu istatistik
        for(int i = 1; i < 4; i++) {
            List<WordSentimentFrequency> sentimentList = sentimentFrequencyDAO.getMaxWords(i);
            Sentiment sentiment = sentimentList.get(0).getWordSentimentFrequencyPK().getSentiment();
            wordStatistic.getSentimentList().put(sentiment, sentimentList);
        }

        //top 20 kelime
        wordStatistic.setTopList(categoryFrequencyDAO.getTopList());

        HttpSession session = request.getSession();
        session.setAttribute("istatistik", wordStatistic);

        response.sendRedirect("istatistik.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}