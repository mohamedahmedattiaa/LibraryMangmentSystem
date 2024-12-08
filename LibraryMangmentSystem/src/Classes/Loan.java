package Classes;

import java.io.IOException;
import java.util.Calendar;
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
        Calendar calendar = Calendar.getInstance(); // adds 14 days to the issue date
        calendar.setTime(this.issueDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        this.returnDate = calendar.getTime();
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

    public static void borrowBook(String memberID, String bookId, Catalog catalog) throws IOException {
        Member member = dataBaseMembers.SearchMember(memberID);
        if(member == null) {
            System.out.println("No member found with ID: " + memberID);
            return;
        }
        Book book = Catalog.searchBook(bookId);
        if (book != null && book.getAvailablityStatus()) {
            book.setAvailablityStatus(false);
            Loan loan = new Loan(bookId, member.getmemberId());
            System.out.println("Loan successfully created: " + loan.getLoanID() + " for book " + book.getBookTitle() + " by member " + member.getName() + "your return date is: " + loan.getReturnDate() + ".");
        } else if (book != null && !book.getAvailablityStatus()) {
            System.out.println("Book " + bookId + " is not available. Adding request for member " + member.getName() + ".");
            Loan loan = new Loan(bookId, member.getmemberId());
            PendingRequestsQueue.enqueue(loan); // Add the loan request to the queue
        } else {
            System.out.println("Book with ID " + bookId + " not found in catalog.");
        }
    } // bla bla bla

    public static void returnBook(String memberId, String bookId, Catalog catalog) throws IOException {
        Book book = Catalog.searchBook(bookId);
        Member member = dataBaseMembers.SearchMember(memberId);
        Date returnDate = new Date();
        if (book != null) {
            if (!book.getAvailablityStatus()) {
                Loan currentLoan = new Loan(bookId, member.getmemberId());
                if (returnDate.after(currentLoan.getReturnDate())) {  //checking if he passed the return date
                    System.out.println("yous passed the return date");
                }
                book.setAvailablityStatus(true);

                System.out.println("Book " + book.getBookTitle() + " returned by member " + member.getName() + ".");
                System.out.println("Return Date: " + returnDate);

                if (!PendingRequestsQueue.isEmpty()) {
                    Loan nextLoan = PendingRequestsQueue.dequeue();
                    Member nextMember = dataBaseMembers.SearchMember(nextLoan.memberId);
                    System.out.println("Processing next request for book: " + bookId + " for member: " + nextMember.getName() + " , memberId: " + nextMember.getmemberId());
                    borrowBook(nextMember.getmemberId(), bookId, catalog); // edited
                }
            } else {
                System.out.println("Book " + book.getBookTitle() + " is already available.");
            }
        } else {
            System.out.println("Book with ID " + bookId + " not found in catalog.");
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