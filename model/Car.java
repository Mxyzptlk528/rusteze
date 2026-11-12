package rusteze.model;

import rusteze.contract.Summarizable;

/**
 * Car
 * Purpose: represents ONE vehicle in the dealership and controls how its
 * data (especially stock) is allowed to change.
 *
 * OOP concepts: Encapsulation (private fields + getters, no plain setStock),
 * Interface (implements Summarizable).
 */
public class Car implements Summarizable {

    // ----- Attributes (all private = encapsulation) -----
    private String model;
    private double price;
    private int stock;

    /** Constructor: sets the starting values the moment a Car object is created. */
    public Car(String model, double price, int stock) {
        this.model = model;
        this.price = price;
        this.stock = stock;
    }

    // ----- Getters -----
    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getFormattedPrice() {
        return "\u20B1" + String.format("%,.0f", price);
    }

    public boolean isInStock() {
        return stock > 0;
    }

    // ----- Controlled stock changes (instead of a public setStock) -----
    /** Called after a sale: lowers stock by one (never below zero). */
    public void reduceStock() {
        if (stock > 0) {
            stock--;
        }
    }

    /** Called when restocking: only positive quantities are accepted. */
    public void addStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }

    // ----- Summarizable -----
    @Override
    public String getSummary() {
        return model + " | " + getFormattedPrice() + " | Stock: " + stock + " unit(s)";
    }
}
