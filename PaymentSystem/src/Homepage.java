import Interest.Interest;
import Templates.ConsoleTemplates;
import Statement.Statement;

import Transaction.Transaction;

import java.util.List;
import java.util.Objects;

import static Templates.ConsoleTemplates.*;

public class Homepage {

    public void printMainMenu() {
        buildMainMenu().stream()
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }

    private List<String> buildMainMenu() {
        return List.of(
                MAIN_MENU_OPTION_TRANSACTION,
                MAIN_MENU_OPTION_INTEREST,
                MAIN_MENU_OPTION_STATEMENT,
                MAIN_MENU_OPTION_QUIT
        );
    }

    void handleMainMenuInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println(ERROR_MAIN_MENU_INPUT);
            return;
        }

        switch (input.trim().toLowerCase()) {
            case MAIN_MENU_SYMBOL_TRANSACTION:
                handleTransactionMenu();
                break;
            case MAIN_MENU_SYMBOL_INTEREST:
                handleInterestMenu();
                break;
            case MAIN_MENU_SYMBOL_STATEMENT:
                handleStatementMenu();
                break;
            case MAIN_MENU_SYMBOL_QUIT:
                quitApplication();
                break;
            default:
                System.out.println(ERROR_MAIN_MENU_INPUT);
                break;
        }
    }

    private void quitApplication() {
        System.out.println(APPLICATION_OUTRO);
        System.exit(0);
    }

    private void handleTransactionMenu() {
        Transaction transaction = new Transaction();
        transaction.transactionMenu();
    }

    private void handleInterestMenu() {
        Interest interest = new Interest();
        interest.interestMenu();
    }

    private void handleStatementMenu() {
        Statement statement = new Statement();
        statement.statementMenu();
    }
}
