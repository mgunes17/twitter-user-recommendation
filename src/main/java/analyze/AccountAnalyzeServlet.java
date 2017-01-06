package analyze;

import collection.FetchTweet;
import db.compositekey.AccountAnalyzePK;
import db.hibernate.AccountAnalyzeDAO;
import db.model.AccountAnalyze;
import db.model.Category;
import db.model.ParsedTweet;
import db.model.PlainTweet;
import parsing.ParseAlgorithm;
import training.TfIdf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgunes on 06.01.2017.
 */
@WebServlet(name = "AccountAnalyzeServlet", urlPatterns = {"/accountanalyze"})
public class AccountAnalyzeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        FetchTweet fetchTweet = new FetchTweet(username, 200);
        List<PlainTweet> plainTweetList = fetchTweet.getTweetsByUsername();

        ParseAlgorithm parseAlgorithm = new ParseAlgorithm();
        parseAlgorithm.parsePlainTweets(plainTweetList);
        List<ParsedTweet> parsedTweets = parseAlgorithm.getParsedTweets();

        TfIdf tfIdf = new TfIdf();
        int[] categoryWeight = new int[8];

        for (int i = 1; i < 8; i++) {
            categoryWeight[i] = 0;
        }

        for (ParsedTweet p : parsedTweets) {
            tfIdf.setTweet(p.getOrderedWords().replaceAll("-", " "));
            categoryWeight[tfIdf.findCategoryID()]++;
        }

        List<AccountAnalyze> accountAnalyzes = new ArrayList<AccountAnalyze>();

        for (int i = 1; i < 8; i++) {
            AccountAnalyze accountAnalyze = new AccountAnalyze();
            AccountAnalyzePK pk = new AccountAnalyzePK();

            pk.setCategory(new Category(i));
            pk.setUserName(username);
            accountAnalyze.setPk(pk);
            accountAnalyze.setWeight(categoryWeight[i] * 100 / plainTweetList.size());
        }

        AccountAnalyzeDAO accountAnalyzeDAO = new AccountAnalyzeDAO();
        accountAnalyzeDAO.saveAnalyzeList(accountAnalyzes);

        /*
            En yakın kullanıcıların listesini çek

            Kendi kayıtlarını sırayla çek sorguda Diğer i hariç
        */

        HttpSession session = request.getSession();
        response.sendRedirect("hesap-analizi.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
