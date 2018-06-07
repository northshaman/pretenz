package Services;

import SQl.SQLScenarioMapRepository;
import Utils.DAO.PretenzDAO;
import Utils.DAO.ccbToPretenz.CCBToPretenzTransferer;
import models.UpdatePeriod;
import models.UpdateScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class for making and updating fields in tables PRETENZ
 **/
@Service
public class TableUpdaterImpl implements TableUpdater {
    private Map<Integer, UpdateScenario> scenarioMap;
    private CCBToPretenzTransferer ccbToPretenzTransferer;
    private PretenzDAO pretenzDAO;

    @Autowired
    public TableUpdaterImpl(SQLScenarioMapRepository mapRepository, CCBToPretenzTransferer ccbToPretenzTransferer,
                            PretenzDAO pretenzDAO) {
        this.scenarioMap = mapRepository.getScenarioMap();
        this.ccbToPretenzTransferer = ccbToPretenzTransferer;
        this.pretenzDAO = pretenzDAO;
    }

    @Override
    public void updateCurrentQuery(int numberOfQuery) throws InterruptedException {
        UpdateScenario scenario = scenarioMap.get(numberOfQuery);
        List<Runnable> runnables = new ArrayList<>();
        runnables.add(
                () -> clearInsertUpdate(scenario.getSelectStatement(), scenario.getInsertStatement(), scenario.getUpdateStatement(), scenario.getTableName())
        );
        executeMultipleThreads(runnables);
    }

    @Override
    public void updateDailyQueries() throws InterruptedException {
        executeMultipleThreads(getRunnables(getDailyScenarios()));
    }

    @Override
    public void updateMonthlyQueries() throws InterruptedException {
        executeMultipleThreads(getRunnables(getMonthlyScenarios()));
    }

    @Override
    public void updateAllQuery() throws InterruptedException {
        executeMultipleThreads(getRunnables(scenarioMap));
    }

    /**
     * @param period daily\mothly
     * @return filtered map
     */
    private Map<Integer,UpdateScenario> filterMapByPeriod(UpdatePeriod period){
        return scenarioMap.entrySet().stream().filter(
                t -> period.equals(t.getValue()
                        .getUpdatePeriod()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer,UpdateScenario> getDailyScenarios(){
        return filterMapByPeriod(UpdatePeriod.DAILY);
    }

    private Map<Integer,UpdateScenario> getMonthlyScenarios(){
        return filterMapByPeriod(UpdatePeriod.MONTHLY);
    }

    /**
     * Method - wrapper for Scenario map
     * @return List of Runnable Scenarios
     */
    private List<Runnable> getRunnables(Map<Integer,UpdateScenario> map) {
        List<Runnable> runnables = new ArrayList<>();
        map.forEach((k, v) ->
            runnables.add(
                    () -> {
                        Thread.currentThread().setName(v.getTableName());
                        clearInsertUpdate(v.getSelectStatement(), v.getInsertStatement(), v.getUpdateStatement(), v.getTableName());
                    }

            )
        );
        return runnables;
    }

    /**
     * Create pool of threads and run each in a separate thread
     * @param runnables List of Runnable Scenarios
     * @throws InterruptedException really?
     */
    private void executeMultipleThreads(List<Runnable> runnables) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        runnables.forEach(service::execute);
        service.shutdown();
        service.awaitTermination(2, TimeUnit.HOURS);
    }

    public void clearInsertUpdate(String selectStatement, String insertStatement, String updateStatement, String tableName) {
        pretenzDAO.clearTable(tableName);
        ccbToPretenzTransferer.insertIntoPretenz(selectStatement, insertStatement);
        pretenzDAO.makeTransaction(updateStatement);
    }

}
