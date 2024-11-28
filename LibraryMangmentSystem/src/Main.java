import javax.swing.*;
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

        System.out.println(catalog.FindBookByTitle("MOBY DicK"));


    }
}

