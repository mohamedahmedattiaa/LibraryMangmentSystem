package Classes;

import java.util.LinkedList;
import java.util.Queue;

public class PendingRequestsQueue {
    private static Queue<Loan> request = new LinkedList<>();

    public static void enqueue(Loan loan) {
        request.add(loan);
        System.out.println("Loan request added for book " + loan.getBookId() + " by member '" + loan.getMemberId() + ".");
    }

    public static Loan dequeue() {
        return request.poll();
    }

    public static boolean isEmpty() {
        return request.isEmpty();
    }

    public static void display() {
        System.out.println("Loan Requests:");
        for (Loan loan : request) {
            System.out.println("Book ID: " + loan.getBookId() + ", Member: " + loan.getMemberId());
        }
    }
}
