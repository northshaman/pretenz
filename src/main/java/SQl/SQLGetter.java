package SQl;

import Utils.ConfigManager;
import models.QueryDateParams;
import models.QueryType;
import models.UpdatePeriod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static models.QueryType.INSERT_QUERY;
import static models.QueryType.SELECT_QUERY;
import static models.QueryType.UPDATE_QUERY;

/**
 * Class for get sql statements from files
 */

public class SQLGetter {

    private int filesQuantity;

    public int getFilesQuantity() {
        return filesQuantity;
    }

    public SQLGetter() {
        this.filesQuantity = this.getQueryQuantity(new File(getClass().getResource("/sqlStorage/select").getPath()));
    }

    /**
     * @param index number of query file
     * @return select SQL Statement
     */
    public String getSelectStatement(int index) {
        return getSelectQueryFromFile(getCurrentQueryStatementFilePath(index,SELECT_QUERY),
                new QueryDateParams("","","",""));
    }

    /**
     *
     * @param index number of query file
     * @param dateParams Storage bean whith current\otchet dates
     * @return select SQL Statement
     */
    public String getSelectStatement(int index, QueryDateParams dateParams) {
        return getSelectQueryFromFile(getCurrentQueryStatementFilePath(index, SELECT_QUERY),dateParams);
    }

    public String getUpdateStatement(int index){
        return getQueryFromFile(getCurrentQueryStatementFilePath(index,UPDATE_QUERY));
    }

    public String getInsertStatement(int index){
        return getQueryFromFile(getCurrentQueryStatementFilePath(index,INSERT_QUERY));
    }

    /**
     * Magic method, which replace all of the
     *  magic annotations in SQL files "@InsertCurrentDateParam" and "@InsertOtchetDateParam" to
     * TO_DATE('" + currentmonth + "." + currentyear + "', 'mm.yyyy') or
     * TO_DATE('" + otchetmonth + "." + otchetyear + "', 'mm.yyyy') respectively
     * @param
     */
    private String getSelectQueryFromFile(String absoluteFilePath, QueryDateParams dateParams) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
                Files.lines(Paths.get(absoluteFilePath), StandardCharsets.UTF_8).forEach(l -> {
                    stringBuilder.append(" ");
                    if (l.contains("@InsertCurrentDateParam"))
                    stringBuilder.append(l.replaceAll("@InsertCurrentDateParam", "TO_DATE('" + dateParams.getCurrentMonth() + "." + dateParams.getCurrentYear() + "', 'mm.yyyy')"));
                    else if (l.contains("@InsertOtchetDateParam"))
                    stringBuilder.append(l.replaceAll("@InsertOtchetDateParam", "TO_DATE('" + dateParams.getOtchetMonth() + "." + dateParams.getOtchetYear() + "', 'mm.yyyy')"));
                    else
                        stringBuilder.append(l);
                });
            } catch (IOException e) {
            // TODO: add log here
        }
        return stringBuilder.toString();
    }

    private String getQueryFromFile(String absoluteFilePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.lines(Paths.get(absoluteFilePath), StandardCharsets.UTF_8).forEach(
                    l -> {
                        stringBuilder.append(" ");
                        stringBuilder.append(l);
                    }
            );
        } catch (IOException e) {
            // TODO: add log here
        }
        return stringBuilder.toString();
    }

    /**
     *
     * @param index number of statement
     * @param type Enum as query type
     * @return filepath
     */
    private String getCurrentQueryStatementFilePath(int index, QueryType type){
        File file;
        switch (type){
            case INSERT_QUERY:
                file =  new File(getClass().getResource("/sqlStorage/insert/i"+index+".sql").getPath());
                break;
            case UPDATE_QUERY:
                file =  new File(getClass().getResource("/sqlStorage/update/u"+index+".sql").getPath());
                break;
            case SELECT_QUERY:
            file =  new File(getClass().getResource("/sqlStorage/select/s"+index+".sql").getPath());
                break;
            default:
                file = null;
        }
        return file.getAbsolutePath();
    }

    /**
     * @return Quantity of files in Select dir
     */
    private int getQueryQuantity(File folder) {
        File[] folderEntries = folder.listFiles();
        int counter=0;
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                    getQueryQuantity(entry);

                continue;
            }
            // иначе вам попался файл, считаем!
            counter++;
        }
        return counter;
    }

    public UpdatePeriod getUpdatePeriod(int number){
        try (InputStream inputStream = ConfigManager.class.getResourceAsStream("/sqlTypes.properties")){
            Properties props = new Properties();
            props.load(inputStream);
            String propValue = props.getProperty(String.valueOf(number));
            if (propValue!=null && "month".equals(propValue))
                return UpdatePeriod.MONTHLY;
            else
                if((propValue!=null && "day".equals(propValue)))
                    return UpdatePeriod.DAILY;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Config file not find!");
        }
        return null;
    }

}