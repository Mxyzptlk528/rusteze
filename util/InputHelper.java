package rusteze.util;

import java.util.Scanner;

/**
 * InputHelper
 * Purpose: reads and validates console input in one place, so the rest of the
 * program never has to deal with bad input or crashes.
 */
public class InputHelper {

    // ----- Attribute -----
    private Scanner scanner;

    public InputHelper() {
        scanner = new Scanner(System.in);
    }

    /** Keeps asking until the user types a valid whole number. */
    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    /** Keeps asking until the user types a valid number. */
    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /** Keeps asking until the user types Y or N. */
    public boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y")) return true;
            if (answer.equals("n")) return false;
            System.out.println("Please type (Y) or (N).");
        }
    }
}
