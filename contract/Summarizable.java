package rusteze.contract;

/**
 * Summarizable (INTERFACE)
 * Purpose: gives unrelated classes (Car and Transaction) one common behavior
 * without forcing them to share a parent class.
 * Any class that implements this interface MUST provide getSummary().
 */
public interface Summarizable {

    /** @return a one-line text description of the object */
    String getSummary();
}
