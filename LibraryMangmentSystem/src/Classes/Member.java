package Classes;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Member {
    private String memberId;
    private String name;
    private String Email;
    private String password;
    private static linkedlist booksBorrowed;
    private IDGenerator idGenerator;
    static BufferedWriter writer;
    static BufferedReader reader;
    private List<Loan> loans;

    public Member(String name, String Email, String password) throws IOException {
        if (Email.contains("@") && Email.contains(".")) {
            this.Email = Email;
        } else throw new IllegalArgumentException("Invalid Email");

        this.memberId = IDGenerator.generateMemberID(name, Email);
        this.name = name;
        this.password = password;
        this.booksBorrowed = new linkedlist();

        // Check if the member already exists using name, email, and password
        if (findExistingMemberID(name, Email, password) == null) {
            try {
                // Write member data to the file
                writer.write(this.memberId + "," + this.name + "," + this.Email + "," + this.password + ",");
                writer.write(booksBorrowed.toStringForMember());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.flush();
        }
    }

    public static void updateMemberFile(String memberId, String bookId, boolean isBorrowing) {
        File memberFile = new File("members.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(memberFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(memberFile, true))) {

            String line;
            boolean found = false;
            StringBuilder updatedData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String[] memberData = line.split(",");
                if (memberData[0].equals(memberId)) {
                    found = true;
                    // Update borrowed books list
                    if (isBorrowing) {
                        updatedData.append(line).append(",").append(bookId).append("\n");
                    } else {
                        // Remove the returned book
                        String[] books = memberData[3].split(",");
                        StringBuilder updatedBooks = new StringBuilder();
                        for (String book : books) {
                            if (!book.equals(bookId)) {
                                updatedBooks.append(book).append(",");
                            }
                        }
                        // Remove the trailing comma
                        updatedBooks.setLength(updatedBooks.length() - 1);
                        updatedData.append(memberData[0] + "," + memberData[1] + "," + memberData[2] + "," + updatedBooks.toString() + "\n");
                    }
                } else {
                    updatedData.append(line).append("\n");
                }
            }

            if (!found) {
                // If member does not exist in the file, add new entry
                updatedData.append(memberId + "," + "Email" + "," + "PhoneNumber" + "," + bookId + "\n");
            }

            // Write the updated data to the file
            try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(memberFile))) {
                writer2.write(updatedData.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Member() throws IOException {
    }

    public String getmemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", Email='" + Email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    static {
        try {
            writer = new BufferedWriter(new FileWriter("Members.txt", true)); // Use true to append data
            reader = new BufferedReader(new FileReader("Members.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Add a member to the file
    public static void add(Member member, Catalog catalog) throws IOException {
        if (member != null) {
            writer.write(member.getmemberId() + "," + member.getName() + "," + member.getEmail() + "," + member.getPassword() + " , ");
            writer.write("\n");
            writer.flush();
        }
    }

    // Display all members' details
    public static String display() throws IOException {
        String line = "";
        System.out.println("Member Details: ");

        // Initialize a BufferedReader to read from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("Members.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) { // Ensure the array has all necessary parts
                    String memberId = data[0].trim();
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String password = data[3].trim();
                    String borrowedBooks = data[4].trim();

                    System.out.println("Member ID: " + memberId);
                    System.out.println("Name: " + name);
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);
                    System.out.println("Borrowed Books: " + borrowedBooks);
                    System.out.println("----------");
                } else {
                    System.out.println("Error: Invalid data format in the file.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return "End of Member List";
    }

    // Search for a member by memberID
    public static Member SearchMember(String memberID) throws IOException {
        if (memberID == null || memberID.isEmpty()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("Members.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && data[0].trim().equals(memberID)) {
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String password = data[3].trim();
                    return new Member(name, email, password);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return null;
    }

    // Add a book to the member's borrowed list
    public void addBook(Book book) {
        if (booksBorrowed == null) {
            booksBorrowed = new linkedlist();
        }
        booksBorrowed.insertAtBeginning(book);
    }

    // Remove a book from the member's borrowed list
    public void remove(Book book) {
        booksBorrowed.deleteById(book.getBookID());
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }


    // Display books borrowed by the member using catalog data
    public static void displayBooksBorrowed(String memberID, Catalog catalog) throws IOException {
        if (catalog == null) {
            System.out.println("Catalog is not available.");
            return;
        }

        System.out.println("Books Borrowed by Member ID: " + memberID);
        Report.displayActiveLoansForMember(memberID, catalog);
    }

    // Check if the member already exists using name, email, and password
    private static String findExistingMemberID(String name, String email, String password) {
        try (Scanner scanner = new Scanner(new File("Members.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String existingName = data[1].trim();
                    String existingEmail = data[2].trim();
                    String existingPassword = data[3].trim();
                    if (existingName.equalsIgnoreCase(name) && existingEmail.equalsIgnoreCase(email) && existingPassword.equals(password)) {
                        return data[0].trim(); // Return the existing ID
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return null; // Return null if not found
    }
}
