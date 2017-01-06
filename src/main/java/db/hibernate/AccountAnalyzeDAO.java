package db.hibernate;

import db.model.AccountAnalyze;

import java.util.List;

/**
 * Created by mgunes on 06.01.2017.
 */
public class AccountAnalyzeDAO extends AbstractDAO {
    public boolean saveAnalyzeList(List<AccountAnalyze> accountAnalyzes) {
        return saveList(accountAnalyzes);
    }
}
