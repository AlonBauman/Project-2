import java.util.Scanner;

/**
 * Handles the menu interactions for the Fleet Management System.
 * Allows users to print the fleet report, add boats, remove boats, and handle expenses.
 *
 * @author Alon Bauman
 * @version 1.0
 */
public class MenuController {
    private FleetManager fleetManager; // The FleetManager instance managing the fleet.
    private Scanner scanner; // Scanner for user input.
    private String option; // The selected menu option.
    private String csvData; // Input data for adding boats in CSV format.
    private String name; // Name of the boat for add, remove, or expense operations.
    private String[] parts; // Parsed CSV data.
    private Boat.BoatType type; // Type of boat being added.
    private int year; // Year of manufacture of the boat.
    private String makeModel; // Make and model of the boat.
    private double length; // Length of the boat.
    private double price; // Purchase price of the boat.
    private Boat boat; // The boat instance found for expenses.
    private double amount; // Expense amount.
    private double totalAmount = 0; //Expenses total amount

    /**
     * Constructs a MenuController with a given FleetManager.
     *
     * @param fleetManager The FleetManager instance managing the fleet.
     */
    public MenuController(FleetManager fleetManager) {
        this.fleetManager = fleetManager;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu and handles user interaction.
     */
    public void displayMenu() {
        option = null; // Initialize option to null
        do {
            System.out.print("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");
            option = scanner.nextLine().trim().toUpperCase();
            switch (option) {
                case "P":
                    fleetManager.printFleetReport(); // Print fleet report
                    break;
                case "A":
                    addBoat(); // Add a new boat
                    break;
                case "R":
                    removeBoat(); // Remove an existing boat
                    break;
                case "E":
                    handleExpenses(); // Handle expenses for a boat
                    break;
                case "X":
                    break; // Exit the menu
                default:
                    System.out.println("Invalid menu option, try again."); // Handle invalid input
            }
        } while (!option.equals("X")); // Loop until user chooses to exit
    }

    /**
     * Adds a new boat to the fleet based on user-provided CSV data.
     */
    private void addBoat() {
        csvData = null;
        System.out.print("Please enter the new boat CSV data: ");
        csvData = scanner.nextLine();
        try {
            // Parse and validate the CSV data
            parts = csvData.split(",");
            type = Boat.BoatType.valueOf(parts[0].toUpperCase());
            name = parts[1];
            year = Integer.parseInt(parts[2]);
            makeModel = parts[3];
            length = Double.parseDouble(parts[4]);
            price = Double.parseDouble(parts[5]);
            // Add the new boat to the fleet
            fleetManager.addBoat(new Boat(type, name, year, makeModel, length, price));
        } catch (Exception e) {
            System.out.println("Invalid boat data format."); // Handle invalid input
        }
    }

    /**
     * Removes a boat from the fleet by name.
     */
    private void removeBoat() {
        name = null;
        System.out.print("Which boat do you want to remove? ");
        name = scanner.nextLine();
        if (fleetManager.removeBoat(name)) {
            System.out.println("Boat removed."); // Boat successfully removed
        } else {
            System.out.println("Cannot find boat " + name); // Handle non-existent boat
        }
    }

    /**
     * Handles adding maintenance expenses for a boat.
     */
    private void handleExpenses() {
        name = null;
        amount = 0;
        System.out.print("Which boat do you want to spend on? ");
        name = scanner.nextLine();
        boat = fleetManager.findBoat(name);
        if (boat == null) {
            System.out.println("Cannot find boat " + name); // Handle non-existent boat
            return;
        }
        System.out.print("How much do you want to spend? ");
        amount = Double.parseDouble(scanner.nextLine());
        totalAmount += amount;
        // Check if expense is within the allowable limit
        if (boat.getMaintenanceExpenses() + amount > boat.getPurchasePrice()) {
            System.out.printf("Expense not permitted, only $%.2f left to spend.%n",
                    boat.getPurchasePrice() - boat.getMaintenanceExpenses());
        } else {
            boat.addExpense(amount); // Add the expense to the boat
            System.out.printf("Expense authorized, $%.2f spent.%n", totalAmount);
        }
    }
}
