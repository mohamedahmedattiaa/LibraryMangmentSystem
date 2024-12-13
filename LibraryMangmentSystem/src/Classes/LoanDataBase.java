package Classes;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoanDataBase {
    private static final String FILE_NAME = "loans.txt";

    public static void saveLoansToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            for (Loan loan : Loan.activeLoans) {
                writer.println(loan.getLoanID() + "," + loan.getBookId() + "," + loan.getMemberId() +
                        "," + loan.getIssueDate() + "," + loan.getReturnDate());
            }
            writer.flush();
            System.out.println("Loans saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving loans to file: " + e.getMessage());
        }
    }

    public static void loadLoansFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No loan data found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] loanData = line.split(",");
                if (loanData.length == 5) {
                    String loanID = loanData[0];
                    String bookId = loanData[1];
                    String memberId = loanData[2];
                    Date issueDate = parseDate(loanData[3]);
                    Date returnDate = parseDate(loanData[4]);

                    Loan loan = new Loan();
                    loan.setLoanID(loanID);
                    loan.setBookId(bookId);
                    loan.setMemberId(memberId);
                    loan.setIssueDate(issueDate);
                    loan.setReturnDate(returnDate);

                    if (returnDate == null) {
                        Loan.activeLoans.add(loan);
                    } else {
                        Loan.returnedLoans.add(loan);
                    }
                }
            }
            System.out.println("Loans loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading loans from file: " + e.getMessage());
        }
    }

    private static Date parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + dateString);
            return null;
        }
    }

    public static void clearLoanFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.close();
            writer.flush();
            System.out.println("Loan file cleared.");
        } catch (IOException e) {
            System.out.println("Error clearing the loan file: " + e.getMessage());
        }
    }
}
