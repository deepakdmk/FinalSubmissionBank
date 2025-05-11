package Utility;

import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner in = new Scanner(System.in);

    public static String getInput() {
        return in.hasNextLine() ? in.nextLine() : "";
    }
}
