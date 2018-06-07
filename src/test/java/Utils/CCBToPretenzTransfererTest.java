package Utils;

import SQl.SQLGetter;
import Utils.DAO.ccbToPretenz.CCBToPretenzTransferer;
import config.AppConfig;
import models.QueryDateParams;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for SQL Getter
 **/
@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CCBToPretenzTransfererTest {
    @Autowired
    private CCBToPretenzTransferer transferer;

    @Test
    @Ignore
    public void insertIntoPretenztest(){
        SQLGetter sqlGetter = new SQLGetter();
        String select = sqlGetter.getSelectStatement(1, new QueryDateParams("05", "2018", "04", "2018"));
        String insert = sqlGetter.getInsertStatement(1);
        transferer.insertIntoPretenz(select,insert);
    }
}
