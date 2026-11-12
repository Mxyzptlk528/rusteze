package rusteze;

import rusteze.contract.PaymentConstants;
import rusteze.contract.Summarizable;
import rusteze.model.Car;
import rusteze.model.CashTransaction;
import rusteze.model.CreditCardTransaction;
import rusteze.model.InstallmentTransaction;
import rusteze.model.Transaction;
import rusteze.service.Inventory;
import rusteze.service.SalesHistory;
import rusteze.util.InputHelper;

/**
 * Main
 * Purpose: the entry point. It runs the menu and connects the other classes.
 * It holds one Inventory, one SalesHistory and one InputHelper.
 *
 * Modularity: the program is split into packages by responsibility:
 *   rusteze.contract - interfaces      rusteze.model   - Car and transactions
 *   rusteze.service  - Inventory and SalesHistory      rusteze.util - InputHelper
 */
public class Main {

    private static Inventory inventory = new Inventory();
    private static SalesHistory salesHistory = new SalesHistory();
    private static InputHelper input = new InputHelper();

    public static void main(String[] args) {
        runMenu();
    }

    /** Works with ANY Summarizable object (a Car or a Transaction). */
    public static void printSummary(String label, Summarizable item) {
        System.out.println(label + item.getSummary());
    }

    public static void runMenu() {
        while (true) {
            System.out.println();
            System.out.println("===== Rusteze Car Dealership System =====");
            System.out.println();
            System.out.println("1. Create a Transaction");
            System.out.println("2. View Sales History");
            System.out.println("3. Restock Car");
            System.out.println("4. Exit");
            System.out.println();
            System.out.println("----------------------------------------");
            int choice = input.readInt("Enter choice: ");

            if (choice == 1) {
                viewCarModel();
            } else if (choice == 2) {
                salesHistory.viewHistory();
            } else if (choice == 3) {
                restockCar();
            } else if (choice == 4) {
                boolean confirmed = input.readYesNo("Are you sure you want to exit? (Y) or (N): ");
                if (confirmed) {
                    System.out.println();
                    System.out.println("Exiting!.....");
                    break;
                }
            } else {
                System.out.println("Invalid input!. Only choose from 1 to 4");
            }
        }
    }

    public static void viewCarModel() {
        System.out.println();
        System.out.println("===== View Cars =====");
        System.out.println();
        System.out.println("1. Show All Cars");
        System.out.println("2. Show In-Stock Cars Only");
        System.out.println("0. Back to Main Menu");
        int filterChoice = input.readInt("Enter your choice: ");

        if (filterChoice == 0) {
            return;
        } else if (filterChoice == 1) {
            inventory.displayTable();          // overload 1: all cars
        } else if (filterChoice == 2) {
            inventory.displayTable(true);      // overload 2: in-stock only
        } else {
            System.out.println("Invalid choice! Returning to main menu.");
            return;
        }

        chooseCar();
    }

    public static void chooseCar() {
        int select = input.readInt("\nEnter the car number to select (1-" + inventory.size() + "), or 0 to cancel: ");

        if (select == 0) {
            System.out.println("Transaction cancelled. Returning to main menu.");
            return;
        }

        if (select < 1 || select > inventory.size()) {
            System.out.println("Invalid choice! Choose only from 1 and " + inventory.size() + ".");
            return;
        }

        Car car = inventory.getCar(select - 1);
        if (!car.isInStock()) {
            System.out.println("Sorry, " + car.getModel() + " is sold out! Please choose another car.");
            return;
        }

        System.out.println();
        System.out.println("===== Car Selected =====");
        System.out.println();
        printSummary("Selected: ", car);

        choosePaymentMethod(car);
    }

    public static void choosePaymentMethod(Car car) {
        // The rates and terms come from the PaymentConstants interface
        long discountPercent = Math.round(PaymentConstants.DISCOUNT_RATE * 100);
        long interestPercent = Math.round(PaymentConstants.ANNUAL_INTEREST_RATE * 100);
        long cardFeePercent = Math.round(PaymentConstants.CARD_FEE_RATE * 100);

        System.out.println();
        System.out.println("===== Choose Payment Method =====");
        System.out.println();
        System.out.println("1. Cash        - for " + discountPercent + "% Discount");
        System.out.println("2. Installment - for " + interestPercent + "% Annual Interest Rate");
        System.out.println("3. Credit Card - for " + cardFeePercent + "% Processing Fee");
        System.out.println("0. Back to Main Menu");
        System.out.println();
        System.out.println("----------------------------------------");
        int choice = input.readInt("Enter your choice: ");

        // Parent-type variable: it can hold ANY kind of transaction (polymorphism)
        Transaction transaction;

        if (choice == 0) {
            System.out.println("Transaction cancelled. Returning to main menu.");
            return;
        } else if (choice == 1) {
            transaction = new CashTransaction(car);
        } else if (choice == 2) {
            double downPayment = input.readDouble("Enter down payment amount (0 if none): \u20B1");
            if (downPayment < 0 || downPayment >= car.getPrice()) {
                System.out.println("Invalid down payment amount.");
                return;
            }

            int months = input.readInt("Enter how many months [" + PaymentConstants.TERM_12 + ", "
                    + PaymentConstants.TERM_24 + ", " + PaymentConstants.TERM_36 + "]: ");
            if (months != PaymentConstants.TERM_12 && months != PaymentConstants.TERM_24
                    && months != PaymentConstants.TERM_36) {
                System.out.println("Invalid months! Choose only " + PaymentConstants.TERM_12 + ", "
                        + PaymentConstants.TERM_24 + ", or " + PaymentConstants.TERM_36 + ".");
                return;
            }

            if (downPayment == 0) {
                transaction = new InstallmentTransaction(car, months);               // overloaded constructor
            } else {
                transaction = new InstallmentTransaction(car, downPayment, months);
            }
        } else if (choice == 3) {
            transaction = new CreditCardTransaction(car);
        } else {
            System.out.println("Invalid choice! Choose only 1, 2, 3, or 0.");
            return;
        }

        // Stock only changes through the Car's own method (encapsulation)
        car.reduceStock();
        salesHistory.addTransaction(transaction);

        // The right receipt is printed for each type (polymorphism)
        System.out.println(transaction.generateReceipt());
        printSummary("Saved to history: ", transaction);
    }

    public static void restockCar() {
        inventory.displayRestockTable();

        int select = input.readInt("\nEnter the car number to restock (1-" + inventory.size() + "), or 0 to cancel: ");
        if (select == 0) {
            System.out.println("Returning to main menu.");
            return;
        }
        if (select < 1 || select > inventory.size()) {
            System.out.println("Invalid choice! Select a number between 1 to " + inventory.size() + ".");
            return;
        }

        int addAmount = input.readInt("Enter quantity: ");
        if (addAmount <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        inventory.restock(select - 1, addAmount);
        Car car = inventory.getCar(select - 1);
        System.out.println("[\u2713] " + car.getModel() + " restocked. New stock: " + car.getStock());
    }
}
