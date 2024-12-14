package Classes;
import java.io.IOException;
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
    static BufferedWriter writer;
    static BufferedReader reader;


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

    public Loan(String loanID, String bookId, String memberId, Date issueDate, Date returnDate) throws IOException {
        this.loanID = loanID;
        this.bookId = bookId;
        this.memberId = memberId;
        Member member = Member.SearchMember(memberId);
        this.issueDate = issueDate;
        this.returnDate = returnDate;
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

            // Create a new Loan and set issueDate and returnDate
            Loan loan = new Loan(bookId, member.getmemberId());
            saveLoansToFile(loan);
            addloanA(loan.getLoanID());



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
    public static void addloanA(String loanID){
        Loan loan = searchLoan(loanID);
        if (loan != null) {
            activeLoans.add(loan);
        } else {
            System.out.println("Loan ID not found.");
        }

    }
    public static void addloanR(String loanID){
        Loan loan = searchLoanR(loanID , new Date());
        if (loan != null) {
            returnedLoans.add(loan);
        } else {
            System.out.println("Loan ID not found.");
        }

    }



    public static void returnBook(String memberId, String bookId) throws IOException {
        Book book = Catalog.searchBook(bookId);
        Member member = Member.SearchMember(memberId);
        Date returnDate = new Date(); // Current return date

        if (book != null) {
            if (!book.getAvailablityStatus()) {
                book.setAvailablityStatus(true);
                Loan currentLoan = null;
                // Find the active loan for this member and book
                for (Loan loan : activeLoans) {
                    if (loan.getMemberId().equals(memberId) && loan.getBookId().equals(bookId)) {
                        currentLoan = loan;
                        break;
                    }
                }




                // Check if the return date is overdue
                if (returnDate.after(currentLoan.getReturnDate())) {
                    System.out.println("You have returned the book past the due date!");
                }




                activeLoans.remove(currentLoan);
                addloanR(currentLoan.getLoanID());

                System.out.println("Book " + book.getBookTitle() + " returned by member " + member.getName() + ".");
                System.out.println("Return Date: " + returnDate);

                // Process the next pending request for the book, if any
                if (!PendingRequestsQueue.isEmpty()) {
                    Loan nextLoan = PendingRequestsQueue.dequeue();
                    Member nextMember = Member.SearchMember(nextLoan.memberId);
                    System.out.println("Processing next request for book: " + bookId + " for member: " + nextMember.getName());
                    borrowBook(nextMember.getmemberId(), bookId);
                }
            } else {
                System.out.println("Book " + book.getBookTitle() + " is already available.");
            }
        } else {
            System.out.println("Book with ID " + bookId + " not found in catalog.");
        }
    }



    private static void updateLoanInFile(String loanID, Date newReturnDate) throws IOException {
        List<String> updatedLines = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy"); // Match the date format in the file
        boolean returnDateUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("loans.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5 && data[0].equals(loanID)) {
                    // Only update the return date, do not modify the issue date
                    data[4] = dateFormat.format(newReturnDate);
                    returnDateUpdated = true;
                }
                updatedLines.add(String.join(",", data)); // Add the updated line or unchanged line
            }
        }

        // Only write to the file if the return date was updated
        if (returnDateUpdated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("loans.txt"))) {
                for (String updatedLine : updatedLines) {
                    writer.println(updatedLine);
                }
            }
            System.out.println("Return date updated for loan ID " + loanID);
        } else {
            System.out.println("No update needed for return date. It was already the same.");
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

   public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
   }

    public static void saveLoansToFile(Loan loan) throws IOException {
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
           {
                if (!existingLoanIds.contains(loan.getLoanID())) {
                    writer.println(loan.getLoanID() + "," + loan.getBookId() + "," + loan.getMemberId() +
                            "," + loan.getIssueDate() + "," + loan.getReturnDate());
                }
            }
            writer.flush();
        }
    }



    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        return formatter.parse(dateString);
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


    public static void displayLoanFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("Contents of the Loan file:");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) { // Check if all fields are present
                    System.out.printf("Loan id: %s Book Id: %s Member Id: %s Issue Date: %s%n", 
                            data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Loan file not found.");
        } catch (IOException e) {
            System.out.println("Error reading the loan file: " + e.getMessage());
        }
    }

    public static Loan searchLoan(String loanID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(loanID)) {
                    String loanId =data[0];
                    String BookID = data[1];
                    String memberID = data[2];
                    Date issuedate = parseDate(data[3]);
                    Date returndate = parseDate(data[4]);
                     Loan loan = new Loan(loanId,BookID,memberID,issuedate,returndate);
                   return loan;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
           return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static Loan searchLoanR(String loanID , Date ReturnDate) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(loanID)) {
                    String loanId =data[0];
                    String BookID = data[1];
                    String memberID = data[2];
                    Date issuedate = parseDate(data[3]);
                    Date returndate = ReturnDate;
                    Loan loan = new Loan(loanId,BookID,memberID,issuedate,returndate);
                    return loan;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeLoanById(String loanID) throws IOException {
        if (searchLoan(loanID).equals("Loan ID not found.")) {
            System.out.println("Loan ID " + loanID + " does not exist in the file.");
            return;
        }

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(loanID)) {
                    continue; // Skip the line with the matching loan ID
                }
                updatedLines.add(line); // Add all other lines to the updated list
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
            writer.flush();
        }

        System.out.println("Loan ID " + loanID + " has been removed from the file.");
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Loan loan = (Loan) obj;
        return loanID.equals(loan.loanID); // Compare by loan ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanID); // Hash based on loan ID
    }
    
    
    
    
    
    
    }