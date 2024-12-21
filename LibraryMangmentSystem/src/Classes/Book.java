package Classes;

public class Book {
    private String bookID;
    private String bookTitle;
    private String author;
    private String genere;
    private boolean availablityStatus;
    private IDGenerator idGenerator;

        public Book(String bookTitle, String author , String genere) {
        this.bookID = IDGenerator.generateBookID();
        this.bookTitle = bookTitle.toLowerCase();
        this.author = author;
        this.genere = genere;
        this.availablityStatus = (Boolean) true;
    }

    public Book() {
    }

    public Book(String bookId, String title, String author, String genre, boolean availabilityStatus) {
            this.bookID = bookId;
            this.bookTitle = title;
            this.author = author;
            this.genere = genre;
            this.availablityStatus = availabilityStatus;

    }

    public boolean getAvailablityStatus() {
        return availablityStatus;
    }

    public void setAvailablityStatus(boolean availablityStatus) {

            this.availablityStatus = availablityStatus;
    }

    public String getGenere() {return genere;}

    public String getAuthor() {
        return author;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getBookTitle() {
        return bookTitle.toLowerCase();
    }

    public String getBookID() {
        return bookID;
    }


    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", bookTitle='" + bookTitle.toLowerCase() + '\'' +
                ", author='" + author + '\'' +
                ", genere='" + genere + '\'' +
                ", availablityStatus=" + availablityStatus +
                '}';
    }
}
