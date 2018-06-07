package models;

/**
 * Class for storage Date params of queries in SQL statements
 */
public class QueryDateParams {
    private String currentMonth, currentYear;
    private String otchetMonth, otchetYear;

    public QueryDateParams(String currentMonth, String currentYear, String otchetMonth, String otchetYear) {
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;
        this.otchetMonth = otchetMonth;
        this.otchetYear = otchetYear;
    }

    public QueryDateParams(){}

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getOtchetMonth() {
        return otchetMonth;
    }

    public void setOtchetMonth(String otchetMonth) {
        this.otchetMonth = otchetMonth;
    }

    public String getOtchetYear() {
        return otchetYear;
    }

    public void setOtchetYear(String otchetYear) {
        this.otchetYear = otchetYear;
    }

    @Override
    public String toString() {
        return "QueryDateParams{" +
                "currentMonth='" + currentMonth + '\'' +
                ", currentYear='" + currentYear + '\'' +
                ", otchetMonth='" + otchetMonth + '\'' +
                ", otchetYear='" + otchetYear + '\'' +
                '}';
    }
}
