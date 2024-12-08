package Classes;

import java.util.*;

public class PendingRequestsQueue {
    private static Queue<Loan> request = new LinkedList<>();

    public static void enqueue(Loan loan) {
        request.add(loan);
        System.out.println("Loan request added for book " + loan.getBookId() + " by member '" + loan.getMemberId() + ".");
    }
  // test
    public static Loan dequeue() {
        if(request.isEmpty()) {
            return null;
        }
        return request.poll();
    }

    public static boolean isEmpty() {
        return request.isEmpty(); // is this working where is the condition ??
    }

    public static void display() {
        System.out.println("Loan Requests:");
        for (Loan loan : request) {
            System.out.println("Book ID: " + loan.getBookId() + ", Member: " + loan.getMemberId());
        }
    }
    public static void sortloanByMemberID (){                                   // for librarian
            List<Loan> loanList = new ArrayList<>(request);                     // we convert the queue to an array to access the built-in sort method
            loanList.sort(Comparator.comparing(Loan::getMemberId));             // compare member id between each other
            request.clear();
            request.addAll(loanList);
            System.out.println("Loan requests sorted by Member ID.");
        }
                                                                            // why to ues timsort because it is easier on code , faster because we don`t have too much complicated data
        public static void sortloanBydate (){                               // for librarian
            List<Loan> loanList = new ArrayList<>(request);                 // we convert the queue to an array to access the built-in sort method
            loanList.sort(Comparator.comparing(Loan::getIssueDate));        // compare member id between each other
            request.clear();
            request.addAll(loanList);
            System.out.println("Loan requests sorted by date.");
        }
    public static List<Loan> searchLoans(String memberID) { // returns an array of loans so we know what did the member borrow
        List<Loan> loans = new ArrayList<>();

        for (Loan loan : request) {
            if (loan.getMemberId().equals(memberID)) {
                loans.add(loan);
            }
        }

        return loans; // edit
    }
}


