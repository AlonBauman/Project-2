import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Entry point for the Fleet Management System application.
 * Manages the loading, interaction, and saving of fleet data.
 *
 * @author Alon Bauman
 * @version 1.0
 */
public class FleetManagementSystem {
    private static final String DB_FILE_PATH = "FleetData.db";

    public static void main(String[] args) {
        // Initialize fleet manager and file handler
        FleetManager fleetManager = new FleetManager();
        FileHandler fileHandler = new FileHandler();

        // Paths for database and CSV files
        String csvFilePath = args.length > 0 ? args[0] : "fleetData.csv";

        // Declare file and fleet variables
        File dbFile;
        ArrayList<Boat> fleet = null;
        MenuController menuController;

        // Welcome message
        System.out.println("Welcome to the Fleet Management System!");
        System.out.println("--------------------------------------");

        // Attempt to load fleet data from the database or CSV file
        try {
            dbFile = new File(DB_FILE_PATH);
            if (dbFile.exists()) {
                // Load from database if it exists
                fleet = fileHandler.loadFleetFromDb(DB_FILE_PATH);
            } else {
                // Load from CSV file if no database is found
                fleet = fileHandler.loadFleetFromCsv(csvFilePath);
            }
            // Add loaded boats to the fleet manager
            for (Boat boat : fleet) {
                fleetManager.addBoat(boat);
            }
        } catch (Exception e) {
            System.out.println("Error loading fleet data.");
        }

        // Initialize menu controller and display the menu
        menuController = new MenuController(fleetManager);
        menuController.displayMenu();

        // Attempt to save fleet data to the database
        try {
            fileHandler.saveFleetToDb(DB_FILE_PATH, fleetManager.getFleet());
        } catch (IOException e) {
            System.out.println("Error saving fleet data.");
        }

        // Exit message
        System.out.println("Exiting the Fleet Management System");
    }
}
