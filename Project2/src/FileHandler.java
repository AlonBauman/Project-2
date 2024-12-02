import java.io.*;
import java.util.ArrayList;

/**
 * Handles file operations for loading and saving fleet data in CSV and database formats.
 * Provides methods to read from and write to files.
 *
 * @author Alon Bauman
 * @version 1.0
 */
public class FileHandler {
    private static ArrayList<Boat> fleet; // Stores the list of boats in the fleet
    private static BufferedReader br; // BufferedReader for reading from files
    private static PrintWriter writer; // PrintWriter for writing to files
    private static ObjectOutputStream oos; // ObjectOutputStream for saving to a database
    private static ObjectInputStream ois; // ObjectInputStream for loading from a database
    private static String line; // Holds each line read from a CSV file
    private static String[] parts; // Parsed parts of a CSV line
    private static Boat.BoatType type; // Type of the boat being read
    private static String name; // Name of the boat
    private static int yearOfManufacture; // Year the boat was manufactured
    private static String makeModel; // Make and model of the boat
    private static double length; // Length of the boat
    private static double purchasePrice; // Purchase price of the boat

    /**
     * Loads the fleet from a CSV file.
     *
     * @param csvFilePath Path to the CSV file.
     * @return List of boats loaded from the CSV file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static ArrayList<Boat> loadFleetFromCsv(String csvFilePath) throws IOException {
        fleet = new ArrayList<>();
        br = null;
        line = null;
        parts = null;

        try {
            br = new BufferedReader(new FileReader(csvFilePath));
            // Read each line, parse it, and create Boat objects
            while ((line = br.readLine()) != null) {
                parts = line.split(",");
                type = Boat.BoatType.valueOf(parts[0].toUpperCase());
                name = parts[1];
                yearOfManufacture = Integer.parseInt(parts[2]);
                makeModel = parts[3];
                length = Double.parseDouble(parts[4]);
                purchasePrice = Double.parseDouble(parts[5]);
                fleet.add(new Boat(type, name, yearOfManufacture, makeModel, length, purchasePrice));
            }
        } finally {
            if (br != null) {
                br.close(); // Ensure BufferedReader is closed to release resources
            }
        }
        return fleet;
    }

    /**
     * Saves the fleet to a CSV file.
     *
     * @param csvFilePath Path to the CSV file.
     * @param fleet List of boats to save.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public static void saveFleetToCsv(String csvFilePath, ArrayList<Boat> fleet) throws IOException {
        writer = null;

        try {
            writer = new PrintWriter(new FileWriter(csvFilePath));
            // Write each boat's data as a CSV line
            for (Boat boat : fleet) {
                writer.printf("%s,%s,%d,%s,%.2f,%.2f%n",
                        boat.getType(),
                        boat.getName(),
                        boat.getYearOfManufacture(),
                        boat.getMakeModel(),
                        boat.getLength(),
                        boat.getPurchasePrice());
            }
        } finally {
            if (writer != null) {
                writer.close(); // Ensure PrintWriter is closed to flush and save data
            }
        }
    }

    /**
     * Saves the fleet to a binary database file.
     *
     * @param filePath Path to the database file.
     * @param fleet List of boats to save.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public static void saveFleetToDb(String filePath, ArrayList<Boat> fleet) throws IOException {
        oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(fleet); // Serialize the fleet list and save it to the file
        } finally {
            if (oos != null) {
                oos.close(); // Ensure ObjectOutputStream is closed to release resources
            }
        }
    }

    /**
     * Loads the fleet from a binary database file.
     *
     * @param filePath Path to the database file.
     * @return List of boats loaded from the database file.
     * @throws IOException If an I/O error occurs during file reading.
     * @throws ClassNotFoundException If the Boat class is not found during deserialization.
     */
    public static ArrayList<Boat> loadFleetFromDb(String filePath) throws IOException, ClassNotFoundException {
        ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            // Deserialize the fleet list from the file
            fleet = (ArrayList<Boat>) ois.readObject();
        } finally {
            if (ois != null) {
                ois.close(); // Ensure ObjectInputStream is closed to release resources
            }
        }
        return fleet;
    }
}