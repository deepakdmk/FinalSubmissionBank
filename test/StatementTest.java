import Interest.DTO.InterestDTO;
import Interest.DTO.InterestPeriodDTO;
import Interest.Interest;
import Statement.DTO.StatementDTO;
import Transaction.DTO.TransactionDTO;
import Transaction.Transaction;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.nimbus.State;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Templates.ConsoleTemplates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import Exception.GeneralException;

import Statement.Statement;

public class StatementTest {

    private String globalAcc ="ACC01";

    @Test
    void testStatementInputHappyFlow() throws GeneralException {
        createRules();
        createTransactions();

        String req="ACC01 202306";
        Statement statement = new Statement();
        statement.statementHandler(req);
    }


    @Test
    void testStateInvalidInput(){
        String req="Random Words";
        Statement statement = new Statement();
        try {
            statement.statementHandler(req);
        } catch (GeneralException e) {
            assertEquals(ERROR_STATEMENT_INPUT,e.getMessage());
        }
    }

    @Test
    void testInvalidAccountStatementInput(){
        String req="ACC01 202506";
        Statement statement = new Statement();
        try {
            statement.statementHandler(req);
        } catch (GeneralException e) {
            assertEquals(ERROR_STATEMENT_INVALID_ACCOUNT,e.getMessage());
        }
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
