public class catalog {
    private linkedlist bookList;

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

    public Book searchBook(String bookId) {
        return bookList.Searchbook(bookId);
    }

    public void displayCatalog() {
        System.out.println("Library Catalog:");
        bookList.display();
    }
}
