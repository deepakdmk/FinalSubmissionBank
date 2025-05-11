package Transaction;

import Exception.GeneralException;
import Transaction.DTO.TransactionDTO;
import Transaction.DTO.TransactionDetailsDTO;
import Utility.BigDecimalUtils;
import Utility.ConsoleUtils;
import Utility.DateUtils;
import Utility.StringUtils;

import java.math.BigDecimal;
import java.util.*;

import static Templates.ConsoleTemplates.*;

public class Transaction {
    private static Map<String, List<TransactionDTO>> accountTransactionMap = new HashMap<>();

    public static Map<String, List<TransactionDTO>> getTransactionMap(){
        return accountTransactionMap;
    }

    public void transactionMenu() {
        boolean mainMenu = false;
        while (!mainMenu) {
            try {
                System.out.println(TRANSACTION_INTRO);
                String input = ConsoleUtils.getInput();
                if (StringUtils.isEmpty(input)) {
                    System.out.println(MENU_OUTRO);
                    break;
                }
                transactionHandler(input);
                System.out.println(MENU_OUTRO);
                mainMenu = true;
            } catch (Exception e) {
                System.out.println(ERROR + " - " + e.getMessage());
            }
        }
    }

    public void printTransactionStatement(String accountId, List<TransactionDTO> transactions) {
        System.out.println(); //Spacing for nicer UX
        System.out.printf((TRANSACTION_HEADER) + "%n", accountId);
        System.out.printf((TRANSACTION_TABLE_HEADER_FORMAT) + "%n", "Date", "Txn Id", "Type", "Amount");
        transactions.stream().filter(txn ->
                        !StringUtils.isEmpty(txn.getDate()) &&
                                !StringUtils.isEmpty(txn.getTransactionId()) &&
                                !StringUtils.isEmpty(txn.getType()) &&
                                Objects.nonNull(txn.getAmount()))
                .forEach(txn -> System.out.printf((TRANSACTION_TABLE_ROW_FORMAT) + "%n",
                        txn.getDate(),
                        txn.getTransactionId(),
                        txn.getType(),
                        txn.getAmount()));
        System.out.println();//Spacing for nicer UX
    }

    public void transactionHandler(String input) throws GeneralException {
        TransactionDetailsDTO request = convertInputToDTO(input);
        if (!validateTransactionDetails(request)) {
            throw new GeneralException(ERROR_TRANSACTION_INPUT);
        }
        //create the transaction number
        createTransactionNumber(request);
        TransactionDTO txn = createTransaction(request);
        //Insert into Map
        addTransaction(request.getAccountNumber(), txn);
        printTransactionStatement(request.getAccountNumber(), getTransactions(request.getAccountNumber()));
    }

    private TransactionDTO createTransaction(TransactionDetailsDTO request) {
        TransactionDTO txn = new TransactionDTO();
        txn.setTransactionId(request.getTransactionId());
        txn.setAmount(new BigDecimal(request.getAmount()));
        txn.setType(request.getTransactionType());
        txn.setDate(request.getDate());
        return txn;
    }

    private void createTransactionNumber(TransactionDetailsDTO request) {
        String requestDate = request.getDate();
        List<TransactionDTO> transactionList = getTransactions(request.getAccountNumber());

        long count = transactionList.stream().filter(txn -> txn.getDate().equals(requestDate)).count();

        Integer transactionNumber = (int) count + 1;
        String transactionId = request.getDate() + "-" + String.format("%02d", transactionNumber);
        request.setTransactionId(transactionId);
    }

    private void addTransaction(String accountNumber, TransactionDTO newTxn) throws GeneralException {
        List<TransactionDTO> txns = accountTransactionMap.computeIfAbsent(accountNumber, k -> new ArrayList<>());

        txns.add(newTxn);
        txns.sort(Comparator.comparing(TransactionDTO::getDate));

        //I'll recalculator the balanceAfter just incase someone inputs a backdated value.
        BigDecimal balance = BigDecimal.ZERO;
        for (TransactionDTO txn : txns) {
            BigDecimal amount = txn.getAmount();
            if (txn.getType().equalsIgnoreCase("w")) {
                amount = amount.negate();
            }
            balance = balance.add(amount);
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                txns.remove(newTxn);
                throw new GeneralException(ERROR_INVALID_TRANSACTION_NEGATIVE_BALANCE);
            }
            txn.setBalanceAfter(balance);
        }
    }

    private List<TransactionDTO> getTransactions(String accountNumber) {
        return accountTransactionMap.getOrDefault(accountNumber, new ArrayList<>());
    }

    private TransactionDetailsDTO convertInputToDTO(String input) throws GeneralException {
        String[] information = input.split(" ");
        if (information.length != 4) {
            throw new GeneralException(ERROR_TRANSACTION_INPUT);
        }
        TransactionDetailsDTO resp = new TransactionDetailsDTO();
        resp.setDate(information[0]);
        resp.setAccountNumber(information[1]);
        resp.setTransactionType(information[2]);
        resp.setAmount(information[3]);
        return resp;
    }

    private boolean validateTransactionDetails(TransactionDetailsDTO request) {
        return DateUtils.isValidDate(request.getDate()) && validateTransactionTypeInput(request.getTransactionType()) && BigDecimalUtils.ifBgThanZero(new BigDecimal(request.getAmount()));
    }

    private boolean validateTransactionTypeInput(String transactionType) {
        return StringUtils.equals(transactionType.trim().toLowerCase(), "w") || StringUtils.equals(transactionType.trim().toLowerCase(), "d");
    }
}
