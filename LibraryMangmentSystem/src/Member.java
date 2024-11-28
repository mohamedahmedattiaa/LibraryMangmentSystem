public class Member {
    private String memberId; //?????
    private String name;
    private String contactInfo;
    private linkedlist booksBorrowed;
    private IDGenerator idGenerator;

    public Member( String name, String contactInfo) {
        this.memberId = IDGenerator.generateMemberID();
        this.name = name;
        this.contactInfo = contactInfo;
        this.booksBorrowed = new linkedlist();

    }

    public String getmemberId() {return memberId;}

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }


    public void borrowBook(Book book) { // id??
        if(book.getAvailablityStatus()) {
            booksBorrowed.insertAtBeginning(book);
          book.setAvailablityStatus(false);
            System.out.println(name+" Book Borrowed successfully "+book.getBookTitle());
        }else{
            System.out.println(name+" Book Not Borrowed "+book.getBookTitle());
        }
    }

    public void returnBook(Book book) { // id
        if(booksBorrowed.Search(book.getBookID())){
            booksBorrowed.delete(book.getBookID());
            book.setAvailablityStatus(true);
            System.out.println(name+" Book Returned successfully "+book.getBookTitle());
        }else{
            System.out.println(name+" Book is already in the catalog "+book.getBookTitle());
        }
    }
}
