package Classes;

public class Catalog {
    public static linkedlist bookList =new linkedlist();
    public Catalog() {
    }

    public static void addBook(Book book) {
        book.setAvailablityStatus(true);
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
    public static String checkBookAvailability(String bookId) {
        for (Node current = bookList.getHead(); current != null; current = current.getNext()) {
            Book book = current.getBook();
            if (book.getBookID().equals(bookId)) {
                if (book.getAvailablityStatus()) {
                    return "Book exists and is available!";
                } else {
                    return "Book exists but is not available!";
                }
            }
        }
        return "Book not found!";
    }

    public static String updateBook(String bookId, boolean availabilityStatus, String title, String author, String genre) {
        for (Node current = bookList.getHead(); current != null; current = current.getNext()) {
            Book book = current.getBook();
            if (book.getBookID().equals(bookId)) {
                if (book.getAvailablityStatus()) {
                    book.setAvailablityStatus(availabilityStatus);
                    book.setBookTitle(title);
                    book.setAuthor(author);
                    book.setGenere(genre);
                    return "Book details and availability status updated successfully!";
                } else {
                    return "Book is not available for update!";
                }
            }
        }
        return "Book not found!";
    }

    public static Book searchBook(String bookId) {
        return bookList.Searchbook(bookId);
    }

    public static void displayCatalog() {
        bookList.display();
    }

    public static boolean searchBookByTitle(String bookTitle) {
        bookTitle = bookTitle.toLowerCase();
        String bookId = bookList.Findbook(bookTitle);
        if (bookId == null) {
            return false;
        }
        Book book = Catalog.searchBook(bookId);
        if (book == null) {
            return false;
        }
        if (!book.getAvailablityStatus()) {
            return false;
        }
        String bookID = book.getBookID();
        System.out.println("Book found: " + bookTitle + " in section: " + bookID.charAt(0) +
                " in roof number: " + bookID.charAt(1) +
                " the number of the Book is: " + bookID.substring(2));

        return true;
    }


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