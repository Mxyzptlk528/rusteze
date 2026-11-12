package rusteze.model;

import rusteze.contract.PaymentConstants;

/**
 * CashTransaction
 * Purpose: a sale paid in full with cash, with a discount.
 *
 * OOP concepts: Inheritance (extends Transaction), Interface (implements
 * PaymentConstants), Polymorphism (overrides computeTotal and getPaymentType).
 */
public class CashTransaction extends Transaction implements PaymentConstants {

    public CashTransaction(Car car) {
        super(car);                      // reuse the parent's constructor
        setTotalPrice(computeTotal());
    }

    /** Overrides the abstract method: price minus the cash discount. */
    @Override
    public double computeTotal() {
        return getCar().getPrice() * (1 - DISCOUNT_RATE);
    }

    @Override
    public String getPaymentType() {
        return "Cash";
    }
}
