package Utils.DBF;

import Utils.FTP.FTPManager;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for working with DBF files
 */
public abstract class DBFManager implements AutoCloseable{
    private Logger logger = LoggerFactory.getLogger("pretenzLogFile");
    private Map<String,Integer> fieldsMap = new HashMap<>();
    private DBFReader dbfReader;
    private int rowCount;
    private Object[] currentRow;

    /**
     * initializes DBFReader, rowcount and calls {@link DBFManager#initializeFieldsDBF()}
     * @param fileName - name of dbf file on ftp
     * @throws ServletException - if file doesn't exist
     */
    DBFManager(String fileName) throws ServletException {
        FTPManager ftpManager = new FTPManager();
        try {
            this.dbfReader = new DBFReader(new FileInputStream(ftpManager.getFileCopy(fileName)));
        } catch (IOException e) {
            throw new ServletException("Файл не найден");
        }
        rowCount = dbfReader.getRecordCount();
        logger.info(" In file -> " + fileName + " " + rowCount + " rows.");
        dbfReader.setCharactersetName("cp866");
        initializeFieldsDBF();
    }

    Map<String, Integer> getFieldsMap() {
        return fieldsMap;
    }

    DBFReader getDbfReader() {
        return dbfReader;
    }

    /**
     * @param columnName - name of dbf column
     * @return object from column in current row
     */
    public Object getCell(String columnName) {
        return currentRow[fieldsMap.get(columnName)];
    }

    /**
     * writes indexes of dbf columns
     */
    protected abstract void initializeFieldsDBF();

    /**
     * gets next dbf row and makes sure it's not null
     * @return true if current row is not last
     */
    public boolean next(){
        if (rowCount>0) {
            currentRow = dbfReader.nextRecord();
            --rowCount;
            return currentRow!=null || this.next();
        } else
            return false;
    }

    public void close(){
        dbfReader.close();
    }

}
