package Utils.DAO.ccbToPretenz;

import Utils.TimerForLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * class for inserting new data to pretenz from external source
 */
@Component
public class PretenzResultSetInserter implements ResultSetExtractor {
    private Connection connectionPretenz;
    private String query;
    private final int BULK_SIZE = 1000;

//    @Autowired
    public PretenzResultSetInserter(Connection connectionPretenz) {
        this.connectionPretenz = connectionPretenz;
    }


    void setQuery(String query) {
        this.query = query;
    }

    /**
     * Extracts data from ResultSet and
     * runs batch insert statement
     **/
    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        try (PreparedStatement preparedStatementPretanz = connectionPretenz.prepareStatement(query)) {
            connectionPretenz.setAutoCommit(false);
            int bulkSize = BULK_SIZE;
            while (rs.next()) {
                for (int index = 1; index <= columnCount; index++) {
                    switch (metaData.getColumnTypeName(index)) {
                        case "VARCHAR2":
                        case "CHAR":
                            preparedStatementPretanz.setString(index, rs.getString(index));
                            break;
                        case "NUMBER":
                            preparedStatementPretanz.setDouble(index, rs.getDouble(index));
                            break;
                        case "DATE":
                            preparedStatementPretanz.setDate(index, rs.getDate(index));
                    }
                }
                preparedStatementPretanz.addBatch();
                if (--bulkSize <= 0) {
                    preparedStatementPretanz.executeBatch();
                    bulkSize = BULK_SIZE;
                }
            }
            if (bulkSize < BULK_SIZE)
                preparedStatementPretanz.executeBatch();
            connectionPretenz.commit();
        }
        return null;
    }
}
