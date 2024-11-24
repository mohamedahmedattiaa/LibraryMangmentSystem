public class Member {
    private String bookId;
    private String name;
    private String contactInfo;
    private String booksBorrowed;
    private linkedlist linkedlist;
    private IDGenerator idGenerator;

    public Member( String name, String contactInfo, String booksBorrowed) {
        this.bookId = IDGenerator.generateMemberID();
        this.name = name;
        this.contactInfo = contactInfo;
        this.booksBorrowed = booksBorrowed;
    }

    public String getBookId() {
        return bookId;
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

    public void borrowBook(Book book) {
        if(book.getAvailablityStatus()) {
            linkedlist.insertAtBeginning(book);
          book.setAvailablityStatus(false);
            System.out.println(name+" Book Borrowed successfully "+book.getBookTitle());
        }else{
            System.out.println(name+" Book Not Borrowed "+book.getBookTitle());
        }
    }

    public void returnBook(Book book) {
        if(!linkedlist.search(book.getBookID())){
            book.setAvailablityStatus(true);
            System.out.println(name+" Book Returned successfully "+book.getBookTitle());
        }else{
            System.out.println(name+" Book is already in the catalog "+book.getBookTitle());
        }
    }
}
