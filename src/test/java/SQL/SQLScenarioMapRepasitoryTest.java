package SQL;

import SQl.SQLScenarioMapRepository;
import models.QueryDateParams;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SQLScenarioMapRepasitoryTest {
    private static SQLScenarioMapRepository scenarioMapRepository;
    private static QueryDateParams defaultdateParams;

    @BeforeClass
    public static void setUp(){
        defaultdateParams = new QueryDateParams("05","2018","04","2018");
        scenarioMapRepository = new SQLScenarioMapRepository();
    }
    @Ignore
    @Test
    public void setScenarioMapTest(){
        scenarioMapRepository.setScenarioMap(defaultdateParams);
        System.out.println(scenarioMapRepository.getScenarioMap());
    }

}
