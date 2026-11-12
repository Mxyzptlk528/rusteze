package rusteze.contract;

/**
 * PaymentConstants (INTERFACE)
 * Purpose: stores the fixed values shared by every payment class.
 * Attributes in an interface are implicitly public, static and final,
 * so the values live in ONE place and can never be changed while running.
 */
public interface PaymentConstants {

    double DISCOUNT_RATE = 0.10;          // cash discount (10%)
    double ANNUAL_INTEREST_RATE = 0.05;   // installment interest per year (5%)
    double CARD_FEE_RATE = 0.03;          // credit card processing fee (3%)

    int TERM_12 = 12;                     // allowed installment terms (months)
    int TERM_24 = 24;
    int TERM_36 = 36;
}
