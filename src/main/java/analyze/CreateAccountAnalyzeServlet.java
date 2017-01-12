package analyze;

import db.hibernate.AccountAnalyzeDAO;
import db.hibernate.AccountWordAnalyzeDAO;
import db.model.AccountWordyAnalyze;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CreateAccountAnalyzeServlet", urlPatterns = {"/createaccounts"})
public class CreateAccountAnalyzeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountWordAnalyzeDAO accountWordAnalyzeDAO = new AccountWordAnalyzeDAO();
        List<AccountWordyAnalyze> accountWordyAnalyzeList = accountWordAnalyzeDAO.getAllRows("AccountWordyAnalyze");

        Map<String, List<Integer>> userMaps = new HashMap<String, List<Integer>>();

        List<String> usernames = new ArrayList<String>();

        int value;
        for(AccountWordyAnalyze awa: accountWordyAnalyzeList){
            List<Integer> list = new ArrayList<Integer>();
            list.add(0);
            list.add(0);
            list.add(0);
            list.add(0);
            list.add(0);
            list.add(0);
            list.add(0);
            String username = awa.getPk().getUserName();
            usernames.add(username);

            if(!userMaps.containsKey(username)){
                userMaps.put(username, list);
            } else {
                value = userMaps.get(username).get(awa.getPk().getCategory().getId() - 1);
                userMaps.get(username).set((awa.getPk().getCategory().getId() - 1), ++value);
            }
        }

        for(String usr: usernames){
            int total = 0;
            for(Integer count: userMaps.get(usr)){
                total += count;
            }
            int i = 0;
            for(Integer count: userMaps.get(usr)){
                userMaps.get(usr).set(i, (count * 100 / total));
                i++;
            }
        }

        AccountAnalyzeDAO accountAnalyzeDAO = new AccountAnalyzeDAO();
        accountAnalyzeDAO.saveUserMap(userMaps);
    }
}
