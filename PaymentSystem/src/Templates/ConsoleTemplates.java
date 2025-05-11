package Templates;

public class ConsoleTemplates {

    private ConsoleTemplates() {}

    public static final String APPLICATION_INTRO= "Welcome to AwesomeGIC Bank! What would you like to do?";
    public static final String MAIN_MENU_OPTION_TRANSACTION= "[T] Input transactions";
    public static final String MAIN_MENU_OPTION_INTEREST= "[I] Define interest rules";
    public static final String MAIN_MENU_OPTION_STATEMENT= "[P] Print statement";
    public static final String MAIN_MENU_OPTION_QUIT= "[Q] Quit";
    public static final String MAIN_MENU_SYMBOL_TRANSACTION= "t";
    public static final String MAIN_MENU_SYMBOL_INTEREST= "i";
    public static final String MAIN_MENU_SYMBOL_STATEMENT= "p";
    public static final String MAIN_MENU_SYMBOL_QUIT= "q";
    public static final String APPLICATION_OUTRO="Thank you for banking with AwesomeGIC Bank.\nHave a nice day!";
    public static final String MENU_OUTRO = "Is there anything else you'd like to do?";

    public static final String TRANSACTION_INTRO="Please enter transaction details in <Date> <Account> <Type> <Amount> format\n (or enter blank to go back to main menu):";
    public static final String TRANSACTION_HEADER = "Account: %s";
    public static final String TRANSACTION_TABLE_HEADER_FORMAT = "| %-9s | %-12s | %-4s | %7s |";
    public static final String TRANSACTION_TABLE_ROW_FORMAT = "| %-9s | %-12s | %-4s | %7.2f |";

    public static final String INTEREST_INTRO="Please enter interest rules details in <Date> <RuleId> <Rate in %> format\n (or enter blank to go back to main menu):";
    public static final String INTEREST_HEADER = "Interest rules:";
    public static final String INTEREST_TABLE_HEADER_FORMAT = "| %-9s | %-6s | %9s |";
    public static final String INTEREST_TABLE_ROW_FORMAT = "| %-9s | %-6s | %9.2f |";

    public static final String STATEMENT_INTRO="Please enter account and month to generate the statement <Account> <Year><Month>\n (or enter blank to go back to main menu):";
    public static final String STATEMENT_HEADER = "Account: %s";
    public static final String STATEMENT_TABLE_HEADER_FORMAT = "| %-9s | %-12s | %-4s | %7s | %8s |";
    public static final String STATEMENT_TABLE_ROW_FORMAT = "| %-9s | %-12s | %-4s | %7.2f | %8.2f |";
    
    public static final String ERROR="Error";
    public static final String ERROR_MAIN_MENU_INPUT="Please enter a valid option - T, I, P or Q\n";
    public static final String ERROR_TRANSACTION_INPUT="Invalid transaction request format!\n";
    public static final String ERROR_INVALID_TRANSACTION_NEGATIVE_BALANCE="Balance cannot go below zero.\n";
    public static final String ERROR_INTEREST_INPUT="Invalid interest request format!\n";
    public static final String ERROR_STATEMENT_INPUT="Invalid statement request format!\n";
    public static final String ERROR_STATEMENT_INVALID_ACCOUNT="Account does not exist!\n";

}
