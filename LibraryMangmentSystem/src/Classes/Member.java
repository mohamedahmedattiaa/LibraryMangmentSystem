package Classes;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
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
    private static Node membersList;

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
            writer = new BufferedWriter(new FileWriter("TempMembers.txt", true)); // Use true to append data
            reader = new BufferedReader(new FileReader("TempMembers.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Display all members' details
    public static String display() throws IOException {
        String line;
        System.out.println("Member Details:");

        // Initialize a BufferedReader to read from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("TempMembers.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String memberID = data[0].trim();
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String password = data[3].trim();

                    if (SearchMember(memberID) == null) {
                        System.out.println("Member ID: " + memberID);
                        System.out.println("Name: " + name);
                        System.out.println("Email: " + email);
                        System.out.println("Password: " + password);
                        System.out.println("----------");
                    } else {
                        System.out.println("Member with ID " + memberID + " already exists. Skipping.");
                    }
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

    public static List<Member> readMembersFromFile(String filePath) throws IOException {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String memberID = parts[0].trim();
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    String password = parts[3].trim();
                    members.add(new Member(name, email, password));
                }
            }
        }
        return members;
    }

    // Search for a member by memberID
    public static Member SearchMember(String memberID) throws IOException {
        if (memberID == null || memberID.isEmpty()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("TempMembers.txt"))) {
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

    public static boolean removeMember(String memberID) throws IOException {
        File inputFile = new File("TempMembers.txt");
        File tempFile = new File("TempMembers.txt");

        boolean isRemoved = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].trim().equals(memberID)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    isRemoved = true; // Member found and skipped
                }
            }
        }

        // Replace the old file with the new one
        if (isRemoved && inputFile.delete() && tempFile.renameTo(inputFile)) {
            System.out.println("Member with ID " + memberID + " removed successfully.");
        } else if (!isRemoved) {
            System.out.println("Member with ID " + memberID + " not found.");
        }

        return isRemoved;
    }

    public static boolean updateMember(String memberID, String newName, String newEmail, String newPassword) throws IOException {
        File inputFile = new File("TempMembers.txt");
        File tempFile = new File("TempMembers.txt");

        boolean isUpdated = false;
        List<String> fileContent = new ArrayList<>(); // To store the file contents temporarily

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].trim().equals(memberID)) {
                    data[1] = newName;
                    data[2] = newEmail;
                    data[3] = newPassword;
                    isUpdated = true;
                }
                fileContent.add(String.join(",", data)); // Store the updated or original line
            }
        }

        // Rewrite the file with updated content
        if (isUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
                for (String line : fileContent) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("Member with ID " + memberID + " updated successfully.");
        } else {
            System.out.println("Member with ID " + memberID + " not found.");
        }

        return isUpdated;
    }


    public static void addMember(String name, String email, String password) throws IOException {
        try {
            Member newMember = new Member(name, email, password); // Constructor handles validations and writing.
            System.out.println("Member added successfully: " + newMember);
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    public static Member getMemberByIdAndName(String memberId, String name) throws IOException {
        List<Member> members = readMembersFromFile(String.valueOf(new File("TempMembers.txt")));
        for (Member member : members) {
            if (member.getmemberId().equals(memberId) && member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public static void displayBooksBorrowed(String memberID) throws IOException {
        System.out.println("Books Borrowed by Member ID: " + memberID);
        Report.displayActiveLoansForMember(memberID);
    }

    private static String findExistingMemberID(String name, String email, String password) {
        try (Scanner scanner = new Scanner(new File("TempMembers.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String existingName = data[1].trim();
                    String existingEmail = data[2].trim();
                    if (existingName.equalsIgnoreCase(name) && existingEmail.equalsIgnoreCase(email)) {
                        return data[0].trim();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return null;
    }

    public static void sortMembers(String sortBy) throws IOException {
        File inputFile = new File("TempMembers.txt");
        List<String> memberLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                memberLines.add(line);
            }
        }
        switch (sortBy.toLowerCase().trim()) {
            case "memberid":
                memberLines.sort(Comparator.comparing(line -> line.split(",")[0].trim()));
                break;
            case "name":
                memberLines.sort(Comparator.comparing(line -> line.split(",")[1].trim()));
                break;
            case "email":
                memberLines.sort(Comparator.comparing(line -> line.split(",")[2].trim()));
                break;
            default:
                System.out.println("Invalid sorting criteria. Please choose 'memberID', 'name', or 'email'.");
                return;
        }
        System.out.println("Sorted Members by " + sortBy + ":");
        for (String line : memberLines) {
            String[] data = line.split(",");
            if (data.length >= 4) {
                System.out.println("Member ID: " + data[0].trim());
                System.out.println("Name: " + data[1].trim());
                System.out.println("Email: " + data[2].trim());
                System.out.println("Password: " + data[3].trim());
                System.out.println("----------");
            }
        }
    }

}
