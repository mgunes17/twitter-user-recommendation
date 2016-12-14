package collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Created by mgunes on 14.12.2016.
 */
@WebServlet(name = "FetchingServlet", urlPatterns = {"/fetching"})
public class FetchingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConfigurationBuilder cfb = new ConfigurationBuilder();

        cfb.setDebugEnabled(true)
                .setOAuthConsumerKey("0M2bfJYlUWpnMM2iM67qBzp1j")
                .setOAuthConsumerSecret("BZhIUxlddgQJ6uBd1HjZrQFnrzJEum4BJU1kZxfHEuyvFQ7r1X")
                .setOAuthAccessToken("2587971211-ZqKWpwR2U589KURGoV7yKlUJ7yyNZaZIU7v0lxH")
                .setOAuthAccessTokenSecret("1WEUrc0LWiYJlluDMGLsP1HoDu2jlIs84TgkrAOplzSQd");

        TwitterFactory tf = new TwitterFactory(cfb.build());
        Twitter twitter = tf.getInstance();

        try {
            Paging paging = new Paging(1, 100);
            List<Status> status = twitter.getUserTimeline("mgunes17", paging);

            Query query = new Query("#ilanediyorum");
            query.count(100);
            QueryResult result = twitter.search(query);
            List<Status> status2 = result.getTweets();

            /*for(Status s1: status) {
                System.out.println(s1.getUser().getScreenName()  + "   " + s1.getText());
            }*/

            for(Status s2: status2) {
                System.out.println(s2.getUser().getScreenName()  + "   " + s2.);
                System.out.println("------------");

            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
