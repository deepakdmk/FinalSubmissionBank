import Interest.DTO.InterestDTO;
import Interest.DTO.InterestPeriodDTO;
import Interest.Interest;
import Interest.InterestCalculator;
import Statement.DTO.StatementDTO;
import Statement.Statement;
import Transaction.DTO.TransactionDTO;
import Transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import Exception.GeneralException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterestCalculatorTest {

    //This test follows the document example of interest calculation
    @Test
    public void testInterestCalculation() {
        createTransactions();
        createRules();
        StatementDTO request = createStatementRequest();

        InterestCalculator interestCalculator = new InterestCalculator();
        Statement statement = new Statement();

        interestCalculator.calculateInterest(request);
        Map<String, List<TransactionDTO>> accountMap = Transaction.getTransactionMap();
        List<TransactionDTO> transaction = accountMap.get("ACC01");

        statement.printStatement(request.getAccountNum(), Transaction.getTransactionMap().get(request.getAccountNum()));
        assertEquals(0, transaction.get(transaction.size() - 1).getBalanceAfter().compareTo(new BigDecimal("130.39")));
    }

    @Test
    void testfilterTransactionsForRequestMonth() {
        InterestCalculator testFilter = new InterestCalculator();
        StatementDTO statementDto = new StatementDTO();
        statementDto.setMonth("06");
        statementDto.setYear("2025");
        List<TransactionDTO> transactions = new ArrayList<>();
        TransactionDTO temp = new TransactionDTO();
        temp.setAmount(new BigDecimal("100"));
        temp.setDate("20250601");
        temp.setType("D");
        temp.setBalanceAfter(new BigDecimal("100"));
        temp.setTransactionId("20250601-01");
        transactions.add(temp);
        TransactionDTO temp2 = new TransactionDTO();
        temp2.setAmount(new BigDecimal("100"));
        temp2.setDate("20250701");
        temp2.setType("D");
        temp2.setBalanceAfter(new BigDecimal("100"));
        temp2.setTransactionId("20250701-01");
        transactions.add(temp2);
        System.out.println(transactions);
        List<TransactionDTO> resp = testFilter.filterTransactionsForRequestMonth(statementDto, transactions);
        System.out.println(resp);
        assertEquals(1, resp.size());
    }

    @Test
    void testGetInterestRateForStart() {
        List<InterestDTO> interestRules = new ArrayList<>();
        InterestDTO interestRuleOne = new InterestDTO();
        interestRuleOne.setRate(new BigDecimal("1.00"));
        interestRuleOne.setDate("20250101");
        interestRuleOne.setRuleId("RULE01");
        interestRules.add(interestRuleOne);

        InterestDTO interestRuleTwo = new InterestDTO();
        interestRuleTwo.setRate(new BigDecimal("2.00"));
        interestRuleTwo.setDate("20250201");
        interestRuleTwo.setRuleId("RULE02");
        interestRules.add(interestRuleTwo);

        String requestDate = "202502";

        InterestCalculator intCalc = new InterestCalculator();
        BigDecimal resp = intCalc.getInterestRate(requestDate, interestRules);
        assertEquals(0, resp.compareTo(new BigDecimal("2.00")));
    }

    @Test
    void testGetInterestRateForMiddleOfDay() {
        List<InterestDTO> interestRules = new ArrayList<>();
        InterestDTO interestRuleOne = new InterestDTO();
        interestRuleOne.setRate(new BigDecimal("1.00"));
        interestRuleOne.setDate("20250201");
        interestRuleOne.setRuleId("RULE01");
        interestRules.add(interestRuleOne);

        InterestDTO interestRuleTwo = new InterestDTO();
        interestRuleTwo.setRate(new BigDecimal("2.00"));
        interestRuleTwo.setDate("20250215");
        interestRuleTwo.setRuleId("RULE02");
        interestRules.add(interestRuleTwo);

        String requestDate = "202502";

        InterestCalculator intCalc = new InterestCalculator();
        BigDecimal resp = intCalc.getInterestRate(requestDate, "20", interestRules);
        assertEquals(0, resp.compareTo(new BigDecimal("2.00")));
        System.out.println(resp);
    }



    private StatementDTO createStatementRequest() {
        StatementDTO request = new StatementDTO();
        request.setAccountNum("ACC01");
        request.setYear("2023");
        request.setMonth("06");
        request.setYearMonth("202306");
        return request;
    }

    void createRules() {

        InterestDTO ruleOne = new InterestDTO();
        ruleOne.setRuleId("RULE01");
        ruleOne.setRate(new BigDecimal("1.95"));
        ruleOne.setDate("20230101");

        InterestDTO ruleTwo = new InterestDTO();
        ruleTwo.setRuleId("RULE02");
        ruleTwo.setRate(new BigDecimal("1.90"));
        ruleTwo.setDate("20230520");

        InterestDTO ruleThree = new InterestDTO();
        ruleThree.setRuleId("RULE03");
        ruleThree.setRate(new BigDecimal("2.20"));
        ruleThree.setDate("20230615");

        List<InterestDTO> interestDTOList = List.of(ruleOne, ruleTwo, ruleThree);
        Interest.getInterestRules().addAll(interestDTOList);

    }

    void createTransactions() {
        TransactionDTO txn = new TransactionDTO();
        txn.setTransactionId("20230505-01");
        txn.setDate("20230505");
        txn.setType("D");
        txn.setAmount(new BigDecimal("100.00"));
        txn.setBalanceAfter(new BigDecimal("100.00"));

        TransactionDTO txn2 = new TransactionDTO();
        txn2.setTransactionId("20230601-01");
        txn2.setDate("20230601");
        txn2.setType("D");
        txn2.setAmount(new BigDecimal("150.00"));
        txn2.setBalanceAfter(new BigDecimal("250.00"));

        TransactionDTO txn3 = new TransactionDTO();
        txn3.setTransactionId("20230626-01");
        txn3.setDate("20230626");
        txn3.setType("W");
        txn3.setAmount(new BigDecimal("20.00"));
        txn3.setBalanceAfter(new BigDecimal("230.00"));

        TransactionDTO txn4 = new TransactionDTO();
        txn4.setTransactionId("20230626-02");
        txn4.setDate("20230626");
        txn4.setType("W");
        txn4.setAmount(new BigDecimal("100.00"));
        txn4.setBalanceAfter(new BigDecimal("130.00"));

        List<TransactionDTO> txnList = new ArrayList<>(List.of(txn, txn2, txn3, txn4));
        Transaction.getTransactionMap().put("ACC01", txnList);
    }
}
