package Classes;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActiveLoansDatabase {
    private static final String ACTIVE_LOANS_FILE = "active_loans.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");


    public static void saveActiveLoansToFile(Queue<Loan> activeLoans) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACTIVE_LOANS_FILE))) {
            for (Loan loan : activeLoans) {
                writer.write(formatLoanForFile(loan));
                writer.newLine();
            }
            System.out.println("Active loans saved to file successfully.");
        }
    }

    // Load all active loans from the file
    public static Queue<Loan> loadActiveLoansFromFile() throws IOException, ParseException {
        Queue<Loan> activeLoans = new LinkedList<>();
        File file = new File(ACTIVE_LOANS_FILE);

        if (!file.exists()) {
            System.out.println("Active loans file does not exist. Returning an empty queue.");
            return activeLoans;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Loan loan = parseLoanFromFile(line);
                if (loan != null) {
                    activeLoans.add(loan);
                }
            }
            System.out.println("Active loans loaded from file successfully.");
        }
        return activeLoans;
    }

    public static void addLoanToFile(Loan loan) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACTIVE_LOANS_FILE, true))) {
            writer.write(formatLoanForFile(loan));
            writer.newLine();
            System.out.println("Loan added to active loans file.");
        }
    }
    public static void removeLoanFromFile(Queue<Loan> activeLoans) throws IOException, ParseException {
        List<Loan> loans = new ArrayList<>(loadActiveLoansFromFile());
        boolean found = loans.removeIf(loan -> loan.getLoanID().equals(activeLoans));

        if (found) {
            saveActiveLoansToFile(new LinkedList<>(loans));
            System.out.println("Loan is removed from file.");
        } else {
            System.out.println("Loan is not found in file.");
        }
    }

    private static String formatLoanForFile(Loan loan) {
        return loan.getLoanID() + "," +
                loan.getBookId() + "," +
                loan.getMemberId() + "," +
                DATE_FORMAT.format(loan.getIssueDate()) + "," +
                DATE_FORMAT.format(loan.getReturnDate());
    }

    private static Loan parseLoanFromFile(String line) throws ParseException, IOException {
        String[] data = line.split(",");
        if (data.length < 5) {
            System.out.println("Invalid loan record: " + line);
            return null;
        }

        String loanID = data[0];
        String bookId = data[1];
        String memberId = data[2];
        Date issueDate = DATE_FORMAT.parse(data[3]);
        Date returnDate = DATE_FORMAT.parse(data[4]);

        return new Loan(loanID, bookId, memberId, issueDate, returnDate);
    }
}

