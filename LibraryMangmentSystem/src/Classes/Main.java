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


        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);
        catalog.addBook(book4);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
// Borrow and return books
        Member member1 = new Member("atta", "ezz@gmail.com");
        Member member2 = new Member("ezz", "Mohamed1122@gmail.com");
        Member member3 = new Member("a7med", "Ezz@gmail.com");
        Loan.borrowBook("M200","B100",catalog);
        Member.display();

    }

}

