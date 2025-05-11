package Statement;

import Exception.GeneralException;
import Interest.InterestCalculator;
import Statement.DTO.StatementDTO;
import Transaction.DTO.TransactionDTO;
import Transaction.Transaction;
import Utility.ConsoleUtils;
import Utility.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static Templates.ConsoleTemplates.*;

public class Statement {

    public void statementMenu() {
        boolean mainMenu = false;
        while (!mainMenu) {
            try {
                System.out.println(STATEMENT_INTRO);
                String input = ConsoleUtils.getInput();
                if (StringUtils.isEmpty(input)) {
                    System.out.println(MENU_OUTRO);
                    break;
                }
                statementHandler(input);
                System.out.println(MENU_OUTRO);
                mainMenu = true;
            } catch (Exception e) {
                System.out.println(ERROR + " - " + e.getMessage());
            }
        }
    }

    public void printStatement(String accountId, List<TransactionDTO> transactions) {
        System.out.println(); //Spacing for nicer UX
        System.out.printf((STATEMENT_HEADER) + "%n", accountId);
        System.out.printf((STATEMENT_TABLE_HEADER_FORMAT) + "%n", "Date", "Txn Id", "Type", "Amount", "Balance");
        transactions.stream().filter(txn ->
                        !StringUtils.isEmpty(txn.getDate()) &&
                                !StringUtils.isEmpty(txn.getType()) &&
                                Objects.nonNull(txn.getAmount()) &&
                                Objects.nonNull(txn.getBalanceAfter()))
                .forEach(txn -> System.out.printf((STATEMENT_TABLE_ROW_FORMAT) + "%n",
                        txn.getDate(),
                        txn.getTransactionId(),
                        txn.getType(),
                        txn.getAmount(),
                        txn.getBalanceAfter()));
        System.out.println();//Spacing for nicer UX
    }

    public void statementHandler(String input) throws GeneralException {
        StatementDTO request = convertToDTO(input);
        if (!validateStatementInput(request)) {
            throw new GeneralException(ERROR_STATEMENT_INVALID_ACCOUNT);
        }

        InterestCalculator intCalc = new InterestCalculator();
        intCalc.calculateInterest(request);
        printStatement(request.getAccountNum(), Transaction.getTransactionMap().get(request.getAccountNum()));
    }

    private boolean validateStatementInput(StatementDTO request) {
        Map<String, List<TransactionDTO>> accountTransactionMap = Transaction.getTransactionMap();
        return accountTransactionMap.containsKey(request.getAccountNum());
        //Free format so account will retrieve case sensitively.
    }

    private StatementDTO convertToDTO(String input) throws GeneralException {
        String[] inputRequest = input.split(" ");
        if (inputRequest.length != 2) {
            throw new GeneralException(ERROR_STATEMENT_INPUT);
        }
        StatementDTO resp = new StatementDTO();
        resp.setAccountNum(inputRequest[0]);
        if(inputRequest[1].length()<6){
            throw new GeneralException(ERROR_STATEMENT_INPUT);
        }
        resp.setYear(inputRequest[1].substring(0, 4));
        resp.setMonth(inputRequest[1].substring(4));
        resp.setYearMonth(inputRequest[1]);
        return resp;
    }





}
