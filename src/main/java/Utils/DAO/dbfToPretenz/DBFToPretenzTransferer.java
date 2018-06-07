package Utils.DAO.dbfToPretenz;

import Utils.DBF.ColumnNamesAndParameterNumbers;
import Utils.DBF.DBFManager;
import Utils.DBF.PretenzDBFManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * class for transferring data from dbf to pretenz
 **/
@Component
public class DBFToPretenzTransferer {
    private final int BULK_SIZE = 1000;
    private Connection connectionPretenz;

    @Autowired
    public DBFToPretenzTransferer(Connection connectionPretenz) {
        this.connectionPretenz = connectionPretenz;
    }

    public void makeInsertFromDBF(String insertStatement, String fileName) throws SQLException, ServletException {
        String distrinctN = fileName.substring(2, 4);
        try (DBFManager dbfManager = new PretenzDBFManager(fileName);
             PreparedStatement preparedStatement = connectionPretenz.prepareStatement(insertStatement)) {
            ColumnNamesAndParameterNumbers columns = new ColumnNamesAndParameterNumbers();
            connectionPretenz.setAutoCommit(false);
            int counter = 0;
            int bulkSize = BULK_SIZE;

            while (dbfManager.next()) {
                ++counter;
                preparedStatement.setString(1, distrinctN);
                for (int i = 2; i <= 23; i++) {
                    if (i == 6) { //IT_EP+IT_E
                        preparedStatement.setBigDecimal(6, ((BigDecimal) dbfManager.getCell(columns.getName(i))).add((BigDecimal) dbfManager.getCell("IT_EP")));
                        continue;
                    }

                    switch (columns.getType(i)) {
                        case "String":
                            preparedStatement.setString(i, ((String) dbfManager.getCell(columns.getName(i))).replaceAll("\u0000", ""));
                            break;
                        case "BigDecimal":
                            preparedStatement.setBigDecimal(i, (BigDecimal) dbfManager.getCell(columns.getName(i)));
                            break;
                        case "Date":
                            java.sql.Date sqlDate = null;
                            if (dbfManager.getCell(columns.getName(i)) != null) {
                                java.util.Date utilDate = (java.util.Date) dbfManager.getCell(columns.getName(i));
                                sqlDate = new java.sql.Date(utilDate.getTime());
                            }
                            preparedStatement.setDate(i, sqlDate);
                    }
                }
                preparedStatement.addBatch();
                if (--bulkSize <= 0) {
                    preparedStatement.executeBatch();
                    bulkSize = BULK_SIZE;
                }
            }
            if (bulkSize < BULK_SIZE)
                preparedStatement.executeBatch();
            connectionPretenz.commit();
        }
    }
}
