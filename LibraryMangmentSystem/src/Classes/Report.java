package Classes;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;


public class  Report {
    PendingRequestsQueue pendingRequestsQueue = new PendingRequestsQueue();
    Loan loan = new Loan();
    public static ActiveLoansDatabase activeLoansDatabase = new ActiveLoansDatabase();
    public static void generateReportForMember(String memberId, Catalog catalog) throws IOException, ParseException, ClassNotFoundException {
        System.out.println("\nGenerating report for Member ID: " + memberId);


        System.out.println("\nActive Loans for Member ID: " + memberId);
        displayActiveLoansForMember(memberId);


        System.out.println("\nPending Requests for Member ID: " + memberId);
        displayPendingLoansForMember(memberId);


    }

    public static void displayActiveLoansForMember(String memberId) throws IOException, ClassNotFoundException, ParseException {
        // Load active loans from the file
        Queue<Loan> activeLoans = activeLoansDatabase.loadActiveLoansFromFile();

        boolean found = false;
        for (Loan loan : activeLoans) {
            if (loan.getMemberId().equals(memberId)) {
                Book book = Catalog.searchBook(loan.getBookId());

                if (book != null) {
                    System.out.println("Loan ID: " + loan.getLoanID() + " | Book Title: " + book.getBookTitle() +
                            " | Return Date: " + loan.getReturnDate());
                } else {
                    System.out.println("The book is not available");
                }
                found = true;
            }
        }

        if (!found) {
            System.out.println("No active loans for this member.");
        }
    }


    public static void displayPendingLoansForMember(String memberId) {
        boolean found = false;
        for (Loan loan : PendingRequestsQueue.request) {
            if (loan.getMemberId().equals(memberId)) {
                System.out.println("Loan ID: " + loan.getLoanID() + " | Book ID: " + loan.getBookId() +
                        " | Requested on: " + loan.getIssueDate());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No pending requests for this member.");
        }
    }

    public static void generateGeneralReport(Catalog catalog) {
        System.out.println("\nGeneral Library Report:");

        displayActiveLoans(catalog);

        displayOverdueBooks(catalog);

        displayPendingLoans(catalog);

        displayPopularGenre(catalog);
    }

    public static void displayActiveLoans(Catalog catalog) {
        System.out.println("\nActive Loans in the Library:"); // multiple borrow
        for (Loan loan : Loan.activeLoans) {
            Book book = catalog.searchBook(loan.getBookId());
            if (book != null) {
                System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                        " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate());
            }
        }
    }

    public static void displayOverdueBooks(Catalog catalog) {
        Date currentDate = new Date(); // something error here
        System.out.println("\nOverdue Books:");
        for (Loan loan : Loan.activeLoans) {
            if (loan.getReturnDate().before(currentDate)) {
                Book book = catalog.searchBook(loan.getBookId());
                if (book != null) {
                    System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                            " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate());
                } else {
                    System.out.println("The book is not available");
                }
            }

        }
        System.out.println("No overdue books found.");
    }

    public static void displayPendingLoans(Catalog catalog) {
        System.out.println("\nPending Loans in the Library:");
        for (Loan loan : PendingRequestsQueue.request) {
            Book book = catalog.searchBook(loan.getBookId());
            if (book != null) {
                System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                        " | Requested on: " + loan.getIssueDate());
            }
        }
    }
    //

    public static String displayPopularGenre(Catalog catalog) {
        String output = "";

        if (Loan.activeLoans.isEmpty()) {
            output = "No active loans to calculate popular genres.";
        } else {
            String mostPopularGenre = null;
            int maxCount = 0;
            for (Loan loan : Loan.activeLoans) {
                Book book = catalog.searchBook(loan.getBookId());
                if (book != null) {
                    String genre = book.getGenere();
                    int count = 0;
                    for (Loan innerLoan : Loan.activeLoans) {
                        Book innerBook = catalog.searchBook(innerLoan.getBookId());
                        if (innerBook != null && innerBook.getGenere().equals(genre)) {
                            count++;
                        }
                    }
                    if (count > maxCount) {
                        maxCount = count;
                        mostPopularGenre = genre;
                    }
                }
            }
            if (mostPopularGenre != null) {
                output = "Most Popular Genre: " + mostPopularGenre + " with " + maxCount + " loans.";
            } else {
                output = "No genres found.";
            }
        }
        return output;
    }
}