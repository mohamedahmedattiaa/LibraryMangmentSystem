package Classes;

import java.io.*;
import java.util.Scanner;
public class IDGenerator {
    private static int memberCounter = 199; // Start from 200
    private static final String Members = "TempMembers.txt";
    private static int loanCounter = 300; // Start from L300
    private static final String Loans = "loans.txt";

    public static String generateMemberID(String name, String email) throws IOException {
        // Check if the member already exists in the file
        String existingID = findExistingMemberID(name, email);
        if (existingID != null) {
            return existingID; // Return the existing ID
        }

        // Generate a new ID if not found
        return "M" + getNextAvailableID();
    }

    private static int getNextAvailableID() throws IOException {
        int maxID = memberCounter; // Start from M200

        try (BufferedReader reader = new BufferedReader(new FileReader(Members))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Skip empty lines
                    String[] memberData = line.split(",");
                    if (memberData.length > 0) {
                        String memberID = memberData[0];
                        if (memberID.startsWith("M") && memberID.length() > 1) {
                            try {
                                int currentID = Integer.parseInt(memberID.substring(1));
                                maxID = Math.max(maxID, currentID);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid member ID format: " + memberID);
                            }
                        } else {
                            System.err.println("Skipping invalid ID: " + memberID);
                        }
                    }
                }
            }
        }
        return Math.max(maxID, memberCounter) + 1;
    }

        private static String findExistingMemberID(String name, String email) {
            try (Scanner scanner = new Scanner(new File("TempMembers.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    if (data.length >= 3) {
                        String existingName = data[1].trim();
                        String existingEmail = data[2].trim();
                        if (existingName.equalsIgnoreCase(name) && existingEmail.equalsIgnoreCase(email)) {
                            return data[0].trim();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }
            return null;
        }

        private static int bookCounter = 100;


    public static String generateBookID() {
        String newBookID = "B" + (bookCounter++);
        while (Catalog.searchBook(newBookID) != null) {
            newBookID = "B" + (bookCounter++);
        }
        return newBookID;
    }

    public static String generateLoanID() throws IOException {
        String newLoanID = "L" + (loanCounter++);
        while (Loan.searchloan(newLoanID) != null) {
            newLoanID = "L" + (loanCounter++);
        }
        return "L" + loanCounter++;
    }
}



