import java.io.*;
import java.util.Scanner;
public class IDGenerator {
        private static int memberCounter = 200; // Start from 200
        private static final String FILE_NAME = "text.txt";
        public static String generateMemberID(String name, String email) {
            // Check if the member already exists in the file
            String existingID = findExistingMemberID(name, email);
            if (existingID != null) {
                return existingID; // Return the existing ID
            }

            // Generate a new ID if not found
            return "M" + (memberCounter++);
        }

        // Helper method to search for an existing member in the file
        private static String findExistingMemberID(String name, String email) {
            try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    if (data.length >= 3) {
                        String existingName = data[1].trim();
                        String existingEmail = data[2].trim();
                        if (existingName.equalsIgnoreCase(name) && existingEmail.equalsIgnoreCase(email)) {
                            return data[0].trim(); // Return the existing ID
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }
            return null; // Return null if not found
        }

        private static int bookCounter = 100;
        private static int loanCounter = 300;

        public static String generateBookID() {
            return "B" + (bookCounter++);
        }
        public static String generateLoanID() {
            return "L" + (loanCounter++);
        }
        //loan adjustment;
    }



