public class Member {
    private String bookId;
    private String name;
    private String contactInfo;
    private linkedlist booksBorrowed;
    private IDGenerator idGenerator;

    public Member( String name, String contactInfo) {
        this.bookId = IDGenerator.generateMemberID();
        this.name = name;
        this.contactInfo = contactInfo;
        this.booksBorrowed = new linkedlist();

    }

    public String getBookId() {return bookId;}

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }


    public void borrowBook(Book book) {
        if(book.getAvailablityStatus()) {
            booksBorrowed.insertAtBeginning(book);
          book.setAvailablityStatus(false);
            System.out.println(name+" Book Borrowed successfully "+book.getBookTitle());
        }else{
            System.out.println(name+" Book Not Borrowed "+book.getBookTitle());
        }
    }

    public void returnBook(Book book) {
        if(booksBorrowed.Search(book.getBookID())){
            booksBorrowed.delete(book.getBookID());
            book.setAvailablityStatus(true);
            System.out.println(name+" Book Returned successfully "+book.getBookTitle());
        }else{
            System.out.println(name+" Book is already in the catalog "+book.getBookTitle());
        }
    }
}
