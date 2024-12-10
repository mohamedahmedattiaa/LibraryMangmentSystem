package Classes;

import java.io.IOException;
import java.util.*;


public class Report {
    PendingRequestsQueue pendingRequestsQueue =new PendingRequestsQueue();
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

    private void displayActiveLoans(Catalog catalog) {
        System.out.println("\nActive Loans in the Library:"); // multiple borrow
        for (Loan loan : Loan.activeLoans) {
            Book book = catalog.searchBook(loan.getBookId());
            if (book != null) {
                System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                        " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate());
            }
        }
    }

    private void displayOverdueBooks(Catalog catalog) {
        Date currentDate = new Date(); // something error here
        System.out.println("\nOverdue Books:");
        for (Loan loan : Loan.activeLoans) {
            if (loan.getReturnDate().before(currentDate)) {
                Book book = catalog.searchBook(loan.getBookId());
                if (book != null) {
                    System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                            " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate());
                }
            }
        }
    }

    private void displayPendingLoans(Catalog catalog) {
        System.out.println("\nPending Loans in the Library:");
        for (Loan loan : PendingRequestsQueue.request) {
            Book book = catalog.searchBook(loan.getBookId());
            if (book != null) {
                System.out.println("Loan ID: " + loan.getLoanID() + " | Book: " + book.getBookTitle() +
                        " | Requested on: " + loan.getIssueDate());
            }
        }
    }

//    private void displayPopularGenre(Catalog catalog) {
//        // Create a HashMap to track genre counts
//        Map<String, Integer> genreCountMap = new HashMap<>();
//
//        // Iterate over all active loans
//        for (Loan loan : Loan.activeLoans) {
//            Book book = catalog.searchBook(loan.getBookId());
//            if (book != null) {
//                String genre = book.getGenere(); // Assuming the Book class has a getGenre method
//
//                // Increment the count of the genre in the HashMap
//                genreCountMap.put(genre, genreCountMap.getOrDefault(genre, 0) + 1);
//            }
//        }
//
//        // Convert the map to a list of entries to sort it by count
//        List<Map.Entry<String, Integer>> sortedGenres = new ArrayList<>(genreCountMap.entrySet());
//        sortedGenres.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));
//
//        // Display the most popular genre(s)
//        System.out.println("\nPopular Genres:");
//        for (Map.Entry<String, Integer> entry : sortedGenres) {
//            System.out.println("Genre: " + entry.getKey() + " | Count: " + entry.getValue());
//        }
//    }

}
