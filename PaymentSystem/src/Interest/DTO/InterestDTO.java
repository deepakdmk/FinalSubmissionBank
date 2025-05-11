package Interest.DTO;

import java.math.BigDecimal;

public class InterestDTO {

    private String date; //same as transaction YYYYMMdd

    private String ruleId;

    private BigDecimal rate;

    public InterestDTO() {
    }

    public InterestDTO(String date, String ruleId, BigDecimal rate) {
        this.date = date;
        this.ruleId = ruleId;
        this.rate = rate;
    }

    public InterestDTO(String s) {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "InterestDTO{" +
                "date='" + date + '\'' +
                ", ruleId='" + ruleId + '\'' +
                ", rate=" + rate +
                '}';
    }
}
