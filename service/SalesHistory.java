package rusteze.service;

import java.util.ArrayList;
import rusteze.model.Transaction;

/**
 * SalesHistory
 * Purpose: keeps a record of every completed sale.
 * The transactions are created elsewhere and only collected here,
 * so this class "has many" transactions (aggregation).
 *
 * OOP concept: Polymorphism - the list holds Transaction references, but each
 * element may be a Cash, Installment or CreditCard transaction.
 */
public class SalesHistory {

    // ----- Attribute -----
    private ArrayList<Transaction> transactions;

    public SalesHistory() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void viewHistory() {
        System.out.println();
        System.out.println("===== Sales History =====");
        System.out.println();
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = 0; i < transactions.size(); i++) {
                // Same call for every type; each object gives its own payment type
                System.out.println((i + 1) + ". " + transactions.get(i).getSummary());
            }
        }
        System.out.println();
    }
}
