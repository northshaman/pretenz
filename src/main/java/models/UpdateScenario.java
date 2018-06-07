package models;

/**
 * Class describe sql entity for make some operations with tables
 */
public class UpdateScenario {
    private String tableName;
    private String selectStatement;
    private String insertStatement;
    private String updateStatement;
    private UpdatePeriod updatePeriod;


    public UpdateScenario(String tableName, String selectStatement, String insertStatement, String updateStatement, UpdatePeriod period) {
        this.selectStatement = selectStatement;
        this.insertStatement = insertStatement;
        this.updateStatement = updateStatement;
        this.tableName = tableName;
        this.updatePeriod = period;
    }

    public String getTableName() {
        return tableName;
    }

    public String getSelectStatement() {
        return selectStatement;
    }

    public String getInsertStatement() {
        return insertStatement;
    }

    public String getUpdateStatement() {
        return updateStatement;
    }

    public UpdatePeriod getUpdatePeriod() {
        return updatePeriod;
    }

    @Override
    public String toString() {
        return "UpdateScenario{\n" +
                "->tableName='" + tableName + '\'' +"\n"+
                "->updatePeriod='" + updatePeriod + '\'' +"\n"+
                "-> selectStatement='" + selectStatement + '\'' +"\n"+
                "-> insertStatement='" + insertStatement + '\'' +"\n"+
                "-> updateStatement='" + updateStatement + '\'' +"\n"+
                '}'+"\n";
    }
}
