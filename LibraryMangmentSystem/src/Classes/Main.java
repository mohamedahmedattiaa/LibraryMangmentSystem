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
Member member1 = new Member("ezz","@.","123");
Member member2 = new Member("atta","@.","123");
Member member3 = new Member("ezaz","@.","123");
Loan.borrowBook("M200","B100");
Loan.borrowBook("M200","B101");
Loan.borrowBook("M200","B102");
Loan.returnBook("M200","B100");





        System.out.println(Loan.activeLoans.toString());
        System.out.println("------");
        System.out.println(Loan.returnedLoans.toString());

    }

}

