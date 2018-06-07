package Services;

/**
 * Inerface for making updates with tables
 */
public interface TableUpdater{
    void updateCurrentQuery(int numberOfQuery) throws InterruptedException;
    void updateDailyQueries() throws InterruptedException;
    void updateAllQuery() throws InterruptedException;
    void updateMonthlyQueries() throws InterruptedException;
}
