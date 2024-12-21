package Classes;

import java.io.*;
import java.util.Scanner;

public class Catalog {
    public static linkedlist bookList = new linkedlist();
    private static final String FILE_PATH = "catalog.txt"; // File o store the catalog data

    public Catalog() {
        loadCatalogFromFile(); // Load catalog data when the program starts
    }

    public static void addBook(Book book) {
        // Check if the book already exists in the file
        if (searchBook(book.getBookID()) != null) {
            System.out.println("Book with ID " + book.getBookID() + " already exists in the catalog.");
            return; // Exit the method without adding the book
        }
        bookList.insertAtBeginning(book);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(book.getBookID() + "," +
                    book.getBookTitle() + "," +
                    book.getAuthor() + "," +
                    book.getGenere() + "," +
                    book.getAvailablityStatus());
            writer.newLine();
            System.out.println("Added to catalog: " + book);
        } catch (IOException e) {
            System.err.println("Error adding book to catalog: " + e.getMessage());
        }
    }


    public static boolean removeBook(String bookId) {
        boolean isRemoved = bookList.deleteById(bookId);
        if (isRemoved) {
            System.out.println("Book with ID " + bookId + " removed successfully.");
        } else {
            System.out.println("Book with ID " + bookId + " not found.");
        }
        saveCatalogToFile(); // Save updated catalog to file
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
                    saveCatalogToFile();
                    return "Book details and availability status updated successfully!";
                } else {
                    return "Book is not available for update!";
                }
            }
        }
        return "Book not found!";
    }

    public static Book searchBook(String bookId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse the line to create a Book object
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String fileBookId = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    boolean availabilityStatus = Boolean.parseBoolean(parts[4]);

                    // If the book ID matches, return the Book object
                    if (fileBookId.equals(bookId)) {
                        return new Book(fileBookId, title, author, genre, availabilityStatus);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error searching for book in file: " + e.getMessage());
        }
        return null; // Return null if the book is not found
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

    public static void Sorting(String SortedBy) {
        bookList.sorting(SortedBy);
    }

    private static void saveCatalogToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Node current = bookList.getHead(); current != null; current = current.getNext()) {
                Book book = current.getBook();
                writer.write(book.toString()); // Customize toString for saving data
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving catalog to file: " + e.getMessage());
        }
    }

    private static void loadCatalogFromFile() {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Parse the line to create a Book object
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String bookId = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    boolean availabilityStatus = Boolean.parseBoolean(parts[4]);

                    Book book = new Book(bookId, title, author, genre, availabilityStatus);
                    bookList.insertAtEnd(book);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Catalog file not found. Starting with an empty catalog.");
        } catch (Exception e) {
            System.err.println("Error loading catalog from file: " + e.getMessage());
        }
    }
    public static void updateBookAvailabilityInFile(String bookId , boolean ava) throws IOException {
        File originalFile = new File("catalog.txt"); // The file where book data is stored
        File tempFile = new File("catalog_temp.txt"); // Temporary file to hold updated data
        Book book = Catalog.searchBook(bookId);
        boolean bookFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Assuming the file format: BookID,Title,Author,Availability
                if (data.length >= 5 && data[0].equals(bookId)) {
                    data[4] = String.valueOf(ava); // Update availability status
                    bookFound = true;
                }

                // Write the updated or unchanged line to the temp file
                writer.println(String.join(",", data));
            }
        }

        // Replace the original file with the temporary file
        if (bookFound) {
            if (originalFile.delete()) {
                if (!tempFile.renameTo(originalFile)) {
                    System.out.println("Error: Could not rename temp file.");
                } else {
                    System.out.println("Book availability updated successfully in the file.");
                }
            } else {
                System.out.println("Error: Could not delete original file.");
            }
        } else {
            tempFile.delete();
            System.out.println("Book ID not found in the file.");
        }
    }

}
