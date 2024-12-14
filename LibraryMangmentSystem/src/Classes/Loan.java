package Classes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Loan {
    private String loanID;
    private String bookId;
    private String memberId;
    private Member member;
    private Date issueDate;
    private Date returnDate;
    private static final String FILE_NAME = "loans.txt";


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

    public Loan() {
        this.loanID = IDGenerator.generateLoanID();
        this.bookId = getBookId();
        this.memberId = getMemberId();
        this.issueDate = new Date();

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

    public static void borrowBook(String memberID, String bookId) throws IOException {
        Member member = Member.SearchMember(memberID);
        if (member == null) {
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
            saveLoansToFile();
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
                for (Loan loan : activeLoans) {
                    if (loan.getMemberId().equals(memberId) && loan.getBookId().equals(bookId)) {
                        System.out.println("You already returned this book.");
                        return;
                    }
                }
                book.setAvailablityStatus(true);
                activeLoans.remove(currentLoan);
                returnedLoans.add(currentLoan);
                System.out.println("Book " + book.getBookTitle() + " returned by member " + member.getName() + ".");
                System.out.println("Return Date: " + returnDate);
                saveLoansToFile();
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

    public static void sortloanBydate() {
        List<Loan> loanList = new ArrayList<>(activeLoans);
        loanList.sort(Comparator.comparing(Loan::getIssueDate));
        activeLoans.clear();
        activeLoans.addAll(loanList);
        List<Loan> returnedList = new ArrayList<>(returnedLoans);
        returnedList.sort(Comparator.comparing(Loan::getIssueDate));
        returnedLoans.clear();
        returnedLoans.addAll(returnedList);

        System.out.println("Loan requests sorted by date.");
    }

    public static void sortloanByMemberID() {
        List<Loan> loanList = new ArrayList<>(activeLoans);
        loanList.sort(Comparator.comparing(Loan::getMemberId));
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
        returnDate1.set(2023, 1, 1);
        this.returnDate = returnDate1.getTime();
    }

    public static void saveLoansToFile() throws IOException {
        Set<String> existingLoanIds = new HashSet<>();
        File file = new File(FILE_NAME);

        // Load existing loan IDs to avoid duplicates
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length > 0) {
                        existingLoanIds.add(data[0]); // Assuming the first field is the loan ID
                    }
                }
            }
        }

        // Append new loans that are not already in the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            for (Loan loan : Loan.activeLoans) {
                if (!existingLoanIds.contains(loan.getLoanID())) {
                    writer.println(loan.getLoanID() + "," + loan.getBookId() + "," + loan.getMemberId() +
                            "," + loan.getIssueDate() + "," + loan.getReturnDate());
                }
            }
            writer.flush();
            System.out.println("Loans saved to file.");
        }
    }


    public static void loadLoansFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No loan data found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Ensure we have at least 4 fields: LoanId, BookID, MemberID, loan status
                if (data.length >= 4) {
                    String LoanId = data[0];
                    String BookID = data[1];
                    String MemberID = data[2];
                    String loanStatus = data[3];

                    Member member = Member.SearchMember(MemberID);


                    if (!loanStatus.equals("No books borrowed") && data.length > 4) {
                        // Add each book ID to the active loans and associate it with the member
                        for (int i = 4; i < data.length; i++) {
                            String bookId = data[i];
                            Book book = Catalog.searchBook(bookId);  // Assuming you have a Catalog class to search for books

                            if (book != null) {
                                Loan loan = new Loan(bookId, MemberID);
                                activeLoans.add(loan);  // Add to active loans
                                member.addLoan(loan);  // Add loan to member's loan list
                                System.out.println("Loan added: Member " + member.getName() + " borrowed book " + bookId);
                            } else {
                                System.out.println("Book with ID " + bookId + " not found.");
                            }
                        }
                    } else {
                        System.out.println("No books borrowed for member: " + member.getName());
                    }
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
            System.out.println("Loans loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading loans from file: " + e.getMessage());
        }
    }




    private static Date parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + dateString);
            return null;
        }
    }
    public static void clearLoanFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.close();
            writer.flush();
            System.out.println("Loan file cleared.");
        } catch (IOException e) {
            System.out.println("Error clearing the loan file: " + e.getMessage());
        }
    }

}