package Classes;

import java.util.PriorityQueue;
import java.util.Queue;

public class PendingRequestsQueue {

    private static Queue<Loan> request = new PriorityQueue<>((loan1, loan2) ->
            loan1.getReturnDate().compareTo(loan2.getReturnDate()));

    public static void enqueue(Loan loan) {
        request.add(loan);
        System.out.println("Loan request added for book " + loan.getBookId() +
                " by member '" + loan.getMemberId() + "' with due date " + loan.getReturnDate() + ".");
    }

    public static Loan dequeue() {
        Loan loan = request.poll();
        if (loan != null) {
            System.out.println("Processing loan request for book " + loan.getBookId() +
                    " by member '" + loan.getMemberId() + "'.");
        } else {
            System.out.println("No loan requests to process.");
        }
        return loan;
    }

    // Check if the queue is empty
    public static boolean isEmpty() {
        return request.isEmpty();
    }

    public static void display() {
        System.out.println("Loan Requests (sorted by due date):");
        if (request.isEmpty()) {
            System.out.println("No pending loan requests.");
        } else {
            for (Loan loan : request) {
                System.out.println("Book ID: " + loan.getBookId() + ", Member: " + loan.getMemberId() +
                        ", Due Date: " + loan.getReturnDate());
            }
        }
    }
}
