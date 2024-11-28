import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Create Catalog to store books
        catalog catalog = new catalog();

        // Create Books
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction");
        Book book2 = new Book("1984", "George Orwell", "Dystopian");
        Book book3 = new Book("Moby Dick", "Herman Melville", "Adventure");

        // Add books to the catalog
        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);

        // Create Members
        Member member1 = new Member("John Doe", "john.doe@example.com");
        Member member2 = new Member("Alice Smith", "alice.smith@example.com");

        // Display the initial catalog
        System.out.println("Initial Catalog:");
        catalog.displayCatalog();

        // John Doe borrows "The Great Gatsby"
        Loan loan1 = new Loan(book1.getBookID(), member1.getBookId());
        loan1.borrowBook(catalog, member1);  // Borrow the book
        System.out.println("\nLoan Details for John Doe:");
        loan1.displayLoanDetails();  // Display loan details for John Doe

        // Alice Smith borrows "1984"
        Loan loan2 = new Loan(book2.getBookID(), member2.getBookId());
        loan2.borrowBook(catalog, member2);  // Borrow the book
        System.out.println("\nLoan Details for Alice Smith:");
        loan2.displayLoanDetails();  // Display loan details for Alice Smith

        // Display updated catalog after books are borrowed
        System.out.println("\nUpdated Catalog After Borrowing:");
        catalog.displayCatalog();

        // John Doe returns "The Great Gatsby"
        loan1.returnBook(catalog, member1);
        System.out.println("\nUpdated Loan Details for John Doe after Returning the Book:");
        loan1.displayLoanDetails();  // Display updated loan details after returning

        // Display updated catalog after "The Great Gatsby" is returned
        System.out.println("\nUpdated Catalog After John Returns the Book:");
        catalog.displayCatalog();

        // Alice Smith returns "1984"
        loan2.returnBook(catalog, member2);
        System.out.println("\nUpdated Loan Details for Alice Smith after Returning the Book:");
        loan2.displayLoanDetails();  // Display updated loan details after returning

        // Display final catalog after all books are returned
        System.out.println("\nFinal Catalog After All Books Are Returned:");
        catalog.displayCatalog();
    }
}
