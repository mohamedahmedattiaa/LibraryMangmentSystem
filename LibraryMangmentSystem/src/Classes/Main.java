package Classes;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Catalog catalog = new Catalog();
        PendingRequestsQueue pendingRequestsQueue = new PendingRequestsQueue();

// Add books to the catalog
        Book book1 = new Book("c", "George washonton", "Fiction");
        Book book2 = new Book("d", "Ibraham lincolin", "Dystopian");
        Book book3 = new Book("a", "Wealm sheckspear", "Dystopian");
        Book book4 = new Book("e", "Albert fred", "Fiction");

//
       Loan.borrowBook("M201","B100");
       Loan.borrowBook("M201","B101");
       Loan.borrowBook("M202","B101");
       Loan.borrowBook("M202","B103");
       Loan.borrowBook("M202","B102");
       Loan.returnBook("M202","B102");
       PendingRequestsQueue.display();
    }
}

