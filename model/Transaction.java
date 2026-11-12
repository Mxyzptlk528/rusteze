package rusteze.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import rusteze.contract.Summarizable;

/**
 * Transaction (ABSTRACT CLASS)
 * Purpose: holds what EVERY completed sale has in common (car, total, date)
 * and forces each payment type to define its own way of computing the total.
 * You cannot create a plain Transaction object; only its subclasses.
 *
 * OOP concepts: Abstraction (abstract methods), Encapsulation (private fields),
 * Inheritance (parent of the payment classes), Interface (Summarizable).
 */
public abstract class Transaction implements Summarizable {

    // ----- Attributes -----
    private Car car;
    private double totalPrice;
    private String dateTime;

    /** Constructor: every subclass calls this with super(car). */
    public Transaction(Car car) {
        this.car = car;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = LocalDateTime.now().format(formatter);
    }

    // ----- Abstract methods: each subclass MUST override them (polymorphism) -----
    /** @return the final price for this payment type */
    public abstract double computeTotal();

    /** @return the name of the payment type, e.g. "Cash" */
    public abstract String getPaymentType();

    // ----- Getters -----
    public Car getCar() {
        return car;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDateTime() {
        return dateTime;
    }

    /** Protected: only subclasses may store the computed total. */
    protected void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // ----- Hook methods: subclasses MAY override these to add receipt lines -----
    protected String getExtraReceiptDetails() {
        return "";
    }

    protected String getPaymentBreakdown() {
        return "";
    }

    /** Builds the receipt text. The same code works for every payment type. */
    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Car purchased successfully!\n\n");
        sb.append("               Receipt:\n");
        sb.append("----------------------------------------\n");
        sb.append("Car Model: ").append(car.getModel()).append("\n");
        sb.append(String.format("Original Price: \u20B1%,.2f%n", car.getPrice()));
        sb.append("Payment Type: ").append(getPaymentType()).append("\n");
        sb.append(getExtraReceiptDetails());
        sb.append(String.format("Total Price: \u20B1%,.2f%n", totalPrice));
        sb.append(getPaymentBreakdown());
        sb.append("Transaction Date: ").append(dateTime).append("\n");
        sb.append("----------------------------------------");
        return sb.toString();
    }

    // ----- Summarizable -----
    @Override
    public String getSummary() {
        return dateTime + " | " + car.getModel() + " | " + getPaymentType() +
                " | Total: \u20B1" + String.format("%,.2f", totalPrice);
    }
}
