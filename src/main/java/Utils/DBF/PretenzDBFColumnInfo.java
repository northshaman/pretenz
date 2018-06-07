package Utils.DBF;

/**
 * stores info about a column in pretenz dbf file
 **/
class PretenzDBFColumnInfo {
    private String columnName;
    private int sqlParameterNumber;
    private String columnType;

    /**
     * @param columnName - name of column
     * @param sqlParameterNumber - index of parameter in SQL statement in which this column should be put in
     * @param columnType - type of data in column
     */
    PretenzDBFColumnInfo(String columnName, int sqlParameterNumber, String columnType) {
        this.columnName = columnName;
        this.sqlParameterNumber = sqlParameterNumber;
        this.columnType = columnType;
    }

    String getColumnName() {
        return columnName;
    }

    int getSqlParameterNumber() {
        return sqlParameterNumber;
    }

    String getColumnType() {
        return columnType;
    }
}
