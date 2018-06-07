package Services;

import Utils.CSVManager;
import Utils.DAO.PretenzDAO;
import Utils.DAO.dbfToPretenz.DBFToPretenzTransferer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.sql.SQLException;

/**
 * class for making pretenz update/recreate actions
 **/
@Component
public class PretenzService {
    @Value("${csvStoragePath}")
    private String csvStoragePath;
    private PretenzDAO pretenzDAO;
    private DBFToPretenzTransferer dbfToPretenzTransferer;
    private TableUpdater tableUpdater;

    @Autowired
    public PretenzService(PretenzDAO pretenzDAO, DBFToPretenzTransferer dbfToPretenzTransferer, TableUpdater tableUpdater) {
        this.pretenzDAO = pretenzDAO;
        this.dbfToPretenzTransferer = dbfToPretenzTransferer;
        this.tableUpdater = tableUpdater;
    }

    public synchronized void fullTableReload(String otchetMonth) throws ServletException, SQLException, InterruptedException{
        loadFromMailDBF(otchetMonth);
        tableUpdater.updateAllQuery();
        updateReculcFields();
        convertMainTableToCSV();
        convertReportToCSV("ReportView1");
    }

    public synchronized void dailyTableUpdate()throws InterruptedException{
        tableUpdater.updateDailyQueries();
        updateReculcFields();
        convertMainTableToCSV();
        convertReportToCSV("ReportView1");
    }

    private void loadFromMailDBF(String otchetMonth) throws ServletException, SQLException {
        pretenzDAO.clearTable("cm_pretenz_all");
        String updateStatement = "insert into cm_pretenz_all t (\n" +
                "                            district_no,                --1\n" +
                "                            personal_count,             --2\n" +
                "                            fio,                        --3\n" +
                "                            counting_period,            --4\n" +
                "                            saldo_on_first_day_of_period,--5\n" +
                "                            charged_in_period,          --6\n" +
                "                            charged_counter_day,        --7\n" +
                "                            charged_counter_night,      --8\n" +
                "                            charged_counter_hight,      --9\n" +
                "                            last_kp_in_period_day,      --10\n" +
                "                            last_kp_in_period_night,    --11\n" +
                "                            last_kp_in_period_hight,    --12\n" +
                "                            id_parent_object,           --13\n" +
                "                            type_oo,                    --14\n" +
                "                            date_kp,                    --15\n" +
                "                            GOROD,                      --16\n" +
                "                            POSELOK,                    --17\n" +
                "                            STREET,                     --18\n" +
                "                            HOUSE,                      --19\n" +
                "                            LITERA,                     --20\n" +
                "                            KORPUS,                     --21\n" +
                "                            FLAT,                       --22\n" +
                "                            NROOM)                      --23\n" +


                "                values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        for (int distrinctN = 1; distrinctN <= 6; distrinctN++) {
            String fileName = "bd0" + distrinctN + "_" + otchetMonth + ".dbf";
            dbfToPretenzTransferer.makeInsertFromDBF(updateStatement, fileName);
        }
    }

    /**
     * Creates csv file from table CM_PRETENZ_ALL
     */
    private void convertMainTableToCSV() {
        CSVManager csvMaker = new CSVManager(csvStoragePath+"pretenz.csv");
        pretenzDAO.processSelectResult("SELECT * FROM CM_PRETENZ_ALL", csvMaker);
    }

    private void convertReportToCSV(String reportname){
        CSVManager csvMaker = new CSVManager(csvStoragePath+reportname+".csv");
        pretenzDAO.processSelectResult("SELECT * FROM "+reportname, csvMaker);
    }

    /**
     *  Method for run PLSQL Procedure
     * @return time of execution in  sec.
     */
    private void updateReculcFields() {
        pretenzDAO.callProcedure("UPDATECULCFIELDS");
    }
}
