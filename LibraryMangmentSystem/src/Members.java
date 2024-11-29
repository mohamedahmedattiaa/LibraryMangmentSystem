import java.util.LinkedList;

public class Members {
    private static LinkedList<Member> members = new LinkedList<>();
    public static void add(Member member) {
        members.add(member);
    }
    public static void remove(Member member) {
        members.remove(member);
    }
    public static void display() {
        System.out.println("Members:"+" "+members);
    }
    public static Member SearchMember(String memberID){ // test
String Temp = memberID;
for(Member member: members) {
    if (Temp.toLowerCase().equals(member.getmemberId().toLowerCase())) {
        return member;
    }
    if (!Temp.toLowerCase().equals(member.getmemberId().toLowerCase())) {
        continue;
    }
    else {
        System.out.println("Member not found");
        return null;
    }
}
 return null;
    }
}