package Utils.DBF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * stores info about all of the pretenz dbf file columns
 **/
public class ColumnNamesAndParameterNumbers {
    private List<PretenzDBFColumnInfo> columnInfos = new ArrayList<>(30);
    private Logger logger = LoggerFactory.getLogger("pretenzLogger");

    /**
     * initializes arraylist with settings for reading pretenz dbf file
     */
    public ColumnNamesAndParameterNumbers(){
        columnInfos.add(new PretenzDBFColumnInfo("ACCT_ID",2, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("FIO",3, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("MES_OPL",4, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("IT_ED",5, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("IT_E",6, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("IT_EP",6, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("P_ND",7, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("P_NN",8, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("P_NP",9, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("LastPD",10, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("LastPN",11, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("LastPP",12, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("NDOMID",13, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("PREM_TYPE",14, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("DLastP",15, "Date"));
        columnInfos.add(new PretenzDBFColumnInfo("GOROD",16, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("POSELOK",17, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("UL",18, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("DOM",19, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("LIT",20, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("KORP",21, "BigDecimal"));
        columnInfos.add(new PretenzDBFColumnInfo("KV",22, "String"));
        columnInfos.add(new PretenzDBFColumnInfo("NROOM",23, "BigDecimal"));
    }

    /**
     * @param parameterNum - index of parameter in SQL statement
     * @return name of dbf column to put in this parameter
     */
    public String getName(int parameterNum){
        for (PretenzDBFColumnInfo p : columnInfos){
            if (p.getSqlParameterNumber() == parameterNum)
                return p.getColumnName();
        }
        logger.info("column index not found: " + parameterNum);
        return "";
    }

    /**
     * @param parameterNum - index of parameter in SQL statement
     * @return type of dbf cell object to put in this parameter
     */
    public String getType(int parameterNum){
        for(PretenzDBFColumnInfo p : columnInfos){
            if(p.getSqlParameterNumber() == parameterNum)
                return p.getColumnType();
        }
        logger.info("column type not found: " + parameterNum);
        return "";
    }
}
