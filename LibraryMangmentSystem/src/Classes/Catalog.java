package Classes;

import java.util.LinkedList;
import java.util.Queue;

public class Catalog {
    public static linkedlist bookList =new linkedlist();
    public Catalog() {
    }

    public static void addBook(Book book) {
        bookList.insertAtEnd(book);
        System.out.println("Added to catalog: " + book);

    }

    public static boolean removeBook(String bookId) {
        boolean isRemoved = bookList.deleteById(bookId);
        if (isRemoved) {
            System.out.println("Book with ID " + bookId + " removed successfully.");
        } else {
            System.out.println("Book with ID " + bookId + " not found.");
        }
        System.out.println("After removing:");
        bookList.display();
        return isRemoved;
    }
    public static String updateBook(String bookId, boolean availabilityStatus) {
        Book book = bookList.Searchbook(bookId);
        if (book != null) {
           book.setAvailablityStatus(false);
            return "Book updated: " + book;
        }
        return "Book with ID " + bookId + " not found.";
    }

    public static Book searchBook(String bookId) {
        return bookList.Searchbook(bookId);
    }

    public static void displayCatalog() {
        bookList.display();
    }

   /* public static String FindBookByTitle(String bookTitle) {
        bookTitle = bookTitle.toLowerCase();
        String bookid = bookList.Findbook(bookTitle);
        if (bookid == null) {
            return "Book not found: " + bookTitle;
        }
        Book book = Catalog.searchBook(bookid);
        if (book == null) {
            return "Book not found: " + bookTitle;
        }
        if (!book.getAvailablityStatus()) {
            return "Book not available: " + bookTitle;
        }
        String bookID = book.getBookID();
        return "Book found: " + bookTitle + " in section: " + bookID.charAt(0) +
                " in roof number: " + bookID.charAt(1) +
                " the number of the Book is: " + bookID.substring(2);
    }*/

   public static Node FindBookByTitleOrAuthor(String searchQuery, String sortedBy) {
       Node foundBooks = null;

       // Traverse all the books and check if the title or author matches the query
       Node temp = bookList.getHead(); // Traverse the linked list using the head of bookList
       while (temp != null) {
           Book book = temp.book;
           if (book.getBookTitle().contains(searchQuery) || book.getAuthor().contains(searchQuery)) {
               Node newNode = new Node(book);
               foundBooks = bookList.merge(foundBooks, newNode, sortedBy); // Sort the books using the merge function
           }
           temp = temp.next; // Move to the next node
       }

       return foundBooks;
   }

    public static void Sorting(String SortedBy){
        bookList.sorting(SortedBy);
    }
}
