package Classes;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Catalog catalog = new Catalog();
        PendingRequestsQueue pendingRequestsQueue = new PendingRequestsQueue();

// Add books to the catalog
        Book book1 = new Book("c", "George washonton", "Fiction");
        Book book2 = new Book("d", "Ibraham lincolin", "Dystopian");
        Book book3 = new Book("a", "Wealm sheckspear", "Dystopian");
        Book book4 = new Book("e", "Albert fred", "Fiction");
        Catalog.bookList.display();
        Loan.borrowBook("M201","B100");
        System.out.println("-------------");
        Catalog.bookList.display();

    }

}

