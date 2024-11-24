public class Book {
    private int bookID;
    private String bookTitle;
    private String author;
    private String genere;
    private Boolean availablityStatus;

    public Book(int bookID, String bookTitle, String author , String genere , Boolean availablityStatus) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.author = author;
        this.genere = genere;
        this.availablityStatus = availablityStatus;

    }

    public Boolean getAvailablityStatus() {
        return availablityStatus;
    }

    public void setAvailablityStatus(Boolean availablityStatus) {
        this.availablityStatus = availablityStatus;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", genere='" + genere + '\'' +
                ", availablityStatus=" + availablityStatus +
                '}';
    }
}
