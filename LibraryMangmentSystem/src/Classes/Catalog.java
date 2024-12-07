package Classes;

public class Catalog {
    public static linkedlist bookList;

    public Catalog() {
        this.bookList = new linkedlist();
    }

    public static void addBook(Book book) {
        bookList.insertAtEnd(book);
        System.out.println("Added to catalog: " + book);
    }

    public static void removeBook(Book book) {
        bookList.deleteAtEnd();
        System.out.println("Removed from catalog: " + book);
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

    public void displayCatalog() {
        System.out.println("Library Catalog:");
        bookList.display();
    }

    public String FindBookByTitle(String bookTitle) {
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
    }
    public void Sorting(String SortedBy){
        bookList.sorting(SortedBy);
    }
}
