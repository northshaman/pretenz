package SQl;

import models.QueryDateParams;
import models.UpdateScenario;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for getting Map of SQL Scenarios
 */
@Repository
public class SQLScenarioMapRepository {
    private final  SQLGetter sqlGetter = new SQLGetter();
    private final  Map<Integer, UpdateScenario> scenarioMap = new HashMap<>();

    public void setScenarioMap(QueryDateParams dateParams) {
        for (int i = 0; i < sqlGetter.getFilesQuantity(); i++) {
            UpdateScenario scenario = new UpdateScenario(
                    "CASH_CCB_TABLE_Q" + i,
                    sqlGetter.getSelectStatement(i, dateParams),
                    sqlGetter.getInsertStatement(i),
                    sqlGetter.getUpdateStatement(i),
                    sqlGetter.getUpdatePeriod(i));
            scenarioMap.put(i, scenario);
        }
    }

    public  Map<Integer, UpdateScenario> getScenarioMap() {
        return scenarioMap;
    }

}
