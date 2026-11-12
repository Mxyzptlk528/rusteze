package rusteze.service;

import java.util.ArrayList;
import rusteze.model.Car;

/**
 * Inventory
 * Purpose: owns the list of cars and handles viewing, filtering and restocking.
 * It creates its own Car objects, so it is made up of (composed of) cars.
 *
 * OOP concepts: Encapsulation (private list), Method overloading (displayTable).
 */
public class Inventory {

    // ----- Attribute -----
    private ArrayList<Car> cars;

    public Inventory() {
        cars = new ArrayList<>();
        loadDefaultCars();
    }

    /** Creates the 20 starting cars (2 units each). */
    private void loadDefaultCars() {
        cars.add(new Car("Toyota Vios", 738000.0, 2));
        cars.add(new Car("Toyota Wigo", 735000.0, 2));
        cars.add(new Car("Honda Brio", 827000.0, 2));
        cars.add(new Car("Honda City", 998000.0, 2));
        cars.add(new Car("Mitsubishi Mirage", 720000.0, 2));
        cars.add(new Car("Mitsubishi Mirage G4", 793000.0, 2));
        cars.add(new Car("Mitsubishi Xpander", 1008000.0, 2));
        cars.add(new Car("Honda BR-V", 1155000.0, 2));
        cars.add(new Car("Honda Civic", 1600000.0, 2));
        cars.add(new Car("Mitsubishi Montero Sport", 1568000.0, 2));
        cars.add(new Car("Toyota Fortuner", 1968000.0, 2));
        cars.add(new Car("Toyota Avanza", 1030000.0, 2));
        cars.add(new Car("Toyota Innova", 1370000.0, 2));
        cars.add(new Car("Honda CR-V", 1890000.0, 2));
        cars.add(new Car("Honda HR-V", 1400000.0, 2));
        cars.add(new Car("Mitsubishi Strada", 1442000.0, 2));
        cars.add(new Car("Mitsubishi Outlander", 1740000.0, 2));
        cars.add(new Car("Nissan Almera", 828000.0, 2));
        cars.add(new Car("Nissan Navara", 1499000.0, 2));
        cars.add(new Car("Ford Ranger", 1495000.0, 2));
    }

    public int size() {
        return cars.size();
    }

    /** @return the car at the index, or null if the index is out of range */
    public Car getCar(int index) {
        if (index < 0 || index >= cars.size()) {
            return null;
        }
        return cars.get(index);
    }

    public void restock(int index, int quantity) {
        Car car = getCar(index);
        if (car != null) {
            car.addStock(quantity);
        }
    }

    /** Private helper: only this class draws the table lines. */
    private void printLine() {
        System.out.println("+-----+--------------------------+----------------+------------+");
    }

    /** OVERLOAD 1: no parameter, shows ALL cars. */
    public void displayTable() {
        displayTable(false);
    }

    /** OVERLOAD 2: shows all cars, or only the in-stock cars. */
    public void displayTable(boolean inStockOnly) {
        System.out.println();
        System.out.println("===== " + (inStockOnly ? "In-Stock Cars" : "Available Cars") + " =====");
        System.out.println();
        printLine();
        System.out.printf("| %-3s | %-24s | %-14s | %-10s |%n", "No.", "Car Model", "Price", "Status");
        printLine();

        boolean anyShown = false;
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (inStockOnly && !car.isInStock()) continue;

            String status = car.isInStock() ? "In Stock" : "Sold Out";
            System.out.printf("| %-3d | %-24s | %-14s | %-10s |%n",
                    i + 1, car.getModel(), car.getFormattedPrice(), status);
            anyShown = true;
        }
        if (!anyShown) {
            System.out.println("|                  No cars match this filter.                  |");
        }
        printLine();
    }

    public void displayRestockTable() {
        System.out.println();
        System.out.println("===== Restock a Car =====");
        System.out.println();
        printLine();
        System.out.printf("| %-3s | %-24s | %-14s | %-10s |%n", "No.", "Car Model", "Price", "Stock");
        printLine();
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.printf("| %-3d | %-24s | %-14s | %-10d |%n", i + 1, car.getModel(), "", car.getStock());
        }
        printLine();
    }
}
