import java.util.LinkedList;
import java.util.Queue;

public class PendingRequestsQueue {
//     public LinkedQueue pendingRequests;
     private Queue<Loan> pendingRequests;
     public PendingRequestsQueue() {
         pendingRequests = new LinkedList<>();
     }

     public void enqueue(String bookId , String memberId) {
         Loan loan = new Loan(bookId, memberId);
         pendingRequests.add(loan);
         System.out.println("Your pending request has been enqueued: "+loan);
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

    public void display() {
        if (pendingRequests.isEmpty()) {
            System.out.println("The queue is empty.");
        } else {
            System.out.println("Pending Requests in the queue:");
            for (Loan request : pendingRequests){
                System.out.println(request.toString());
            }
        }
    }
}
