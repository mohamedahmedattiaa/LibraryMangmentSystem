package GUI;

import java.io.*;
import java.util.Scanner;

public class UserDatabase {
    private static final String FILE_PATH = "UserDatabase.txt";

    // Add user to the database
    public static void addUser(String FirstName, String LastName, String email, String password, String user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Convert email to lowercase before storing
            email = email.toLowerCase();

            writer.write(FirstName + "," + LastName + "," + email + "," + password);
            writer.newLine();
            System.out.println(email + " User database added successfully");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }


    // Check if an email is already taken
    public static boolean isEmailTaken(String email) {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length > 2 && parts[2].equals(email)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file not found: " + e.getMessage());
        }
        return false;
    }

    // Validate user login credentials
    public static boolean validateUser(String email, String password) {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                // Ensure the line has the correct number of parts before accessing
                if (parts.length == 4 && parts[2].equals(email) && parts[3].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file not found: " + e.getMessage());
        }
        return false;
    }
}