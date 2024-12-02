import java.io.Serializable;

/**
 * Represents a Boat with its details including type, name, year of manufacture,
 * make/model, length, purchase price, and maintenance expenses.
 * Implements Serializable for object serialization.
 *
 * @author Alon Bauman
 * @version 1.0
 */
public class Boat implements Serializable {
    // Enum for boat types (e.g., Sailing, Power)
    public enum BoatType {
        SAILING, POWER
    }

    private BoatType type; // The type of the boat (Sailing or Power)
    private String name; // The name of the boat
    private int yearOfManufacture; // The year the boat was manufactured
    private String makeModel; // The make and model of the boat
    private double length; // The length of the boat in feet
    private double purchasePrice; // The purchase price of the boat
    private double maintenanceExpenses; // Total maintenance expenses for the boat

    /**
     * Constructor for initializing a new Boat object with the given parameters.
     * Sets initial maintenance expenses to 0.0.
     *
     * @param type Type of the boat (Sailing or Power)
     * @param name Name of the boat
     * @param yearOfManufacture Year the boat was manufactured
     * @param makeModel Make and model of the boat
     * @param length Length of the boat
     * @param purchasePrice Purchase price of the boat
     */
    public Boat(BoatType type, String name, int yearOfManufacture, String makeModel, double length, double purchasePrice) {
        this.type = type;
        this.name = name;
        this.yearOfManufacture = yearOfManufacture;
        this.makeModel = makeModel;
        this.length = length;
        this.purchasePrice = purchasePrice;
        this.maintenanceExpenses = 0.0; // Initial maintenance expenses are set to 0
    }

    // Getter methods to access boat attributes

    public BoatType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public double getLength() {
        return length;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getMaintenanceExpenses() {
        return maintenanceExpenses;
    }

    /**
     * Adds a given expense amount to the boat's maintenance expenses.
     *
     * @param amount The amount to be added to maintenance expenses
     */
    public void addExpense(double amount) {
        this.maintenanceExpenses += amount;
    }

    /**
     * Returns a string representation of the Boat object in a formatted manner.
     * Includes details such as type, name, year, make/model, length, purchase price,
     * and maintenance expenses.
     *
     * @return A formatted string describing the boat
     */
    @Override
    public String toString() {
        return String.format("%-8s %-20s %4d %-12s %3.0f' : Paid $ %10.2f : Spent $ %10.2f",
                type, name, yearOfManufacture, makeModel, length, purchasePrice, maintenanceExpenses);
    }
}
