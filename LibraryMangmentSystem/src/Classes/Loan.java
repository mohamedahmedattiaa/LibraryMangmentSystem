package Classes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Loan {
    private String loanID;
    private String bookId;
    private String memberId;
    private Member member;
    private Date issueDate;
    private Date returnDate;

    public static Queue<Loan> activeLoans = new LinkedList<>();
    public static Queue<Loan> returnedLoans = new LinkedList<>();

    public Loan(String bookId, String memberId) {
        this.loanID = IDGenerator.generateLoanID();
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = new Date(); // Initialize issueDate as the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.DAY_OF_MONTH, 14); // Add 14 days to create a return date
        this.returnDate = calendar.getTime();
    }
    public Loan(){
        this.loanID = IDGenerator.generateLoanID();
        this.bookId = getBookId();
        this.memberId = getMemberId();
        this.issueDate = new Date();

    }

    public String getLoanID() {
        return loanID;
    }

    public  String getBookId() {
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

    public static void borrowBook(String memberID, String bookId) throws IOException {
        Member member = Member.SearchMember(memberID);
        if(member == null) {
            System.out.println("No member found with ID: " + memberID);
            return;
        }

        for (Loan loan : activeLoans) {
            if (loan.getMemberId().equals(memberID) && loan.getBookId().equals(bookId)) {
                System.out.println("You already borrowed this book.");
                return;
            }
        }
        
        
        Book book = Catalog.searchBook(bookId);
        if (book != null && book.getAvailablityStatus()) {
            book.setAvailablityStatus(false);
            Loan loan = new Loan(bookId, member.getmemberId());
            activeLoans.add(loan);

            System.out.println("Loan successfully created: " + loan.getLoanID() + " for book " + book.getBookTitle() +
                    " by member " + member.getName() + "  your return date is: " + loan.getReturnDate() + ".");

        } else if (book != null && !book.getAvailablityStatus()) {
            System.out.println("Book " + bookId + " is not available. Adding request for member " + member.getName() + ".");
            Loan loan = new Loan(bookId, member.getmemberId());
            PendingRequestsQueue.enqueue(loan); // Add the loan request to the queue
        } else {
            System.out.println("Book with ID " + bookId + " not found in catalog.");
        }
    }

    public static void returnBook(String memberId, String bookId) throws IOException {
        Book book = Catalog.searchBook(bookId);
        Member member = Member.SearchMember(memberId);
        Date returnDate = new Date();
        if (book != null) {
            if (!book.getAvailablityStatus()) {
                Loan currentLoan = new Loan(bookId, member.getmemberId());
                if (returnDate.after(currentLoan.getReturnDate())) {  //checking if he passed the return date
                    System.out.println("yous passed the return date");
                }
                book.setAvailablityStatus(true);
                activeLoans.remove(currentLoan);
                returnedLoans.add(currentLoan);
                System.out.println("Book " + book.getBookTitle() + " returned by member " + member.getName() + ".");
                System.out.println("Return Date: " + returnDate);

                if (!PendingRequestsQueue.isEmpty()) {
                    Loan nextLoan = PendingRequestsQueue.dequeue();
                    Member nextMember = Member.SearchMember(nextLoan.memberId);
                    System.out.println("Processing next request for book: " + bookId + " for member: " + nextMember.getName() + " , memberId: " + nextMember.getmemberId());
                    borrowBook(nextMember.getmemberId(), bookId); // edited
                }
            } else {
                System.out.println("Book " + book.getBookTitle() + " is already available.");
            }
        } else {
            System.out.println("Book with ID " + bookId + " not found in catalog.");
        }
    }

    public static void sortloanBydate (){                                             // for librarian
        List<Loan> loanList = new ArrayList<>(activeLoans);
        List<Loan> returnedlist =new ArrayList<>(returnedLoans);
        returnedlist.sort(Comparator.comparing(Loan::getIssueDate));                                                                        // we convert the queue to an array to access the built-in sort method
        loanList.sort(Comparator.comparing(Loan::getIssueDate));
        returnedlist.clear();                                                                     // compare member id between each other
        activeLoans.clear();
        activeLoans.addAll(loanList);
        returnedLoans.addAll(returnedlist);
        System.out.println("Loan requests sorted by date.");
    }

    public static void sortloanByMemberID (){                                   // for librarian
        List<Loan> loanList = new ArrayList<>(activeLoans);                     // we convert the queue to an array to access the built-in sort method
        loanList.sort(Comparator.comparing(Loan::getMemberId));             // compare member id between each other
        activeLoans.clear();
        activeLoans.addAll(loanList);

        System.out.println("Loan requests sorted by Member ID.");
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
    public void setReturnDate() {               // setting the date to overdue ( ONLY FOR TESTING DON`T DELETE)
        Calendar returnDate1 = Calendar.getInstance();
        returnDate1.set(2023,1,1);
        this.returnDate = returnDate1.getTime();
    }


}
