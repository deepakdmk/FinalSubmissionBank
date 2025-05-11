package Interest;

import Interest.DTO.InterestDTO;
import Interest.DTO.InterestPeriodDTO;
import Statement.DTO.StatementDTO;
import Transaction.DTO.TransactionDTO;
import Transaction.Transaction;
import Utility.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InterestCalculator {

    /**
     * This will take in a StatementDTO request,
     * calculate the interest and add it into
     * the accountTransactionMap of the request account
     **/
    public void calculateInterest(StatementDTO request) {
        List<TransactionDTO> transactions = Transaction.getTransactionMap().get(request.getAccountNum());
        List<InterestDTO> interestRules = Interest.getInterestRules();

        //First we filter out the transactions for the requested month
        List<TransactionDTO> filteredTransactions = filterTransactionsForRequestMonth(request, transactions);

        //Calc how many days in a month
        int daysInMonth = LocalDate.of(Integer.parseInt(request.getYear()), Integer.parseInt(request.getMonth()), 1).lengthOfMonth();
        List<InterestPeriodDTO> periods = generateInterestPeriods(request.getYearMonth(), daysInMonth, filteredTransactions, interestRules);

        BigDecimal totalInterest = BigDecimal.ZERO;
        for (InterestPeriodDTO p : periods) {
            // 20230614 - 20230601 = 13+1 = 14
            int numDays = p.getEndDay() - p.getStartDay() + 1;
            //250 * 1.90/100 * 14 = 66.50
            BigDecimal periodInterest = p.getBalance()
                    .multiply(p.getRate())
                    .divide(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(numDays));
            //Cumulate
            totalInterest = totalInterest.add(periodInterest);
        }
        BigDecimal interest = totalInterest.divide(BigDecimal.valueOf(365), 2, RoundingMode.HALF_UP);

        if (interest.compareTo(BigDecimal.ZERO) > 0) {
            //Since theres interest we shall create teh transaction to insert
            TransactionDTO interestTransaction = new TransactionDTO();

            interestTransaction.setDate(request.getYearMonth() + daysInMonth); // the requested date + last date of the month
            interestTransaction.setTransactionId(" ");
            interestTransaction.setType("I");
            interestTransaction.setAmount(interest);

            List<TransactionDTO> allTransaction = Transaction.getTransactionMap().get(request.getAccountNum());
            BigDecimal previousBalance = allTransaction.isEmpty()
                    ? BigDecimal.ZERO
                    : allTransaction.get(allTransaction.size() - 1).getBalanceAfter();

            interestTransaction.setBalanceAfter(previousBalance.add(interest));

            allTransaction.add(interestTransaction);
        }
    }


    /**
     * Creates the interest breakdown dto
     * based on transaction update or rule update
     * in the given month
     **/
    public List<InterestPeriodDTO> generateInterestPeriods(String requestYearMonth, int daysInMonth, List<TransactionDTO> transactions,
                                                           List<InterestDTO> interestRules) {
        List<InterestPeriodDTO> periods = new ArrayList<>();

        int currentDay = 1;
        BigDecimal currentBalance = null;

        while (currentDay <= daysInMonth) {
            String currentDateStr = requestYearMonth + DateUtils.makeDayDoubleDigits(currentDay); //YYYYMMdd
            BigDecimal rate = getInterestRate(requestYearMonth, DateUtils.makeDayDoubleDigits(currentDay), interestRules);

            int nextChange = daysInMonth + 1;

            //Determine which is earlier, transaction day or rule change day

            for (TransactionDTO transaction : transactions) {
                //if a withdrawal or deposit occur, it will trigger an interest row
                int transactionDay = Integer.parseInt(transaction.getDate().substring(6, 8));
                if (transactionDay > currentDay) {
                    nextChange = Math.min(nextChange, transactionDay);
                }
            }
            for (InterestDTO rule : interestRules) {
                //if the rule's date matches this month, and its later then current day
                //this triggers another interest row.
                if (rule.getDate().compareTo(currentDateStr) > 0 && rule.getDate().startsWith(requestYearMonth)) {
                    int ruleDay = Integer.parseInt(rule.getDate().substring(6, 8));
                    if (ruleDay > currentDay) {
                        nextChange = Math.min(nextChange, ruleDay);
                    }
                }
            }
            int endDay = Math.min(nextChange - 1, daysInMonth);
            //get todays balance
            for (TransactionDTO transaction : transactions) {
                int transactionDay = Integer.parseInt(transaction.getDate().substring(6, 8));
                if (transactionDay <= currentDay) {
                    currentBalance = transaction.getBalanceAfter();
                }
            }
            if (currentBalance != null && endDay >= currentDay) {
                periods.add(new InterestPeriodDTO(currentDay, endDay, rate, currentBalance));
            }
            currentDay = nextChange;
        }
        return periods;
    }


    public List<TransactionDTO> filterTransactionsForRequestMonth(StatementDTO request, List<TransactionDTO> transactions) {
        String requestDate = request.getYear() + request.getMonth();
        return transactions.stream().filter(t ->
                t.getDate().startsWith(requestDate)
        ).collect(Collectors.toList());
    }

    public BigDecimal getInterestRate(String requestYearMonth, List<InterestDTO> interestRules) {
        return getInterestRate(requestYearMonth, "01", interestRules);
    }

    public BigDecimal getInterestRate(String requestYearMonth, String targetDay, List<InterestDTO> interestRules) {
        String targetDate = requestYearMonth + targetDay;
        //get the max rule date before target date
        return interestRules.stream()
                .filter(rule -> rule.getDate().compareTo(targetDate) <= 0)
                .max(Comparator.comparing(InterestDTO::getDate))//
                .map(InterestDTO::getRate)
                .orElse(BigDecimal.ZERO);
    }
}
