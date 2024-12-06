package Classes;

import java.util.LinkedList;
import java.io.*;


public class dataBaseMembers {
    private static LinkedList<Member> members = new LinkedList<>();
    static BufferedWriter writer;
    static BufferedReader reader;

    static {
        try {
            writer = new BufferedWriter(new FileWriter("text.txt", false));
            reader = new BufferedReader(new FileReader("text.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public dataBaseMembers() throws IOException {
    }

    public static void add(Member member) throws IOException {
        if (member != null) {
          writer.write(member.getmemberId() + "," + member.getName() + "," +member.getEmail() + "\n");
            writer.flush();

        }
    }

    public static void remove(Member member) {
        if (member == null) {

        }

    }

    public static String display() throws IOException {
        String line = "";
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());;
            }
            String[] data = line.split(",");
            if (data.length >= 3 ) {
                String name = data[1].trim();
                String email = data[2].trim();
                String memberId = data[0].trim();
                System.out.println(name + " " + email+" "+memberId);
            }

        }
         return "End OF Classes.Member List";
    }

    public static Member SearchMember(String memberID) throws IOException {
        if (memberID == null || memberID.isEmpty()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("text.txt"))) {
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
    // get name method get id ;

}
