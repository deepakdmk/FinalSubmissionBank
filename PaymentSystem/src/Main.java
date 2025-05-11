import Templates.ConsoleTemplates;
import Utility.ConsoleUtils;

public class Main {
    public static void main(String[] args) {

        Homepage home = new Homepage();
        System.out.println(ConsoleTemplates.APPLICATION_INTRO);

        while (true) {
            home.printMainMenu();
            home.handleMainMenuInput(ConsoleUtils.getInput());
        }
    }


}