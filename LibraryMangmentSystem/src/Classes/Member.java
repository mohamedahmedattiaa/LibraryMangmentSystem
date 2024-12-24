package Classes;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
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
    private static String currentMemberId;

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

    public Member(String memberId, String name, String email, String password) {
        this.memberId = memberId;
        this.name = name;
        this.Email = email;
        this.password = password;
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

    public static boolean removeMemberByID(String memberId) {
        String filePath = "TempMembers.txt";  // Ensure this is the correct path
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return false;  // File doesn't exist
        }

        try {
            // Read all lines from the file
            List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            // Skip the header (first line)
            if (!lines.isEmpty()) {
                String header = lines.get(0);  // Keep the header
                List<String> updatedLines = new ArrayList<>();
                updatedLines.add(header);  // Add header to new list

                // Process the lines and skip the one to remove
                boolean memberRemoved = false;
                for (String line : lines.subList(1, lines.size())) {
                    String[] data = line.split(",");

                    // Debugging: Print each member's ID and the comparison result
                    System.out.println("Checking ID: '" + data[0].trim() + "' against '" + memberId.trim() + "'");

                    // Compare and trim both sides to avoid issues with extra spaces
                    if (data[0].trim().equals(memberId.trim())) {
                        memberRemoved = true;  // Skip this line
                        System.out.println("Removed member: " + line);
                        continue;
                    }
                    updatedLines.add(line);  // Keep other lines
                }

                if (!memberRemoved) {
                    System.out.println("No member found with ID: " + memberId);
                    return false;  // No matching member found
                }

                // Write the updated lines back to the file
                Files.write(file.toPath(), updatedLines, StandardCharsets.UTF_8);
                System.out.println("Member with ID " + memberId + " has been removed.");
                return true;
            }

        } catch (IOException e) {
            System.out.println("Error reading or writing file: " + e.getMessage());
        }

        return false;  // Something went wrong
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

    public static Member getMemberByIDandName(String name, String memberId) {
        String filePath = "TempMembers.txt";  // Ensure this is the correct path

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Skip the header line if it exists
            reader.readLine();  // Remove this line if there's no header

            // Read through the file line by line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {  // Ensure there are 4 values (ID, Name, Email, Password)
                    String id = data[0].trim();
                    String memberName = data[1].trim();
                    String email = data[2].trim();
                    String password = data[3].trim();

                    // Debugging output to check the read data
                    System.out.println("Checking: ID=" + id + ", Name=" + memberName);
                    System.out.println("Looking for: ID=" + memberId + ", Name=" + name);

                    if (id.equals(memberId) && memberName.equals(name)) {
                        return new Member(id, memberName, email, password);
                    }
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return null;  // No member found
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public static void displayBooksBorrowed(String memberID) throws IOException, ParseException, ClassNotFoundException {
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

        // Sort based on the chosen criteria
        switch (sortBy.toLowerCase().trim()) {
            case "memberid":
                // Sorting by Member ID in ascending order
                memberLines.sort(Comparator.comparing(line -> line.split(",")[0].trim()));
                break;
            case "name":
                memberLines.sort(Comparator.comparing(line -> {
                    String name = line.split(",")[1].trim();  // Get full name
                    return name.toLowerCase();
                }));
                break;
            default:
                System.out.println("Invalid sorting criteria. Please choose 'memberid', 'name'");
                return;
        }

        // Write the sorted data back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String line : memberLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }




    public static boolean isEmailTaken(String email) {
        try (Scanner scanner = new Scanner(new File("TempMembers.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length > 2 && parts[2].equals(email)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file not found: " + e.getMessage());
        }
        return false;
    }


    public static String validateUser(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("TempMembers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] memberData = line.split(",");
                if (memberData.length >= 4) {
                    String storedEmail = memberData[2].trim();
                    String storedPassword = memberData[3].trim();
                    if (storedEmail.equalsIgnoreCase(email) && storedPassword.equals(password)) {
                        return memberData[0].trim();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error during validation: " + e.getMessage());
        }
        return null; // Return null if no match found
    }

    public static String validateIDUser(String id,String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("TempMembers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] memberData = line.split(",");
                if (memberData.length >= 4) {
                    String storedID = memberData[0].trim();
                    String storedPassword = memberData[3].trim();
                    if (storedID.equalsIgnoreCase(id) && storedPassword.equals(password)) {
                        return storedID;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error during validation: " + e.getMessage());
        }
        return null;
    }
}
