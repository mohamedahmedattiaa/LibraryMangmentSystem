
import java.time.LocalDate;
import java.util.Date;
public class Loan extends PendingRequestsQueue  {
    private String loanID;
    private String bookId;
    private String memberId;
    private Date issueDate;
    private Date returnDate;
    private IDGenerator idGenerator;

    public Loan( String bookId,  String memberId ){  // why book is an attribute
        this.loanID = IDGenerator.generateLoanID();  // defining loan will clarify
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = new Date();
        this.returnDate = null;
    }
    public Loan(){  // why book is an attribute
        this.loanID = IDGenerator.generateLoanID();  // defining loan will clarify
        this.bookId = getBookId();
        this.memberId =getMemberId();
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
    PendingRequestsQueue P = new PendingRequestsQueue();
    public void borrowBook( Member member ,String bookid) {  // should we send catalog not a book , and member ID ??
        Book book = catalog.searchBook(bookid);
        if (book != null && book.getAvailablityStatus()) {
            book.setAvailablityStatus(false);
            //serach for member
            System.out.println(member.getName() + " borrowed " + book.getBookTitle() + " on " + issueDate);

         bookId = bookid;
         memberId = member.getmemberId();
            System.out.println("Book is borrowed.");
        }
        if (book != null && book.getAvailablityStatus()==false){

            P.enqueue(bookid,member.getmemberId());
        }
        else {
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(Member member , String bookId) {
        Book book = catalog.searchBook(bookId);
        if (book != null) {
            book.setAvailablityStatus(true);
            this.returnDate = new Date();
            System.out.println(member.getName() + " returned " + book.getBookTitle() + " on " + returnDate);
            if(P.equals(book)){
                P.dequeue();
            }
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
