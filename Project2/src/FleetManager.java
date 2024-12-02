import java.util.ArrayList;

/**
 * Manages a fleet of boats, including adding, removing, searching, and reporting on the fleet.
 *
 * @author Alon Bauman
 * @version 1.0
 */
public class FleetManager {
    private ArrayList<Boat> fleet; // List of boats in the fleet
    private boolean isRemoved; // Tracks if a boat was successfully removed
    private Boat foundBoat; // Stores the result of a boat search
    private double totalPaid; // Total purchase price of all boats
    private double totalSpent; // Total maintenance expenses of all boats
    private String format; // String format for fleet report output

    /**
     * Constructs an empty fleet manager.
     */
    public FleetManager() {
        this.fleet = new ArrayList<>();
    }

    /**
     * Adds a boat to the fleet.
     *
     * @param boat The boat to add.
     */
    public void addBoat(Boat boat) {
        fleet.add(boat);
    }

    /**
     * Removes a boat from the fleet by its name.
     *
     * @param name The name of the boat to remove.
     * @return true if a boat was removed; false otherwise.
     */
    public boolean removeBoat(String name) {
        // Remove boat matching the provided name (case insensitive)
        isRemoved = fleet.removeIf(boat -> boat.getName().equalsIgnoreCase(name));
        return isRemoved;
    }

    /**
     * Finds a boat in the fleet by its name.
     *
     * @param name The name of the boat to find.
     * @return The boat if found; null otherwise.
     */
    public Boat findBoat(String name) {
        foundBoat = null;
        for (Boat boat : fleet) {
            // Compare boat names ignoring case
            if (boat.getName().equalsIgnoreCase(name)) {
                foundBoat = boat;
                break;
            }
        }
        return foundBoat;
    }

    /**
     * Prints a report of all boats in the fleet, including totals for purchase price
     * and maintenance expenses.
     */
    public void printFleetReport() {
        totalPaid = 0.0; // Initialize total purchase price
        totalSpent = 0.0; // Initialize total maintenance expenses
        format = "    Total%46s : Paid $ %10.2f : Spent $ %10.2f\n"; // Format for report summary

        for (Boat boat : fleet) {
            // Print each boat's details
            System.out.println("    " + boat);
            // Accumulate totals
            totalPaid += boat.getPurchasePrice();
            totalSpent += boat.getMaintenanceExpenses();
        }
        // Print total summary
        System.out.printf(format, "", totalPaid, totalSpent);
    }

    /**
     * Gets the list of boats in the fleet.
     *
     * @return The list of boats.
     */
    public ArrayList<Boat> getFleet() {
        return fleet;
    }
}
