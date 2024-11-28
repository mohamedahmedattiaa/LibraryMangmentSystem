public class catalog {
    private static linkedlist bookList;

    public catalog() {
        this.bookList = new linkedlist();
    }

    public void addBook(Book book) {
        bookList.insertAtEnd(book);
        System.out.println("Added to catalog: " + book);
    }

    public String updateBook(String bookId, boolean availabilityStatus) {
        Book book = bookList.Searchbook(bookId);
        if (book != null) {
            book.setAvailablityStatus(availabilityStatus);
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

    public static String FindBookByTitle(String bookTitle) {
        bookTitle = bookTitle.toLowerCase();
        String bookid = bookList.Findbook(bookTitle);
        Book book =catalog.searchBook(bookid);
        if (book == null) {
            return ("Book not found: " + bookTitle);
        } else if (book.getAvailablityStatus() == false) {
            return ("Book not available: " + bookTitle);
        } else if (book.getAvailablityStatus() == true) {
            return "Book found: " + bookTitle + " in section : " + book.getBookID().charAt(0) + " in roof number : " + book.getBookID().charAt(1) + " the number of the Book is :" + book.getBookID().charAt(2) + book.getBookID().charAt(3);
        }
        return null;
    }
}
