import Transaction.DTO.TransactionDTO;
import Exception.GeneralException;
import Transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static Templates.ConsoleTemplates.ERROR_INVALID_TRANSACTION_NEGATIVE_BALANCE;
import static Templates.ConsoleTemplates.ERROR_TRANSACTION_INPUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionTest {

    private String globalAcc ="ACC01";

    @Test
    void testTransactionHandlerNewAccountShouldNotThrow() throws GeneralException {
        String req="20230505 ACC01 D 100";
        Transaction transaction = new Transaction();
        transaction.transactionHandler(req);
        Map<String, List<TransactionDTO>> resp = Transaction.getTransactionMap();
        assertTrue(resp.get(globalAcc).size()>.0);
    }

    @Test
    void testTransactionHandlerInvalidInput(){
        String req="Random words";
        Transaction transaction = new Transaction();
        try {
            transaction.transactionHandler(req);
        } catch (GeneralException e) {
           assertEquals(ERROR_TRANSACTION_INPUT,e.getMessage());
        }
    }

    @Test
    void testNegativeBalanceHandler(){
        String depositRequest="20230505 ACC01 D 100";
        String withdrawalRequest="20230605 ACC01 W 200";
        Transaction transaction = new Transaction();
        try {
            transaction.transactionHandler(depositRequest);
            transaction.transactionHandler(withdrawalRequest);
        } catch (GeneralException e) {
            assertEquals(ERROR_INVALID_TRANSACTION_NEGATIVE_BALANCE,e.getMessage());
        }
    }

    @Test
    void testBackdatedDepositBalanceHandler() throws GeneralException {
        List<TransactionDTO> accountMap = Transaction.getTransactionMap().get(globalAcc);
        accountMap.clear();
        //Cleanup transactions before testing
        String depositRequest="20230101 ACC01 D 100";
        String backedDepositRequest="20210605 ACC01 D 200";
        Transaction transaction = new Transaction();
        transaction.transactionHandler(depositRequest);
        transaction.transactionHandler(backedDepositRequest);
        Map<String, List<TransactionDTO>> resp = Transaction.getTransactionMap();
        List<TransactionDTO> transactions = resp.get(globalAcc);
        assertEquals(0, transactions.get(transactions.size() - 1).getBalanceAfter().compareTo(new BigDecimal("300")));
    }

}
