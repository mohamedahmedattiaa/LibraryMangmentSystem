
import java.time.LocalDate;
import java.util.Date;

public class Loan {
    private String loanID;
    private String bookId;
    private String memberId;
    private Date issueDate;
    private Date returnDate;
    private IDGenerator idGenerator;
    public Loan( String bookId,  String memberId ){
        this.loanID = IDGenerator.generateLoanID();
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = new Date();
        this.returnDate = null;
    }

    public String getLoanID() {
        return loanID;
    }

    public String getBookId() {
        return bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void borrowBook(catalog catalog, Member member) {
        Book book = catalog.searchBook(bookId);
        if (book != null && book.getAvailablityStatus()) {
            book.setAvailablityStatus(false);
            System.out.println(member.getName() + " borrowed " + book.getBookTitle() + " on " + issueDate);
        } else {
            System.out.println("Book is unavailable.");
        }
    }

    public void returnBook(catalog catalog, Member member) {
        Book book = catalog.searchBook(bookId);
        if (book != null) {
            book.setAvailablityStatus(true);
            this.returnDate = new Date();
            System.out.println(member.getName() + " returned " + book.getBookTitle() + " on " + returnDate);
        } else {
            System.out.println("This book is not part of the catalog.");
        }
    }

    public void displayLoanDetails() {
        System.out.println("Loan ID: " + loanID);
        System.out.println("Book ID: " + bookId);
        System.out.println("Member ID: " + memberId);
        System.out.println("Issue Date: " + issueDate);
        if (returnDate != null) {
            System.out.println("Return Date: " + returnDate);
        } else {
            System.out.println("Book not yet returned.");
        }
    }
}
