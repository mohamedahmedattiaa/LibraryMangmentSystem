package Classes;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PendingRequestsQueue {
    // Queue to store pending requests in memory
    public static Queue<Loan> request = new LinkedList<>();
    private static final String FILE_NAME = "pending_requests.txt";

    // Static block to load pending requests from the file when the class is called
    static {
        try {
            loadPendingRequestsFromFile();
        } catch (IOException | ParseException e) {
            System.err.println("Error initializing pending requests: " + e.getMessage());
        }
    }

    // Enqueue a loan request to the queue and add it to the file if it doesn't already exist
    public static void enqueue(Loan loan) {
        if (!isLoanInFile(loan.getLoanID())) { // Check if the loan is already in the file
            request.add(loan);
            try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
                writer.println(loan.getLoanID() + "," + loan.getBookId() + "," + loan.getMemberId() +
                        "," + loan.getIssueDate() + "," + loan.getReturnDate() + "," + loan.Isactive());
                System.out.println("Pending Requests request added for book " + loan.getBookId() +
                        " by member '" + loan.getMemberId() + "', loan ID: " + loan.getLoanID() + ".");
            } catch (IOException e) {
                System.err.println("Error adding loan to file: " + e.getMessage());
            }
        } else {
            System.out.println("Loan with ID " + loan.getLoanID() + " already exists in the file. Skipping addition.");
        }
    }

    // Helper method to check if a loan ID already exists in the file
    private static boolean isLoanInFile(String loanID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(loanID)) {
                    return true; // Loan ID found in the file
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking loan in file: " + e.getMessage());
        }
        return false; // Loan ID not found in the file
    }


    // Dequeue a loan request from the queue
    public static Loan dequeue() {
        if (request.isEmpty()) {
            return null;
        }
        Loan loan = request.poll();
        try {
            savePendingRequestsToFile(); // Update the file after removing a request
        } catch (IOException e) {
            System.err.println("Error updating the file after dequeue: " + e.getMessage());
        }
        return loan;
    }

    // Check if the queue is empty
    public static boolean isEmpty() {
        return request.isEmpty();
    }

    // Display the pending requests in the queue
    public static void display() {
        System.out.println("Pending Requests:");
        for (Loan loan : request) {
            System.out.println("Loan ID: " + loan.getLoanID() + ", Book ID: " + loan.getBookId() +
                    ", Member: " + loan.getMemberId());
        }
    }

    // Return the queue of pending requests
    public static Queue<Loan> getPendingRequestsQueue() {
        return request;
    }

    // Sort loan requests by member ID
    public static void sortLoanByMemberID() {
        List<Loan> loanList = new ArrayList<>(request);
        loanList.sort(Comparator.comparing(Loan::getMemberId));
        request.clear();
        request.addAll(loanList);
        try {
            savePendingRequestsToFile(); // Save the sorted queue to the file
            System.out.println("Loan requests sorted by Member ID.");
        } catch (IOException e) {
            System.err.println("Error saving sorted loans to file: " + e.getMessage());
        }
    }

    // Sort loan requests by issue date
    public static void sortLoanByDate() {
        List<Loan> loanList = new ArrayList<>(request);
        loanList.sort(Comparator.comparing(Loan::getIssueDate));
        request.clear();
        request.addAll(loanList);
        try {
            savePendingRequestsToFile(); // Save the sorted queue to the file
            System.out.println("Loan requests sorted by date.");
        } catch (IOException e) {
            System.err.println("Error saving sorted loans to file: " + e.getMessage());
        }
    }

    // Search for loans by member ID
    public static List<Loan> searchLoans(String memberID) {
        List<Loan> loans = new ArrayList<>();
        for (Loan loan : request) {
            if (loan.getMemberId().equals(memberID)) {
                loans.add(loan);
            }
        }
        return loans;
    }

    // Save the pending requests from the queue to the file
    private static void savePendingRequestsToFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Loan loan : request) {
                writer.println(loan.getLoanID() + "," + loan.getBookId() + "," + loan.getMemberId() +
                        "," + loan.getIssueDate() + "," + loan.getReturnDate() + "," + loan.Isactive());
            }
        }
    }

    // Load pending requests from the file into the queue
    private static void loadPendingRequestsFromFile() throws IOException, ParseException {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No pending requests file found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String loanId = data[0];
                    String bookId = data[1];
                    String memberId = data[2];
                    Date issueDate = parseDate(data[3]);
                    Date returnDate = parseDate(data[4]);
                    boolean isActive = Boolean.parseBoolean(data[5]);

                    Loan loan = new Loan(loanId, bookId, memberId, issueDate, returnDate, isActive);
                    request.add(loan);
                }
            }
        }
    }

    // Parse a date string to Date object
    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        return formatter.parse(dateString);
    }

    // Display the contents of the pending requests file
    public static void displayPendingRequestsFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("Contents of the Pending Requests file:");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    System.out.printf("Loan ID: %s, Book ID: %s, Member ID: %s, Issue Date: %s, Return Date: %s, Is Active: %s%n",
                            data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the pending requests file: " + e.getMessage());
        }
    }

    // Remove a pending loan request by loan ID
    public static void removePendingLoanById(String loanID) throws IOException {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(loanID)) {
                    continue; // Skip the line with the matching loan ID
                }
                updatedLines.add(line); // Add all other lines to the updated list
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
        }

        request.removeIf(loan -> loan.getLoanID().equals(loanID)); // Remove from the queue
        System.out.println("Pending loan ID " + loanID + " has been removed from the pending requests file.");
    }
}
