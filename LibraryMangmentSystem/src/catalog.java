public class catalog {
    public static linkedlist bookList;

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
        Book book = catalog.searchBook(bookid);
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
