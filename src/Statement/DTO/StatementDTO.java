package Statement.DTO;

public class StatementDTO {

    private String accountNum;
    private String year;
    private String month;

    private String yearMonth;

    public StatementDTO() {
    }

    public StatementDTO(String accountNum, String year, String month, String yearMonth) {
        this.accountNum = accountNum;
        this.year = year;
        this.month = month;
        this.yearMonth = yearMonth;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
