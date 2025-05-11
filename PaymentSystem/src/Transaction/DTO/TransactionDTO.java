package Transaction.DTO;

import java.math.BigDecimal;

public class TransactionDTO {

    private String date; //YYYYMMDD //we'll just convert it during business logic but save as String for DTO
    private String transactionId; // YYYYMMDD-xx , ill do a count based on read dates and +1
    private String type; // D W I only these 3 should be displayed
    private BigDecimal amount;
    private BigDecimal balanceAfter;

    public TransactionDTO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "date='" + date + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                '}';
    }
}
