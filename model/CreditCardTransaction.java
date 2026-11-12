package rusteze.model;

import rusteze.contract.PaymentConstants;

/**
 * CreditCardTransaction
 * Purpose: a sale paid by credit card, with a processing fee added.
 * It was added as a NEW subclass, so no existing class had to be changed.
 *
 * OOP concepts: Inheritance, Interface, Polymorphism (method overriding).
 */
public class CreditCardTransaction extends Transaction implements PaymentConstants {

    public CreditCardTransaction(Car car) {
        super(car);
        setTotalPrice(computeTotal());
    }

    /** @return the card fee, which is a percentage of the car's price */
    public double getProcessingFee() {
        return getCar().getPrice() * CARD_FEE_RATE;
    }

    @Override
    public double computeTotal() {
        return getCar().getPrice() + getProcessingFee();
    }

    @Override
    public String getPaymentType() {
        return "Credit Card";
    }

    @Override
    protected String getExtraReceiptDetails() {
        return String.format("Processing Fee (%d%%): \u20B1%,.2f%n",
                Math.round(CARD_FEE_RATE * 100), getProcessingFee());
    }
}
