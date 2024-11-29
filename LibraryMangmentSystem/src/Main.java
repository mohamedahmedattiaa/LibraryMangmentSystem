import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        catalog catalog = new catalog();
        PendingRequestsQueue pendingRequestsQueue = new PendingRequestsQueue();

// Add books to the catalog
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction");
        Book book2 = new Book("1984",  "George Orwell", "Dystopian");
        Book book3 = new Book("The Zobr", "George Orwell", "Dystopian");
        Book book4 = new Book("THE GREATEST ASS", "George Orwell", "Fiction");
        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);
        catalog.addBook(book4);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
// Borrow and return books
        Loan loan = new Loan();
        Member member1 = new Member("atta", "Ahmed1121@gmail.com");
        Member member2 = new Member("ezz", "Mohamed1122@gmail.com");
        Member member3 = new Member("a7med", "Ezz@gmail.com");
        System.out.println(member1.getmemberId());
        Members.add(member1);
        Members.add(member2);
        Members.add(member3);
        Loan.borrowBook("M200","B100",catalog);
        Loan.borrowBook("M201","B100",catalog);
        System.out.println("--------------------------------------------------------------------------------------------");
        Loan.returnBook("M200","B100",catalog);
        PendingRequestsQueue.display(); //test
    }
}

