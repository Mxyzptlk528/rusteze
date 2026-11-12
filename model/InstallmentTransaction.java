package rusteze.model;

import rusteze.contract.PaymentConstants;

/**
 * InstallmentTransaction
 * Purpose: a sale paid over 12, 24 or 36 months, with yearly interest.
 *
 * OOP concepts: Inheritance, Interface, Polymorphism (method overriding),
 * Constructor overloading (two constructors with different parameters).
 */
public class InstallmentTransaction extends Transaction implements PaymentConstants {

    // ----- Attributes -----
    private double downPayment;
    private int months;
    private double monthlyPayment;

    /** Constructor 1: with a down payment. */
    public InstallmentTransaction(Car car, double downPayment, int months) {
        super(car);
        this.downPayment = downPayment;
        this.months = months;
        setTotalPrice(computeTotal());
        this.monthlyPayment = getTotalPrice() / months;
    }

    /** Constructor 2 (OVERLOADING): no down payment, so it is treated as 0. */
    public InstallmentTransaction(Car car, int months) {
        this(car, 0, months);            // reuse Constructor 1
    }

    /** Overrides the abstract method: financed amount plus yearly interest. */
    @Override
    public double computeTotal() {
        double financedAmount = getCar().getPrice() - downPayment;
        double interestRate = ANNUAL_INTEREST_RATE * (months / 12.0);
        return financedAmount * (1 + interestRate);
    }

    @Override
    public String getPaymentType() {
        return "Installment";
    }

    // ----- Getters -----
    public double getDownPayment() {
        return downPayment;
    }

    public int getMonths() {
        return months;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    // ----- Overridden hook methods: add installment lines to the receipt -----
    @Override
    protected String getExtraReceiptDetails() {
        if (downPayment > 0) {
            return String.format("Down Payment: \u20B1%,.2f%n", downPayment);
        }
        return "";
    }

    @Override
    protected String getPaymentBreakdown() {
        return "Duration: " + months + " months\n" +
                String.format("Monthly Payment: \u20B1%,.2f%n", monthlyPayment);
    }
}
