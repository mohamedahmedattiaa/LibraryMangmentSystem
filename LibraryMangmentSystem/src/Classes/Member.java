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
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.flush();
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
            writer = new BufferedWriter(new FileWriter("Members.txt", false)); // Use true to append data
            reader = new BufferedReader(new FileReader("Members.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                if (data.length >= 4) { // Ensure the array has all necessary parts
                    String memberId = data[0].trim();
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String password = data[3].trim();

                    System.out.println("Member ID: " + memberId);
                    System.out.println("Name: " + name);
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);
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

    public void addLoan(Loan loan) {
        loans.add(loan);
    }


    // Display books borrowed by the member using catalog data
    public static void displayBooksBorrowed(String memberID) throws IOException {

        System.out.println("Books Borrowed by Member ID: " + memberID);
        Report.displayActiveLoansForMember(memberID);
    }

    // Check if the member already exists using name, email, and password
    private static String findExistingMemberID(String name, String email, String password) {
        try (Scanner scanner = new Scanner(new File("Members.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String existingName = data[1].trim();
                    String existingEmail = data[2].trim();
                    if (existingName.equalsIgnoreCase(name) && existingEmail.equalsIgnoreCase(email)) {
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
