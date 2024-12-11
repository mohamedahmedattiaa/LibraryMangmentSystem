package Classes;

import java.io.IOException;
import java.util.*;


public class  Report {
    PendingRequestsQueue pendingRequestsQueue =new PendingRequestsQueue();
    Loan loan = new Loan();
    public void generateReportForMember(String memberId, Catalog catalog) throws IOException {
        System.out.println("\nGenerating report for Member ID: " + memberId);


        System.out.println("\nActive Loans for Member ID: " + memberId);
        displayActiveLoansForMember(memberId, catalog);


        System.out.println("\nPending Requests for Member ID: " + memberId);
        displayPendingLoansForMember(memberId);


    }

    private void displayActiveLoansForMember(String memberId, Catalog catalog) {
        boolean found = false;
        for (Loan loan : Loan.activeLoans) {
            if (loan.getMemberId().equals(memberId)) {
                Book book = catalog.searchBook(loan.getBookId());

                if(book != null) {
                    System.out.println("Loan ID: " + loan.getLoanID() + " | Book Title: " + book.getBookTitle() +
                            " | Return Date: " + loan.getReturnDate());
                }else{
                    System.out.println("The book is not available");
                }
                found = true;
            }
        }
        if (!found) {
            System.out.println("No active loans for this member.");
        }
    }

    private void displayPendingLoansForMember(String memberId) {
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

    public void generateGeneralReport(Catalog catalog) {
        System.out.println("\nGeneral Library Report:");


        displayActiveLoans(catalog);

        displayOverdueBooks(catalog);

        displayPendingLoans(catalog);

//        displayPopularGenre(catalog);
    }

    public void displayActiveLoans(Catalog catalog) {
        System.out.println("\nActive Loans in the Library:"); // multiple borrow
        for (Loan loan : Loan.activeLoans) {
            Book book = catalog.searchBook(loan.getBookId());
            if (book != null) {
                System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                        " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate());
            }
        }
    }

    public  static void displayOverdueBooks(Catalog catalog) {
        Date currentDate = new Date(); // something error here
        System.out.println("\nOverdue Books:");
        for (Loan loan : Loan.activeLoans) {
            if (loan.getReturnDate().before(currentDate)) {
                Book book = catalog.searchBook(loan.getBookId());
                if (book != null) {
                    System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                            " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate());
                }
                else{
                    System.out.println("The book is not available");
                }
            }

            }
        System.out.println("No overdue books found.");
        }

    public void displayPendingLoans(Catalog catalog) {
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

    public void displayPopularGenre(Catalog catalog) {
        if (Loan.activeLoans.isEmpty()) {
            System.out.println("No active loans to calculate popular genres.");
            return;
        }

        String mostPopularGenre = null;
        int maxCount = 0;

        // Iterate through all loans to determine the most popular genre
        for (Loan loan : Loan.activeLoans) {
            Book book = catalog.searchBook(loan.getBookId());
            if (book != null) {
                String genre = book.getGenere(); // Assuming `getGenere()` returns the genre of the book
                int count = 0;

                // Count occurrences of this genre in active loans
                for (Loan innerLoan : Loan.activeLoans) {
                    Book innerBook = catalog.searchBook(innerLoan.getBookId());
                    if (innerBook != null && innerBook.getGenere().equals(genre)) {
                        count++;
                    }
                }

                // Update the most popular genre if the current genre has more loans
                if (count > maxCount) {
                    maxCount = count;
                    mostPopularGenre = genre;
                }
            }
        }

        if (mostPopularGenre != null) {
            System.out.println("Most Popular Genre: " + mostPopularGenre + " with " + maxCount + " loans.");
        } else {
            System.out.println("No genres found.");
        }
    }



}



