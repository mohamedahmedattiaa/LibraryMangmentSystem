public class Member {
    private int bookId;
    private String name;
    private String contactInfo;
    private String booksBorrowed;

    public Member(int bookId, String name, String contactInfo, String booksBorrowed) {
        this.bookId = bookId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.booksBorrowed = booksBorrowed;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getBooksBorrowed() {
        return booksBorrowed;
    }

    public void setBooksBorrowed(String booksBorrowed) {
        this.booksBorrowed = booksBorrowed;
    }

//    public Book borrowBook(String bookName) {
//
//    }
}
