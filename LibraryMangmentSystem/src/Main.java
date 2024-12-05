import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        catalog catalog = new catalog();
        PendingRequestsQueue pendingRequestsQueue = new PendingRequestsQueue();

// Add books to the catalog
        Book book1 = new Book("The Great man", "George washonton", "Fiction");
        Book book2 = new Book("1984",  "Ibraham lincolin", "Dystopian");
        Book book3 = new Book("The AdventureLand", "Wealm sheckspear", "Dystopian");
        Book book4 = new Book("THE GREATEST Fan", "Albert fred", "Fiction");
        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);
        catalog.addBook(book4);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
// Borrow and return books
        Member member1 = new Member("atta", "ezz@gmail.com");
        Member member2 = new Member("ezz", "Mohamed1122@gmail.com");
        Member member3 = new Member("a7med", "Ezz@gmail.com");
        dataBaseMembers.add(member1);
        dataBaseMembers.add(member2);
        dataBaseMembers.add(member3);
        System.out.println(dataBaseMembers.display());
//        Loan.borrowBook("M200","B100", catalog); // what if he borrowed the same book twice??
//        Loan.borrowBook("M201","B100", catalog);
//        pendingRequestsQueue.display();
//        System.out.println("//////////////////////////////////////////////");
//        Loan.returnBook("M200","B100", catalog);// it created a new LOAn ID same solution as member ID
        catalog.displayCatalog();
        catalog.S("author");
        catalog.displayCatalog();
    }
}

