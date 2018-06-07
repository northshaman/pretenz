package Utils;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.servlet.ServletException;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Class for managing csv files
 **/
public class CSVManager implements ResultSetExtractor{
    private String filePath;

    public CSVManager(String filePath) {
        this.filePath = filePath;
    }

    public File getCSVFile() throws ServletException{
        File file = new File(filePath);
        if (file.exists())
            return file;
        else {
            throw new ServletException("csv file doesn't exist");
        }
    }

    /**
     * Creates csv file from ResultSet
     * @param rs record set from DB
     * @param filePath is path to File
     * @throws SQLException from DB problems
     * @throws IOException from files problems
     */
    private void convertToCsv(ResultSet rs, String filePath) throws SQLException, IOException {
        File file = new File(filePath);
        file.createNewFile();
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp1251"))) {
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            StringBuilder dataHeaders = new StringBuilder(meta.getColumnName(1));
            for (int i = 2; i < numberOfColumns + 1; i++)
                dataHeaders.append(";").append(meta.getColumnName(i));
            bw.write(dataHeaders.toString());
            bw.newLine();

            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                if (rs.getString(1) != null)
                    row.append(rs.getString(1).replaceAll("[;\"\n]", "."));
                else row.append(" ");
                for (int i = 2; i < numberOfColumns + 1; i++) {
                    if (rs.getString(i) != null)
                        row.append(";").append(rs.getString(i).replaceAll("[;\"\n]", "."));
                    else
                        row.append("; ");
                }
                bw.write(row.toString());
                bw.newLine();
            }
        }
    }

    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        try {
            convertToCsv(rs, filePath);
        } catch (IOException e){
            System.out.println("ERROR CREATING CSV FILE");
        }
        return null;
    }
}
