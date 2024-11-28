import java.util.LinkedList;
import java.util.Queue;

public class  PendingRequestsQueue {

     private static Queue<Loan> pendingRequests;
     public PendingRequestsQueue() {
         pendingRequests = new LinkedList<>();
     }

    static public void   enqueue(String bookId , String memberId) {
         Loan loan = new Loan(bookId,memberId);
         pendingRequests.add(loan);
         System.out.println("Your pending request has been enqueued: "+loan.getLoanID());

     }

     public Loan dequeue() {
         if (pendingRequests.isEmpty()) {
             System.out.println("Your pending request is empty: " + pendingRequests.toString());
             return null;
         }
         Loan loan = pendingRequests.poll();
         System.out.println("Your pending request has been dequeued: " + loan.toString());
         return loan;
     }

    public static void display() {
 Loan temp =pendingRequests.peek();
        while (temp != null) {
            System.out.println(temp.getLoanID());
            temp =temp.n

        }
    }
}
