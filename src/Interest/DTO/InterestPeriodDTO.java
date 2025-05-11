package Interest.DTO;

import java.math.BigDecimal;

public class InterestPeriodDTO {

    int startDay;
    int endDay;
    BigDecimal rate;
    BigDecimal balance;

    public InterestPeriodDTO() {
    }

    public InterestPeriodDTO(int startDay, int endDay, BigDecimal rate, BigDecimal balance) {
        this.startDay = startDay;
        this.endDay = endDay;
        this.rate = rate;
        this.balance = balance;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "InterestPeriodDTO{" +
                "startDay=" + startDay +
                ", endDay=" + endDay +
                ", rate=" + rate +
                ", balance=" + balance +
                '}';
    }
}
