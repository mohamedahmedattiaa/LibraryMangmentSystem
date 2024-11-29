import java.util.Date;

public class Loan {
    private String loanID;
    private String bookId;
    private String memberId;
    private Member member;
    private Date issueDate;
    private Date returnDate;

    public Loan(String bookId, String memberId) {
        this.loanID = IDGenerator.generateLoanID();
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = new Date();
        this.returnDate = null;
    }
    public Loan(){
        this.loanID = IDGenerator.generateLoanID();
        this.bookId = getBookId();
        this.memberId = getMemberId();
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

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public static void borrowBook(Member member, String bookId, catalog catalog) {
        Book book = catalog.searchBook(bookId);
        if (book != null && book.getAvailablityStatus()) {
            book.setAvailablityStatus(false);
            Loan loan = new Loan(bookId, member.getmemberId());
            System.out.println("Loan successfully created: " + loan.getLoanID() + " for book '" + book.getBookTitle() + "' by member '" + member.getName() + "'.");
        } else {
            System.out.println("Book '" + bookId + "' is not available. Adding request for member '" + member.getName() + "'.");
            Loan loan = new Loan(bookId, member.getmemberId());
            PendingRequestsQueue.enqueue(loan);  // Add the loan request to the queue
        }
    }

    public static void returnBook(Member member, String bookId, catalog catalog) {
        Book book = catalog.searchBook(bookId);

        if (book != null) {
            if (!book.getAvailablityStatus()) {
                book.setAvailablityStatus(true);
                Loan currentLoan = new Loan(bookId, member.getmemberId());
                Date returnDate = new Date();
                currentLoan.setReturnDate(returnDate);

                System.out.println("Book '" + book.getBookTitle() + "' returned by member '" + member.getName() + "'.");
                System.out.println("Return Date: " + returnDate);

                if (!PendingRequestsQueue.isEmpty()) {
                    Loan nextLoan = PendingRequestsQueue.dequeue();
                    String nextMemberId = nextLoan.getMemberId();
                    System.out.println("Processing next request for book: " + bookId + " for member: " + member.getName() + " , memberId: " + member.getmemberId());
                    borrowBook(new Member(nextMemberId, "Unknown"), bookId, catalog);
                }
            } else {
                System.out.println("Book '" + book.getBookTitle() + "' is already available.");
            }
        } else {
            System.out.println("Book with ID '" + bookId + "' not found in catalog.");
        }
    }

    @Override
    public String toString() {
        return "Loan Details:\n" +
                "Loan ID      : " + loanID + "\n" +
                "Book ID      : " + bookId + "\n" +
                "Member ID    : " + memberId + "\n" +
                "Issue Date   : " + issueDate + "\n" +
                "Return Date  : " + (returnDate != null ? returnDate : "Not returned yet");
    }
}
