package Classes;

import java.io.*;
import java.util.Scanner;

public  class Member {
    private String memberId;
    private String name;
    private String Email;
    private static linkedlist booksBorrowed;
    private IDGenerator idGenerator;
    static BufferedWriter writer;
    static BufferedReader reader;

    public Member( String name, String Email) throws IOException {
        if (Email.contains("@") && Email.contains(".")) {
            this.Email = Email;
        } else throw new IllegalArgumentException("Invalid Email");
        this.memberId = IDGenerator.generateMemberID(name, Email);
        this.name = name;
        this.booksBorrowed = new linkedlist();
        if (findExistingMemberID(name, Email) == null) {
            try {
                writer.write(this.memberId + "," + this.name + "," + this.Email + ",");
                writer.write(booksBorrowed.toStringForMember());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.flush();
        }
    }

    public Member() throws IOException{
    }

    public String getmemberId() {return memberId;}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return Email;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", Email='" + Email + '\'' +

                '}';
    }
    static {
        try {
            writer = new BufferedWriter(new FileWriter("Members.txt", false));
            reader = new BufferedReader(new FileReader("Members.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void add(Member member) throws IOException {
        if (member != null) {
            writer.write(member.getmemberId() + "," + member.getName() + "," + member.getEmail() + " , ");
            Member.displayBooksBorrowed();
            writer.write("\n");
            writer.flush();

        }
    }
    public static String display() throws IOException {
        String line = "";
        System.out.println("Member Details: ");
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 4) { // Ensure the array has all necessary parts
                String memberId = data[0].trim();
                String name = data[1].trim();
                String email = data[2].trim();
                String borrowedBooks = data[3].trim();

                System.out.println("Member ID: " + memberId);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Borrowed Books: " + borrowedBooks);
                System.out.println("----------");
            } else {
                System.out.println("Error: Invalid data format in the file.");
            }
        }
        return "End of Member List";
    }

    public static Member SearchMember(String memberID) throws IOException {
        if (memberID == null || memberID.isEmpty()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("Members.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && data[0].trim().equals(memberID)) {
                    String name = data[1].trim();
                    String email = data[2].trim();
                    return new Member(name, email);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return null;
    }
    public void addBook(Book book) {
        if (booksBorrowed == null) {
            booksBorrowed = new linkedlist();
        }
        booksBorrowed.insertAtBeginning(book);
    }

    public void remove(Book book){
        booksBorrowed.deleteById(book.getBookID());
    }
    public static void displayBooksBorrowed() {
        if (booksBorrowed == null || booksBorrowed.isEmpty()) {
            System.out.println("No books have been borrowed.");
        } else {
            System.out.println("Borrowed Books: " + booksBorrowed);
        }
    }

    private static String findExistingMemberID(String name, String email) {
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
