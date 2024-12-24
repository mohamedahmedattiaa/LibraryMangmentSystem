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
        Book book5 = new Book("The man Who sold the world", "Albert fred", "Fiction");
     catalog.addBook(book1);
     catalog.addBook(book2);
     catalog.addBook(book3);
     catalog.addBook(book4);
     catalog.addBook(book5);
//        System.out.println("-------------------");
//        Loan.borrowBook("M201","B100"); // borrow
//        Loan.borrowBook("M202","B100"); //pending
//        Loan.borrowBook("M201","B101"); //borrow
//        Loan.returnBook("M201","B100");// return
//        Loan.borrowBook("M202","B101"); //borrow
//        Loan.returnBook("M202","B101");

    }
}

