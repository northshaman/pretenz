package Utils.DBF;

import com.linuxense.javadbf.DBFField;

import javax.servlet.ServletException;

/**
 * DBF manager for pretenz files
 **/
public class PretenzDBFManager extends DBFManager {
    public PretenzDBFManager(String fileName) throws ServletException{
        super(fileName);
    }

    /**
     * writes indexes of dbf columns
     */
    protected void initializeFieldsDBF(){
        for (int i = 0; i < getDbfReader().getFieldCount(); i++) {
            DBFField field = getDbfReader().getField(i);
            switch (field.getName()) {
                case "ACCT_ID" : getFieldsMap().put("ACCT_ID",i); break;
                case "GOROD" : getFieldsMap().put("GOROD",i); break;
                case "POSELOK" : getFieldsMap().put("POSELOK",i);  break;
                case "UL" : getFieldsMap().put("UL",i); break;
                case "DOM" : getFieldsMap().put("DOM",i); break;
                case "LIT" : getFieldsMap().put("LIT",i); break;
                case "KORP" : getFieldsMap().put("KORP",i); break;
                case "KV" : getFieldsMap().put("KV",i); break;
                case "NROOM" : getFieldsMap().put("NROOM",i); break;
                case "PREM_TYPE" : getFieldsMap().put("PREM_TYPE",i); break;
                case "FIO" : getFieldsMap().put("FIO",i); break;
                case "MES_OPL" : getFieldsMap().put("MES_OPL",i); break;
                case "IT_E" : getFieldsMap().put("IT_E",i); break;
                case "IT_EP" : getFieldsMap().put("IT_EP",i); break;
                case "IT_ED" : getFieldsMap().put("IT_ED",i); break;
                case "P_ND" : getFieldsMap().put("P_ND",i); break;
                case "P_NN" : getFieldsMap().put("P_NN",i); break;
                case "P_NP" : getFieldsMap().put("P_NP",i); break;
                case "LastPD" : getFieldsMap().put("LastPD",i); break;
                case "LastPN" : getFieldsMap().put("LastPN",i); break;
                case "LastPP" : getFieldsMap().put("LastPP",i); break;
                case "DLastP" : getFieldsMap().put("DLastP",i); break;
                case "NDOMID" : getFieldsMap().put("NDOMID",i); break;
                case "CM_OTDELEN" : getFieldsMap().put("CM_OTDELEN",i); break;
            }
        }
    }
}
